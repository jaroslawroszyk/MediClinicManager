package pl.clinicmanager.model;

import java.util.HashSet;
import java.util.Set;
//import pl.clinicmanager.model.DoctorSpecialty;

public class Doctor {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private Set<DoctorSpecialty> specialties;

    public Doctor(int id, String firstName, String lastName, String phoneNumber, String email, String address, Set<DoctorSpecialty> specialties) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.specialties = specialties;
    }

    public Doctor() {
        this.specialties = new HashSet<>();
    }

    // Gettery i Settery
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<DoctorSpecialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<DoctorSpecialty> specialties) {
        this.specialties = specialties;
    }

    public void addSpecialty(DoctorSpecialty specialty) {
        specialties.add(specialty);
    }
}

/*
public record PersonalInfo(String peselNumber,
                           String firstName,
                           String lastName,
                           LocalDate dateOfBirth,
                           String phoneNumber,
                           String email) {}

 */
/*

TODO: Kolejne wymagane - czyli musze zrobic wspolna strukture zeby kod byl czytelny
TODO: + ta struktura moze korzystac z validatora (wszystko pieknie sie zgrywa xD)
Dane pacjentów potrzebne do świadczenia usług medycznych to:

imię,
nazwisko,
nr PESEL,
data urodzenia,
wiek,
telefon kontaktowy,
adres mailowy.

Dane lekarzy wymagane na potrzeby pracy placówki medycznej to:

wszystkie dane osobowe i kontaktowe (tak samo jak u pacjentów),
 */