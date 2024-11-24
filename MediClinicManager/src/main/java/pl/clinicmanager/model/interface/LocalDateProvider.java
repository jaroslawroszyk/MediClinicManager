package pl.clinicmanager.model;

import java.time.LocalDate;

/**
 * Functional interface for providing the current date.
 * Can be implemented to supply different date strategies (e.g., for testing or production).
 */
@FunctionalInterface
public interface LocalDateProvider {
    /**
     * Retrieves the current date.
     *
     * @return the current LocalDate
     */
    LocalDate getCurrentDate();
}
