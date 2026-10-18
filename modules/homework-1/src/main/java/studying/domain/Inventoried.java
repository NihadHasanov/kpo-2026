package studying.domain;

/** Something that has an inventory number and a display name. */
public interface Inventoried {
    /**
     * Returns the unique inventory number.
     *
     * @return inventory number
     */
    String inventoryNumber();

    /**
     * Returns the human-readable display name.
     *
     * @return display name
     */
    String displayName();
}
