package studying.domain; 
 
import static org.junit.jupiter.api.Assertions.assertFalse; 
import static org.junit.jupiter.api.Assertions.assertThrows; 
import static org.junit.jupiter.api.Assertions.assertTrue; 
 
import org.junit.jupiter.api.DisplayName; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.params.ParameterizedTest; 
import org.junit.jupiter.params.provider.ValueSource; 
import studying.exception.ApplicationException; 
 
@SuppressWarnings("checkstyle:MagicNumber") 
@DisplayName("Unit-тесты правила для новичков") 
class VehicleTest { 
 
    @ParameterizedTest 
    @ValueSource(ints = {6, 7, 8, 9, 10}) 
    @DisplayName("Транспорт со сложностью >= 6 доступен новичкам") 
    void suitableForNoviceWhenSimplicityAtLeastSix(final int value) { 
        final var bicycle = new Bicycle("B-1", "Test", value); 
        assertTrue(bicycle.isSuitableForNovice()); 
    } 
 
    @ParameterizedTest 
    @ValueSource(ints = {1, 2, 3, 4, 5}) 
    @DisplayName("Транспорт со сложностью < 6 недоступен новичкам") 
    void notSuitableForNoviceBelowSix(final int value) { 
        final var bicycle = new Bicycle("B-1", "Test", value); 
        assertFalse(bicycle.isSuitableForNovice()); 
    } 
 
    @Test 
    @DisplayName("Инвентарный номер обязателен") 
    void rejectsBlankInventoryNumber() { 
        assertThrows(ApplicationException.class, 
                () -> new Bicycle(" ", "Test", 5)); 
    } 
 
    @Test 
    @DisplayName("Сложность вне диапазона 1..10 отклоняется") 
    void rejectsSimplicityOutOfRange() { 
        assertThrows(ApplicationException.class, 
                () -> new Bicycle("B-1", "Test", 0)); 
        assertThrows(ApplicationException.class, 
                () -> new Bicycle("B-1", "Test", 11)); 
    } 
} 
