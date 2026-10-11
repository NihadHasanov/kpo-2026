package studying.domain;

/** Something that consumes energy. */
@FunctionalInterface
public interface EnergyConsumer {
    /**
     * Returns the daily energy consumption in kWh.
     *
     * @return daily energy in kWh
     */
    double dailyEnergyKwh();
}
