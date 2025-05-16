package dev.luanfernandes.domain.entity;

import java.time.Instant;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Setter
@DynamoDbBean
public class Animal {

    private String id;
    private String personId;
    private String name;
    private String species;
    private String photoKey;
    private Instant createdAt;
    private Instant updatedAt;

    @DynamoDbAttribute("id")
    @DynamoDbSecondaryPartitionKey(indexNames = "animalIdIndex")
    @DynamoDbSortKey
    public String getId() {
        return id;
    }

    @DynamoDbAttribute("personId")
    @DynamoDbPartitionKey
    public String getPersonId() {
        return personId; // ID do dono do animal
    }

    @DynamoDbAttribute("name")
    public String getName() {
        return name;
    }

    @DynamoDbAttribute("species")
    public String getSpecies() {
        return species;
    }

    @DynamoDbAttribute("photoKey")
    public String getPhotoKey() {
        return photoKey;
    }

    @DynamoDbAttribute("createdAt")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @DynamoDbAttribute("updatedAt")
    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
