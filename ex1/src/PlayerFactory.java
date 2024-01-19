import java.lang.invoke.CallSite;

public class PlayerFactory {

    public Player createPlayer(String playerName) {
        switch (playerName) {
            case Constants.HUMAN_PLAYER_NAME:
                return new HumanPlayer();

            case Constants.RANDOM_PLAYER_NAME:
                return new WhateverPlayer();

            case Constants.CLEVER_PLAYER_NAME:
                return new CleverPlayer();

            case Constants.GENIUS_PLAYER_NAME:
                return new GeniusPlayer();

            default:
                return null;

        }
    }
}
