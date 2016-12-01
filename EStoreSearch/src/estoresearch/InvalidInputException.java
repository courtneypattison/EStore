package estoresearch;

/**
 * Checked exception for invalid user input
 *
 * @author Courtney Bodi
 */
public class InvalidInputException extends Exception {

    /**
     * Creates a new instance of <code>InvalidInputException</code> without
     * detail message.
     */
    public InvalidInputException() {
    }

    /**
     * Constructs an instance of <code>InvalidInputException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidInputException(String msg) {
        super(msg);
    }
}
