package com.mygdx.game.maincontroller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GUI.DisplaySizeRatios;
import com.mygdx.game.GUI.Main;
import com.mygdx.game.GUI.WinnerScreen;
import com.mygdx.game.MyRuntimeException;
import com.mygdx.game.cheat.CheatCountDown;
import com.mygdx.game.cheat.CheatIcon;
import com.mygdx.game.dice.Dice;
import com.mygdx.game.display.Worm;
import com.mygdx.game.game.Elevator;
import com.mygdx.game.game.Field;
import com.mygdx.game.game.GameField;
import com.mygdx.game.game.Player;
import com.mygdx.game.netwoking.FromNetworkProcessor;
import com.mygdx.game.netwoking.NetworkManager;
import com.mygdx.game.netwoking.NetworkTrafficReceiver;
import com.mygdx.game.util.CustomLogger;
import com.mygdx.game.util.SoundHandler;

import static com.mygdx.game.GUI.DisplaySizeRatios.DICE_SIZE;


/**
 * The type Controller.
 */
public class Controller implements InputProcessor {
    private static final CustomLogger LOGGER = new CustomLogger("CONTROLLER");
    private static final String OTHER_PLAYER_ROLLED_DICE_MESSAGE = "Other_Player_Rolled_Dice";
    private static final String OTHER_PLAYER_CHEATED_MESSAGE = "Other_Player_Cheated";
    private static final String OTHER_PLAYER_CHEATED_SUCCESSFULL_MESSAGE = "Other_Player_Successfull=";
    private static final String OTHER_PLAYER_CHEATED_DETECTED_MESSAGE = "CheatDetected";

    private static GameField gameField = Main.getGameField();
    private static CheatCountDown cheatCountDown = Main.getCheatCountdown();
    private static CheatIcon cheatIcon = Main.getCheatIcon();
    private static OrthographicCamera camera = Main.getCamera();
    private static int currentFieldNumberPlayerOne = gameField.getPlayer(Player.PLAYER_ONE_ID).getCurrentField().getFieldnumber();
    private static int currentFieldNumberPlayerTwo = gameField.getPlayer(Player.PLAYER_TWO_ID).getCurrentField().getFieldnumber();
    private static int numberOfPlayers = 0;
    private static boolean singlePlayerBoolean = false;
    private boolean turnBlocked = false;

    private static final String TAG = "Controller";
    private NetworkTrafficReceiver networkTrafficReceiver;
    private final Worm wormOne;
    private final Worm wormTwo;

    /**
     * Sensor Items
     */
    private float accelLast;  //Current acceleration value and gravity
    private float accelVal; //Last acceleration value and gravity
    private float shake; //Acceleration value differ from gravity

    private static boolean[] playerCheatedList = new boolean[2];
    private boolean playerOneTurn = true;
    private boolean gameStarted = false;

    private static final ThreadLocal<WinnerScreen> winnerScreen = new ThreadLocal<>();

    private static boolean winnerDecided = false;
    private boolean coughtEnemyCheating = false;
    private boolean gotCoughtEnemyCheating = false;

    /**
     * Instantiates a new Controller.
     *
     * @param wormOne the worm one
     */
    public Controller(final Worm wormOne, final Worm wormTwo) {
        setInputProcessor();
        this.wormOne = wormOne;
        this.wormTwo = wormTwo;

        playerCheatedList[0] = false;
        playerCheatedList[1] = false;

        if (NetworkManager.isMultiplayer() && NetworkManager.isClient()) {
            playerOneTurn = false;
        }

        final Controller currentInstance = this;
        networkTrafficReceiver = new NetworkTrafficReceiver(new FromNetworkProcessor() {
            public void receiveMessage(String message) {
                currentInstance.processMessageFromNetwork(message);
            }
        });
        if (NetworkManager.isSinglePlayer()) {
            Bot bot = new Bot(wormOne, wormTwo, this);
        }
    }

    public synchronized boolean isPlayerOneTurn() {
        return playerOneTurn;
    }


    //------------------ INPUT PROCESSOR ----------------------
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Gdx.app.log("Main.touchDown", "X=" + screenX + "Y=" + screenY);

        if (Player.getCounter() >= 2 && cheatUsage(screenX, screenY)) {
            return true;
        }
        if (cheatIcon.touchDown(screenX, screenY)) {
            Gdx.app.log("CHEAT", "Cheater endeckt");
        }
        if (cheatIcon.touchDown(screenX, screenY)) {
            Gdx.app.log("CHEAT", "Cheater endeckt");
            if (NetworkManager.isSinglePlayer() && !playerOneTurn) {
                setPlayerCheated();
                CheatIcon.setVisibility(false);


            } else if (NetworkManager.isMultiplayer()) {
                CheatIcon.setVisibility(false);
                NetworkManager.send(OTHER_PLAYER_CHEATED_DETECTED_MESSAGE);
                Player.switchCurrentPlayerIndex();
                playerCheatedList[Player.getCurrentPlayerIndex()] = true;
                playerOneTurn = true;
                coughtEnemyCheating = true;
                Player.switchCurrentPlayerIndex();

            }

            camera.update();
        }


        touchInputPlayerTurn();

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if (cheatCountDown.cheatingIsActive()) {

            if (NetworkManager.isSinglePlayer()) {
                Integer integer = cheatCountDown.stopCountDown();
                cheatMovement(gameField.getPlayer(Player.PLAYER_ONE_ID), integer);
                playerOneTurn = false;
                Player.resetCounter();
                camera.update();

            } else if (NetworkManager.isMultiplayer()) {
                Integer integer = cheatCountDown.stopCountDown();

                checkIfEnemyCaught(integer);

            }

            return true;

        }


        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    //---------------------------------------------------------

    private void checkIfEnemyCaught(Integer steps) {
        if (gotCoughtEnemyCheating) {
            playerOneTurn = false;
        } else {

            NetworkManager.send(OTHER_PLAYER_CHEATED_SUCCESSFULL_MESSAGE + steps);
            if (playerOneTurn) {
                cheatMovement(gameField.getPlayer(Player.PLAYER_ONE_ID), steps);
                playerOneTurn = false;
            }
            Player.resetCounter();
            camera.update();

        }
    }

    /**
     * Gets input processor.
     *
     * @return the input processor
     */
    public InputProcessor getInputProcessor() {

        return Gdx.input.getInputProcessor();
    }

    /**
     * Sets input processor.
     */
    private void setInputProcessor() {
        Gdx.input.setInputProcessor(this);
    }


    /**
     * Movement.
     *
     * @param player the player
     * @param dice   the dice
     */
    private void movement(Player player, Dice dice) {
        gameStarted = true;

        if (getWorm(player).stillMoving()) {
            return;
        }
        int eyeNumber = dice.rollTheDice();
        SoundHandler.getMusicManager().shuffle();
        Gdx.input.vibrate(100);

        if (checkForGoalField(player, eyeNumber)) {
            eyeNumber = player.getCurrentField().getFieldnumber() - 91;
        }

        doPlayerMovement(player, eyeNumber);

        updateCurrentFieldnumber(player);
        checkField(player);
        checkForWinner();
        Player.increaseCounter();

        if (!turnBlocked) {
            Player.switchCurrentPlayerIndex();
        }

    }

    /**
     * @param player -> The Player
     * @return new Worm-Object
     */
    private Worm getWorm(Player player) {
        if (player.getPlyerId().equals(wormOne.getPlayerId())) {
            return wormOne;
        }

        if (player.getPlyerId().equals(wormTwo.getPlayerId())) {
            return wormTwo;
        } else throw new MyRuntimeException("Worm not found");
    }

    /**
     * Cheat movement.
     *
     * @param player         the player
     * @param cheatCountdown the cheat countdown
     */
    private void cheatMovement(Player player, Integer cheatCountdown) {
        if (getWorm(player).stillMoving()) {
            return;
        }

        doPlayerMovement(player, cheatCountdown);

        updateCurrentFieldnumber(player);
        checkForWinner();
        checkField(player);
        Player.switchCurrentPlayerIndex();


    }

    private boolean checkForGoalField(Player player, int eyeNumber) {

        return player.getCurrentField().getFieldnumber() > 91 && player.getCurrentField().getFieldnumber() - eyeNumber < 91;
    }

    private void doPlayerMovement(Player player, int eyeNumber) {

        for (int i = 0; i < eyeNumber; i++) {
            player.move();
        }
    }

    /**
     * Update current FieldNumber.
     */
    private static void updateCurrentFieldnumber(Player player) {
        setCurrentFieldnumberForPlayer(player, gameField.getPlayer(player.getPlyerId()).getCurrentField().getFieldnumber());
    }

    /**
     * Set current FieldNumber for player.
     *
     * @param player      -> The Player
     * @param fieldNumber -> new FieldNumber
     */
    private static void setCurrentFieldnumberForPlayer(Player player, int fieldNumber) {
        if (player.getPlyerId().equals(Player.PLAYER_ONE_ID)) {
            currentFieldNumberPlayerOne = fieldNumber;
        }

        if (player.getPlyerId().equals(Player.PLAYER_TWO_ID)) {
            currentFieldNumberPlayerTwo = fieldNumber;
        }
    }


    /**
     * Check field.
     *
     * @param player the player
     */
    private void checkField(Player player) {

        setCurrentFieldnumberForPlayer(player, player.getCurrentField().getFieldnumber());

        Gdx.app.log(TAG, Integer.toString(currentFieldNumberPlayerOne));
        Gdx.app.log(TAG, Integer.toString(currentFieldNumberPlayerTwo));

        int[] elevatorNumber = Elevator.getElevatorFields();

        for (int anElevatorNumber : elevatorNumber) {

            if (player.getCurrentField().getFieldnumber() == anElevatorNumber) {

                int newElevatorFieldnumber = Elevator.getNewElevatorFieldnumber(player.getCurrentField().getFieldnumber());
                port(newElevatorFieldnumber, player);
                getWorm(player).teleport(gameField.getFieldFrom(anElevatorNumber), gameField.getFieldFrom(newElevatorFieldnumber));
                break;
            }

        }


    }

    private static void checkForWinner() {

        if (gameField.getPlayerOne().getCurrentField().equals(gameField.getFieldFrom(91))) {

            Gdx.app.log(TAG, "SPIELER 1 hat gewonnen !!");
            winnerScreen.set(new WinnerScreen(gameField.getPlayerOne().getPlyerId(), Main.getStage()));
            SoundHandler.getMusicManager().finishSound();
            winnerDecided = true;

        } else if (gameField.getPlayerTwo().getCurrentField().equals(gameField.getFieldFrom(91))) {

            Gdx.app.log(TAG, "SPIELER 2 hat gewonnen!!");
            winnerScreen.set(new WinnerScreen(gameField.getPlayerTwo().getPlyerId(), Main.getStage()));
            SoundHandler.getMusicManager().finishSound();
            winnerDecided = true;
        }

    }

    /**
     * Port.
     *
     * @param fieldnumber the fieldnumber
     * @param player      the player
     */
    private static void port(int fieldnumber, Player player) {
        Field newCurrentField = gameField.getFieldFrom(fieldnumber);
        player.setCurrentField(newCurrentField);
    }


//The following methods exist, so that the logic classes are separated from each other and from the GUI classes. Architectural purposes

    /**
     * Get elevator fields int [ ].
     *
     * @return the int [ ]
     */
    public static int[] getElevatorFields() {
        return Elevator.getElevatorFields();
    }

    public static void setNumberOfPlayers(int players) {
        numberOfPlayers = players;
    }

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public static boolean getSinglePlayerBoolean() {
        return singlePlayerBoolean;
    }

    public static void setSinglePlayerBoolean(boolean state) {
        singlePlayerBoolean = state;
    }

    //-------------------- CHEATING PART ----------------------
    private void setPlayerCheated() {
        Player.switchCurrentPlayerIndex();
        playerCheatedList[Player.getCurrentPlayerIndex()] = true;
        turnBlocked = true;
        Player.switchCurrentPlayerIndex();
    }

    private void checkUsageCounter() {

        CheatCountDown.increaseUsageCounter();

        if (NetworkManager.isSinglePlayer()) {
            if (CheatCountDown.getUsageCounter() >= 2) {

                CheatIcon.setVisibility(true);

            }
        } else if (NetworkManager.isMultiplayer()) {

            NetworkManager.send(OTHER_PLAYER_CHEATED_MESSAGE);

        }
    }

    private boolean cheatUsage(int screenX, int screenY) {

        if (NetworkManager.isSinglePlayer()) {

            if (cheatCountDown.touchDown(screenX, screenY) && Player.getCurrentPlayerIndex() == 0) {

                checkUsageCounter();

                return true;
            }


        } else if (playerOneTurn && cheatCountDown.touchDown(screenX, screenY)) {

            checkUsageCounter();
            return true;
        }
        return false;
    }

    public static boolean getWinnerDecided() {
        return winnerDecided;
    }
    //---------------------------------------------------------

    /**
     * Checks whether the device is shaken or not.
     *
     * @return true -> iff device acceleration is detected
     * false -> no acceleration detected
     */
    //------------------ GESTURE CONTROLLER DETECTION ----------
    public void checkAcceleration() {

        if (!winnerDecided && Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)) {

            float x = Gdx.input.getAccelerometerX();
            float y = Gdx.input.getAccelerometerY();

            accelLast = accelVal;
            accelVal = (float) Math.sqrt((double) (x * x + y * y));
            float delta = accelVal - accelLast;
            shake = shake * 0.9f + delta;

            if (shake > 6 && gameStarted) {

                checkPlayerTurn();

            }
        }
    }

    /**
     * Switch between players, if touch input is detected.
     */
    //------------------ TOUCH DETECTION PLAYER TURN -----------
    private void touchInputPlayerTurn() {

        if (diceSpriteTouched() && playerOneTurn && !wormOne.stillMoving() && !wormTwo.stillMoving()) {
            checkPlayerTurn();
        }
    }

    /**
     * Switch between players.
     */
    //------------------ GESTURE CONTROL PLAYER TURN -----------
    void checkPlayerTurn() {

        if (playerOneTurn) {
            movement(gameField.getPlayer(Player.PLAYER_ONE_ID), Main.getDice());
            Main.setDiceAnimationTrue();
            playerOneTurn = false;

            if (coughtEnemyCheating) {
                playerOneTurn = true;
                coughtEnemyCheating = false;
            }

            if (NetworkManager.isMultiplayer()) {
                NetworkManager.send(OTHER_PLAYER_ROLLED_DICE_MESSAGE);
            }

        } else if (NetworkManager.isSinglePlayer()) {

            movement(gameField.getPlayer(Player.PLAYER_TWO_ID), Main.getDice());
            Main.setDiceAnimationTrue();
            if (!turnBlocked) {
                playerOneTurn = true;
            } else {
                turnBlocked = false;
            }
        }
        camera.update();
    }

    public boolean getPlayerOneTurn() {
        return playerOneTurn;
    }

    /**
     * This method checks whether the dice sprite is touched or not.
     *
     * @return true -> dice is touched
     * false -> no touch input detected
     */
    //------------------ DICE TOUCH DETECTION -----------------
    private boolean diceSpriteTouched() {

        Vector3 touchPoint = new Vector3();

        if (!winnerDecided && Gdx.input.isTouched()) {

            touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPoint);

            if (checkTouchX(touchPoint) && checkTouchY(touchPoint)) {

                return true;
            }
        }

        return false;
    }

    /**
     * @param touch -> Touch Position
     * @return true -> if touch X-position matches with dice sprite X-position
     * false -> touch doesn't match with dice sprite X-position
     */
    private boolean checkTouchX(Vector3 touch) {

        return touch.x >= DisplaySizeRatios.X_DICE && touch.x <= DisplaySizeRatios.X_DICE + DICE_SIZE;

    }


    /**
     * @param touch -> Touch Position
     * @return true -> if touch Y-position matches with dice sprite Y-position
     * false -> touch doesn't match with dice sprite Y-position
     */
    private boolean checkTouchY(Vector3 touch) {
        return touch.y <= Gdx.graphics.getHeight() - DisplaySizeRatios.Y_DICE && touch.y >= Gdx.graphics.getHeight() - (DisplaySizeRatios.Y_DICE + DisplaySizeRatios.DICE_SIZE);
    }
    //---------------------------------------------------------

    /**
     * Process message from network.
     *
     * @param inputString the input string
     */
    public void processMessageFromNetwork(final String inputString) {
        boolean log = true;

        if (inputString.equals(OTHER_PLAYER_ROLLED_DICE_MESSAGE)) {
            Player player = gameField.getPlayer(Player.PLAYER_TWO_ID);
            Player playerTwo = player;
            movement(playerTwo, Main.getDice());

            Main.setDiceAnimationTrue();
            if (gotCoughtEnemyCheating) {
                playerOneTurn = false;
                gotCoughtEnemyCheating = false;
            } else {

                playerOneTurn = true;
            }
            camera.update();
        } else if (inputString.equals(OTHER_PLAYER_CHEATED_MESSAGE)) {
            CheatIcon.setVisibility(true);

        } else if (inputString.startsWith(OTHER_PLAYER_CHEATED_SUCCESSFULL_MESSAGE)) {
            CheatIcon.setVisibility(false);
            Player player = gameField.getPlayer(Player.PLAYER_TWO_ID);
            String replace = inputString.replace(OTHER_PLAYER_CHEATED_SUCCESSFULL_MESSAGE, "");
            Integer cheatedNumberOfSteps = Integer.valueOf(replace);
            doPlayerMovement(player, cheatedNumberOfSteps);
            checkForWinner();
            checkField(player);
            playerOneTurn = true;
            camera.update();

        } else if (inputString.equals(OTHER_PLAYER_CHEATED_DETECTED_MESSAGE)) {
            cheatCountDown.stopCountDown();
            gotCoughtEnemyCheating = true;
            Player.switchCurrentPlayerIndex();
            playerOneTurn = false;
            camera.update();
        } else {
            log = false;
        }
        if (log) {
            Gdx.app.log(TAG, inputString);
        }

    }

    public NetworkTrafficReceiver getNetworkTrafficReceiver() {
        return networkTrafficReceiver;
    }
}
