package studying.domain;

/** Regular bicycle that does not consume energy. */
public final class Bicycle extends Vehicle {
    /**
     * Creates a bicycle.
     *
     * @param number unique inventory number
     * @param modelName model name
     * @param simplicity score from 1 to 10
     */
    public Bicycle(final String number,
                   final String modelName,
                   final int simplicity) {
        super(number, "Bicycle " + modelName, simplicity);
    }
}
