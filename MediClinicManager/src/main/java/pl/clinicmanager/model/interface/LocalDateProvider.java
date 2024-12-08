package pl.clinicmanager.model;

import java.time.LocalDate;

@FunctionalInterface
public interface LocalDateProvider {

    LocalDate getCurrentDate();
}
