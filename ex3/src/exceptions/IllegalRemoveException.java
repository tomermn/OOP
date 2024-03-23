/* package */
package exceptions;
/**
 * Represents an exception thrown when a "remove" command is illegal due to incorrect format.
 * Extends the {@code IllegalCommandException} class, indicating a general illegal command scenario.
 */
public class IllegalRemoveException extends IllegalCommandException {
    private static final String ERROR_MESSAGE = "Did not remove due to incorrect format.";

    /**
     * Constructs a new {@code IllegalRemoveException} with the default error message.
     */
    public IllegalRemoveException(){
        super(ERROR_MESSAGE);
    }
}
