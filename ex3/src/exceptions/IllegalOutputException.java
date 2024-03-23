/* package */
package exceptions;


/**
 * Exception thrown to indicate that the output method cannot be changed due to an incorrect format.
 * This class extends IllegalCommandException and provides a specific error message for the situation.
 */
public class IllegalOutputException extends IllegalCommandException {
    private static final String ERROR_MESSAGE = "Did not change output method due to incorrect format.";

    /**
     * Constructs an IllegalOutputException with the predefined error message.
     */
    public IllegalOutputException(){
        super(ERROR_MESSAGE);
    }
}
