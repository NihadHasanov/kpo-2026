package studying.domain.port;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Базовый интерфейс репозитория.
 * @param <T> тип сущности
 */
public interface Repository<T> {
    /**
     * Сохранить сущность.
     * @param entity сущность
     */
    void save(T entity);

    /**
     * Найти по ID.
     * @param id идентификатор
     * @return сущность
     */
    Optional<T> findById(UUID id);

    /**
     * Найти все.
     * @return список сущностей
     */
    List<T> findAll();
}
