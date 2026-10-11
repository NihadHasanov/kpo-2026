package studying.inspection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import studying.domain.Bicycle;

@SuppressWarnings("checkstyle:MagicNumber")
@DisplayName("Unit-тесты сервиса техосмотра по умолчанию")
class DefaultServiceCenterTest {

    private final ServiceCenter serviceCenter = new DefaultServiceCenter();

    @Test
    @DisplayName("Принимает любой непустой объект")
    void acceptsAnyNonNullItem() {
        final var result = serviceCenter.inspect(
                new Bicycle("B-1", "Test", 5));
        assertTrue(result.accepted());
    }

    @Test
    @DisplayName("Отклоняет null")
    void rejectsNull() {
        final var result = serviceCenter.inspect(null);
        assertFalse(result.accepted());
    }
}
