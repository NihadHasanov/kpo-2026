package studying.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import studying.domain.exception.DomainException;
import studying.domain.service.WeightedGradeCalculator;
import studying.infrastructure.persistence.InMemoryCriterionRepository;
import studying.infrastructure.persistence.InMemoryEvaluationRepository;
import studying.infrastructure.persistence.InMemoryStudentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("checkstyle:MagicNumber")
@DisplayName("Сценарии модуля ПОС")
class PosManagementServiceTest {

    @Test
    @DisplayName("Успешный сценарий: студент, критерий, оценка, итог")
    void successfulScenario() {
        var service = new PosManagementService(
                new InMemoryStudentRepository(),
                new InMemoryCriterionRepository(),
                new InMemoryEvaluationRepository(),
                new WeightedGradeCalculator()
        );

        var student = service.addStudent("Иван");
        var criterion = service.addCriterion("ДЗ", 10.0, 1.0);

        service.assignGrade(student.id(), criterion.id(), 8.0);

        double finalGrade = service.calculateFinalGrade(student.id());
        assertEquals(80.0, finalGrade);
    }

    @Test
    @DisplayName("Выброс ошибки, если критерий не найден")
    void throwsIfCriterionNotFound() {
        var service = new PosManagementService(
                new InMemoryStudentRepository(),
                new InMemoryCriterionRepository(),
                new InMemoryEvaluationRepository(),
                new WeightedGradeCalculator()
        );

        var student = service.addStudent("Иван");

        assertThrows(DomainException.class,
                () -> service.assignGrade(student.id(), java.util.UUID.randomUUID(), 5.0));
    }
}
