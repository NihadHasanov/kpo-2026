package studying.domain.exception;

/** Исключение при нарушении бизнес-правил домена. */
public class DomainException extends RuntimeException {
    /**
     * Конструктор.
     * @param message сообщение об ошибке
     */
    public DomainException(final String message) {
        super(message);
    }
}
