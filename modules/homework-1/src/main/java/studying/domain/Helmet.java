package studying.domain; 
 
/** Safety helmet. */ 
public final class Helmet extends Thing { 
    /** 
     * Creates a helmet. 
     * 
     * @param number unique inventory number 
     * @param size helmet size label 
     */ 
    public Helmet(final String number, final String size) { 
        super(number, "Helmet " + size); 
    } 
} 
