package core.assets.assets.main_controler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;

import core.assets.assets.GUI.Main;
import core.assets.assets.cheat.CheatCountDown;
import core.assets.assets.dice.Dice;
import core.assets.assets.game.Elevator;
import core.assets.assets.game.Field;
import core.assets.assets.game.GameField;
import core.assets.assets.game.Player;


public class Controler implements InputProcessor {

    //    boolean cheatMode = true;               //Stud for the cheat mode
    private static Sprite diceSprite = Main.getDiceSprite();
    private static Dice dice = Main.getDice();
    private static GameField gameField = Main.getGameField();
    private static CheatCountDown cheatCountDown = Main.getCheatCountdown();
    private static OrthographicCamera camera = Main.getCamera();
    private static int currentFieldnumber = gameField.getPlayer().getCurrentField().getFieldnumber();
    private Field goal = gameField.getGoal();


    public Controler() {
        setInputProcessor();
    }


    public InputProcessor getInputProcessor() {

        return Gdx.input.getInputProcessor();
    }

    public void setInputProcessor() {
        Gdx.input.setInputProcessor(this);
    }


    public void movement(Player player, Dice dice) {

        int eyeNumber = dice.rollTheDice();
        for (int i = 0; i < eyeNumber; i++) {
            player.move();
        }
        updateCurrentFieldnumber();
        checkField(player);


 /*       if(cheatMode) {
            eyeNumber = dice.rollTheDice();
        }
        else{
            eyeNumber = dice.cheatDice(dice.getDice_p());
        }
*/

    }

    public void cheatMovement(Player player, Integer cheatCountdown) {

        for (int i = 0; i < cheatCountdown; i++) {
            player.move();
        }

//        player.move(cheatCountdown);
        updateCurrentFieldnumber();
        checkField(player);


    }

    public void updateCurrentFieldnumber() {
        currentFieldnumber = gameField.getPlayer().getCurrentField().getFieldnumber();
    }


    public void checkField(Player player) {

        currentFieldnumber = player.getCurrentField().getFieldnumber();
        System.out.println(currentFieldnumber);                 //test to determine if the method works
        int[] elevatorNumber = Elevator.getElevatorFields();

        for (int i = 0; i < 7; i++) {
            System.out.println(elevatorNumber[i]);          //test to determine if the method works
            if (currentFieldnumber == elevatorNumber[i]) {
                int newElevatorFieldnumber = Elevator.getNewElevatorFieldnumber(currentFieldnumber);
                //i = 7;                                       //if a field is found, the loop should stop
//                System.out.println("Übereinstimmung");      //test to determine if the method works
                port(newElevatorFieldnumber, player);

                break;
            }

        }


    }

    public void port(int fieldnumber, Player player) {
        Field newCurrentField = gameField.getFieldFrom(fieldnumber);
        player.setCurrentField(newCurrentField);
    }

//The following methods exist, so that the logic classes are seperated from each other and from the GUI classes. Architectual porpuses

    public static int[] getElevatorFields() {
        int[] elevatorFields = Elevator.getElevatorFields();
        return elevatorFields;
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

        if (cheatCountDown.touchDown(screenX, screenY)) {

            return true;
        }

        if (Gdx.input.isTouched()) {


            if (gameField.getPlayer().getCurrentField().getNextField() != null) {

                movement(gameField.getPlayer(), dice);
                diceSprite.setTexture(dice.getDice_p());
                Main.setDiceAnimationTrue();

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
