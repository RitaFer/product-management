package com.rita.product_management.core.common.util;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class RandomPasswordGeneratorTest {

    private static final Pattern UPPERCASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile("[a-z]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d");
    private static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile("[!@#$%^&*()\\-_=<>?/|~]");
    private static final int EXPECTED_LENGTH = 10;

    @Test
    void givenValidInputsThenGeneratePasswordWithCorrectLength() {
        String password = RandomPasswordGenerator.generatePassword();
        assertNotNull(password);
        assertEquals(EXPECTED_LENGTH, password.length());
    }

    @Test
    void givenValidInputsThenPasswordContainsUppercase() {
        String password = RandomPasswordGenerator.generatePassword();
        assertTrue(UPPERCASE_PATTERN.matcher(password).find());
    }

    @Test
    void givenValidInputsThenPasswordContainsLowercase() {
        String password = RandomPasswordGenerator.generatePassword();
        assertTrue(LOWERCASE_PATTERN.matcher(password).find());
    }

    @Test
    void givenValidInputsThenPasswordContainsDigit() {
        String password = RandomPasswordGenerator.generatePassword();
        assertTrue(DIGIT_PATTERN.matcher(password).find());
    }

    @Test
    void givenValidInputsThenPasswordContainsSpecialCharacter() {
        String password = RandomPasswordGenerator.generatePassword();
        assertTrue(SPECIAL_CHARACTER_PATTERN.matcher(password).find());
    }

    @Test
    void givenValidInputsThenPasswordCharactersAreShuffled() {
        String password = RandomPasswordGenerator.generatePassword();
        assertNotEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", password);
    }
}
