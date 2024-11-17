package pl.clinicmanager.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.Period;
import pl.clinicmanager.model.LocalDateProvider;

/**
 * Represents a birthdate with validation and age calculation.
 */
public class BirthDate {
    private final LocalDate birthDate;
    private final LocalDateProvider dateProvider;

    /**
     * Constructs a BirthDate instance using the provided birthdate string.
     * Defaults to using the current system date for age calculations.
     * @param birthDateString the birthdate as a string in the format "yyyy-MM-dd"
     * @throws IllegalArgumentException if the date format is invalid or the date is in the future
     */
    public BirthDate(String birthDateString) {
        this(birthDateString, LocalDate::now);
    }
    /**
     * Constructs a BirthDate instance using the provided birthdate string and a custom date provider.
     * @param birthDateString the birthdate as a string in the format "yyyy-MM-dd"
     * @param dateProvider the provider for the current date
     * @throws IllegalArgumentException if the date format is invalid or the date is in the future
     */
    public BirthDate(String birthDateString, LocalDateProvider dateProvider) {
        this.birthDate = validateAndParse(birthDateString);
        this.dateProvider = dateProvider;
    }
    /**
     * Validates and parses the birthdate string.
     * Ensures the date is in the correct format, is not in the future, and accounts for leap year.
     * @param birthDateString the birthdate as a string
     * @return the parsed LocalDate object
     * @throws IllegalArgumentException if the date is invalid or improperly formatted
     */
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
    /**
     * Calculates the age based on the birth date and the current date provided by the date provider.
     * @return the calculated age in years
     */
    public int calculateAge() {
        return Period.between(this.birthDate, dateProvider.getCurrentDate()).getYears();
    }
    /**
     * Returns the birth date formatted as a string in ISO-8601 format (yyyy-MM-dd).
     * @return the formatted birth date string
     */
    public String getFormattedDate() {
        return this.birthDate.toString();
    }
}
