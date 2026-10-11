package studying.fleet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import studying.domain.Bicycle;
import studying.domain.DockingStation;
import studying.domain.Helmet;
import studying.domain.Inventoried;
import studying.domain.Scooter;
import studying.exception.ApplicationException;
import studying.inspection.InspectionResult;
import studying.inspection.ServiceCenter;

@SuppressWarnings("checkstyle:MagicNumber")
@DisplayName("Unit-тесты парка")
class FleetTest {

    private static final double SCOOTER_ENERGY = 2.0;
    private static final double DOCK_ENERGY = 0.5;

    private static ServiceCenter accepting() {
        return item -> InspectionResult.ok();
    }

    private static ServiceCenter rejecting(final String reason) {
        return item -> InspectionResult.rejected(reason);
    }

    @Test
    @DisplayName("Добавляет технику, если техосмотр принял её")
    void addsVehicleWhenInspectionAccepts() {
        final var fleet = new Fleet(accepting());
        fleet.addVehicle(new Scooter("S-1", "X", 8, SCOOTER_ENERGY));
        assertEquals(1, fleet.vehicles().size());
    }

    @Test
    @DisplayName("Отклоняет технику, если техосмотр отказал")
    void rejectsVehicleWhenInspectionRejects() {
        final var fleet = new Fleet(rejecting("broken"));
        assertThrows(ApplicationException.class,
                () -> fleet.addVehicle(
                        new Scooter("S-1", "X", 8, SCOOTER_ENERGY)));
        assertEquals(0, fleet.vehicles().size());
    }

    @Test
    @DisplayName("Отклоняет вещь, если техосмотр отказал")
    void rejectsThingWhenInspectionRejects() {
        final var fleet = new Fleet(rejecting("no"));
        assertThrows(ApplicationException.class,
                () -> fleet.addThing(new Helmet("H-1", "M")));
        assertEquals(0, fleet.things().size());
    }

    @Test
    @DisplayName("Считает суммарное суточное потребление")
    void computesTotalDailyEnergy() {
        final var fleet = new Fleet(accepting());
        fleet.addVehicle(new Scooter("S-1", "X", 8, SCOOTER_ENERGY));
        fleet.addVehicle(new Bicycle("B-1", "Y", 9));
        fleet.addThing(new DockingStation("D-1", DOCK_ENERGY));
        fleet.addThing(new Helmet("H-1", "M"));
        assertEquals(SCOOTER_ENERGY + DOCK_ENERGY,
                fleet.totalDailyEnergyKwh());
    }

    @Test
    @DisplayName("Список для новичков содержит только подходящие")
    void returnsOnlyNoviceFriendlyVehicles() {
        final var fleet = new Fleet(accepting());
        fleet.addVehicle(new Scooter("S-1", "Easy", 8, SCOOTER_ENERGY));
        fleet.addVehicle(new Scooter("S-2", "Hard", 3, SCOOTER_ENERGY));
        fleet.addVehicle(new Bicycle("B-1", "Mid", 6));
        assertEquals(2, fleet.noviceFriendlyVehicles().size());
    }

    @Test
    @DisplayName("Все единицы парка включают транспорт и вещи")
    void allItemsIncludesVehiclesAndThings() {
        final var fleet = new Fleet(accepting());
        fleet.addVehicle(new Scooter("S-1", "X", 8, SCOOTER_ENERGY));
        fleet.addThing(new Helmet("H-1", "M"));
        final List<Inventoried> all = fleet.allItems();
        assertEquals(2, all.size());
        assertEquals("S-1", all.get(0).inventoryNumber());
        assertEquals("H-1", all.get(1).inventoryNumber());
        assertEquals(2, fleet.totalUnits());
    }
}
