package pl.clinicmanager.model;

import java.util.regex.Pattern;

/**
 * Utility class for validating common data formats in the clinic management system.
 * Provides methods to validate PESEL, email, and phone number formats.
 */
public class Validator {
    // Regex patterns for email, phone number and PESEL validation.
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    );
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^\\+?[0-9]{9,15}$"
    );
    private static final Pattern PESEL_PATTERN = Pattern.compile("^[0-9]{11}$");

    /**
     * Validates whether the given PESEL is valid.
     * Checks the format (11 numeric digits) and verifies the checksum.
     *
     * @param pesel the PESEL string to validate
     * @return true if the PESEL is valid, false otherwise
     */
    public static boolean isValidPesel(String pesel) {
        if (pesel == null || !PESEL_PATTERN.matcher(pesel).matches()) {
            return false;
        }

        return validatePeselChecksum(pesel);
    }

    /**
     * Validates whether the given email is in a proper format.
     *
     * @param email the email string to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validates whether the given phone number is in a proper format.
     * Allows an optional "+" at the start and digits between 9 and 15 characters.
     *
     * @param phoneNumber the phone number string to validate
     * @return true if the phone number is valid, false otherwise
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    /**
     * Validates the checksum of a PESEL number.
     * The checksum is calculated using predefined weights for the first 10 digits
     * and comparing it to the last (11th) digit.
     *
     * @param pesel the PESEL string to validate
     * @return true if the checksum is valid, false otherwise
     */
    private static boolean validatePeselChecksum(String pesel) {
        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
        int sum = 0;

        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(pesel.charAt(i)) * weights[i];
        }

        int checksumDigit = (10 - (sum % 10)) % 10;
        return checksumDigit == Character.getNumericValue(pesel.charAt(10));
    }
}
