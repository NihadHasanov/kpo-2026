package studying.domain.model;

import studying.domain.vo.Score;
import java.util.UUID;

/**
 * Оценка студента по критерию.
 * @param id идентификатор
 * @param student студент
 * @param criterion критерий
 * @param score балл
 */
public record Evaluation(
        UUID id,
        Student student,
        Criterion criterion,
        Score score
) {
}
