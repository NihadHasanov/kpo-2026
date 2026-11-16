package studying.domain.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import studying.domain.exception.DomainException;
import studying.domain.model.Criterion;
import studying.domain.model.Student;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("checkstyle:MagicNumber")
@DisplayName("Фабрика оценок: проверка инвариантов")
class EvaluationFactoryTest {
    private final Student student = new Student(UUID.randomUUID(), "Иван");
    private final Criterion criterion = new Criterion(UUID.randomUUID(), "ДЗ", 10.0, 1.0);

    @Test
    @DisplayName("Создает оценку в рамках максимума")
    void createsValidEvaluation() {
        var eval = EvaluationFactory.create(student, criterion, 8.5);
        assertEquals(8.5, eval.score().value());
    }

    @Test
    @DisplayName("Отклоняет оценку выше максимума")
    void rejectsScoreAboveMax() {
        assertThrows(DomainException.class,
                () -> EvaluationFactory.create(student, criterion, 11.0));
    }
}
