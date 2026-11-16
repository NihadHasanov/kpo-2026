package studying.infrastructure.persistence;

import studying.domain.model.Evaluation;
import studying.domain.port.EvaluationRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/** Репозиторий оценок в памяти. */
public final class InMemoryEvaluationRepository
        implements EvaluationRepository {
    private final Map<UUID, Evaluation> storage = new ConcurrentHashMap<>();

    @Override
    public void save(final Evaluation evaluation) {
        storage.put(evaluation.id(), evaluation);
    }

    @Override
    public Optional<Evaluation> findById(final UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Evaluation> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public List<Evaluation> findByStudentId(final UUID studentId) {
        return storage.values().stream()
                .filter(e -> e.student().id().equals(studentId))
                .collect(Collectors.toList());
    }
}
