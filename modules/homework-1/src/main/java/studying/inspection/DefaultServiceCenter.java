package studying.inspection; 
 
import studying.domain.Inventoried; 
 
/** Default service center that accepts every non-null item. */ 
public final class DefaultServiceCenter implements ServiceCenter { 
    @Override 
    public InspectionResult inspect(final Inventoried item) { 
        if (item == null) { 
            return InspectionResult.rejected("Item is null"); 
        } 
        return InspectionResult.ok(); 
    } 
} 
