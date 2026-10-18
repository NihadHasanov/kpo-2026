package studying.domain;

import studying.exception.ApplicationErrorCode;
import studying.exception.ApplicationException;

/** Base class for every inventoried thing in the fleet. */
public abstract class Thing implements Inventoried {
    private final String inventoryNumber;
    private final String displayName;

    /**
     * Creates a thing.
     *
     * @param number unique inventory number
     * @param name human-readable name
     */
    protected Thing(final String number, final String name) {
        if (number == null || number.isBlank()) {
            throw new ApplicationException(
                    ApplicationErrorCode.VALIDATION_ERROR,
                    "Inventory number must not be blank");
        }
        this.inventoryNumber = number;
        this.displayName = name;
    }

    @Override
    public final String inventoryNumber() {
        return inventoryNumber;
    }

    @Override
    public final String displayName() {
        return displayName;
    }
}
