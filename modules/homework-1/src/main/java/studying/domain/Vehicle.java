package studying.domain;

import studying.exception.ApplicationErrorCode;
import studying.exception.ApplicationException;

/** Base class for every vehicle in the fleet. */
public abstract class Vehicle implements Inventoried {
    /** Minimum novice-friendliness score. */
    public static final int MIN_SIMPLICITY = 1;
    /** Maximum novice-friendliness score. */
    public static final int MAX_SIMPLICITY = 10;
    /** Minimum score that allows giving a vehicle to a novice. */
    public static final int NOVICE_THRESHOLD = 6;

    private final String inventoryNumber;
    private final String displayName;
    private final int noviceSimplicity;

    /**
     * Creates a vehicle.
     *
     * @param number unique inventory number
     * @param name human-readable name
     * @param simplicity score from 1 to 10
     */
    protected Vehicle(final String number,
                      final String name,
                      final int simplicity) {
        if (number == null || number.isBlank()) {
            throw new ApplicationException(
                    ApplicationErrorCode.VALIDATION_ERROR,
                    "Inventory number must not be blank");
        }
        if (simplicity < MIN_SIMPLICITY
                || simplicity > MAX_SIMPLICITY) {
            throw new ApplicationException(
                    ApplicationErrorCode.VALIDATION_ERROR,
                    "Novice simplicity must be in [1; 10]");
        }
        this.inventoryNumber = number;
        this.displayName = name;
        this.noviceSimplicity = simplicity;
    }

    @Override
    public final String inventoryNumber() {
        return inventoryNumber;
    }

    @Override
    public final String displayName() {
        return displayName;
    }

    /**
     * Returns the novice-friendliness score.
     *
     * @return score from 1 to 10
     */
    public final int noviceSimplicity() {
        return noviceSimplicity;
    }

    /**
     * Returns whether this vehicle may be given to a novice.
     *
     * @return {@code true} when the score is at least 6
     */
    public final boolean isSuitableForNovice() {
        return noviceSimplicity >= NOVICE_THRESHOLD;
    }
}
