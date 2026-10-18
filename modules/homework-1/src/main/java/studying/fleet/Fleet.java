package studying.fleet; 
 
import java.util.ArrayList; 
import java.util.List; 
import studying.domain.EnergyConsumer; 
import studying.domain.Inventoried; 
import studying.domain.Thing; 
import studying.domain.Vehicle; 
import studying.exception.ApplicationErrorCode; 
import studying.exception.ApplicationException; 
import studying.inspection.InspectionResult; 
import studying.inspection.ServiceCenter; 
 
/** In-memory fleet that accepts items after inspection. */ 
public final class Fleet { 
    private final ServiceCenter serviceCenter; 
    private final List<Vehicle> vehicles = new ArrayList<>(); 
    private final List<Thing> things = new ArrayList<>(); 
 
    /** 
     * Creates a fleet. 
     * 
     * @param inspectionService service center used for inspection 
     */ 
    public Fleet(final ServiceCenter inspectionService) { 
        this.serviceCenter = inspectionService; 
    } 
 
    /** 
     * Inspects and adds a vehicle. 
     * 
     * @param vehicle vehicle to add 
     * @throws ApplicationException when the service center rejects it 
     */ 
    public void addVehicle(final Vehicle vehicle) { 
        accept(vehicle); 
        vehicles.add(vehicle); 
    } 
 
    /** 
     * Inspects and adds a thing. 
     * 
     * @param thing thing to add 
     * @throws ApplicationException when the service center rejects it 
     */ 
    public void addThing(final Thing thing) { 
        accept(thing); 
        things.add(thing); 
    } 
 
    /** 
     * Returns the vehicles. 
     * 
     * @return immutable snapshot 
     */ 
    public List<Vehicle> vehicles() { 
        return List.copyOf(vehicles); 
    } 
 
    /** 
     * Returns the things. 
     * 
     * @return immutable snapshot 
     */ 
    public List<Thing> things() { 
        return List.copyOf(things); 
    } 
 
    /** 
     * Returns all inventoried items: vehicles first, then things. 
     * 
     * @return immutable snapshot 
     */ 
    public List<Inventoried> allItems() { 
        final List<Inventoried> all = new ArrayList<>(vehicles); 
        all.addAll(things); 
        return List.copyOf(all); 
    } 
 
    /** 
     * Returns the total number of units. 
     * 
     * @return total number of units 
     */ 
    public int totalUnits() { 
        return vehicles.size() + things.size(); 
    } 
 
    /** 
     * Returns the total daily energy consumption. 
     * 
     * @return total daily energy in kWh 
     */ 
    public double totalDailyEnergyKwh() { 
        double total = 0.0; 
        for (final Inventoried item : allItems()) { 
            if (item instanceof EnergyConsumer consumer) { 
                total += consumer.dailyEnergyKwh(); 
            } 
        } 
        return total; 
    } 
 
    /** 
     * Returns vehicles suitable for novices. 
     * 
     * @return novice-friendly vehicles 
     */ 
    public List<Vehicle> noviceFriendlyVehicles() { 
        return vehicles.stream() 
                .filter(Vehicle::isSuitableForNovice) 
                .toList(); 
    } 
 
    private void accept(final Inventoried item) { 
        final InspectionResult result = serviceCenter.inspect(item); 
        if (!result.accepted()) { 
            throw new ApplicationException( 
                    ApplicationErrorCode.INSPECTION_REJECTED, 
                    "Item rejected: " + result.reason()); 
        } 
    } 
} 
