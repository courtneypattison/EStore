
package estoresearch;

/**
 * @author Courtney Bodi
 */
public class Product {
    
    public static final int ID_LENGTH = 6; 
    
    private String id, name;
    private int year;
    private double price;
    
    public Product(String id, String name, int year, double price) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.price = price;
    }
    
    public Product(String id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }
    
    public Product() {
        id = name = "";
        year = 0;
        price = 0.0;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     * @return success of setting id
     */
    public boolean setId(String id) {
        if (id == null || !id.matches("^\\d+$") || id.length() != ID_LENGTH) {
            System.out.println("Invalid input: you must enter a number.");
            return false;
        } else {
            this.id = id;
            return true;
        }
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     * @return success of setting name
     */
    public boolean setName(String name) {
        if (name == null || id.matches("")) {
            System.out.println("Invalid input: you must enter a name.");
            return false;
        } else {
            this.name = name;
            return true;
        }
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     * @return success of setting year
     */
    public boolean setYear(int year) {
        if (year < 1000 || year > 9999) {
            System.out.println("Invalid input: you must enter a year between 1000 and 9999.");
            return false;
        } else {
            this.year = year;
            return true;
        }
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     * @return success of setting price
     */
    public boolean setPrice(double price) {
        if (price <= 0) {
            System.out.println("Invalid input: you must enter a price greater than or equal to 0.");
            return false;
        } else {
            this.price = price;
            return true;
        }
    }

    /**
     * @param other
     * @return whether or not the Products are equal
     */
    public boolean equals(Product other) {
        return id.equals(other.id)
            && name.equals(other.name)
            && year == other.year
            && price == other.price;
    }
    
    /**
     * @return Product string in the form of id, name, year, price
     */
    public String toString() {
        return (id + System.lineSeparator()
            + name + System.lineSeparator()
            + year + System.lineSeparator()
            + price + System.lineSeparator());
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Product product1 = new Product();
        Product product2 = new Product("000025", "Absolute Java", 2015);
        Product product3 = new Product("000025", "Absolute Java", 2015, 199.95);
        
        System.out.println(product1.toString());
        System.out.println(product2.toString());
        System.out.println(product3.toString());
        
        printTest(product1.setId("000026"));
        product1.setId("hello");
        
        System.out.println(product1.toString());
    }
    
    private static void printTest(boolean value) {
        if (value) {
            System.out.println("PASS\t");
        } else {
            System.out.println("FAIL\t");
        }
    }
    
    
}
