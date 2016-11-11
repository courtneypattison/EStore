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
     * @param brandAndModel
     * @param year
     * @param price
     * @param numSeats
     * @param isAllTerrain
     * @param tireBrand 
     * @throws java.lang.Exception 
     */
    public SUV(String brandAndModel, int year, int price, int numSeats, boolean isAllTerrain, String tireBrand) throws Exception {
        super(brandAndModel, year, price);
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
    public boolean getIsAllTerrain() {
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
    
    @Override
    public String toString() {
        return super.toString() + numSeats + isAllTerrain + tireBrand;
    }
    
    @Override
    public String dataDump() {
        return getBrandAndModel().split("\\s")[1] + " " + getYear() + " " + getPrice() + " 1" + getNumSeats() + " " + getIsAllTerrain() + " " + getTireBrand(); 
    }
}
