package studying.withsolid.exception;

/**
 * Прикладное исключение для ошибок валидации и операций ввода-вывода.
 * Содержит код ошибки и, при необходимости, исходную причину.
 */
public class ApplicationException extends RuntimeException {

    // Код ошибки
    private final ApplicationErrorCode code;

    /**
     * Создаёт исключение без исходной причины.
     *
     * @param code    код прикладной ошибки
     * @param message понятное описание ошибки
     */
    public ApplicationException(ApplicationErrorCode code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * Создаёт исключение с сохранением исходной причины.
     *
     * @param code    код прикладной ошибки
     * @param message понятное описание ошибки
     * @param cause   исходное исключение (например, IOException)
     */
    public ApplicationException(ApplicationErrorCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    /**
     * Возвращает код прикладной ошибки.
     *
     * @return код ошибки
     */
    public ApplicationErrorCode getCode() {
        return code;
    }
}