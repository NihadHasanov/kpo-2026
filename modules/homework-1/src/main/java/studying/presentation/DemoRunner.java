package studying.presentation;

import java.io.PrintStream;
import studying.domain.Bicycle;
import studying.domain.ChargingCabinet;
import studying.domain.DockingStation;
import studying.domain.ElectricBicycle;
import studying.domain.Helmet;
import studying.domain.Scooter;
import studying.fleet.Fleet;

/** Runs a fixed demo scenario to showcase all features. */
public final class DemoRunner {

    /** Demo scooter energy in kWh. */
    private static final double SCOOTER_ENERGY = 1.2;
    /** Demo second scooter energy in kWh. */
    private static final double SCOOTER_ENERGY_2 = 1.4;
    /** Demo e-bike energy in kWh. */
    private static final double EBIKE_ENERGY = 0.8;
    /** Demo docking station energy in kWh. */
    private static final double DOCK_ENERGY = 0.3;
    /** Demo charging cabinet energy in kWh. */
    private static final double CABINET_ENERGY = 5.0;

    /** Demo simplicity for the first scooter. */
    private static final int SCOOTER_SIMPLICITY_1 = 8;
    /** Demo simplicity for the second scooter. */
    private static final int SCOOTER_SIMPLICITY_2 = 4;
    /** Demo simplicity for the bicycle. */
    private static final int BICYCLE_SIMPLICITY = 9;
    /** Demo simplicity for the electric bicycle. */
    private static final int EBIKE_SIMPLICITY = 6;

    /** Fleet populated by the demo. */
    private final Fleet fleet;
    /** Output stream for the report. */
    private final PrintStream output;

    /**
     * Creates a demo runner.
     *
     * @param targetFleet fleet to populate
     * @param targetOutput output stream
     */
    public DemoRunner(final Fleet targetFleet,
                      final PrintStream targetOutput) {
        this.fleet = targetFleet;
        this.output = targetOutput;
    }

    /**
     * Populates the fleet with demo data and prints the report.
     */
    public void run() {
        output.println("=== Демонстрация работы ===");
        fleet.addVehicle(new Scooter("S-001", "Ninebot Max",
                SCOOTER_SIMPLICITY_1, SCOOTER_ENERGY));
        fleet.addVehicle(new Scooter("S-002", "Ninebot Pro",
                SCOOTER_SIMPLICITY_2, SCOOTER_ENERGY_2));
        fleet.addVehicle(new Bicycle("B-001", "Stels",
                BICYCLE_SIMPLICITY));
        fleet.addVehicle(new ElectricBicycle("E-001", "Vitus",
                EBIKE_SIMPLICITY, EBIKE_ENERGY));
        fleet.addThing(new Helmet("H-001", "M"));
        fleet.addThing(new Helmet("H-002", "L"));
        fleet.addThing(new DockingStation("D-001", DOCK_ENERGY));
        fleet.addThing(new ChargingCabinet("C-001", CABINET_ENERGY));

        new FleetReporter(output).print(fleet);
    }
}
