package pl.clinicmanager.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a patient in the clinic management system.
 * This class encapsulates patient details such as personal information, contact details,
 * and methods for validation and formatting.
 */
public class Patient {
    //TODO: extract patient info like first/last, dateOfBirh,phone,email, pesel to external class
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String pesel;
    private BirthDate birthDate;
    private int age;
    private String phoneNumber;
    @Nullable
    private String email; // TODO: that field can be optional

    /**
     * Default constructor. Required for certain serialization and deserialization frameworks.
     */
    public Patient() {

    }

    /**
     * Simplified constructor for testing purposes.
     * Should be removed after implementation is complete.
     *
     * @param first the first name of the patient
     * @param last  the last name of the patient
     */
    public Patient(String first, String last) { // for test purposes - should be deleted after implementation
        this.firstName = first;
        this.lastName = last;
    }

    /**
     * Full constructor for creating a complete patient object.
     * Validates and assigns the input parameters to respective fields.
     *
     * @param first       the first name of the patient
     * @param last        the last name of the patient
     * @param pesel       the PESEL of the patient
     * @param birthDate   the BirthDate object representing the patient's birth date
     * @param age         the age of the patient
     * @param phoneNumber the patient's phone number
     * @param email       the patient's email (optional, can be null)
     * @throws IllegalArgumentException if any of the fields are invalid
     */
    public Patient(String first, String last, String pesel, BirthDate birthDate, int age, String phoneNumber, @Nullable String email) {
        setFirstName(first);
        setLastName(last);
        setPesel(pesel);
        setBirthDate(birthDate);
        setAge(age);
        setPhoneNumber(phoneNumber);
        setEmail(email);
    }

    //    public void setFirstName(String firstName) {
//        if (firstName != null && !firstName.isEmpty()) {
//            this.firstName = capitalize(firstName);
//        } else {
//            this.firstName = firstName;
//        }
//    }
//
//    public void setLastName(String lastName) {
//        if (lastName != null && !lastName.isEmpty()) {
//            this.lastName = capitalize(lastName);
//        } else {
//            this.lastName = lastName;
//        }
//    }

    /**
     * Sets the first name of the patient.
     * The name must not be null or empty.
     *
     * @param firstName the first name to set
     * @throws IllegalArgumentException if the name is null or empty
     */
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        this.firstName = capitalize(firstName);
    }

    /**
     * Sets the last name of the patient.
     * The name must not be null or empty.
     *
     * @param lastName the last name to set
     * @throws IllegalArgumentException if the name is null or empty
     */
    public void setLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        this.lastName = capitalize(lastName);
    }

    /**
     * Sets the phone number of the patient.
     * Validates the format before assigning.
     *
     * @param phoneNumber the phone number to set
     * @throws IllegalArgumentException if the phone number format is invalid
     */
    public void setPhoneNumber(String phoneNumber) {
        if (Validator.isValidPhoneNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }

    /**
     * Sets the age of the patient.
     * Age must not be negative.
     *
     * @param age the age to set
     * @throws IllegalArgumentException if the age is negative
     */
    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.age = age;
    }

    /**
     * Sets the birth date of the patient.
     *
     * @param birthDate the BirthDate object to set
     */
    public void setBirthDate(BirthDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Sets the PESEL of the patient.
     * Validates the PESEL format before assigning.
     *
     * @param pesel the PESEL to set
     * @throws IllegalArgumentException if the PESEL format is invalid
     */
    public void setPesel(String pesel) {
        if (Validator.isValidPesel(pesel)) {
            this.pesel = pesel;
        } else {
            throw new IllegalArgumentException("Invalid PESEL format");
        }
    }

    //    public void setEmail(String email) {
//        if (Validator.isValidEmail(email)) {
//            this.email = email;
//        } else {
//            throw new IllegalArgumentException("Invalid email format");
//        }
//    }

    /**
     * Sets the email of the patient.
     * Validates the format if the email is not null.
     *
     * @param email the email to set, or null if not provided
     * @throws IllegalArgumentException if the email format is invalid
     */
    public void setEmail(@Nullable String email) {
        if (email == null || Validator.isValidEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    /**
     * Getters for this class
     */
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public String getBirthDate() {
        return birthDate.getFormattedDate();
    }

    public int getAge() {
        return age;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    private String capitalize(String name) {
        if (name.isEmpty()) {
            return name;
        }
        return Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase();
    }

//    private void validateNonEmptyString(String value, String fieldName) {
//        if (value == null || value.isEmpty()) {
//            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
//        }
//    }
}
