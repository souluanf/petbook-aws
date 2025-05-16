#!/bin/bash

set -e
export AWS_PAGER=""

# 🔧 Variáveis de configuração
AWS_ENDPOINT="http://localstack:4566"
AWS_REGION="sa-east-1"

DYNAMO_PERSON_TABLE="petbook_person"
DYNAMO_ANIMAL_TABLE="petbook_animal"
S3_BUCKET_NAME="petbook-fotos"
SNS_TOPIC_NAME="novo-usuario-criado"
SQS_QUEUE_NAME="fila-email-criado"

# 🪣 Criar bucket S3
echo "⏳ Criando bucket S3: $S3_BUCKET_NAME..."
aws --endpoint-url="$AWS_ENDPOINT" --region "$AWS_REGION" s3api create-bucket \
  --bucket "$S3_BUCKET_NAME" \
  --create-bucket-configuration LocationConstraint="$AWS_REGION"
echo "✅ Bucket S3 criado."

# 📦 Criar tabelas DynamoDB
echo "⏳ Criando tabela DynamoDB: $DYNAMO_PERSON_TABLE..."
aws --endpoint-url="$AWS_ENDPOINT" --region "$AWS_REGION" dynamodb create-table \
  --table-name "$DYNAMO_PERSON_TABLE" \
  --attribute-definitions AttributeName=id,AttributeType=S AttributeName=email,AttributeType=S \
  --key-schema AttributeName=id,KeyType=HASH \
  --global-secondary-indexes '[
    {
      "IndexName": "emailIndex",
      "KeySchema": [
        { "AttributeName": "email", "KeyType": "HASH" }
      ],
      "Projection": { "ProjectionType": "ALL" }
    }
  ]' \
  --billing-mode PAY_PER_REQUEST

echo "⏳ Criando tabela DynamoDB: $DYNAMO_ANIMAL_TABLE..."
aws --endpoint-url="$AWS_ENDPOINT" --region "$AWS_REGION" dynamodb create-table \
  --table-name "$DYNAMO_ANIMAL_TABLE" \
  --attribute-definitions AttributeName=personId,AttributeType=S AttributeName=id,AttributeType=S \
  --key-schema AttributeName=personId,KeyType=HASH AttributeName=id,KeyType=RANGE \
  --global-secondary-indexes '[
    {
      "IndexName": "animalIdIndex",
      "KeySchema": [
        { "AttributeName": "id", "KeyType": "HASH" }
      ],
      "Projection": { "ProjectionType": "ALL" }
    }
  ]' \
  --billing-mode PAY_PER_REQUEST
echo "✅ Tabelas DynamoDB criadas."

# 📣 Criar tópico SNS e fila SQS
echo "⏳ Criando tópico SNS: $SNS_TOPIC_NAME..."
TOPIC_ARN=$(aws --endpoint-url="$AWS_ENDPOINT" --region "$AWS_REGION" sns create-topic \
  --name "$SNS_TOPIC_NAME" \
  --query 'TopicArn' --output text)

echo "⏳ Criando fila SQS: $SQS_QUEUE_NAME..."
QUEUE_URL=$(aws --endpoint-url="$AWS_ENDPOINT" --region "$AWS_REGION" sqs create-queue \
  --queue-name "$SQS_QUEUE_NAME" \
  --query 'QueueUrl' --output text)

QUEUE_ARN=$(aws --endpoint-url="$AWS_ENDPOINT" --region "$AWS_REGION" sqs get-queue-attributes \
  --queue-url "$QUEUE_URL" \
  --attribute-name QueueArn \
  --query 'Attributes.QueueArn' --output text)

# 🔒 Aplicar política de acesso SNS → SQS
echo "⏳ Aplicando política de permissão SNS → SQS..."
POLICY=$(jq -n \
  --arg arn "$QUEUE_ARN" \
  --arg topic "$TOPIC_ARN" \
  '{Version:"2012-10-17",
    Statement:[{
      Effect:"Allow",
      Principal:{Service:"sns.amazonaws.com"},
      Action:"sqs:SendMessage",
      Resource:$arn,
      Condition:{ArnEquals:{"aws:SourceArn":$topic}}
    }]
  }' | jq -c .)

cat > attrs.json <<EOF
{
  "QueueUrl": "$QUEUE_URL",
  "Attributes": {
    "Policy": $(jq -Rs . <<<"$POLICY")
  }
}
EOF

aws --endpoint-url="$AWS_ENDPOINT" \
    --region="$AWS_REGION" \
    sqs set-queue-attributes \
    --cli-input-json file://attrs.json

rm -f attrs.json

# 🔗 Assinar a fila SQS no tópico SNS
echo "⏳ Subscribing SQS à SNS..."
aws --endpoint-url="$AWS_ENDPOINT" --region "$AWS_REGION" sns subscribe \
  --topic-arn "$TOPIC_ARN" \
  --protocol sqs \
  --notification-endpoint "$QUEUE_ARN"

# 🔐 Criar segredo no Secrets Manager
SECRET_NAME="/petbook/config/external-apis"
SECRET_STRING='{
  "external.apis.opencep.url": "https://opencep.com",
  "external.apis.opencep.route": "/v1/{cep}"
}'


echo "⏳ Criando segredo no Secrets Manager: $SECRET_NAME..."
aws --endpoint-url="$AWS_ENDPOINT" --region "$AWS_REGION" secretsmanager create-secret \
  --name "$SECRET_NAME" \
  --secret-string "$SECRET_STRING" \
  || {
    echo "ℹ️  Segredo já existe. Atualizando valor..."
    aws --endpoint-url="$AWS_ENDPOINT" --region "$AWS_REGION" secretsmanager put-secret-value \
      --secret-id "$SECRET_NAME" \
      --secret-string "$SECRET_STRING"
  }

# ✅ Finalização
echo ""
echo "✅ Recursos criados com sucesso!"
echo "🔗 S3 BUCKET:        $S3_BUCKET_NAME"
echo "🔗 DYNAMO TABLES:    $DYNAMO_PERSON_TABLE, $DYNAMO_ANIMAL_TABLE"
echo "🔗 SNS TOPIC ARN:    $TOPIC_ARN"
echo "🔗 SQS QUEUE URL:    $QUEUE_URL"
echo "🔗 SQS QUEUE ARN:    $QUEUE_ARN"
echo "🔗 Segredo configurado: $SECRET_NAME"
