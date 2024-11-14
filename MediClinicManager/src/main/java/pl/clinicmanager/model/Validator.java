package pl.clinicmanager.model;

import java.util.regex.Pattern;

public class Validator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    );

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^\\+?[0-9]{9,15}$"
    );

    private static final Pattern PESEL_PATTERN = Pattern.compile("^[0-9]{11}$");

    public static boolean isValidPesel(String pesel) {
        if (pesel == null || !PESEL_PATTERN.matcher(pesel).matches()) {
            return false;
        }

        return validatePeselChecksum(pesel);
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && PHONE_PATTERN.matcher(phoneNumber).matches();
    }
    
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
