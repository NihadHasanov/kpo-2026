package studying.application;

import studying.domain.model.Criterion;
import studying.domain.model.Student;
import java.util.UUID;

/** Фасад для консольного интерфейса. */
public final class PosFacade {
    private final PosManagementService managementService;

    /**
     * Конструктор.
     * @param service сервис
     */
    public PosFacade(final PosManagementService service) {
        this.managementService = service;
    }

    /**
     * Зарегистрировать студента.
     * @param name имя
     * @return студент
     */
    public Student registerStudent(final String name) {
        return managementService.addStudent(name);
    }

    /**
     * Зарегистрировать критерий.
     * @param name название
     * @param maxScore балл
     * @param weight вес
     * @return критерий
     */
    public Criterion registerCriterion(final String name,
                                       final double maxScore,
                                       final double weight) {
        return managementService.addCriterion(name, maxScore, weight);
    }

    /**
     * Выставить оценку.
     * @param studentId id
     * @param criterionId id
     * @param score балл
     */
    public void gradeStudent(final UUID studentId,
                             final UUID criterionId,
                             final double score) {
        managementService.assignGrade(studentId, criterionId, score);
    }

    /**
     * Получить результат.
     * @param studentId id
     * @return балл
     */
    public double getStudentResult(final UUID studentId) {
        return managementService.calculateFinalGrade(studentId);
    }
}
