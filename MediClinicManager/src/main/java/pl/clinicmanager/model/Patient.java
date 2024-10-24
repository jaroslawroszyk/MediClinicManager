package pl.clinicmanager.model;

public class Patient {

    private String firstName;
    private String lastName;
    private String pesel;
    private BirthDate birthDate;
    private int age;
    private String phoneNumber;
    private String email; // TODO: that field can be optional

    public Patient(String first, String last) { // for test purposes - should be deleted later
        this.firstName = first;
        this.lastName = last;
    }

    public Patient(String first, String last, String pesel, BirthDate birthDate, int age, String phoneNumber, String email) {
        setFirstName(first);
        setLastName(last);
        setPesel(pesel);
        setBirthDate(birthDate);
        setAge(age);
        setPhoneNumber(phoneNumber);
        setEmail(email);
    }

    public void setFirstName(String firstName) {
        if (firstName != null && !firstName.isEmpty()) {
            this.firstName = capitalize(firstName);
        } else {
            this.firstName = firstName;
        }
    }

    public void setLastName(String lastName) {
        if (lastName != null && !lastName.isEmpty()) {
            this.lastName = capitalize(lastName);
        } else {
            this.lastName = lastName;
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        if (Validator.isValidPhoneNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }

    public void setAge(int age) {
        if (age < 0)
        {
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

    public void setEmail(String email) {
        if (Validator.isValidEmail(email)) {
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
