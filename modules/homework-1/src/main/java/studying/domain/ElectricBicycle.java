package studying.domain;

/** Electric bicycle. */
public final class ElectricBicycle extends MotorizedVehicle {
    /**
     * Creates an electric bicycle.
     *
     * @param number unique inventory number
     * @param modelName model name
     * @param simplicity score from 1 to 10
     * @param energyKwh positive daily energy in kWh
     */
    public ElectricBicycle(final String number,
                           final String modelName,
                           final int simplicity,
                           final double energyKwh) {
        super(number, "Electric bicycle " + modelName,
                simplicity, energyKwh);
    }
}
