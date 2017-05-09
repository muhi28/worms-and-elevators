package com.mygdx.game.netwoking;


import static com.mygdx.game.netwoking.GameSync.GameState.OTHER_PLAYER_HAS_WORM_SELECTED;

public final class GameSync {

    private static GameSync gameSync;

    private GameState gameState;

    public GameSync(GameState gameState) {
        this.gameState = gameState;
    }

    public static GameSync getSync() {
        if (gameSync == null) {
            gameSync = new GameSync(GameState.OFFLINE);
        }

        return gameSync;
    }

    public static GameState getState() {
        return getSync().gameState;
    }

    public enum GameState {
        OFFLINE,
        MULTIPLAYER_GAME_STARTED,
        WAIT_FOR_OTHER_PLAYER,
        WAIT_FOR_WORM_SELECTION,
        OTHER_PLAYER_HAS_WORM_SELECTED,
        GAME_STARTED
    }

    public static boolean isMultiplayerGame() {
        return getState() != GameState.OFFLINE;
    }

    public void startMultiplayerGame() {

        gameState = GameState.MULTIPLAYER_GAME_STARTED;

    }

    public void waitForOtherPlayer() throws RuntimeException {
        if (gameState == GameState.MULTIPLAYER_GAME_STARTED) {
            gameState = GameState.WAIT_FOR_OTHER_PLAYER;
        } else {
            //  throw new RuntimeException("Invalid state");
        }


    }

    public void waitForWormSelection() {
        if (gameState == GameState.WAIT_FOR_OTHER_PLAYER) {
            gameState = GameState.WAIT_FOR_WORM_SELECTION;
        } else {
            //  throw new RuntimeException("Invalid state");
        }
    }

    public void otherPlayerHasSelectedWorm() {
        if (gameState == GameState.WAIT_FOR_WORM_SELECTION || gameState == OTHER_PLAYER_HAS_WORM_SELECTED) {
            gameState = OTHER_PLAYER_HAS_WORM_SELECTED;
        } else {
            //  throw new RuntimeException("Invalid state");
        }
    }

    public void startGame() {
        if (gameState == OTHER_PLAYER_HAS_WORM_SELECTED) {
            gameState = GameState.GAME_STARTED;
        } else {
            //    throw new RuntimeException("Invalid state");
        }
    }


}
