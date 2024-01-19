import java.util.Locale;

/**
 * Represents a Tic Tac Toe tournament between two players.
 * Manages multiple rounds and keeps track of points.
 * @author Tomer Meidan
 */
public class Tournament {
    private int rounds;
    private Renderer renderer;
    private Player player1;
    private Player player2;
    private int player1Points = 0;
    private int player2Points = 0;
    private int ties = 0;


    /**
     * Constructs a Tournament with the specified parameters.
     *
     * @param rounds   the number of rounds in the tournament
     * @param renderer the renderer for displaying the game
     * @param player1  the first Player instance
     * @param player2  the second Player instance
     */
    public Tournament(int rounds, Renderer renderer, Player player1, Player player2) {
        this.rounds = rounds;
        this.renderer = renderer;
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Plays the entire tournament with the specified parameters.
     *
     * @param size        the size of the game board
     * @param winStreak   the required win streak to win a round
     * @param playerName1 the name of player 1
     * @param playerName2 the name of player 2
     */
    public void playTournament(int size, int winStreak, String playerName1, String playerName2) {
        for (int round = 0; round < rounds; round++) {
            playSingleRound(round, size, winStreak);
        }
        printResults(playerName1, playerName2);
    }

    /**
     * Plays a single round of the tournament.
     *
     * @param round     the current round number
     * @param size      the size of the game board (row length, col length)
     * @param winStreak the required win streak to win a round
     */
    private void playSingleRound(int round, int size, int winStreak) {
        Game game;
        if (round % 2 == 0) {
            game = new Game(player1, player2, size, winStreak, renderer);
        }
        else{
            game = new Game(player2, player1, size, winStreak, renderer);
        }
        Mark gameResult = game.run();
        calculateResults(gameResult, round);

    }

    /**
     * Prints the results of the tournament.
     *
     * @param player1Name the name of player 1
     * @param player2Name the name of player 2
     */
    private void printResults(String player1Name, String player2Name) {
        System.out.println("######### Results #########");
        System.out.printf("Player 1, %s won: %d rounds%n", player1Name, player1Points);
        System.out.printf("Player 2, %s won: %d rounds%n", player2Name, player2Points);
        System.out.printf("Ties: %d%n", ties);
    }

    /**
     * Calculates the results of a game and updates the points accordingly.
     *
     * @param victoryMark the mark of the victory streak.
     * @param round       the current round number
     */
    private void calculateResults(Mark victoryMark, int round) {
        switch (victoryMark) {
            case BLANK:
                ties++;
                break;
            case X:
                if (round % 2 == 0){
                    player1Points++;
                } else {
                    player2Points++;
                }
                break;
            case O:
                if (round % 2 == 0) {
                    player2Points++;
                } else {
                    player1Points++;
                }
                break;
            default:
                break;
        }
    }



    /**
     * Validates the command-line arguments for the tournament.
     *
     * @param args the command-line arguments
     * @return true if the arguments are valid, false otherwise
     */
    private static boolean checkArgsValidation (String[] args) {
        return isRendererNameValid(args[Constants.RENDERER_ARG]) &&
                isPlayerNameValid(args[Constants.PLAYER1_ARG]) &&
                isPlayerNameValid(args[Constants.PLAYER2_ARG]);
    }

    /**
     * Checks if the specified renderer name is valid.
     *
     * @param rendererName the renderer name to be validated from the CLI.
     * @return true if the renderer name is valid, false otherwise
     */
    private static boolean isRendererNameValid (String rendererName) {
        if (!(rendererName.equalsIgnoreCase(Constants.CONSOLE_RENDERER_NAME) || rendererName.equalsIgnoreCase(Constants.VOID_RENDERER_NAME))) {
            System.out.println(Constants.UNKNOWN_RENDERER_NAME);
            return false;
        }
        return true;
    }

    /**
     * Checks if the specified player name is valid.
     *
     * @param playerName the player name to be validated from the CLI.
     * @return true if the player name is valid, false otherwise
     */
    private static boolean isPlayerNameValid(String playerName) {

        if (!(playerName.equalsIgnoreCase(Constants.HUMAN_PLAYER_NAME) || playerName.equalsIgnoreCase(Constants.RANDOM_PLAYER_NAME) ||
                playerName.equalsIgnoreCase(Constants.CLEVER_PLAYER_NAME) || playerName.equalsIgnoreCase(Constants.GENIUS_PLAYER_NAME))) {
            System.out.println(Constants.UNKNOWN_PLAYER_NAME);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        if (!checkArgsValidation(args)) {
            return;
        }
        int roundCount = Integer.parseInt(args[Constants.ROUNDS_ARG]);
        int size = Integer.parseInt(args[Constants.SIZE_ARG]);
        int winStreak = Integer.parseInt(args[Constants.WIN_STREAK_ARG]);
        String rendererName = args[Constants.RENDERER_ARG].toLowerCase(Locale.ROOT);
        String player1Name = args[Constants.PLAYER1_ARG].toLowerCase();
        String player2Name = args[Constants.PLAYER2_ARG].toLowerCase();

        PlayerFactory playerFactory = new PlayerFactory();
        RendererFactory rendererFactory = new RendererFactory();
        Player player1 = playerFactory.createPlayer(player1Name);
        Player player2 = playerFactory.createPlayer(player2Name);
        Renderer renderer = rendererFactory.createRenderer(rendererName, size);

        if (player1 != null && player2 != null && renderer != null) {
            Tournament tournament = new Tournament(roundCount, renderer, player1, player2);
            tournament.playTournament(size, winStreak, player1Name, player2Name);
        }
    }
}
