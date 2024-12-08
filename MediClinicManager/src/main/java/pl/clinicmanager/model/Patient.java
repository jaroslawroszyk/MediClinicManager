package pl.clinicmanager.model;

import org.jetbrains.annotations.NotNull;

public class Patient {
    private int id;
    private PersonalInfo personalInfo;
    @NotNull
    private String pesel;
    private BirthDate birthDate;
    private int age;

    public Patient() {
    }

    public Patient(int id, PersonalInfo personalInfo, String pesel, BirthDate birthDate, int age) {
        this.id = id;
        this.personalInfo = personalInfo;
        setPesel(pesel);
        this.birthDate = birthDate;
        setAge(age);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        if (Validator.isValidPesel(pesel)) {
            this.pesel = pesel;
        } else {
            throw new IllegalArgumentException("Invalid PESEL format");
        }
    }

    public BirthDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(BirthDate birthDate) {
        this.birthDate = birthDate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.age = age;
    }
}
