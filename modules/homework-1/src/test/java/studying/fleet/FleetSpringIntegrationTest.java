package studying.fleet; 
 
import static org.junit.jupiter.api.Assertions.assertEquals; 
import static org.junit.jupiter.api.Assertions.assertNotNull; 
import static org.junit.jupiter.api.Assertions.assertThrows; 
import static org.mockito.ArgumentMatchers.any; 
import static org.mockito.Mockito.when; 
 
import org.junit.jupiter.api.DisplayName; 
import org.junit.jupiter.api.Test; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.boot.test.context.SpringBootTest; 
import org.springframework.test.context.bean.override.mockito.MockitoBean; 
import studying.Main; 
import studying.domain.Bicycle; 
import studying.domain.Scooter; 
import studying.exception.ApplicationException; 
import studying.inspection.InspectionResult; 
import studying.inspection.ServiceCenter; 
 
@SuppressWarnings("checkstyle:MagicNumber") 
@SpringBootTest(classes = Main.class) 
@DisplayName("Интеграционные тесты Spring-контекста парка") 
class FleetSpringIntegrationTest { 
 
    private static final double ENERGY = 1.0; 
 
    @Autowired 
    private Fleet fleet; 
 
    @MockitoBean 
    private ServiceCenter serviceCenter; 
 
    @Test 
    @DisplayName("Spring создаёт парк и подменяет сервис техосмотра") 
    void contextWiresFleetAndServiceCenter() { 
        assertNotNull(fleet); 
        assertNotNull(serviceCenter); 
    } 
 
    @Test 
    @DisplayName("Парк использует mock сервиса техосмотра при добавлении") 
    void usesMockedServiceCenter() { 
        when(serviceCenter.inspect(any())) 
                .thenReturn(InspectionResult.ok()); 
        fleet.addVehicle(new Scooter("S-1", "X", 8, ENERGY)); 
        assertEquals(1, fleet.vehicles().size()); 
    } 
 
    @Test 
    @DisplayName("Mock сервиса техосмотра может отклонить добавление") 
    void mockCanReject() { 
        when(serviceCenter.inspect(any())) 
                .thenReturn(InspectionResult.rejected("bad")); 
        assertThrows(ApplicationException.class, 
                () -> fleet.addVehicle( 
                        new Bicycle("B-1", "X", 9))); 
    } 
} 
