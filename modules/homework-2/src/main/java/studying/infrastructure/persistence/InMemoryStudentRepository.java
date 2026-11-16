package studying.infrastructure.persistence;

import studying.domain.model.Student;
import studying.domain.port.StudentRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Репозиторий студентов в памяти. */
public final class InMemoryStudentRepository implements StudentRepository {
    private final Map<UUID, Student> storage = new ConcurrentHashMap<>();

    @Override
    public void save(final Student student) {
        storage.put(student.id(), student);
    }

    @Override
    public Optional<Student> findById(final UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Student> findAll() {
        return List.copyOf(storage.values());
    }
}
