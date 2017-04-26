package main_controler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;


import GUI.Main;
import cheat.CheatCountDown;
import dice.Dice;
import game.Elevator;
import game.Field;
import game.GameField;
import game.Player;
import sun.security.ssl.Debug;

public class Controler implements InputProcessor {

    boolean cheatMode = true;               //Stud for the cheat mode
    private static Sprite diceSprite = Main.getDiceSprite();
    private static Dice dice = Main.getDice();
    private static GameField gameField = Main.getGameField();
    private static CheatCountDown cheatCountDown = Main.getCheatCountdown();
    private static OrthographicCamera camera = Main.getCamera();
    private static int currentFieldnumber = gameField.getPlayer().getCurrentField().getFieldnumber();


    public Controler(){
        setInputProcessor();
    }


    public InputProcessor getInputProcessor(){

        return Gdx.input.getInputProcessor();
    }

    public void setInputProcessor(){
        Gdx.input.setInputProcessor(this);
    }





    public void movement(Player player, Dice dice){

        int eyeNumber;

        if(cheatMode) {
            eyeNumber = dice.rollTheDice();
        }
        else{
            eyeNumber = dice.cheatDice(dice.getDice_p());
        }

        player.move(eyeNumber);
        checkField(player.getCurrentField().getFieldnumber());

    }

    public void checkField(int currentFieldnumber){


        int [] elevatorNumber = Elevator.getElevatorFields();

        for (int i = 0; i < 7; i++) {
            if (currentFieldnumber == elevatorNumber[i]){
                Elevator.moveElevator(currentFieldnumber);
            }
        }


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

        if(cheatCountDown.touchDown(screenX,screenY)){

            return true;
        }

        if(Gdx.input.isTouched()){


            if(gameField.getPlayer().getCurrentField().getNextField() != null) {

                movement(gameField.getPlayer(), dice);
                diceSprite.setTexture(dice.getDice_p());

                camera.update();

                if (gameField.getPlayer().getCurrentField().equals(gameField.getGoal())) {

                    System.out.println("YOU ARE A WINNER !!!");
                }
            }

        }


        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if (cheatCountDown.cheatingIsActive()) {

            Integer integer = cheatCountDown.stopCountDown();

            gameField.getPlayer().move(integer);

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
