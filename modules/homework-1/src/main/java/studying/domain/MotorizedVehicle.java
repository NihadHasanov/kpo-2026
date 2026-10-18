package studying.domain; 
 
import studying.exception.ApplicationErrorCode; 
import studying.exception.ApplicationException; 
 
/** Vehicle that consumes energy. */ 
public abstract class MotorizedVehicle extends Vehicle 
        implements EnergyConsumer { 
    private final double dailyEnergyKwh; 
 
    /** 
     * Creates a motorized vehicle. 
     * 
     * @param number unique inventory number 
     * @param name human-readable name 
     * @param simplicity score from 1 to 10 
     * @param energyKwh positive daily energy in kWh 
     */ 
    protected MotorizedVehicle(final String number, 
                               final String name, 
                               final int simplicity, 
                               final double energyKwh) { 
        super(number, name, simplicity); 
        if (energyKwh <= 0) { 
            throw new ApplicationException( 
                    ApplicationErrorCode.VALIDATION_ERROR, 
                    "Daily energy must be positive"); 
        } 
        this.dailyEnergyKwh = energyKwh; 
    } 
 
    @Override 
    public final double dailyEnergyKwh() { 
        return dailyEnergyKwh; 
    } 
} 
