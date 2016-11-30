package estoresearch;

/**
 * Gets and sets id, name, year, and price of product
 *
 * @author Courtney Bodi
 */
public abstract class Product {

    public static final String INVALID_ID = "Invalid input: the ID must be a"
            + " six digit long number.";
    public static final String INVALID_NAME = "Invalid input: the name must"
            + " have at least one character.";
    public static final String INVALID_YEAR = "Invalid input: the year must be"
            + " between 1000 and 9999.";
    public static final String INVALID_PRICE = "Invalid input: the price must"
            + " be greater than or equal to 0.";

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
    public Product(String id, String name, int year, double price) throws
            InvalidInputException {
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
    public Product(String id, String name, int year) throws
            InvalidInputException {
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
            return "productID = \"" + id + "\"\n"
                    + "name = \"" + name + "\"\n"
                    + "price = \"\"\n"
                    + "year = \"" + year + "\"\n";
        } else {
            return "productID = \"" + id + "\"\n"
                    + "name = \"" + name + "\"\n"
                    + "price = \"" + price + "\"\n"
                    + "year = \"" + year + "\"\n";
        }
    }
}
