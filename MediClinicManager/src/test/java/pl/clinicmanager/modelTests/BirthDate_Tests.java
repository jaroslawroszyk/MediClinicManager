package pl.clinicmanager.modelTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.clinicmanager.model.BirthDate;
import pl.clinicmanager.model.LocalDateProvider;

import java.time.LocalDate;

public class BirthDate_Tests {

    @Test
    public void testValidBirthDate() {
        BirthDate birthDate = new BirthDate("1990-05-15");
        Assertions.assertEquals(birthDate.getFormattedDate(), "1990-05-15");
    }

    @Test
    public void testInvalidBirthDateFormatThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new BirthDate("15-05-1990");
        });
        Assertions.assertEquals(exception.getMessage(), "Invalid birth date format. Expected format: yyyy-MM-dd");
    }

    @Test
    public void testFutureBirthDateThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new BirthDate("2050-01-01");
        });
        Assertions.assertEquals(exception.getMessage(), "Birth date cannot be in the future");
    }

    @Test
    public void testCalculateAgeWithMockedDate() {
        LocalDateProvider dateProviderMock = Mockito.mock(LocalDateProvider.class);
        Mockito.when(dateProviderMock.getCurrentDate()).thenReturn(LocalDate.of(2026, 5, 15));

        BirthDate birthDate = new BirthDate("1990-05-15", dateProviderMock);

        int expectedAge = 36;
        int actualAge = birthDate.calculateAge();

        Assertions.assertEquals(expectedAge, actualAge);
    }

    @Test
    public void testValidLeapYearBirthDate() {
        BirthDate birthDate = new BirthDate("2000-02-29");
        Assertions.assertEquals(birthDate.getFormattedDate(), "2000-02-29");
    }
}
