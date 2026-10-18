package studying.presentation; 
 
import static org.junit.jupiter.api.Assertions.assertEquals; 
import static org.junit.jupiter.api.Assertions.assertTrue; 
 
import java.io.BufferedReader; 
import java.io.ByteArrayInputStream; 
import java.io.ByteArrayOutputStream; 
import java.io.InputStreamReader; 
import java.io.PrintStream; 
import java.nio.charset.StandardCharsets; 
import org.junit.jupiter.api.DisplayName; 
import org.junit.jupiter.api.Test; 
import studying.fleet.Fleet; 
import studying.inspection.InspectionResult; 
import studying.inspection.ServiceCenter; 
 
@SuppressWarnings("checkstyle:MagicNumber") 
@DisplayName("Тесты интерактивного консольного меню") 
class FleetConsoleAppTest { 
 
    private static ServiceCenter accepting() { 
        return item -> InspectionResult.ok(); 
    } 
 
    private static FleetConsoleApp app(final String input, 
                                       final Fleet fleet, 
                                       final ByteArrayOutputStream out) { 
        final var reader = new BufferedReader(new InputStreamReader( 
                new ByteArrayInputStream( 
                        input.getBytes(StandardCharsets.UTF_8)), 
                StandardCharsets.UTF_8)); 
        return new FleetConsoleApp(fleet, reader, 
                new PrintStream(out, true, StandardCharsets.UTF_8)); 
    } 
 
    @Test 
    @DisplayName("Добавляет самокат через меню и завершает работу") 
    void addsScooterAndExits() { 
        final var output = new ByteArrayOutputStream(); 
        final var fleet = new Fleet(accepting()); 
 
        app("1\nS-100\nTest\n8\n1.5\n0\n", fleet, output).run(); 
 
        assertTrue(output.toString(StandardCharsets.UTF_8) 
                .contains("Самокат добавлен")); 
        assertEquals(1, fleet.totalUnits()); 
    } 
 
    @Test 
    @DisplayName("Добавляет велосипед через меню") 
    void addsBicycle() { 
        final var output = new ByteArrayOutputStream(); 
        final var fleet = new Fleet(accepting()); 
 
        app("2\nB-100\nTestBike\n5\n0\n", fleet, output).run(); 
 
        assertTrue(output.toString(StandardCharsets.UTF_8) 
                .contains("Велосипед добавлен")); 
        assertEquals(1, fleet.totalUnits()); 
    } 
 
    @Test 
    @DisplayName("Добавляет электровелосипед через меню") 
    void addsElectricBicycle() { 
        final var output = new ByteArrayOutputStream(); 
        final var fleet = new Fleet(accepting()); 
 
        app("3\nE-100\nTestEBike\n7\n2.5\n0\n", fleet, output).run(); 
 
        assertTrue(output.toString(StandardCharsets.UTF_8) 
                .contains("Электровелосипед добавлен")); 
        assertEquals(1, fleet.totalUnits()); 
    } 
 
    @Test 
    @DisplayName("Добавляет шлем через меню") 
    void addsHelmet() { 
        final var output = new ByteArrayOutputStream(); 
        final var fleet = new Fleet(accepting()); 
 
        app("4\nH-100\nXL\n0\n", fleet, output).run(); 
 
        assertTrue(output.toString(StandardCharsets.UTF_8) 
                .contains("Шлем добавлен")); 
        assertEquals(1, fleet.totalUnits()); 
    } 
 
    @Test 
    @DisplayName("Добавляет док-станцию через меню") 
    void addsDockingStation() { 
        final var output = new ByteArrayOutputStream(); 
        final var fleet = new Fleet(accepting()); 
 
        app("5\nD-100\n0.5\n0\n", fleet, output).run(); 
 
        assertTrue(output.toString(StandardCharsets.UTF_8) 
                .contains("Док-станция добавлена")); 
        assertEquals(1, fleet.totalUnits()); 
    } 
 
    @Test 
    @DisplayName("Добавляет зарядный шкаф через меню") 
    void addsChargingCabinet() { 
        final var output = new ByteArrayOutputStream(); 
        final var fleet = new Fleet(accepting()); 
 
        app("6\nC-100\n3.0\n0\n", fleet, output).run(); 
 
        assertTrue(output.toString(StandardCharsets.UTF_8) 
                .contains("Зарядный шкаф добавлен")); 
        assertEquals(1, fleet.totalUnits()); 
    } 
 
    @Test 
    @DisplayName("Показывает отчёт по парку через меню") 
    void printsReport() { 
        final var output = new ByteArrayOutputStream(); 
        final var fleet = new Fleet(accepting()); 
 
        app("7\n0\n", fleet, output).run(); 
 
        assertTrue(output.toString(StandardCharsets.UTF_8) 
                .contains("=== Парк ВышКат ===")); 
    } 
 
    @Test 
    @DisplayName("Принимает запятую как десятичный разделитель") 
    void acceptsCommaAsDecimalSeparator() { 
        final var output = new ByteArrayOutputStream(); 
        final var fleet = new Fleet(accepting()); 
 
        app("1\nS-1\nTest\n8\n1,5\n0\n", fleet, output).run(); 
 
        assertTrue(output.toString(StandardCharsets.UTF_8) 
                .contains("Самокат добавлен")); 
    } 
 
    @Test 
    @DisplayName("Сообщает о неизвестном пункте меню") 
    void reportsUnknownMenuChoice() { 
        final var output = new ByteArrayOutputStream(); 
 
        app("99\n0\n", new Fleet(accepting()), output).run(); 
 
        assertTrue(output.toString(StandardCharsets.UTF_8) 
                .contains("Неизвестный пункт меню")); 
    } 
 
    @Test 
    @DisplayName("Показывает ошибку при некорректном инвентарном номере") 
    void reportsErrorOnInvalidInventoryNumber() { 
        final var output = new ByteArrayOutputStream(); 
        final var fleet = new Fleet(accepting()); 
 
        app("2\n \nTest\n5\n0\n", fleet, output).run(); 
 
        assertTrue(output.toString(StandardCharsets.UTF_8) 
                .contains("Ошибка")); 
        assertEquals(0, fleet.totalUnits()); 
    } 
 
    @Test 
    @DisplayName("Повторяет ввод, если введено не число") 
    void retriesOnInvalidNumber() { 
        final var output = new ByteArrayOutputStream(); 
        final var fleet = new Fleet(accepting()); 
 
        app("1\nS-1\nTest\nabc\n8\n1.5\n0\n", fleet, output).run(); 
 
        assertTrue(output.toString(StandardCharsets.UTF_8) 
                .contains("Введите целое число.")); 
        assertTrue(output.toString(StandardCharsets.UTF_8) 
                .contains("Самокат добавлен")); 
    } 
 
    @Test 
    @DisplayName("Завершает работу при исчерпании ввода") 
    void stopsOnEndOfInput() { 
        final var output = new ByteArrayOutputStream(); 
        final var fleet = new Fleet(accepting()); 
 
        app("", fleet, output).run(); 
 
        assertTrue(output.toString(StandardCharsets.UTF_8) 
                .contains("До свидания")); 
    } 
} 
