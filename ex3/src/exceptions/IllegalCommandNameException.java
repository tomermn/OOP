/* package */
package exceptions;

/**
 * Represents an exception thrown when a command name is illegal and cannot be executed.
 * Extends the {@code IllegalCommandException} class, indicating a general illegal command scenario.
 */
public class IllegalCommandNameException extends IllegalCommandException {
    private static final String ERROR_MESSAGE = "Did not execute due to incorrect command.";

    /**
     * Constructs a new {@code IllegalCommandNameException} with the default error message.
     */
    public IllegalCommandNameException(){
        super(ERROR_MESSAGE);
    }
}
