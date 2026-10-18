package studying.domain;

/** Electric kick scooter. */
public final class Scooter extends MotorizedVehicle {
    /**
     * Creates a scooter.
     *
     * @param number unique inventory number
     * @param modelName model name
     * @param simplicity score from 1 to 10
     * @param energyKwh positive daily energy in kWh
     */
    public Scooter(final String number,
                   final String modelName,
                   final int simplicity,
                   final double energyKwh) {
        super(number, "Scooter " + modelName, simplicity, energyKwh);
    }
}
