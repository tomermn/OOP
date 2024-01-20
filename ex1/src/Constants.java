/**
 * The Constants class contains a set of constant values used throughout the program.
 * These constants include messages for various scenarios, symbols for players,
 * default values, streak types, and expected argument values.
 * Additionally, the class provides a method for generating a text prompt for HumanPlayer.
 */
public class Constants {

    /* Constant for error messages */
    public final static String UNKNOWN_PLAYER_NAME = "Choose a player, and start again." +
            "\nThe players: [human, clever, whatever, genius]";
    public final static String UNKNOWN_RENDERER_NAME = "Choose a renderer, and start again. " +
            "\nPlease choose one of the following [console, none]";
    public final static String INVALID_COORDINATE = "Invalid mark position," +
            " please choose a different position.\n" +
            "Invalid coordinates, type again: ";
    public final static String OCCUPIED_COORDINATE = "Mark position is already occupied.\n" +
            "Invalid coordinates, type again: ";


    /* Constant for tournament result message */
    public final static String TOURNAMENTS_RESULT_HEADLINE = "######### Results #########";
    public final static String TOURNAMENTS_RESULT_PLAYER1 = "Player 1, %s won: %d rounds%n";
    public final static String TOURNAMENTS_RESULT_PLAYER2 = "Player 2, %s won: %d rounds%n";
    public final static String TOURNAMENTS_RESULT_TIES = "Ties: %d%n";


    /* Constants for distinguish players symbols for printing input requests */
    public final static String MARK_X = "X";
    public final static String MARK_O = "O";


    /* Constant for default values */
    public final static int DEFAULT_WIN_STREAK = 3;
    public final static int DEFAULT_BOARD_SIZE = 4;


    /* Constants defining streak types in the Game class, guiding the board-checking process */
    public final static String ROW = "ROW";
    public final static String COL = "COL";
    public final static String DIAGONAL_LEFT = "DIAGONAL_LEFT";
    public final static String DIAGONAL_RIGHT = "DIAGONAL_RIGHT";


    /* Expected arguments values for the program. */
    public final static String HUMAN_PLAYER_NAME = "human";
    public final static String WHATEVER_PLAYER_NAME = "whatever";
    public final static String CLEVER_PLAYER_NAME = "clever";
    public final static String GENIUS_PLAYER_NAME = "genius";
    public final static String CONSOLE_RENDERER_NAME = "console";
    public final static String VOID_RENDERER_NAME = "none";
    public final static int MIN_WIN_STREAK = 2;


    /**
     * Default constructor for the Constants class.
     */
     public Constants() {}

    /**
     * Use this method to generate the text that HumanPlayer should send
     *
     * @param markSymbol according to the Mark the player uses in the current turn.
     * @return String to be printed to the player.
     */
    public static String playerRequestInputString(String markSymbol) {
        return "Player " + markSymbol + ", type coordinates: ";

    }
}


