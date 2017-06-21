package com.mygdx.game.netwoking;


import static com.mygdx.game.netwoking.GameSync.GameState.OTHER_PLAYER_HAS_WORM_SELECTED;

/**
 * The type Game sync.
 */
public final class GameSync {

    private static GameSync gameSync;

    private GameState gameState;

    /**
     * Instantiates a new Game sync.
     *
     * @param gameState the game state
     */
    public GameSync(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Gets sync.
     *
     * @return the sync
     */
    public static GameSync getSync() {
        if (gameSync == null) {
            gameSync = new GameSync(GameState.OFFLINE);
        }

        return gameSync;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public static GameState getState() {
        return getSync().gameState;
    }

    /**
     * The enum Game state.
     */
    public enum GameState {
        /**
         * Offline game state.
         */
        OFFLINE,
        /**
         * Multiplayer game started game state.
         */
        MULTIPLAYER_GAME_STARTED,
        /**
         * Wait for other player game state.
         */
        WAIT_FOR_OTHER_PLAYER,
        /**
         * Wait for worm selection game state.
         */
        WAIT_FOR_WORM_SELECTION,
        /**
         * Other player has worm selected game state.
         */
        OTHER_PLAYER_HAS_WORM_SELECTED,
        /**
         * Game started game state.
         */
        GAME_STARTED
    }

    /**
     * Is multiplayer game boolean.
     *
     * @return the boolean
     */
    public static boolean isMultiplayerGame() {
        return getState() != GameState.OFFLINE;
    }

    /**
     * Start multiplayer game.
     */
    public void startMultiplayerGame() {

        gameState = GameState.MULTIPLAYER_GAME_STARTED;

    }

    /**
     * Wait for other player.
     *
     * @throws RuntimeException the runtime exception
     */
    public void waitForOtherPlayer() throws RuntimeException {
        if (gameState == GameState.MULTIPLAYER_GAME_STARTED) {
            gameState = GameState.WAIT_FOR_OTHER_PLAYER;
        } else {
            //  throw new RuntimeException("Invalid state");
        }


    }

    /**
     * Wait for worm selection.
     */
    public void waitForWormSelection() {
        if (gameState == GameState.WAIT_FOR_OTHER_PLAYER) {
            gameState = GameState.WAIT_FOR_WORM_SELECTION;
        } else {
            //  throw new RuntimeException("Invalid state");
        }
    }

    /**
     * Other player has selected worm.
     */
    public void otherPlayerHasSelectedWorm() {
        if (gameState == GameState.WAIT_FOR_WORM_SELECTION || gameState == OTHER_PLAYER_HAS_WORM_SELECTED) {
            gameState = OTHER_PLAYER_HAS_WORM_SELECTED;
        } else {
            //  throw new RuntimeException("Invalid state");
        }
    }

    /**
     * Start game.
     */
    public void startGame() {
        if (gameState == OTHER_PLAYER_HAS_WORM_SELECTED) {
            gameState = GameState.GAME_STARTED;
        } else {
            //    throw new RuntimeException("Invalid state");
        }
    }


}
