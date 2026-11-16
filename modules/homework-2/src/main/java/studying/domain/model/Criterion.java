package studying.domain.model;

import studying.domain.exception.DomainException;
import java.util.UUID;

/**
 * Критерий оценивания.
 * @param id идентификатор
 * @param name название
 * @param maxScore максимальный балл
 * @param weight вес
 */
public record Criterion(UUID id, String name, double maxScore, double weight) {
    /**
     * Конструктор.
     * @param id идентификатор
     * @param name название
     * @param maxScore максимальный балл
     * @param weight вес
     */
    public Criterion {
        if (name == null || name.isBlank()) {
            throw new DomainException("Название обязательно");
        }
        if (maxScore <= 0) {
            throw new DomainException("Максимальный балл > 0");
        }
        if (weight <= 0) {
            throw new DomainException("Вес > 0");
        }
    }
}
