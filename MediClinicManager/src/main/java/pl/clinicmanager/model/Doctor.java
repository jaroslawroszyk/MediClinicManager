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

    public void printPersonalInfo() {
        System.out.println("id: " + id);
        System.out.println("Name: " + personalInfo.getFirstName());
        System.out.println("Surname: " + personalInfo.getLastName());
        System.out.println("phoneNumber: " + personalInfo.getPhoneNumber());
        System.out.println("email: " + personalInfo.getEmail());
        System.out.println("address: " + personalInfo.getAddress());
        System.out.println("specialties: " + specialties);
    }
}
