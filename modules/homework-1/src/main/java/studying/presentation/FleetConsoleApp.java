package studying.presentation; 
 
import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.PrintStream; 
import studying.domain.Bicycle; 
import studying.domain.ChargingCabinet; 
import studying.domain.DockingStation; 
import studying.domain.ElectricBicycle; 
import studying.domain.Helmet; 
import studying.domain.Scooter; 
import studying.exception.ApplicationErrorCode; 
import studying.exception.ApplicationException; 
import studying.fleet.Fleet; 
 
/** Interactive console menu for the fleet application. */ 
public final class FleetConsoleApp { 
 
    /** Fleet operated by the menu. */ 
    private final Fleet fleet; 
    /** Input reader. */ 
    private final BufferedReader input; 
    /** Output stream for prompts and reports. */ 
    private final PrintStream output; 
    /** Report printer. */ 
    private final FleetReporter reporter; 
 
    /** 
     * Creates a console application. 
     * 
     * @param targetFleet fleet to operate on 
     * @param targetInput input reader 
     * @param targetOutput output stream 
     */ 
    public FleetConsoleApp(final Fleet targetFleet, 
                           final BufferedReader targetInput, 
                           final PrintStream targetOutput) { 
        this.fleet = targetFleet; 
        this.input = targetInput; 
        this.output = targetOutput; 
        this.reporter = new FleetReporter(targetOutput); 
    } 
 
    /** 
     * Runs the interactive menu until the user chooses to exit 
     * or the input stream is exhausted. 
     */ 
    public void run() { 
        output.println(); 
        output.println("=== Интерактивный режим ==="); 
        boolean running = true; 
        while (running) { 
            printMenu(); 
            final String choice = readLine("Выберите пункт: "); 
            if (choice == null) { 
                running = false; 
                continue; 
            } 
            try { 
                running = handle(choice); 
            } catch (final ApplicationException exception) { 
                output.println("Прервано: " + exception.getMessage()); 
                running = false; 
            } 
        } 
        output.println("До свидания!"); 
    } 
 
    private void printMenu() { 
        output.println(); 
        output.println("Меню:"); 
        output.println("  1 — Добавить самокат"); 
        output.println("  2 — Добавить велосипед"); 
        output.println("  3 — Добавить электровелосипед"); 
        output.println("  4 — Добавить шлем"); 
        output.println("  5 — Добавить док-станцию"); 
        output.println("  6 — Добавить зарядный шкаф"); 
        output.println("  7 — Показать отчёт по парку"); 
        output.println("  0 — Выход"); 
    } 
 
    private boolean handle(final String choice) { 
        try { 
            switch (choice) { 
                case "1" -> addScooter(); 
                case "2" -> addBicycle(); 
                case "3" -> addElectricBicycle(); 
                case "4" -> addHelmet(); 
                case "5" -> addDockingStation(); 
                case "6" -> addChargingCabinet(); 
                case "7" -> reporter.print(fleet); 
                case "0" -> { 
                    return false; 
                } 
                default -> output.println("Неизвестный пункт меню"); 
            } 
        } catch (final ApplicationException exception) { 
            output.printf("Ошибка (%s): %s%n", 
                    exception.getCode(), exception.getMessage()); 
        } 
        return true; 
    } 
 
    private void addScooter() { 
        final String number = readLine("Инвентарный номер: "); 
        final String model = readLine("Модель: "); 
        final int simplicity = readInt("Простота (1–10): "); 
        final double energy = readDouble("Энергия, кВт·ч: "); 
        fleet.addVehicle(new Scooter(number, model, simplicity, energy)); 
        output.println("Самокат добавлен."); 
    } 
 
    private void addBicycle() { 
        final String number = readLine("Инвентарный номер: "); 
        final String model = readLine("Модель: "); 
        final int simplicity = readInt("Простота (1–10): "); 
        fleet.addVehicle(new Bicycle(number, model, simplicity)); 
        output.println("Велосипед добавлен."); 
    } 
 
    private void addElectricBicycle() { 
        final String number = readLine("Инвентарный номер: "); 
        final String model = readLine("Модель: "); 
        final int simplicity = readInt("Простота (1–10): "); 
        final double energy = readDouble("Энергия, кВт·ч: "); 
        fleet.addVehicle(new ElectricBicycle(number, model, 
                simplicity, energy)); 
        output.println("Электровелосипед добавлен."); 
    } 
 
    private void addHelmet() { 
        final String number = readLine("Инвентарный номер: "); 
        final String size = readLine("Размер: "); 
        fleet.addThing(new Helmet(number, size)); 
        output.println("Шлем добавлен."); 
    } 
 
    private void addDockingStation() { 
        final String number = readLine("Инвентарный номер: "); 
        final double energy = readDouble("Энергия, кВт·ч: "); 
        fleet.addThing(new DockingStation(number, energy)); 
        output.println("Док-станция добавлена."); 
    } 
 
    private void addChargingCabinet() { 
        final String number = readLine("Инвентарный номер: "); 
        final double energy = readDouble("Энергия, кВт·ч: "); 
        fleet.addThing(new ChargingCabinet(number, energy)); 
        output.println("Зарядный шкаф добавлен."); 
    } 
 
    private String readLine(final String prompt) { 
        output.print(prompt); 
        try { 
            final String line = input.readLine(); 
            if (line == null) { 
                return null; 
            } 
            return line.strip(); 
        } catch (final IOException exception) { 
            output.println("Ошибка ввода: " + exception.getMessage()); 
            return null; 
        } 
    } 
 
    private int readInt(final String prompt) { 
        while (true) { 
            final String value = readLine(prompt); 
            if (value == null) { 
                throw new ApplicationException( 
                        ApplicationErrorCode.VALIDATION_ERROR, 
                        "Ввод прерван"); 
            } 
            try { 
                return Integer.parseInt(value); 
            } catch (final NumberFormatException exception) { 
                output.println("Введите целое число."); 
            } 
        } 
    } 
 
    private double readDouble(final String prompt) { 
        while (true) { 
            final String value = readLine(prompt); 
            if (value == null) { 
                throw new ApplicationException( 
                        ApplicationErrorCode.VALIDATION_ERROR, 
                        "Ввод прерван"); 
            } 
            final String normalized = value.replace(',', '.'); 
            try { 
                return Double.parseDouble(normalized); 
            } catch (final NumberFormatException exception) { 
                output.println("Введите число (например, 1.5 или 1,5)."); 
            } 
        } 
    } 
} 
