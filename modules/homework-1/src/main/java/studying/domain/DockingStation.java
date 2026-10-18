package studying.domain;

import studying.exception.ApplicationErrorCode;
import studying.exception.ApplicationException;

/** Docking station that consumes energy. */
public final class DockingStation extends Thing
        implements EnergyConsumer {
    private final double dailyEnergyKwh;

    /**
     * Creates a docking station.
     *
     * @param number unique inventory number
     * @param energyKwh positive daily energy in kWh
     */
    public DockingStation(final String number,
                          final double energyKwh) {
        super(number, "Docking station");
        if (energyKwh <= 0) {
            throw new ApplicationException(
                    ApplicationErrorCode.VALIDATION_ERROR,
                    "Daily energy must be positive");
        }
        this.dailyEnergyKwh = energyKwh;
    }

    @Override
    public double dailyEnergyKwh() {
        return dailyEnergyKwh;
    }
}
