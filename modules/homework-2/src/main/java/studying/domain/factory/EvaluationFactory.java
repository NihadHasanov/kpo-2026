package studying.domain.factory;

import studying.domain.exception.DomainException;
import studying.domain.model.Criterion;
import studying.domain.model.Evaluation;
import studying.domain.model.Student;
import studying.domain.vo.Score;
import java.util.UUID;

/** Фабрика для безопасного создания оценок. */
public final class EvaluationFactory {

    private EvaluationFactory() {
    }

    /**
     * Создает оценку, проверяя инвариант.
     * @param student студент
     * @param criterion критерий
     * @param scoreValue значение балла
     * @return созданная оценка
     */
    public static Evaluation create(final Student student,
                                    final Criterion criterion,
                                    final double scoreValue) {
        Score score = new Score(scoreValue);
        if (score.value() > criterion.maxScore()) {
            throw new DomainException("Балл " + scoreValue
                    + " превышает максимум " + criterion.maxScore());
        }
        return new Evaluation(UUID.randomUUID(), student, criterion, score);
    }
}
