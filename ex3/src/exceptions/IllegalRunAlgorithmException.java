package exceptions;

/**
 * Exception thrown to indicate that an execution algorithm cannot proceed because the charset is empty.
 * This class extends IllegalCommandException and provides a specific error message for the situation.
 */
public class IllegalRunAlgorithmException extends IllegalCommandException {
    private static final String ERROR_MESSAGE = "Did not execute. Charset is empty.";

    /**
     * Constructs an IllegalRunAlgorithmException with the predefined error message.
     */
    public IllegalRunAlgorithmException(){
        super(ERROR_MESSAGE);
    }
}
