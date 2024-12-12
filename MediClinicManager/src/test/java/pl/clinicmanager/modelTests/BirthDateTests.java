package pl.clinicmanager.modelTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.clinicmanager.model.BirthDate;
import pl.clinicmanager.model.LocalDateProvider;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class BirthDateTests {

    @Test
    public void testValidBirthDate() {
        BirthDate birthDate = new BirthDate("1990-05-15");
        assertEquals("1990-05-15", birthDate.getFormattedDate());
    }

    @Test
    public void testInvalidBirthDateFormatThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new BirthDate("15-05-1990");
        });
        assertEquals("Invalid birth date format. Expected format: yyyy-MM-dd", exception.getMessage());
    }

    @Test
    public void testFutureBirthDateThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new BirthDate("2050-01-01");
        });
        assertEquals("Birth date cannot be in the future", exception.getMessage());
    }

    @Test
    public void testCalculateAgeWithMockedDate() {
        LocalDateProvider dateProviderMock = Mockito.mock(LocalDateProvider.class);
        Mockito.when(dateProviderMock.getCurrentDate()).thenReturn(LocalDate.of(2026, 5, 15));

        BirthDate birthDate = new BirthDate("1990-05-15", dateProviderMock);

        int expectedAge = 36;
        int actualAge = birthDate.calculateAge();

        assertEquals(expectedAge, actualAge);
    }

    @Test
    public void testValidLeapYearBirthDate() {
        BirthDate birthDate = new BirthDate("2000-02-29");
        assertEquals("2000-02-29", birthDate.getFormattedDate());
    }
}
