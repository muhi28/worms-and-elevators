package com.mygdx.game.main_controler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GUI.Main;
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
    public static final String OTHER_PLAYER_ROLLED_DICE_MESSAGE= "Other_Player_Rolled_Dice";

    private static Sprite diceSprite;
    private static GameField gameField = Main.getGameField();
    private static CheatCountDown cheatCountDown = Main.getCheatCountdown();
    private static CheatIcon cheatIcon = Main.getCheatIcon();
    private static OrthographicCamera camera = Main.getCamera();
    private static int currentFieldnumberPlayerOne = gameField.getPlayer(Player.PLAYER_ONE_ID).getCurrentField().getFieldnumber();
    private static int currentFieldnumberPlayerTwo = gameField.getPlayer(Player.PLAYER_TWO_ID).getCurrentField().getFieldnumber();
    private static int numberOfPlayers = 0;
    private static boolean singleplayerBoolean = false;
    private static boolean turnBlocked = false;

    private static final String TAG = "Controler";
    private NetworkTrafficReceiver networkTrafficReceiver;
    private final Worm wormOne;
    private final Worm wormTwo;

    private static boolean[] playerCheatedList = new boolean[2];

    /**
     * Instantiates a new Controler.
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
    public void setInputProcessor() {
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
        if (turnBlocked == false){
            Player.switchCurrentPlayerIndex();
        }

    }

    private Worm getWorm(Player player) {
        if (player.getPlyerId().equals(wormOne.getPlayerId())) {
            return wormOne;
        }

        if (player.getPlyerId().equals(wormTwo.getPlayerId())) {
            return wormTwo;
        }

        throw new RuntimeException("Worm not found");
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
     * Update current fieldnumber.
     */
    private static void updateCurrentFieldnumber(Player player) {
        setCurrentFieldnumberForPlayer(player, gameField.getPlayer(player.getPlyerId()).getCurrentField().getFieldnumber());
    }

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

//The following methods exist, so that the logic classes are seperated from each other and from the GUI classes. Architectual porpuses

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

    public static boolean getSingleplayerBoolean() {
        return singleplayerBoolean;
    }

    public static void setSingleplayerBoolean(boolean state) {
        singleplayerBoolean = state;
    }

    public static void setPlayerCheated(){
        Player.switchCurrentPlayerIndex();
        playerCheatedList[Player.getCurrentPlayerIndex()] = true;
        turnBlocked = true;
        CheatCountDown.resetUsageCounter();
        Player.switchCurrentPlayerIndex();
    }

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


    private static boolean playerOneTurn = true;

    public static boolean getPlayerOneTurn(){
        return playerOneTurn;
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

        Player playerOne = gameField.getPlayer(Player.PLAYER_ONE_ID);
        Player playerTwo = gameField.getPlayer(Player.PLAYER_TWO_ID);

        if (cheatIcon.touchDown(screenX,screenY)){
            if (NetworkManager.isSinglePlayer()){
                setPlayerCheated();
                CheatIcon.setVisibility(false);
            }
        }


        if (diceSpriteTouched()) {


            if (playerOneTurn) {
                if (!wormOne.stillMoving()) {
                    movement(playerOne, Main.getDice());
                    diceSprite = Main.getDiceSprite();
                    diceSprite.setTexture(Main.getDice().getDiceTexture());
                    Main.setDiceAnimationTrue();
                    playerOneTurn = false;


                    if(NetworkManager.isMultiplayer()){
                        NetworkManager.send(OTHER_PLAYER_ROLLED_DICE_MESSAGE);
                    }

                }
            } else if(NetworkManager.isSinglePlayer()) {
                if (!wormTwo.stillMoving()) {
                    movement(playerTwo, Main.getDice());
//
                    diceSprite = Main.getDiceSprite();
                    diceSprite.setTexture(Main.getDice().getDiceTexture());
                    Main.setDiceAnimationTrue();
                    if (!turnBlocked){
                        playerOneTurn = true;
                    }
                    else{
                        turnBlocked = false;
                    }


                }
            }


            camera.update();

            if (playerOne.getCurrentField().equals(gameField.getGoal()))
                Gdx.app.log(TAG, "PLAYER ONE is the WINNER!");

            if (playerTwo.getCurrentField().equals(gameField.getGoal()))
                Gdx.app.log(TAG, "PLAYER TWO is the WINNER!");


        }


        return true;
    }

    private boolean diceSpriteTouched(){

        Vector3 touchpoint = new Vector3();

        if(Gdx.input.isTouched()){

            touchpoint.set(Gdx.input.getX(), Gdx.input.getY(),0);
            camera.unproject(touchpoint);

            if (checkTouchX(touchpoint) && checkTouchY(touchpoint)){

                return true;
            }
        }

        return false;
    }

    private boolean checkTouchX(Vector3 touch){

        if(touch.x >= Main.getDiceSprite().getX() ){

            if (touch.x <= Main.getDiceSprite().getX() + Main.getDiceSprite().getWidth()){

                return true;
            }
        }

        return false;
    }

    private boolean checkTouchY(Vector3 touch){

        return touch.y >= 1550 && touch.y <= 1770;
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
