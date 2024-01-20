/**
 * The Tournament class represents a Tic-Tac-Toe tournament with multiple rounds.
 * It manages the flow of the tournament, plays rounds of games, and tracks the results.
 *
 * <p>
 * The tournament is played between two players over a specified number of rounds. The results are then
 * displayed, including the points earned by each player and the number of tied games.
 * </p>
 *
 * <p>
 * The tournament can be initiated using command-line arguments for the number of rounds, board size,
 * win streak length, renderer type, and player types for Player 1 and Player 2.
 * </p>
 *
 * @author Tomer Meidan
 */
public class Tournament {
    private final int rounds;
    private final Renderer renderer;
    private final Player player1;
    private final Player player2;
    private int player1Points = 0;
    private int player2Points = 0;
    private int ties = 0;

    /**
     * Constructs a new Tournament instance with the specified number of rounds, renderer, and players.
     *
     * @param rounds    The number of rounds in the tournament.
     * @param renderer  The renderer used to display the game board.
     * @param player1   The player assigned as Player 1.
     * @param player2   The player assigned as Player 2.
     */
    public Tournament(int rounds, Renderer renderer, Player player1, Player player2) {
        this.rounds = rounds;
        this.renderer = renderer;
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Main method for running the tournament. Validates command-line arguments and initiates the tournament.
     *
     * @param args Command-line arguments for the tournament configuration.
     */
    public static void main(String[] args) {
        if (!checkArgsValidation(args)) {
            return;
        }

        // Parsing command-line arguments
        int roundCount = Integer.parseInt(args[0]);
        int size = Integer.parseInt(args[1]);
        int winStreak = Integer.parseInt(args[2]);
        String rendererName = args[3].toLowerCase();
        String player1Name = args[4].toLowerCase();
        String player2Name = args[5].toLowerCase();

        // Building players, renderer, and initiating the tournament
        PlayerFactory playerFactory = new PlayerFactory();
        RendererFactory rendererFactory = new RendererFactory();
        Player player1 = playerFactory.buildPlayer(player1Name);
        Player player2 = playerFactory.buildPlayer(player2Name);
        Renderer renderer = rendererFactory.buildRenderer(rendererName, size);

        if (player1 != null && player2 != null && renderer != null) {
            Tournament tournament = new Tournament(roundCount, renderer, player1, player2);
            tournament.playTournament(size, winStreak, player1Name, player2Name);
        }
    }

    /**
     * Plays the tournament by running multiple rounds and printing the final results.
     *
     * @param size        The size of the game board.
     * @param winStreak   The win streak length.
     * @param playerName1 The name of Player 1.
     * @param playerName2 The name of Player 2.
     */
    public void playTournament(int size, int winStreak, String playerName1, String playerName2) {
        for (int round = 0; round < rounds; round++) {
            playSingleRound(round, size, winStreak);
        }
        printResults(playerName1, playerName2);
    }

    /*
     * Plays a single round of the tournament.
     */
    private void playSingleRound(int round, int size, int winStreak) {
        Game game;

        // In even rounds, player1 plays with the X mark. Otherwise, player2.
        if (round % 2 == 0) {
            game = new Game(player1, player2, size, winStreak, renderer);
        }
        else{
            game = new Game(player2, player1, size, winStreak, renderer);
        }
        Mark gameResult = game.run();
        updateTournamentResult(gameResult, round);
    }

    /*
     * Prints the final results of the tournament.
     */
    private void printResults(String player1Name, String player2Name) {
        System.out.println(Constants.TOURNAMENTS_RESULT_HEADLINE);
        System.out.printf(Constants.TOURNAMENTS_RESULT_PLAYER1, player1Name, player1Points);
        System.out.printf(Constants.TOURNAMENTS_RESULT_PLAYER2, player2Name, player2Points);
        System.out.printf(Constants.TOURNAMENTS_RESULT_TIES, ties);
    }

    /*
     * Updates the tournament results based on the outcome of a single round.
     */
    private void updateTournamentResult(Mark victoryMark, int round) {
        switch (victoryMark) {
            case BLANK:
                ties++;
                break;

            case X:
                if (round % 2 == 0){
                    player1Points++; // In even rounds, player1 plays as X.
                } else {
                    player2Points++;
                }
                break;

            case O:
                if (round % 2 == 0) { // In even rounds, player2 plays as O.
                    player2Points++;
                } else {
                    player1Points++;
                }
                break;

            default:
                break;
        }
    }

    /*
     * Checks the validity of command-line arguments for the tournament.
     */
    private static boolean checkArgsValidation (String[] args) {
        return isRendererNameValid(args[3]) && isPlayerNameValid(args[4]) &&
                isPlayerNameValid(args[5]);
    }

    /*
     * Checks if the specified renderer name is valid.
     */
    private static boolean isRendererNameValid (String rendererName) {
        if (!(rendererName.equalsIgnoreCase(Constants.CONSOLE_RENDERER_NAME) ||
                rendererName.equalsIgnoreCase(Constants.VOID_RENDERER_NAME))) {
            System.out.println(Constants.UNKNOWN_RENDERER_NAME);
            return false;
        }
        return true;
    }

    /*
     * Checks if the specified player name is valid.
     */
    private static boolean isPlayerNameValid(String playerName) {

        if (!(playerName.equalsIgnoreCase(Constants.HUMAN_PLAYER_NAME) ||
              playerName.equalsIgnoreCase(Constants.WHATEVER_PLAYER_NAME) ||
              playerName.equalsIgnoreCase(Constants.CLEVER_PLAYER_NAME) ||
              playerName.equalsIgnoreCase(Constants.GENIUS_PLAYER_NAME))) {
            System.out.println(Constants.UNKNOWN_PLAYER_NAME);
            return false;
        }
        return true;
    }
}
