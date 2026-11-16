package studying.domain.model;

import studying.domain.exception.DomainException;
import java.util.UUID;

/**
 * Студент, которого оценивают.
 * @param id идентификатор
 * @param name имя
 */
public record Student(UUID id, String name) {
    /**
     * Конструктор.
     * @param id идентификатор
     * @param name имя
     */
    public Student {
        if (name == null || name.isBlank()) {
            throw new DomainException("Имя студента обязательно");
        }
    }
}
