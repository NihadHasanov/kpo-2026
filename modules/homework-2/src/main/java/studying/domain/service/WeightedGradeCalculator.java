package studying.domain.service;

import studying.domain.model.Criterion;
import studying.domain.model.Evaluation;
import studying.domain.model.Student;
import java.util.List;

/** Считает итог с учетом весов критериев. */
public final class WeightedGradeCalculator implements GradeCalculator {
    private static final double MAX_PERCENTAGE = 100.0;

    @Override
    public double calculateFinalGrade(final Student student,
                                      final List<Evaluation> evaluations,
                                      final List<Criterion> criteria) {
        double totalScore = 0.0;
        double totalWeight = 0.0;

        for (Evaluation eval : evaluations) {
            if (eval.student().id().equals(student.id())) {
                Criterion criterion = eval.criterion();
                double normalizedScore = (eval.score().value()
                        / criterion.maxScore()) * MAX_PERCENTAGE;
                totalScore += normalizedScore * criterion.weight();
                totalWeight += criterion.weight();
            }
        }
        return totalWeight > 0 ? totalScore / totalWeight : 0.0;
    }
}
