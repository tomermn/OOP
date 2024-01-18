public class Tournament {
    int rounds;
    Renderer renderer;
    Player player1;
    Player player2;

    public Tournament(int rounds, Renderer renderer, Player player1, Player player2) {
        this.rounds = rounds;
        this.renderer = renderer;
        this.player1 = player1;
        this.player2 = player2;

    }

    void playTournament(int size, int winStreak, String playerName1, String playerName2) {
        Game game = new Game(player1, player2, size, winStreak, renderer);
        game.run();
    }
    public static void main(String[] args) {
        HumanPlayer player1 = new HumanPlayer();
        HumanPlayer player2 = new HumanPlayer();

        ConsoleRenderer renderer = new ConsoleRenderer(4);

        Tournament tournament = new Tournament(1, renderer, player1, player2);

        tournament.playTournament(4, 3, "hh", "hh");

    }
}
