/* package */
package exceptions;


/**
 * Represents a generic exception thrown when a command is illegal.
 * This class extends the {@code Exception} class, indicating a general exception scenario.
 */
public class IllegalCommandException extends Exception{

    /**
     * Constructs a new {@code IllegalCommandException} with the specified exception message.
     *
     * @param exceptionMessage the message describing the reason for the exception
     */
    public IllegalCommandException(String exceptionMessage){

        super(exceptionMessage);
    }
}
