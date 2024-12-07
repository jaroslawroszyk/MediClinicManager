package pl.clinicmanager.model;

import java.util.HashSet;
import java.util.Set;

public class Doctor {
    private int id;
    private PersonalInfo personalInfo;
    private Set<DoctorSpecialty> specialties;

    public Doctor(int id, PersonalInfo personalInfo, Set<DoctorSpecialty> specialties) {
        this.id = id;
        this.personalInfo = personalInfo;
        this.specialties = specialties;
    }

    public Doctor() {
        this.specialties = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
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