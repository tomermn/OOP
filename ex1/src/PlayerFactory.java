import java.lang.invoke.CallSite;

public class PlayerFactory {

    public Player createPlayer(String playerName) {
        switch (playerName) {
            case Constants.HUMAN_PLAYER_NAME:
                return new HumanPlayer();

            default:
                return null;

        }
    }
}
