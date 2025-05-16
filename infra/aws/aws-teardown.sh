#!/bin/bash

set -e
export AWS_PAGER=""

AWS_ENDPOINT="http://localhost:4566"
AWS_REGION="sa-east-1"
DYNAMO_PERSON_TABLE="petbook_person"
DYNAMO_ANIMAL_TABLE="petbook_animal"
S3_BUCKET_NAME="petbook-fotos"
SNS_TOPIC_NAME="novo-usuario-criado"
SQS_QUEUE_NAME="fila-email-criado"

echo "🗑️ Excluindo tabela DynamoDB: $DYNAMO_PERSON_TABLE"
aws --endpoint-url="$AWS_ENDPOINT" --region "$AWS_REGION" dynamodb delete-table --table-name "$DYNAMO_PERSON_TABLE"

echo "🗑️ Excluindo tabela DynamoDB: $DYNAMO_ANIMAL_TABLE"
aws --endpoint-url="$AWS_ENDPOINT" --region "$AWS_REGION" dynamodb delete-table --table-name "$DYNAMO_ANIMAL_TABLE"

echo "🗑️ Esvaziando bucket S3: $S3_BUCKET_NAME"
aws --endpoint-url="$AWS_ENDPOINT" --region "$AWS_REGION" s3 rm "s3://$S3_BUCKET_NAME" --recursive || true

echo "🗑️ Excluindo bucket S3: $S3_BUCKET_NAME"
aws --endpoint-url="$AWS_ENDPOINT" --region "$AWS_REGION" s3api delete-bucket --bucket "$S3_BUCKET_NAME"

echo "🗑️ Excluindo fila SQS: $SQS_QUEUE_NAME"
QUEUE_URL=$(aws --endpoint-url="$AWS_ENDPOINT" --region "$AWS_REGION" sqs get-queue-url --queue-name "$SQS_QUEUE_NAME" --query 'QueueUrl' --output text)
aws --endpoint-url="$AWS_ENDPOINT" --region "$AWS_REGION" sqs delete-queue --queue-url "$QUEUE_URL"

echo "🗑️ Excluindo tópico SNS: $SNS_TOPIC_NAME"
TOPIC_ARN=$(aws --endpoint-url="$AWS_ENDPOINT" --region "$AWS_REGION" sns list-topics --query "Topics[?contains(TopicArn, '$SNS_TOPIC_NAME')].TopicArn" --output text)
aws --endpoint-url="$AWS_ENDPOINT" --region "$AWS_REGION" sns delete-topic --topic-arn "$TOPIC_ARN"

echo "✅ Recursos removidos com sucesso!"