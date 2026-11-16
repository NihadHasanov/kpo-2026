package studying.domain.service;

import studying.domain.model.Criterion;
import studying.domain.model.Evaluation;
import studying.domain.model.Student;
import java.util.List;

/** Доменный сервис для расчета итогового балла. */
public interface GradeCalculator {
    /**
     * Считает итоговый балл.
     * @param student студент
     * @param evaluations его оценки
     * @param criteria все критерии
     * @return итоговый балл
     */
    double calculateFinalGrade(Student student,
                               List<Evaluation> evaluations,
                               List<Criterion> criteria);
}
