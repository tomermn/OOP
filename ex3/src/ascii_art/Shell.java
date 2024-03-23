/* package */
package ascii_art;

/* imports */
import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import exceptions.*;
import image.Image;
import image.ImageUtils;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;


/**
 * The Shell class represents a command-line shell application that allows users to interact with
 * ASCII art generation and manipulation. It provides various commands to modify character sets,
 * change image settings,change resolution,change outputFormat and run ASCII art algorithms.
 */
public class Shell {

    // Shell default configurations
    private static final String COMMAND_PREFIX = ">>> ";
    private static final int CHANGE_RESOLUTION_FACTOR = 2;

    // Shell commands
    private static final String EXIT_COMMAND = "exit";
    private static final String DISPLAY_CHARS_COMMAND = "chars";
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String CHANGE_RESOLUTION_COMMAND = "res";
    private static final String CHANGE_IMAGE_COMMAND = "image";
    private static final String CHANGE_OUTPUT_COMMAND = "output";
    private static final String RUN_ALGORITHM_COMMAND = "asciiArt";
    private static final String ALL_COMMAND = "all";
    private static final String SPACE_COMMAND = "space";
    private static final char DASH = '-';
    private static final String SPACE = " ";
    private static final String RESOLUTION_UP_COMMAND = "up";
    private static final String RESOLUTION_DOWN_COMMAND = "down";
    private static final int COMMAND_MAX_LENGTH = 2;

    // Ascii values
    private static final int FIRST_ASCII = 32;
    private static final int LAST_ASCII = 126;

    // Output
    private static final String CONSOLE_NAME = "console";
    private static final String HTML_NAME = "html";
    private static final String HTML_FONT = "Courier New";
    private static final String OUTPUT_PATH = "out.html";

    // Image
    private static final String DEFAULT_IMAGE_PATH = "cat.jpeg";

    // Messages
    private static final String SET_RESOLUTION_MESSAGE = "Resolution set to %d.";

    // Shell fields
    private int resolution = 128;
    private String outputDestination = CONSOLE_NAME;
    private final Set<Character> charset = new TreeSet<>(Set.of('0','1','2','3','4','5','6','7','8','9'));
    private Image currentImage;
    private String currentImagePath = DEFAULT_IMAGE_PATH;
    private boolean toExit = false;

    /**
     * Main method to start the shell application.
     *
     * @param args the command-line arguments
     */
    public static void main (String[] args) {
        Shell shellInstance = new Shell();
        shellInstance.run();
    }

    /**
     * Constructs a new Shell instance.
     */
    public Shell(){}

    /**
     * Starts the shell and handles user input until the user exits.
     */
    public void run(){
        String[] inputCommands;
        try {
            buildNewImage(DEFAULT_IMAGE_PATH);
        }
        catch (InvalidImageException e) {
            System.out.println(e.getMessage());
            return;
        }
        toExit = false;
        while (!toExit) {
            System.out.print(COMMAND_PREFIX);
            inputCommands = getUserInput();
            try {
                executeCommand(inputCommands);
            }
            catch (IllegalCommandException exception){
                System.out.println(exception.getMessage());
            }
        }
    }

    /*
     * Executes the command entered by the user.
     *
     * throws InvalidCommand if the command is invalid
     */
    private void executeCommand(String[] inputCommands) throws IllegalCommandException {
        String mainCommand = inputCommands[0];
        switch(mainCommand){
            case(EXIT_COMMAND):
                toExit = true;
                return;

            case(DISPLAY_CHARS_COMMAND):
                displayCharset();
                return;

            case(ADD_COMMAND):
                modifyCharset(inputCommands,true);
                break;

            case(REMOVE_COMMAND):
                modifyCharset(inputCommands,false);
                break;

            case(CHANGE_RESOLUTION_COMMAND):
                changeResolution(inputCommands);
                return;

            case(CHANGE_IMAGE_COMMAND):
                changeImage(inputCommands);
                break;

            case(CHANGE_OUTPUT_COMMAND):
                changeOutput(inputCommands);
                break;

            case(RUN_ALGORITHM_COMMAND):
                runAsciiArtAlgorithm();
                return;

            default:
                throw new IllegalCommandNameException();
        }
    }

    /*
     * Changes the output destination.
     *
     * throws InvalidOutputCommand if the output command is invalid
     */
    private void changeOutput (String[] userInput) throws IllegalOutputException {
        if (userInput.length > COMMAND_MAX_LENGTH || userInput.length == 1) {
            throw new IllegalOutputException();
        }
        switch (userInput[1]) {
            case CONSOLE_NAME:
                outputDestination = CONSOLE_NAME;
                break;
            case HTML_NAME:
                outputDestination = HTML_NAME;
                break;
            default:
                throw new IllegalOutputException();
        }
    }

    /*
     * Runs the ASCII art algorithm.
     */
    private void runAsciiArtAlgorithm() throws IllegalRunAlgorithmException {
        if (charset.isEmpty()) {
            throw new IllegalRunAlgorithmException();
        }
        char[] charset = convertTreeToArray();
        AsciiArtAlgorithm currentAlgorithm = new AsciiArtAlgorithm(currentImage, resolution, charset);
        char [][] result = currentAlgorithm.run();
        AsciiOutput output = initOutput();
        if (output != null) {
            output.out(result);
        }
    }

    /*
     * Initializes the output destination.
     */
    private AsciiOutput initOutput() {
        switch (outputDestination) {
            case CONSOLE_NAME :
                return new ConsoleAsciiOutput();
            case HTML_NAME:
                return new HtmlAsciiOutput(OUTPUT_PATH, HTML_FONT);
            default:
                return null;
        }
    }

    /*
     * Converts the character set tree to an array.
     */
    private char[] convertTreeToArray() {
        char[] charset = new char[this.charset.size()];
        int i = 0;
        for (char c : this.charset) {
            charset[i] = c;
            i++;
        }
        return charset;
    }

    /*
     * Changes the resolution based on user input.
     *
     * throws InvalidCommand if the resolution command is invalid
     * (if the format is invalid or exceeding boundaries)
     */
    private void changeResolution(String[] userInput) throws IllegalCommandException {
        if (userInput.length > COMMAND_MAX_LENGTH) {
            throw new IllegalResolutionException();
        }
        switch (userInput[1]) {
            case RESOLUTION_UP_COMMAND:
                increaseResolution();
                break;
            case RESOLUTION_DOWN_COMMAND:
                decreaseResolution();
                break;
            default:
                throw new IllegalResolutionException();
        }
        System.out.println(String.format(SET_RESOLUTION_MESSAGE, resolution));
    }

    /*
     * Increases the resolution.
     *
     * throws InvalidResolutionBoundary if the resolution boundary is exceeded.
     */
    private void increaseResolution() throws InvalidResolutionBoundaryException {
        if (resolution >= currentImage.getWidth()) {
            throw new InvalidResolutionBoundaryException();
        }
        resolution *= CHANGE_RESOLUTION_FACTOR;
    }

    /*
     * Decreases the resolution.
     *
     * throws InvalidResolutionBoundary if the resolution boundary is exceeded.
     */
    private void decreaseResolution() throws InvalidResolutionBoundaryException {
        int minCharsInRow = Math.max(1, currentImage.getWidth() / currentImage.getHeight());
        if (resolution <= minCharsInRow) {
            throw new InvalidResolutionBoundaryException();
        }
        resolution /= CHANGE_RESOLUTION_FACTOR;
    }

    /*
     * Displays the character set.
     */
    private void displayCharset() {
        for (char c : charset) {
            System.out.print(c + SPACE);
        }
        System.out.println();
    }

    /*
     * Changes the current image based on user input.
     *
     * throws InvalidImage if the image is invalid
     */
    private void changeImage(String[] inputCommands) throws InvalidImageException {
        if (inputCommands.length > COMMAND_MAX_LENGTH){
            throw new InvalidImageException();
        }
        if(inputCommands[1].equals(currentImagePath)){
            return;
        }
        buildNewImage(inputCommands[1]);
        currentImagePath = inputCommands[1];
    }

    /*
     * Modifies the character set based on user input.
     *
     * throws InvalidCommand if the command is invalid
     */
    private void modifyCharset(String[] inputCommands, boolean toAdd) throws IllegalCommandException {
        if (inputCommands.length > COMMAND_MAX_LENGTH) {
            handleCharsetModifyError(toAdd);
        }
        String subCommand = inputCommands[1];
        if (subCommand.equals(ALL_COMMAND)) {
            modifyRangeInCharset(FIRST_ASCII, LAST_ASCII, toAdd);
        }

        else if (subCommand.equals((SPACE_COMMAND))) {
            modifyCharInCharset((char) FIRST_ASCII, toAdd);
        }
        else if (subCommand.length() == 1) {
            modifyCharInCharset(subCommand.charAt(0), toAdd);
        }
        else if (subCommand.length() == 3 && subCommand.charAt(1) == DASH) {
            handleRangeCommand(subCommand, toAdd);
        }
        else {
            handleCharsetModifyError(toAdd);
        }
    }

    /*
     * Modifies a character in the charset.
     */
    private void modifyCharInCharset(char character, boolean toAdd) throws IllegalCommandException {
        if(validateChar(character)){
            handleCharsetModifyError(toAdd);
        }
        if (toAdd) {
            charset.add(character);
        }
        else{
            charset.remove(character);
        }
    }

    /*
     * Modifies a range of characters in the charset.
     */
    private void modifyRangeInCharset(int first, int last, boolean toAdd){
        for (int i = first; i <= last; i++) {
            if (toAdd) {
                charset.add((char) i);
            }
            else{
                charset.remove((char) i);
            }
        }
    }

    /*
     * Handles errors that occur during charset modification.
     */
    private void handleCharsetModifyError(boolean toAdd) throws IllegalCommandException {
        throw (toAdd ? new IllegalAddException() : new IllegalRemoveException());
    }

    /*
     * Handles range commands during charset modification.
     *
     * throws InvalidCommand if the command parameters is invalid
     */
    private void handleRangeCommand(String input, boolean toAdd) throws IllegalCommandException {
        int firstCharVal = input.charAt(0);
        int lastCharVal = input.charAt(2);
        if (validateChar(firstCharVal) || validateChar(lastCharVal)){
            handleCharsetModifyError(toAdd);
        }

        int maxVal = Math.max(firstCharVal, lastCharVal);
        int minVal = Math.min(lastCharVal,firstCharVal);
        modifyRangeInCharset(minVal,maxVal,toAdd);
    }

    /*
     * Retrieves user input from the console.
     */
    private String[] getUserInput(){
        String input = KeyboardInput.readLine();
        return input.split(SPACE);
    }

    /*
     * Builds a new image based on the specified image path.
     *
     * throws InvalidImage if the image is invalid.
     */
    private void buildNewImage(String imagePath) throws InvalidImageException {
        try {
            this.currentImage = ImageUtils.padAndResizeImage(new Image(imagePath));
        }
        catch (IOException e) {
            throw new InvalidImageException();
        }
    }

    /*
     * Validate that a char is between the first and last ascii
     */
    private boolean validateChar(int c){
        return c > LAST_ASCII || c < FIRST_ASCII;
    }
}
