package studying.infrastructure.persistence;

import studying.domain.model.Criterion;
import studying.domain.port.CriterionRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Репозиторий критериев в памяти. */
public final class InMemoryCriterionRepository implements CriterionRepository {
    private final Map<UUID, Criterion> storage = new ConcurrentHashMap<>();

    @Override
    public void save(final Criterion criterion) {
        storage.put(criterion.id(), criterion);
    }

    @Override
    public Optional<Criterion> findById(final UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Criterion> findAll() {
        return List.copyOf(storage.values());
    }
}
