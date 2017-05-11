package com.mygdx.game.main_controler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.mygdx.game.GUI.Main;
import com.mygdx.game.cheat.CheatCountDown;
import com.mygdx.game.dice.Dice;
import com.mygdx.game.game.Elevator;
import com.mygdx.game.game.Field;

import com.mygdx.game.game.GameField;
import com.mygdx.game.game.Player;


/**
 * The type Controler.
 */
public class Controler implements InputProcessor {

    //    boolean cheatMode = true;               //Stud for the cheat mode
    private static Sprite diceSprite = Main.getDiceSprite();
    private static Dice dice = Main.getDice();
    private static GameField gameField = Main.getGameField();
    private static CheatCountDown cheatCountDown = Main.getCheatCountdown();
    private static OrthographicCamera camera = Main.getCamera();
    private static int currentFieldnumber = gameField.getPlayer().getCurrentField().getFieldnumber();

    private static final String TAG = "Controler";

    /**
     * Instantiates a new Controler.
     */
    public Controler() {
        setInputProcessor();
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
    public void movement(Player player, Dice dice) {

        int eyeNumber = dice.rollTheDice();
        for (int i = 0; i < eyeNumber; i++) {
            player.move();
        }
        updateCurrentFieldnumber();
        checkField(player);
        Player.increaseCounter();
        Player.switchPlayer();

    }

    /**
     * Cheat movement.
     *
     * @param player         the player
     * @param cheatCountdown the cheat countdown
     */
    public void cheatMovement(Player player, Integer cheatCountdown) {

        for (int i = 0; i < cheatCountdown; i++) {
            player.move();
        }

        updateCurrentFieldnumber();
        checkField(player);


    }

    /**
     * Update current fieldnumber.
     */
    public static void updateCurrentFieldnumber() {
        currentFieldnumber = gameField.getPlayer().getCurrentField().getFieldnumber();
    }


    /**
     * Check field.
     *
     * @param player the player
     */
    public static void checkField(Player player) {

        currentFieldnumber = player.getCurrentField().getFieldnumber();

        Gdx.app.log(TAG, Integer.toString(currentFieldnumber)); //test to determine if the method works

        int[] elevatorNumber = Elevator.getElevatorFields();

        for (int i = 0; i < 7; i++) {

            if (currentFieldnumber == elevatorNumber[i]) {

                int newElevatorFieldnumber = Elevator.getNewElevatorFieldnumber(currentFieldnumber);
                port(newElevatorFieldnumber, player);

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
    public static void port(int fieldnumber, Player player) {
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

        if (Player.getCounter() >= 3){

            if (cheatCountDown.touchDown(screenX, screenY)) {

                return true;
            }
    }

        if (Gdx.input.isTouched() && gameField.getPlayer().getCurrentField().getNextField() != null) {



                movement(gameField.getPlayer(), dice);
                diceSprite.setTexture(dice.getDiceTexture());
                Main.setDiceAnimationTrue();

                camera.update();

                if (gameField.getPlayer().getCurrentField().equals(gameField.getGoal())) {

                    Gdx.app.log(TAG,"YOU ARE A WINNER !!");
                }



        }


        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if (cheatCountDown.cheatingIsActive()) {

            Integer integer = cheatCountDown.stopCountDown();

            cheatMovement(gameField.getPlayer(), integer);
            Player.resetCounter();

            camera.update();

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
}
