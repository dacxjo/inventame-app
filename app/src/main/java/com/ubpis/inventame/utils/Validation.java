package com.ubpis.inventame.utils;

import android.util.Patterns;

public class Validation {

    public Validation() {
    }

    public boolean exists(String value) {
        return value != null && !value.isEmpty();

    }

    public boolean isEmail(String value) {
        return !Patterns.EMAIL_ADDRESS.matcher(value).matches();
    }

    public boolean matches(String value, String value2) {
        return value.equals(value2);
    }

    public boolean minLength(String value, int length) {
        return value.length() < length;
    }

    public boolean validateDNI(String dni) {
        dni = dni.trim().toUpperCase();

        if (dni.length() != 9) {
            return false;
        }

        for (int i = 0; i < 8; i++) {
            if (!Character.isDigit(dni.charAt(i))) {
                return false;
            }
        }

        char lastChar = dni.charAt(8);
        if (!Character.isLetter(lastChar)) {
            return false;
        }

        int dniNumber = Integer.parseInt(dni.substring(0, 8));
        int remainder = dniNumber % 23;
        char[] letters = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
        char expectedLetter = letters[remainder];
        return lastChar == expectedLetter;
    }

    public boolean validateNIE(String nie) {
        nie = nie.trim().toUpperCase();
        if (nie.length() != 9) {
            return false;
        }
        char firstLetter = nie.charAt(0);
        if (firstLetter != 'X' && firstLetter != 'Y' && firstLetter != 'Z') {
            return false;
        }
        int firstLetterValue;
        switch (firstLetter) {
            case 'X':
                firstLetterValue = 0;
                break;
            case 'Y':
                firstLetterValue = 1;
                break;
            case 'Z':
                firstLetterValue = 2;
                break;
            default:
                return false;
        }
        char lastChar = nie.charAt(8);
        if (!Character.isLetter(lastChar)) {
            return false;
        }
        for (int i = 1; i < 8; i++) {
            if (!Character.isDigit(nie.charAt(i))) {
                return false;
            }
        }
        int dniNumber = Integer.parseInt(firstLetterValue + nie.substring(1, 8));
        int remainder = dniNumber % 23;
        char[] letters = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
        char expectedLetter = letters[remainder];
        return lastChar == expectedLetter;
    }

    public boolean validDocumentNumber(String id) {
        return validateDNI(id) || validateNIE(id);
    }

}
