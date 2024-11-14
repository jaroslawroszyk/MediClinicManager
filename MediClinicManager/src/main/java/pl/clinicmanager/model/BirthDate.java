package pl.clinicmanager.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.Period;
import pl.clinicmanager.model.LocalDateProvider;

public class BirthDate {
    private final LocalDate birthDate;
    private final LocalDateProvider dateProvider;

    public BirthDate(String birthDateString) {
        this(birthDateString, LocalDate::now);
    }

    public BirthDate(String birthDateString, LocalDateProvider dateProvider) {
        this.birthDate = validateAndParse(birthDateString);
        this.dateProvider = dateProvider;
    }

    private LocalDate validateAndParse(String birthDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate parsedDate = LocalDate.parse(birthDateString, formatter);
            if (parsedDate.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Birth date cannot be in the future");
            }
            if (parsedDate.getMonthValue() == 2 && parsedDate.getDayOfMonth() == 29 && !parsedDate.isLeapYear()) {
                throw new IllegalArgumentException("Invalid birth date: " + birthDateString + " (not a leap year)");
            }
            return parsedDate;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid birth date format. Expected format: yyyy-MM-dd", e);
        }
    }

    public int calculateAge() {
        return Period.between(this.birthDate, dateProvider.getCurrentDate()).getYears();
    }

    public String getFormattedDate() {
        return this.birthDate.toString();
    }
}
