package studying.presentation; 
 
import static org.junit.jupiter.api.Assertions.assertTrue; 
 
import java.io.ByteArrayOutputStream; 
import java.io.PrintStream; 
import java.nio.charset.StandardCharsets; 
import org.junit.jupiter.api.DisplayName; 
import org.junit.jupiter.api.Test; 
import studying.domain.Bicycle; 
import studying.domain.Helmet; 
import studying.domain.Scooter; 
import studying.fleet.Fleet; 
import studying.inspection.InspectionResult; 
import studying.inspection.ServiceCenter; 
 
@SuppressWarnings("checkstyle:MagicNumber") 
@DisplayName("Unit-тесты печати отчёта по парку") 
class FleetReporterTest { 
 
    private static ServiceCenter accepting() { 
        return item -> InspectionResult.ok(); 
    } 
 
    @Test 
    @DisplayName("Печатает отчёт по непустому парку") 
    void printsNonEmptyFleetReport() { 
        final var fleet = new Fleet(accepting()); 
        fleet.addVehicle(new Scooter("S-1", "Easy", 8, 1.2)); 
        fleet.addVehicle(new Bicycle("B-1", "Mid", 5)); 
        fleet.addThing(new Helmet("H-1", "M")); 
 
        final var output = new ByteArrayOutputStream(); 
        final var out = new PrintStream(output, true, 
                StandardCharsets.UTF_8); 
        new FleetReporter(out).print(fleet); 
 
        final String text = output.toString(StandardCharsets.UTF_8); 
        assertTrue(text.contains("=== Парк ВышКат ===")); 
        assertTrue(text.contains("Всего единиц: 3")); 
        assertTrue(text.contains("Scooter Easy [S-1]")); 
        assertTrue(text.contains("Helmet M [H-1]")); 
        assertTrue(text.contains("(простота=8)")); 
    } 
 
    @Test 
    @DisplayName("Печатает отчёт по пустому парку без новичковой техники") 
    void printsEmptyFleetReport() { 
        final var fleet = new Fleet(accepting()); 
 
        final var output = new ByteArrayOutputStream(); 
        final var out = new PrintStream(output, true, 
                StandardCharsets.UTF_8); 
        new FleetReporter(out).print(fleet); 
 
        final String text = output.toString(StandardCharsets.UTF_8); 
        assertTrue(text.contains("Всего единиц: 0")); 
        assertTrue(text.contains("(нет)")); 
    } 
} 
