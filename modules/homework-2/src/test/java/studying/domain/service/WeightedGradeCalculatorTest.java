package studying.domain.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import studying.domain.factory.EvaluationFactory;
import studying.domain.model.Criterion;
import studying.domain.model.Student;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("checkstyle:MagicNumber")
@DisplayName("Расчет итогового балла")
class WeightedGradeCalculatorTest {
    private final WeightedGradeCalculator calculator = new WeightedGradeCalculator();

    @Test
    @DisplayName("Считает средневзвешенный балл")
    void calculatesWeightedGrade() {
        Student student = new Student(UUID.randomUUID(), "Иван");
        Criterion c1 = new Criterion(UUID.randomUUID(), "ДЗ", 10.0, 0.5);
        Criterion c2 = new Criterion(UUID.randomUUID(), "Экзамен", 20.0, 0.5);

        var e1 = EvaluationFactory.create(student, c1, 10.0);
        var e2 = EvaluationFactory.create(student, c2, 10.0);

        double result = calculator.calculateFinalGrade(
                student, List.of(e1, e2), List.of(c1, c2));

        assertEquals(75.0, result);
    }
}
