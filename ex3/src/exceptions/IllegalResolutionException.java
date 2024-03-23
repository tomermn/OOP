/* package */
package exceptions;

/**
 * Represents an exception thrown when a resolution change command has an incorrect format.
 * Extends the {@code IllegalCommandException} class, indicating a general illegal command scenario.
 */

public class IllegalResolutionException extends IllegalCommandException {
    private static final String ERROR_MESSAGE = "Did not change resolution due to incorrect format.";

    /**
     * Constructs a new {@code IllegalResolutionException} with the default error message.
     */
    public IllegalResolutionException(){
        super(ERROR_MESSAGE);
    }
}
