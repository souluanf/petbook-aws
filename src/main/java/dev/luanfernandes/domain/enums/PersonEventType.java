package dev.luanfernandes.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PersonEventType {
    ACCOUNT_CREATED("Account Successfully Created"),
    ACCOUNT_UPDATED("Account Updated"),
    PHOTO_UPLOADED("Profile Photo Uploaded"),
    ACCOUNT_DELETED("Account Deleted");
    private final String subject;
}
