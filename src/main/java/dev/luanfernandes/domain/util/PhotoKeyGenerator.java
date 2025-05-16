package dev.luanfernandes.domain.util;

import java.util.UUID;

public class PhotoKeyGenerator {
    public static String generate(String type, String id, String extension) {
        return String.format("photos/%s/%s/%s.%s", type, id, UUID.randomUUID(), extension);
    }
}
