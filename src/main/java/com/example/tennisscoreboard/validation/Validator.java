package com.example.tennisscoreboard.validation;

public class Validator {
    private static final String UUID_REGEX =
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";

    public static boolean isValidUUID(String uuid) {
        return uuid != null && uuid.matches(UUID_REGEX);
    }
}
