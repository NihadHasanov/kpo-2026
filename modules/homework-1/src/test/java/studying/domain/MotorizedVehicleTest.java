package studying.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import studying.exception.ApplicationException;

@SuppressWarnings("checkstyle:MagicNumber")
@DisplayName("Unit-тесты энергопотребления")
class MotorizedVehicleTest {

    @Test
    @DisplayName("Суточный расход сохраняется")
    void storesDailyEnergy() {
        final var scooter = new Scooter("S-1", "Test", 8, 2.5);
        assertEquals(2.5, scooter.dailyEnergyKwh());
    }

    @Test
    @DisplayName("Нулевой или отрицательный расход отклоняется")
    void rejectsNonPositiveEnergy() {
        assertThrows(ApplicationException.class,
                () -> new Scooter("S-1", "Test", 8, 0.0));
        assertThrows(ApplicationException.class,
                () -> new Scooter("S-1", "Test", 8, -1.0));
    }
}
