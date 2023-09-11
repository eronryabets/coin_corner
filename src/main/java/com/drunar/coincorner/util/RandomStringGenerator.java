package com.drunar.coincorner.util;

import java.security.SecureRandom;


public class RandomStringGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomString(int length) {
        StringBuilder randomString = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            randomString.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return randomString.toString();
    }
}
