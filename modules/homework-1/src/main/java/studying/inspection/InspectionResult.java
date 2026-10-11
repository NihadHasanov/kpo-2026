package studying.inspection;

/**
 * Result of a service-center inspection.
 *
 * @param accepted whether the item was accepted
 * @param reason rejection reason, empty when accepted
 */
public record InspectionResult(boolean accepted, String reason) {

    /**
     * Creates an accepted result.
     *
     * @return accepted result
     */
    public static InspectionResult ok() {
        return new InspectionResult(true, "");
    }

    /**
     * Creates a rejected result.
     *
     * @param reason rejection reason
     * @return rejected result
     */
    public static InspectionResult rejected(final String reason) {
        return new InspectionResult(false, reason);
    }
}
