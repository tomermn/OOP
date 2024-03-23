/* package */
package exceptions;

 /**
 * Represents an exception thrown when an "add" command is illegal due to incorrect format.
 * Extends the {@code IllegalCommandException} class, indicating an illegal command in general.
 */
public class IllegalAddException extends IllegalCommandException {
    private static final String ERROR_MESSAGE = "Did not add due to incorrect format.";

    /**
     * Constructs a new {@code IllegalAddException} with the default error message.
     */
    public IllegalAddException(){
        super(ERROR_MESSAGE);
    }
}
