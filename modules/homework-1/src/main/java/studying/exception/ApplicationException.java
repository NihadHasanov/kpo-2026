package studying.exception;

/** Runtime exception with an application-level error code. */
public class ApplicationException extends RuntimeException {
    private final ApplicationErrorCode code;

    /**
     * Creates an application error.
     *
     * @param errorCode error code
     * @param message description
     */
    public ApplicationException(final ApplicationErrorCode errorCode,
                                final String message) {
        super(message);
        this.code = errorCode;
    }

    /**
     * Creates an application error with a cause.
     *
     * @param errorCode error code
     * @param message description
     * @param cause original exception
     */
    public ApplicationException(final ApplicationErrorCode errorCode,
                                final String message,
                                final Throwable cause) {
        super(message, cause);
        this.code = errorCode;
    }

    /**
     * Returns the error code.
     *
     * @return error code
     */
    public ApplicationErrorCode getCode() {
        return code;
    }
}
