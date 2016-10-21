package car;

/**
 *
 * @author courtney
 */
public class SUV extends Car {
    
    private int numSeats;
    private boolean isAllTerrain;
    private String tireBrand;
    
    /**
     * SUV constructor
     * 
     * @param year
     * @param brandAndModel
     * @param price
     * @param numSeats
     * @param isAllTerrain
     * @param tireBrand 
     */
    public SUV(int year, String brandAndModel, int price, int numSeats, boolean isAllTerrain, String tireBrand) {
        super(year, brandAndModel, price);
        this.numSeats = numSeats;
        this.isAllTerrain = isAllTerrain;
        this.tireBrand = tireBrand;
    }
    
    /**
     * Generic SUV constructor
     */
    public SUV() {
        super();
        numSeats = 0;
        isAllTerrain = false;
        tireBrand = "";
    }

    /**
     * @return the numSeats
     */
    public int getNumSeats() {
        return numSeats;
    }

    /**
     * @param numSeats the numSeats to set
     */
    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    /**
     * @return the isAllTerrain
     */
    public boolean isIsAllTerrain() {
        return isAllTerrain;
    }

    /**
     * @param isAllTerrain the isAllTerrain to set
     */
    public void setIsAllTerrain(boolean isAllTerrain) {
        this.isAllTerrain = isAllTerrain;
    }

    /**
     * @return the tireBrand
     */
    public String getTireBrand() {
        return tireBrand;
    }

    /**
     * @param tireBrand the tireBrand to set
     */
    public void setTireBrand(String tireBrand) {
        this.tireBrand = tireBrand;
    }
    
    
}
