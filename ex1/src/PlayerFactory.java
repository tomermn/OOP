
/**
 * Provides a factory method to create instances of players.
 * <p>
 * Players are built based on the specified player type using the buildPlayer method.
 * Supported player types include:
 *  -human
 *  -whatever
 *  -clever
 *  -genius
 * </p>
 * @see Constants
 * @author Tomer Meidan
 */
public class PlayerFactory {

    public PlayerFactory() {}

    /**
     * Builds and returns an instance of a player based on the specified player type.
     *
     * @param playerType The type of the player to be created.
     * @return An instance of the corresponding player or null if the player type is not recognized.
     */
    public Player buildPlayer(String playerType) {
        switch (playerType.toLowerCase()) {

            case Constants.HUMAN_PLAYER_NAME:
                return new HumanPlayer();

            case Constants.WHATEVER_PLAYER_NAME:
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
