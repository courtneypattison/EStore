package car;

/**
 * @author Courtney Bodi
 */
public class Car {

    public static final int DEFAULT_PRICE = 50000;

    private String brandAndModel;
    private int year;
    private int price;

    /**
     * Car constructor
     *
     * @param year
     * @param brandAndModel
     * @param price
     * @throws java.lang.Exception
     */
    public Car(String brandAndModel, int year, int price) throws Exception {
        this.brandAndModel = brandAndModel;
        if (year <= 1986) {
            throw new Exception("Invalid input: Car cannot be greater than 30 years old.");
        }
        this.year = year;
        if (price < 5000) {
            throw new Exception("Invalid input: Price must be $5000 or more.");
        }
        this.price = price;
    }

    /**
     * Car constructor with default price
     *
     * @param year
     * @param brandAndModel
     */
    public Car(String brandAndModel, int year) throws Exception {
        this.brandAndModel = brandAndModel;
        if (year <= 1986) {
            throw new Exception("Invalid input: Car cannot be greater than 30 years old.");
        }
        this.year = year;
        price = DEFAULT_PRICE;
    }

    /**
     * Default Car constructor
     */
    public Car() {
        brandAndModel = "";
        year = 0;
        price = DEFAULT_PRICE;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the brandAndModel
     */
    public String getBrandAndModel() {
        return brandAndModel;
    }

    /**
     * @param brandAndModel the brandAndModel to set
     */
    public void setBrandAndModel(String brandAndModel) {
        this.brandAndModel = brandAndModel;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return brandAndModel + " " + year + " " + price;
    }
    
    public String dataDump() {
        return getBrandAndModel() + " " + getYear() + " " + getPrice() + " 0";
    }
}
