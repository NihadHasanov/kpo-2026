package studying.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import studying.fleet.Fleet;
import studying.inspection.InspectionResult;
import studying.inspection.ServiceCenter;

@SuppressWarnings("checkstyle:MagicNumber")
@DisplayName("Unit-тесты демонстрационного сценария")
class DemoRunnerTest {

    private static ServiceCenter accepting() {
        return item -> InspectionResult.ok();
    }

    @Test
    @DisplayName("Заполняет парк восемью единицами и печатает отчёт")
    void populatesFleetAndPrintsReport() {
        final var output = new ByteArrayOutputStream();
        final var out = new PrintStream(output, true,
                StandardCharsets.UTF_8);
        final var fleet = new Fleet(accepting());

        new DemoRunner(fleet, out).run();

        final String text = output.toString(StandardCharsets.UTF_8);
        assertTrue(text.contains("=== Демонстрация работы ==="));
        assertTrue(text.contains("=== Парк ВышКат ==="));
        assertTrue(text.contains("S-001"));
        assertTrue(text.contains("H-001"));
        assertTrue(text.contains("C-001"));
        assertEquals(8, fleet.totalUnits());
    }
}
