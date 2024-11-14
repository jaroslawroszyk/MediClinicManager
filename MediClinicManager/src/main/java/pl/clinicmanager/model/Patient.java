package pl.clinicmanager.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Patient {
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

    public Patient() {

    }

    public Patient(String first, String last) { // for test purposes - should be deleted after implementation
        this.firstName = first;
        this.lastName = last;
    }

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
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        this.firstName = capitalize(firstName);
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        this.lastName = capitalize(lastName);
    }


    public void setPhoneNumber(String phoneNumber) {
        if (Validator.isValidPhoneNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.age = age;
    }

    public void setBirthDate(BirthDate birthDate) {
        this.birthDate = birthDate;
    }

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
    public void setEmail(@Nullable String email) {
        if (email == null || Validator.isValidEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }
    }


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
