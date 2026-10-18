package studying.inspection; 
 
import studying.domain.Inventoried; 
 
/** Decides whether a new item can be added to the fleet. */ 
@FunctionalInterface 
public interface ServiceCenter { 
    /** 
     * Inspects the given item. 
     * 
     * @param item item to inspect 
     * @return inspection result 
     */ 
    InspectionResult inspect(Inventoried item); 
} 
