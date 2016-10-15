package estoresearch;

/**
 * @author Courtney Bodi
 */
public class Product {

    private static final String INVALID_ID = "Invalid input: the ID must be a six digit long number.";
    private static final String INVALID_NAME = "Invalid input: the name must have at least one character.";
    private static final String INVALID_YEAR = "Invalid input: the year must be between 1000 and 9999.";
    private static final String INVALID_PRICE = "Invalid input: the price must be greater than or equal to 0.";

    public static final int ID_LENGTH = 6;

    private String id, name;
    private int year;
    private double price;

    /**
     * Product constructor with all members
     *
     * @param id
     * @param name
     * @param year
     * @param price
     */
    public Product(String id, String name, int year, double price) {
        if (validateId(id)) {
            this.id = id;
        } else {
            throw new IllegalArgumentException(INVALID_ID);
        }

        if (validateString(name)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException(INVALID_NAME);
        }

        if (validateYear(year)) {
            this.year = year;
        } else {
            throw new IllegalArgumentException(INVALID_YEAR);
        }

        if (validatePrice(price)) {
            this.price = price;
        } else {
            throw new IllegalArgumentException(INVALID_PRICE);
        }
    }

    /**
     * Product constructor with all mandatory members
     *
     * @param id
     * @param name
     * @param year
     */
    public Product(String id, String name, int year) {
        if (validateId(id)) {
            this.id = id;
        } else {
            throw new IllegalArgumentException(INVALID_ID);
        }

        if (validateString(name)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException(INVALID_NAME);
        }

        if (validateYear(year)) {
            this.year = year;
        } else {
            throw new IllegalArgumentException(INVALID_YEAR);
        }
    }

    /**
     * Default Product constructor
     */
    public Product() {
        id = name = "";
        year = 0;
        price = 0.0;
    }

    /**
     * Get id
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Validate id string
     *
     * @param id
     * @return whether or not the id is valid
     */
    private boolean validateId(String id) {
        return id != null && id.matches("^\\d+$") && id.length() == ID_LENGTH;
    }

    /**
     * Set id
     *
     * @param id the id to set
     */
    public void setId(String id) {
        if (validateId(id)) {
            this.id = id;
        } else {
            throw new IllegalArgumentException(INVALID_ID);
        }
    }

    /**
     * Get name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Validate string
     *
     * @param string
     * @return whether or not the string is valid
     */
    public boolean validateString(String string) {
        return string != null && !id.matches("");
    }

    /**
     * Set name
     *
     * @param name the name to set
     */
    public void setName(String name) {
        if (validateString(name)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException(INVALID_NAME);
        }
    }

    /**
     * Get year
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Validate year
     *
     * @param year
     * @return whether or not the year is valid
     */
    private boolean validateYear(int year) {
        return year > 1000 && year < 9999;
    }

    /**
     * Set year
     *
     * @param year the year to set
     */
    public void setYear(int year) {
        if (validateYear(year)) {
            this.year = year;
        } else {
            throw new IllegalArgumentException(INVALID_YEAR);
        }
    }

    /**
     * Get price
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Validate price
     *
     * @param price
     * @return whether or not the price is valid
     */
    private boolean validatePrice(double price) {
        return price >= 0;
    }

    /**
     * Set price
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        if (validatePrice(price)) {
            this.price = price;
        } else {
            throw new IllegalArgumentException(INVALID_PRICE);
        }
    }

    /**
     * Check if Products are equal
     *
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
     * Get string with all members of Product
     *
     * @return Product string in the form of id, name, year, price
     */
    @Override
    public String toString() {
        return id + System.lineSeparator()
                + name + System.lineSeparator()
                + year + System.lineSeparator()
                + price + System.lineSeparator();
    }

    /**
     * Main method for testing Product
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean pass = true;

        Product product1 = null, product2 = null, product3 = null, product4 = null;

        System.out.println("Product Class Testing" + System.lineSeparator()
                + "Prints true when test is passed, false otherwise" + System.lineSeparator());

        try {
            product1 = new Product();
        } catch (IllegalArgumentException e) {
            pass = false;
        } finally {
            System.out.println(pass + "\tnew Product()");
        }

        try {
            product2 = new Product("000025", "Absolute Java", 2015);
        } catch (IllegalArgumentException e) {
            pass = false;
        } finally {
            System.out.println(pass + "\tnew Product(\"000025\", \"Absolute Java\", 2015)");
        }

        try {
            product3 = new Product("000026", "Absolute Java", 2015, 199.95);
        } catch (IllegalArgumentException e) {
            product3 = null;
            pass = false;
        } finally {
            System.out.println(pass + "\tnew Product(\"000026\", \"Absolute Java\", 2015, 199.95)");
        }

        try {
            product4 = new Product("5", "Absolute Java", 2015, 199.95);
        } catch (IllegalArgumentException e) {
            pass = true;
        } finally {
            System.out.println(pass + "\tnew Product(\"5\", \"Absolute Java\", 2015, 199.95)");
        }

        String id = product3.getId();
        pass = id.equals(product3.id);
        System.out.println(pass + "\tgetId(product3)");

        String name = product3.getName();
        pass = name.equals(product3.name);
        System.out.println(pass + "\tgetName(product3)");

        int year = product3.getYear();
        pass = year == product3.year;
        System.out.println(pass + "\tgetYear(product3)");

        double price = product3.getPrice();
        pass = price == product3.price;
        System.out.println(pass + "\tgetPrice(product3)");

        try {
            product3.setId("3");
        } catch (IllegalArgumentException e) {
            pass = true;
        } finally {
            System.out.println(pass + "\tproduct3.setId(\"3\")");
        }

        try {
            product3.setName("");
        } catch (IllegalArgumentException e) {
            pass = true;
        } finally {
            System.out.println(pass + "\tproduct3.setName(\"\")");
        }

        try {
            product3.setYear(3);
        } catch (IllegalArgumentException e) {
            pass = true;
        } finally {
            System.out.println(pass + "\tproduct3.setYear(3)");
        }

        try {
            product3.setPrice(-1);
        } catch (IllegalArgumentException e) {
            pass = true;
        } finally {
            System.out.println(pass + "\tproduct3.setPrice(-1);");
        }

        pass = !product1.equals(product2);
        System.out.println(pass + "\t!product3.equals(product1)");

        pass = product3.toString().equals(product3.id + System.lineSeparator()
                + product3.name + System.lineSeparator()
                + product3.year + System.lineSeparator()
                + product3.price + System.lineSeparator());
        System.out.println(pass + "\tproduct3.toString().equals(product3.id" + System.lineSeparator()
                + "        + product3.name" + System.lineSeparator()
                + "        + product3.year" + System.lineSeparator()
                + "        + product3.price");

    }
}
