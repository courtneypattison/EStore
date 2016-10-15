package estoresearch;

/**
 * @author Courtney Bodi
 */
public class Book extends Product {

    private static final String INVALID_AUTHOR = "Invalid input: the author must have at least one character.";
    private static final String INVALID_PUBLISHER = "Invalid input: the publisher must have at least one character.";

    private String author, publisher;

    /**
     * Book constructor with all fields
     *
     * @param id
     * @param name
     * @param year
     * @param price
     * @param author
     * @param publisher
     */
    public Book(String id, String name, int year, double price, String author, String publisher) {
        super(id, name, year, price);

        if (validateString(author)) {
            this.author = author;
        } else {
            throw new IllegalArgumentException(INVALID_AUTHOR);
        }

        if (validateString(publisher)) {
            this.publisher = publisher;
        } else {
            throw new IllegalArgumentException(INVALID_PUBLISHER);
        }
    }

    /**
     * Book constructor with mandatory fields
     *
     * @param id
     * @param name
     * @param year
     */
    public Book(String id, String name, int year) {
        super(id, name, year);
    }
    
    public Book() {
        super("", "", 0, 0.0);
        author = publisher = "";
    }

    /**
     * Gets author
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets author
     *
     * @param author the author to set
     */
    public void setAuthor(String author) {
        if (validateString(author)) {
            this.author = author;
        } else {
            throw new IllegalArgumentException(INVALID_AUTHOR);
        }
    }

    /**
     * Gets publisher
     *
     * @return the publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Sets publisher
     *
     * @param publisher the publisher to set
     */
    public void setPublisher(String publisher) {
        if (validateString(publisher)) {
            this.publisher = publisher;
        } else {
            throw new IllegalArgumentException(INVALID_PUBLISHER);
        }
    }
    
    /**
     * Main method for testing Book
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Book book1 = null, book2 = null, book3 = null;
        boolean pass = true;
        
        System.out.println("Book Class Testing" + System.lineSeparator()
                + "Prints true when test is passed, false otherwise" + System.lineSeparator());
        
        try {
            book1 = new Book("000000", "foo", 1990, 200.00, "hello", "yo");
        } catch (IllegalArgumentException e) {
            pass = false;
        } finally {
            System.out.println(pass + "\tnew Book(\"000000\", \"foo\", 1990, 200.00, \"hello\")");
        }
        
        try {
            book2 = new Book("000001", "foo", 1990);
        } catch (IllegalArgumentException e) {
            pass = false;
        } finally {
            System.out.println(pass + "\tnew Book(\"000001\", \"foo\", 1990)");
        }
        
        try {
            book1.setAuthor("");
        } catch (IllegalArgumentException e) {
            pass = true;
        } finally {
            System.out.println(pass + "\tbook1.setMaker(\"\")");
        }
        
        try {
            book1.setPublisher("Springer");
        } catch (IllegalArgumentException e) {
            pass = false;
        } finally {
            System.out.println(pass + "\tbook1.setPublisher(\"Springer\")");
        }
        
        pass = !book1.equals(book2);
        System.out.println(pass + "\t!elec1.equals(elec2)");
        
    }
}
