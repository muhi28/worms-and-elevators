package networking;


public final class GameSync {

    private static GameState gameState = GameState.OFFLINE;

    private enum GameState {
        OFFLINE,
        WAIT_FOR_MULTIPLAYER,
        WORM_SELECTION,
        MULTIPLAYER_GAME_RUNNING
    }


    public static void useMultiplayerGame() {
        gameState = GameState.WAIT_FOR_MULTIPLAYER;
    }

    public static void playerConnected() {
        if (waitingForMultitiplayer()) {
            gameState = GameState.WAIT_FOR_MULTIPLAYER;
        }

        throw new RuntimeException("Invalid state");
    }

    public static void wormSelected() {
        if (waitingForWormSelect()) {
            gameState = GameState.WAIT_FOR_MULTIPLAYER;
        }

        throw new RuntimeException("Invalid state");
    }


    public static boolean isOfflineGame() {
        return gameState == GameState.OFFLINE;
    }

    public static boolean waitingForMultitiplayer() {
        return gameState == GameState.WAIT_FOR_MULTIPLAYER;
    }

    public static boolean waitingForWormSelect() {
        return gameState == GameState.WORM_SELECTION;
    }

    public static boolean multiplayerGameIsRunning() {
        return gameState == GameState.MULTIPLAYER_GAME_RUNNING;
    }

}
