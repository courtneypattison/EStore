package estoresearch;

/**
 * Gets and sets id, name, year, price, and maker of electronic
 *
 * @author Courtney Bodi
 */
public class Electronic extends Product {

    public static final String INVALID_MAKER = "Invalid input: the maker must have at least one character.";

    private String maker;

    /**
     * Electronic constructor with all members
     *
     * @param id is a unique 6 digit string
     * @param name of product
     * @param year product released
     * @param price of product in dollars CAD
     * @param maker of product string
     * @throws estoresearch.InvalidInputException custom input validation
     * checked exception
     */
    public Electronic(String id, String name, int year, double price, String maker) throws InvalidInputException {
        super(id, name, year, price);
        if (maker != null) {
            this.maker = maker;
        } else {
            throw new InvalidInputException(INVALID_MAKER);
        }
    }

    /**
     * Electronic constructor with mandatory members
     *
     * @param id is a unique 6 digit string
     * @param name of product
     * @param year product released
     * @throws estoresearch.InvalidInputException custom input validation
     * checked exception
     */
    public Electronic(String id, String name, int year) throws InvalidInputException {
        super(id, name, year);
    }

    /**
     * Default Electronic constructor
     *
     * @throws estoresearch.InvalidInputException custom input validation
     * checked exception
     */
    public Electronic() throws InvalidInputException {
        super();
        maker = "";
    }

    public Electronic(Electronic electronic) throws InvalidInputException {
        this(electronic.getId(), electronic.getName(), electronic.getYear(), electronic.getPrice(), electronic.getMaker());
    }

    /**
     * Gets maker
     *
     * @return the maker
     */
    public String getMaker() {
        return maker;
    }

    /**
     * Sets maker
     *
     * @param maker the maker to set
     * @throws estoresearch.InvalidInputException custom input validation
     * checked exception
     */
    public void setMaker(String maker) throws InvalidInputException {
        if (maker != null) {
            this.maker = maker;
        } else {
            throw new InvalidInputException(INVALID_MAKER);
        }
    }

    /**
     * Determines if Electronics are equal
     *
     * @return whether or not the Electronics are equal
     */
    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null || getClass() != otherObject.getClass()) {
            return false;
        } else {
            Electronic otherElectronic = (Electronic) otherObject;
            return super.equals(otherElectronic)
                    && maker.equals(otherElectronic.maker);
        }
    }

    /**
     * Gets string with all members of Electronic
     *
     * @return string in the form of id, name, year, price, maker
     */
    @Override
    public String toString() {
        return "type = \"electronics\"" + System.lineSeparator()
                + super.toString()
                + "maker = \"" + maker + "\"" + System.lineSeparator();
    }

    /**
     * Main method for testing Electronic
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Electronic elec1 = null, elec2 = null;
        boolean pass = true;

        System.out.println("Electronic Class Testing" + System.lineSeparator()
                + "Prints true when test is passed, false otherwise" + System.lineSeparator());

        try {
            elec1 = new Electronic("000000", "foo", 1990, 200.00, "hello");
        } catch (InvalidInputException e) {
            pass = false;
        } finally {
            System.out.println(pass + "\tnew Electronic(\"000000\", \"foo\", 1990, 200.00, \"hello\")");
        }

        try {
            elec2 = new Electronic("000001", "foo", 1990);
        } catch (InvalidInputException e) {
            pass = false;
        } finally {
            System.out.println(pass + "\tnew Electronic(\"000001\", \"foo\", 1990)");
        }

        try {
            elec1.setMaker("");
        } catch (InvalidInputException e) {
            pass = true;
        } finally {
            System.out.println(pass + "\telec1.setMaker(\"\")");
        }

        pass = !elec1.equals(elec2);
        System.out.println(pass + "\t!elec1.equals(elec2)");

        System.out.println(elec1.toString());

    }
}
