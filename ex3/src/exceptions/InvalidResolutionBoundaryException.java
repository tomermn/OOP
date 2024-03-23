/* package */
package exceptions;

/**
 * Represents an exception thrown when a resolution change command exceeds specified boundaries.
 * Extends the {@code IllegalCommandException} class, indicating a general illegal command scenario.
 */
public class InvalidResolutionBoundaryException extends IllegalCommandException {
    private static final String ERROR_MESSAGE = "Did not change resolution due to exceeding boundaries.";
    /**
     * Constructs a new {@code InvalidResolutionBoundaryException} with the default error message.
     */
    public InvalidResolutionBoundaryException(){
        super(ERROR_MESSAGE);
    }
}
