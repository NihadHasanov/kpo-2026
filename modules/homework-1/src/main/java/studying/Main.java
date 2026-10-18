package studying;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import studying.fleet.Fleet;
import studying.presentation.DemoRunner;
import studying.presentation.FleetConsoleApp;

/** Console entry point for the ВышКат fleet application. */
@SpringBootApplication
public final class Main {

    private Main() { }

    /**
     * Runs the demo scenario and then starts the interactive menu.
     *
     * @param args command-line arguments
     */
    public static void main(final String[] args) {
        final var context = SpringApplication.run(Main.class);
        final Fleet fleet = context.getBean(Fleet.class);

        new DemoRunner(fleet, System.out).run();

        final var reader = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8));
        new FleetConsoleApp(fleet, reader, System.out).run();
    }
}
