package studying.domain.vo;

import studying.domain.exception.DomainException;

/**
 * Оценка за конкретный критерий.
 * @param value значение балла
 */
public record Score(double value) {
    /**
     * Конструктор.
     * @param value значение балла
     */
    public Score {
        if (value < 0) {
            throw new DomainException("Балл не может быть отрицательным");
        }
    }
}
