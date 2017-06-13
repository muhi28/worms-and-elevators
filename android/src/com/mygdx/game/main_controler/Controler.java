package com.mygdx.game.main_controler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GUI.Main;
import com.mygdx.game.MyRuntimeException;
import com.mygdx.game.cheat.CheatCountDown;
import com.mygdx.game.cheat.CheatIcon;
import com.mygdx.game.dice.Dice;
import com.mygdx.game.display.Coordinates;
import com.mygdx.game.display.Worm;
import com.mygdx.game.game.Elevator;
import com.mygdx.game.game.Field;
import com.mygdx.game.game.GameField;
import com.mygdx.game.game.Player;
import com.mygdx.game.netwoking.FromNetworkProcessor;
import com.mygdx.game.netwoking.NetworkManager;
import com.mygdx.game.netwoking.NetworkTrafficReceiver;

import java.util.Observable;


/**
 * The type Controler.
 */
public class Controler extends Observable implements InputProcessor{
    private static final String OTHER_PLAYER_ROLLED_DICE_MESSAGE = "Other_Player_Rolled_Dice";

    private static Sprite diceSprite;
    private static GameField gameField = Main.getGameField();
    private static CheatCountDown cheatCountDown = Main.getCheatCountdown();
    private static CheatIcon cheatIcon = Main.getCheatIcon();
    private static OrthographicCamera camera = Main.getCamera();
    private static int currentFieldnumberPlayerOne = gameField.getPlayer(Player.PLAYER_ONE_ID).getCurrentField().getFieldnumber();
    private static int currentFieldnumberPlayerTwo = gameField.getPlayer(Player.PLAYER_TWO_ID).getCurrentField().getFieldnumber();
    private static int numberOfPlayers = 0;
    private static boolean singlePlayerBoolean = false;
    private static boolean turnBlocked = false;

    private static final String TAG = "Controler";
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
    private static boolean playerOneTurn = true;

    /**
     * Instantiates a new Controller.
     *
     * @param wormOne the worm one
     */
    public Controler(Worm wormOne, Worm wormTwo) {
        setInputProcessor();
        this.wormOne = wormOne;
        this.wormTwo = wormTwo;

        playerCheatedList[0] = false;
        playerCheatedList[1] = false;

        if(NetworkManager.isMultiplayer() && NetworkManager.isClient()){
            playerOneTurn = false;
        }

        final Controler currentInstance = this;
        networkTrafficReceiver = new NetworkTrafficReceiver(new FromNetworkProcessor() {
            public void receiveMessage(String message) {
                currentInstance.processMessageFromNetwork(message);
            }
        });
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

        if (getWorm(player).stillMoving()) {
            return;
        }
        int eyeNumber = dice.rollTheDice();
        for (int i = 0; i < eyeNumber; i++) {
            player.move();
        }
        updateCurrentFieldnumber(player);
        checkField(player);
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
        for (int i = 0; i < cheatCountdown; i++) {
            player.move();
        }

        updateCurrentFieldnumber(player);
        checkField(player);
        Player.switchCurrentPlayerIndex();


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
     * @param player -> The Player
     *
     * @param fieldNumber -> new FieldNumber
     */
    private static void setCurrentFieldnumberForPlayer(Player player, int fieldNumber) {
        if (player.getPlyerId().equals(Player.PLAYER_ONE_ID)) {
            currentFieldnumberPlayerOne = fieldNumber;
        }

        if (player.getPlyerId().equals(Player.PLAYER_TWO_ID)) {
            currentFieldnumberPlayerTwo = fieldNumber;
        }
    }


    /**
     * Check field.
     *
     * @param player the player
     */
    private void checkField(Player player) {

        setCurrentFieldnumberForPlayer(player, player.getCurrentField().getFieldnumber());

        Gdx.app.log(TAG, Integer.toString(currentFieldnumberPlayerOne)); //test to determine if the method works

        int[] elevatorNumber = Elevator.getElevatorFields();

        for (int i = 0; i < 7; i++) {

            if (player.getCurrentField().getFieldnumber() == elevatorNumber[i]) {

                int newElevatorFieldnumber = Elevator.getNewElevatorFieldnumber(player.getCurrentField().getFieldnumber());
                port(newElevatorFieldnumber, player);
                getWorm(player).teleport(player.getCurrentField());
                break;
            }

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

    private static void setPlayerCheated() {
        Player.switchCurrentPlayerIndex();
        playerCheatedList[Player.getCurrentPlayerIndex()] = true;
        turnBlocked = true;
        CheatCountDown.resetUsageCounter();
        Player.switchCurrentPlayerIndex();
    }

    public static boolean getPlayerOneTurn() {
        return playerOneTurn;
    }

    /**
     * Checks whether the device is shaken or not.
     *
     * @return true -> iff device acceleration is detected
     * false -> no acceleration detected
     */
    //------------------ GESTURE CONTROLLER DETECTION ----------
    public boolean checkAcceleration() {

        if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)) {

            float x = Gdx.input.getAccelerometerX();
            float y = Gdx.input.getAccelerometerY();
            float z = Gdx.input.getAccelerometerZ();

            accelLast = accelVal;
            accelVal = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = accelVal - accelLast;
            shake = shake * 0.9f + delta;

            if (shake > 10) {

                checkPlayerTurn();

                return true;
            }

        }

        return false;
    }

    /**
     * Switch between players, if touch input is detected.
     */
    //------------------ TOUCH DETECTION PLAYER TURN -----------
    private void touchInputPlayerTurn() {

        if (diceSpriteTouched()) {
            checkPlayerTurn();
        }
    }

    /**
     * Switch between players.
     */
    //------------------ GESTURE CONTROL PLAYER TURN -----------
    private void checkPlayerTurn() {
        if (playerOneTurn && !wormOne.stillMoving()) {
                movement(gameField.getPlayer(Player.PLAYER_ONE_ID), Main.getDice());
                diceSprite = Main.getDiceSprite();
                diceSprite.setTexture(Main.getDice().getDiceTexture());
                Main.setDiceAnimationTrue();
                playerOneTurn = false;


                if (NetworkManager.isMultiplayer()) {
                    NetworkManager.send(OTHER_PLAYER_ROLLED_DICE_MESSAGE);
                }

        } else if (NetworkManager.isSinglePlayer() && !wormTwo.stillMoving()) {

                movement(gameField.getPlayer(Player.PLAYER_TWO_ID), Main.getDice());
                diceSprite = Main.getDiceSprite();
                diceSprite.setTexture(Main.getDice().getDiceTexture());
                Main.setDiceAnimationTrue();
                if (!turnBlocked) {
                    playerOneTurn = true;
                } else {
                    turnBlocked = false;
                }
        }
        camera.update();
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

        if (Gdx.input.isTouched()) {

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

        return touch.x >= Main.getDiceSprite().getX() && touch.x <= Main.getDiceSprite().getX() + Main.getDiceSprite().getWidth();

    }

    /**
     * @param touch -> Touch Position
     * @return true -> if touch Y-position matches with dice sprite Y-position
     * false -> touch doesn't match with dice sprite Y-position
     */
    private boolean checkTouchY(Vector3 touch) {

        return touch.y >= 1550 && touch.y <= 1770;
    }
    //---------------------------------------------------------


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

        this.setChanged();
        this.notifyObservers(new Coordinates(screenX, screenY));

        Gdx.app.log("Main.touchDown", "X=" + screenX + "Y=" + screenY);

        if (Player.getCounter() >= 2) {

            if (NetworkManager.isSinglePlayer()) {

                if (cheatCountDown.touchDown(screenX, screenY) && Player.getCurrentPlayerIndex() == 0) {

                    CheatCountDown.increaseUsageCounter();

                    if (CheatCountDown.getUsageCounter() >= 1) {

                        CheatIcon.setVisibility(true);
                    }

                    return true;
                }
            }

            else{
                if (cheatCountDown.touchDown(screenX, screenY)) {

                    CheatCountDown.increaseUsageCounter();

                    if (CheatCountDown.getUsageCounter() >= 1) {

                        CheatIcon.setVisibility(true);
                    }

                    return true;
                }

            }
        }

        if (cheatIcon.touchDown(screenX, screenY) && NetworkManager.isSinglePlayer()) {
                setPlayerCheated();
                CheatIcon.setVisibility(false);

        }

        touchInputPlayerTurn();

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if (cheatCountDown.cheatingIsActive()) {

            if (NetworkManager.isSinglePlayer()){
                Integer integer = cheatCountDown.stopCountDown();
                cheatMovement(gameField.getPlayer(Player.PLAYER_ONE_ID), integer);
                playerOneTurn = false;
                Player.resetCounter();
                camera.update();

                return true;

            }
            else{
                Integer integer = cheatCountDown.stopCountDown();

                if (Player.getCurrentPlayerIndex() == 0){
                    cheatMovement(gameField.getPlayer(Player.PLAYER_ONE_ID), integer);
                }
                else if (Player.getCurrentPlayerIndex() == 1){
                    cheatMovement(gameField.getPlayer(Player.PLAYER_TWO_ID), integer);
                }

                Player.resetCounter();
                camera.update();

                return true;
            }

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

    /**
     * Process message from network.
     *
     * @param inputString the input string
     */
    public void processMessageFromNetwork(final String inputString) {

        if (inputString.equals(OTHER_PLAYER_ROLLED_DICE_MESSAGE)) {
            this.setChanged();
            Player playerTwo = gameField.getPlayer(Player.PLAYER_TWO_ID);

            movement(playerTwo, Main.getDice());
            diceSprite = Main.getDiceSprite();
            diceSprite.setTexture(Main.getDice().getDiceTexture());
            Main.setDiceAnimationTrue();
            playerOneTurn = true;
            camera.update();
        }

    }

}
