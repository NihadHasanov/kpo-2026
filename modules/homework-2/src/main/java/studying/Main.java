package studying;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import studying.infrastructure.ui.ConsoleUI;
import java.util.Scanner;

/** Главный класс приложения. */
@SpringBootApplication
public final class Main {

    private Main() {
    }

    /**
     * Точка входа.
     * @param args аргументы
     */
    public static void main(final String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        ConfigurableApplicationContext context = app.run(args);

        ConsoleUI ui = context.getBean(ConsoleUI.class);
        ui.start(new Scanner(System.in), System.out);
    }
}
