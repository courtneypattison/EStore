package estoresearch;

/**
 * @author Courtney Bodi
 */
public class Electronic extends Product {

    public static final String INVALID_MAKER = "Invalid input: the maker must have at least one character.";

    private String maker;

    /**
     * Electronic constructor with all members
     *
     * @param id
     * @param name
     * @param year
     * @param price
     * @param maker
     */
    public Electronic(String id, String name, int year, double price, String maker) {
        super(id, name, year, price);
        if (maker != null) {
            this.maker = maker;
        } else {
            throw new IllegalArgumentException(INVALID_MAKER);
        }
    }

    /**
     * Electronic constructor with mandatory members
     *
     * @param id
     * @param name
     * @param year
     */
    public Electronic(String id, String name, int year) {
        super(id, name, year);
    }

    /**
     * Default Electronic constructor
     */
    public Electronic() {
        super();
        maker = "";
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
     */
    public void setMaker(String maker) {
        if (maker != null) {
            this.maker = maker;
        } else {
            throw new IllegalArgumentException(INVALID_MAKER);
        }
    }

    /**
     * Determines if Electronics are equal
     *
     * @param other
     * @return whether or not the Electronics are equal
     */
    public boolean equals(Electronic other) {
        return super.equals(other)
                && maker.equals(other.maker);
    }

    /**
     * Gets string with all members of Electronic
     *
     * @return string in the form of id, name, year, price, maker
     */
    @Override
    public String toString() {
        return super.toString() + maker;
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
        } catch (IllegalArgumentException e) {
            pass = false;
        } finally {
            System.out.println(pass + "\tnew Electronic(\"000000\", \"foo\", 1990, 200.00, \"hello\")");
        }
        
        try {
            elec2 = new Electronic("000001", "foo", 1990);
        } catch (IllegalArgumentException e) {
            pass = false;
        } finally {
            System.out.println(pass + "\tnew Electronic(\"000001\", \"foo\", 1990)");
        }
        
        try {
            elec1.setMaker("");
        } catch (IllegalArgumentException e) {
            pass = true;
        } finally {
            System.out.println(pass + "\telec1.setMaker(\"\")");
        }
        
        pass = !elec1.equals(elec2);
        System.out.println(pass + "\t!elec1.equals(elec2)");
        
    }
}
