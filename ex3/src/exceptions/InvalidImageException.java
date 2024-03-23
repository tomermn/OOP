/* package */
package exceptions;

/**
 * Represents an exception thrown when an image file is invalid and cannot be processed.
 * Extends the {@code InvalidImageException} class, indicating a general illegal command scenario.
 */
public class InvalidImageException extends IllegalCommandException {
    private static final String ERROR_MESSAGE = "Did not execute due to problem with image file.";
    /**
     * Constructs a new {@code InvalidImageException} with the default error message.
     */
    public InvalidImageException(){
        super(ERROR_MESSAGE);
    }
}
