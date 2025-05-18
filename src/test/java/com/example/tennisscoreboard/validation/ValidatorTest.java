package com.example.tennisscoreboard.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class ValidatorTest {
    @Test
    public void checkValidUUID() {
         UUID uuid = UUID.randomUUID();
         Assertions.assertTrue(Validator.isValidUUID(uuid.toString()));
    }

    @Test
    public void checkNotValidUUID() {
        String notValidUUID = "dsfsdf-fdfsdf-fdsfasd-213";
        Assertions.assertFalse(Validator.isValidUUID(notValidUUID));
    }
}
