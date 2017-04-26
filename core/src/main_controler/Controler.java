package main_controler;

import com.badlogic.gdx.Gdx;
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


public class Controler implements InputProcessor {

//    boolean cheatMode = true;               //Stud for the cheat mode
    private static Sprite diceSprite = Main.getDiceSprite();
    private static Dice dice = Main.getDice();
    private static GameField gameField = Main.getGameField();
    private static CheatCountDown cheatCountDown = Main.getCheatCountdown();
    private static OrthographicCamera camera = Main.getCamera();
    private static int currentFieldnumber = gameField.getPlayer().getCurrentField().getFieldnumber();
    private Field goal = gameField.getGoal();



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

        int eyeNumber = dice.rollTheDice();
        player.move(eyeNumber);
        updateCurrentField();
        checkField(player);


 /*       if(cheatMode) {
            eyeNumber = dice.rollTheDice();
        }
        else{
            eyeNumber = dice.cheatDice(dice.getDice_p());
        }
*/

    }

    public void cheatMovement (Player player, Integer cheatCountdown){

        player.move(cheatCountdown);
        updateCurrentField();
        checkField(player);


    }

    public void updateCurrentField(){
        currentFieldnumber = gameField.getPlayer().getCurrentField().getFieldnumber();
    }

    public static  void updateCamera(){
        camera.update();
    }


    public void checkField(Player player){

        currentFieldnumber = player.getCurrentField().getFieldnumber();
        System.out.println(currentFieldnumber);                 //test to determine if the method works
        int [] elevatorNumber = Elevator.getElevatorFields();

        for (int i = 0; i < 7; i++) {
            System.out.println(elevatorNumber[i]);          //test to determine if the method works
            if (currentFieldnumber == elevatorNumber[i]){
                int newElevatorFieldnumber = Elevator.getNewElevatorFieldnumber(currentFieldnumber);
                i = 7;                                       //if a field is found, the loop should stop
//                System.out.println("Übereinstimmung");      //test to determine if the method works
                port(newElevatorFieldnumber, player);

            }

        }


    }

    public void port(int fieldnumber, Player player){
        Field newCurrentField = gameField.getFieldFrom(fieldnumber);
        player.setCurrentField(newCurrentField);
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

/*                if (gameField.getPlayer().getCurrentField().equals(gameField.getGoal())) {

                    System.out.println("YOU ARE A WINNER !!!");
                }
                */
            }

        }


        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if (cheatCountDown.cheatingIsActive()) {

            Integer integer = cheatCountDown.stopCountDown();

            cheatMovement(gameField.getPlayer(), integer);

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
