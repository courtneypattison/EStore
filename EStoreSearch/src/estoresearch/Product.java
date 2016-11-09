package estoresearch;

/**
 * Gets and sets id, name, year, and price of product
 *
 * @author Courtney Bodi
 */
public class Product {

    public static final String INVALID_ID = "Invalid input: the ID must be a six digit long number.";
    public static final String INVALID_NAME = "Invalid input: the name must have at least one character.";
    public static final String INVALID_YEAR = "Invalid input: the year must be between 1000 and 9999.";
    public static final String INVALID_PRICE = "Invalid input: the price must be greater than or equal to 0.";

    public static final int NO_PRICE = -1;

    public static final int MIN_YEAR = 1000;
    public static final int MAX_YEAR = 9999;

    public static final int ID_LENGTH = 6;

    private String id, name;
    private int year;
    private double price;

    /**
     * Product constructor with all members
     *
     * @param id is a unique 6 digit string
     * @param name of product
     * @param year product released
     * @param price of product in dollars CAD
     * @throws estoresearch.InvalidInputException custom input validation
     * checked exception
     */
    public Product(String id, String name, int year, double price) throws InvalidInputException {
        if (validateId(id)) {
            this.id = id;
        } else {
            throw new InvalidInputException(INVALID_ID);
        }

        if (validateString(name)) {
            this.name = name;
        } else {
            throw new InvalidInputException(INVALID_NAME);
        }

        if (validateYear(year)) {
            this.year = year;
        } else {
            throw new InvalidInputException(INVALID_YEAR);
        }

        if (validatePrice(price)) {
            this.price = price;
        } else {
            throw new InvalidInputException(INVALID_PRICE);
        }
    }

    /**
     * Product constructor with all mandatory members
     *
     * @param id is a unique 6 digit string
     * @param name of product
     * @param year product released
     * @throws estoresearch.InvalidInputException custom input validation
     * checked exception
     */
    public Product(String id, String name, int year) throws InvalidInputException {
        this(id, name, year, 0.0);
    }

    /**
     * Default Product constructor
     *
     * @throws estoresearch.InvalidInputException custom input validation
     * checked exception
     */
    public Product() throws InvalidInputException {
        this("000000", " ", MIN_YEAR, 0.0);
    }

    public Product(Product product) throws InvalidInputException {
        this(product.id, product.name, product.year, product.price);
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
     * @param id is a unique 6 digit string
     * @return whether or not the id is valid
     */
    public boolean validateId(String id) {
        return id != null && id.matches("^\\d+$") && id.length() == ID_LENGTH;
    }

    /**
     * Set id
     *
     * @param id the id to set
     * @throws estoresearch.InvalidInputException custom input validation
     * checked exception
     */
    public void setId(String id) throws InvalidInputException {
        if (validateId(id)) {
            this.id = id;
        } else {
            throw new InvalidInputException(INVALID_ID);
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
     * @param string is any string that is not null or empty
     * @return whether or not the string is valid
     */
    public boolean validateString(String string) {
        return string != null && string.length() > 0;
    }

    /**
     * Set name
     *
     * @param name the name to set
     * @throws estoresearch.InvalidInputException custom input validation
     * checked exception
     */
    public void setName(String name) throws InvalidInputException {
        if (validateString(name)) {
            this.name = name;
        } else {
            throw new InvalidInputException(INVALID_NAME);
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
     * @param year in format 0000
     * @return whether or not the year is valid
     */
    private boolean validateYear(int year) {
        return year >= MIN_YEAR && year <= MAX_YEAR;
    }

    /**
     * Set year
     *
     * @param year the year to set
     * @throws estoresearch.InvalidInputException custom input validation
     * checked exception
     */
    public void setYear(int year) throws InvalidInputException {
        if (validateYear(year)) {
            this.year = year;
        } else {
            throw new InvalidInputException(INVALID_YEAR);
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
     * @param price of product in dollars
     * @return whether or not the price is valid
     */
    private boolean validatePrice(double price) {
        return price >= NO_PRICE;
    }

    /**
     * Set price
     *
     * @param price the price to set
     * @throws estoresearch.InvalidInputException custom input validation
     * checked exception
     */
    public void setPrice(double price) throws InvalidInputException {
        if (validatePrice(price)) {
            this.price = price;
        } else {
            throw new InvalidInputException(INVALID_PRICE);
        }
    }

    /**
     * Check if Products are equal
     *
     * @param otherObject other Product object
     * @return whether or not the Products are equal
     */
    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null || getClass() != otherObject.getClass()) {
            return false;
        } else {
            Product otherProduct = (Product) otherObject;
            return id.equals(otherProduct.id)
                    && name.equals(otherProduct.name)
                    && year == otherProduct.year
                    && price == otherProduct.price;
        }
    }

    /**
     * Get string with all members of Product
     *
     * @return Product string in the form of id, name, year, price
     */
    @Override
    public String toString() {
        if (price == NO_PRICE) {
            return "productID = \"" + id + "\"" + System.lineSeparator()
                    + "name = \"" + name + "\"" + System.lineSeparator()
                    + "price = \"\"" + System.lineSeparator()
                    + "year = \"" + year + "\"" + System.lineSeparator();
        } else {
            return "productID = \"" + id + "\"" + System.lineSeparator()
                    + "name = \"" + name + "\"" + System.lineSeparator()
                    + "price = \"" + price + "\"" + System.lineSeparator()
                    + "year = \"" + year + "\"" + System.lineSeparator();
        }
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
        } catch (InvalidInputException e) {
            pass = false;
        } finally {
            System.out.println(pass + "\tnew Product()");
        }

        try {
            product2 = new Product("000025", "Absolute Java", 2015);
        } catch (InvalidInputException e) {
            pass = false;
        } finally {
            System.out.println(pass + "\tnew Product(\"000025\", \"Absolute Java\", 2015)");
        }

        try {
            product3 = new Product("000026", "Absolute Java", 2015, 199.95);
        } catch (InvalidInputException e) {
            product3 = null;
            pass = false;
        } finally {
            System.out.println(pass + "\tnew Product(\"000026\", \"Absolute Java\", 2015, 199.95)");
        }

        try {
            product4 = new Product("5", "Absolute Java", 2015, 199.95);
        } catch (InvalidInputException e) {
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
        } catch (InvalidInputException e) {
            pass = true;
        } finally {
            System.out.println(pass + "\tproduct3.setId(\"3\")");
        }

        try {
            product3.setName("");
        } catch (InvalidInputException e) {
            pass = true;
        } finally {
            System.out.println(pass + "\tproduct3.setName(\"\")");
        }

        try {
            product3.setYear(3);
        } catch (InvalidInputException e) {
            pass = true;
        } finally {
            System.out.println(pass + "\tproduct3.setYear(3)");
        }

        try {
            product3.setPrice(NO_PRICE);
        } catch (InvalidInputException e) {
            pass = true;
        } finally {
            System.out.println(pass + "\tproduct3.setPrice(NO_PRICE);");
        }

        pass = !product1.equals(product2);
        System.out.println(pass + "\t!product3.equals(product1)");

        System.out.println(product1.toString());
    }
}
