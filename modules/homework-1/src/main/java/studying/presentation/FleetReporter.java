package studying.presentation;

import java.io.PrintStream;
import java.util.List;
import studying.domain.Inventoried;
import studying.domain.Vehicle;
import studying.fleet.Fleet;

/** Prints a human-readable report about the fleet. */
public final class FleetReporter {

    /** Output stream for the report. */
    private final PrintStream output;

    /**
     * Creates a reporter.
     *
     * @param targetOutput output stream
     */
    public FleetReporter(final PrintStream targetOutput) {
        this.output = targetOutput;
    }

    /**
     * Prints the full fleet report.
     *
     * @param fleet fleet to report about
     */
    public void print(final Fleet fleet) {
        output.println("=== Парк ВышКат ===");
        output.printf("Всего единиц: %d%n", fleet.totalUnits());
        output.printf("Суммарное суточное потребление: %.2f кВт·ч%n",
                fleet.totalDailyEnergyKwh());

        output.println();
        output.println("Прокатная техника для новичков:");
        final List<Vehicle> novices = fleet.noviceFriendlyVehicles();
        if (novices.isEmpty()) {
            output.println("  (нет)");
        } else {
            for (final Vehicle vehicle : novices) {
                output.printf("  %s [%s] (простота=%d)%n",
                        vehicle.displayName(),
                        vehicle.inventoryNumber(),
                        vehicle.noviceSimplicity());
            }
        }

        output.println();
        output.println("Все единицы парка:");
        for (final Inventoried item : fleet.allItems()) {
            output.printf("  %s [%s]%n",
                    item.displayName(), item.inventoryNumber());
        }
    }
}
