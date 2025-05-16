package dev.luanfernandes.domain.entity;

import java.time.Instant;
import lombok.Setter;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

@Setter
@ToString
@DynamoDbBean
public class Person {

    private String id;
    private String name;
    private String email;
    private String phone;
    private String photoKey;
    private String addressState;
    private String addressComplement;
    private String addressStreet;
    private String addressBuildingNumber;
    private String addressNeighborhood;
    private String addressCity;
    private String addressIbgeCode;
    private String addressZipCode;
    private Instant createdAt;
    private Instant updatedAt;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    @DynamoDbAttribute("name")
    public String getName() {
        return name;
    }

    @DynamoDbAttribute("photoKey")
    public String getPhotoKey() {
        return photoKey;
    }

    @DynamoDbAttribute("email")
    @DynamoDbSecondaryPartitionKey(indexNames = "emailIndex")
    public String getEmail() {
        return email;
    }

    @DynamoDbAttribute("phone")
    public String getPhone() {
        return phone;
    }

    @DynamoDbAttribute("addressState")
    public String getAddressState() {
        return addressState;
    }

    @DynamoDbAttribute("addressComplement")
    public String getAddressComplement() {
        return addressComplement;
    }

    @DynamoDbAttribute("addressStreet")
    public String getAddressStreet() {
        return addressStreet;
    }

    @DynamoDbAttribute("addressBuildingNumber")
    public String getAddressBuildingNumber() {
        return addressBuildingNumber;
    }

    @DynamoDbAttribute("addressNeighborhood")
    public String getAddressNeighborhood() {
        return addressNeighborhood;
    }

    @DynamoDbAttribute("addressCity")
    public String getAddressCity() {
        return addressCity;
    }

    @DynamoDbAttribute("addressIbgeCode")
    public String getAddressIbgeCode() {
        return addressIbgeCode;
    }

    @DynamoDbAttribute("addressZipCode")
    public String getAddressZipCode() {
        return addressZipCode;
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
