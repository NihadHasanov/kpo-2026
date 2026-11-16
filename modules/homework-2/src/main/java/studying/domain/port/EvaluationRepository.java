package studying.domain.port;

import studying.domain.model.Evaluation;
import java.util.List;
import java.util.UUID;

/** Репозиторий оценок. */
public interface EvaluationRepository extends Repository<Evaluation> {
    /**
     * Найти оценки студента.
     * @param studentId идентификатор студента
     * @return список оценок
     */
    List<Evaluation> findByStudentId(UUID studentId);
}
