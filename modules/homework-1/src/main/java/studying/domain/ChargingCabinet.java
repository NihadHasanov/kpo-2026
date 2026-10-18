package studying.domain;

import studying.exception.ApplicationErrorCode;
import studying.exception.ApplicationException;

/** Charging cabinet that consumes energy. */
public final class ChargingCabinet extends Thing
        implements EnergyConsumer {
    private final double dailyEnergyKwh;

    /**
     * Creates a charging cabinet.
     *
     * @param number unique inventory number
     * @param energyKwh positive daily energy in kWh
     */
    public ChargingCabinet(final String number,
                           final double energyKwh) {
        super(number, "Charging cabinet");
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
