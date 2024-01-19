public class Constants {
    public final static String UNKNOWN_PLAYER_NAME = "Choose a player, and start again." +
            "\nThe players: [human, clever, whatever, genius]";

    public final static String UNKNOWN_RENDERER_NAME = "Choose a renderer, and start again. " +
            "\nPlease choose one of the following [console, none]";

    public final static String INVALID_COORDINATE = "Invalid mark position," +
            " please choose a different position.\n" +
            "Invalid coordinates, type again: ";

    public final static String OCCUPIED_COORDINATE = "Mark position is already occupied.\n" +
            "Invalid coordinates, type again: ";

    public final static int DEFAULT_WIN_STREAK = 3;
    public final static int DEFAULT_BOARD_SIZE = 4;
    public final static String MARK_X = "X";
    public final static String MARK_O = "O";
    public final static String ROW = "ROW";
    public final static String COL = "COL";
    public final static String DIAGONAL_LEFT = "DIAGONAL_LEFT";
    public final static String DIAGONAL_RIGHT = "DIAGONAL_RIGHT";
    public final static String HUMAN_PLAYER_NAME = "human";
    public final static String RANDOM_PLAYER_NAME = "whatever";
    public final static String CLEVER_PLAYER_NAME = "clever";
    public final static String GENIUS_PLAYER_NAME = "genius";
    public final static String CONSOLE_RENDERER_NAME = "console";
    public final static String VOID_RENDERER_NAME = "none";


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


