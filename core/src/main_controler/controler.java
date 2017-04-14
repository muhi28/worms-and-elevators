package main_controler;

import dice.Dice;
import game.Elevator;
import game.Field;
import game.Player;

public class controler {

    Field currentField;
    boolean cheatMode = true;               //Stud for the cheat mode


    public void movement(Player player, Dice dice){

        int eyeNumber;

        if(cheatMode = false) {
            eyeNumber = dice.rollTheDice();
        }
        else{
            eyeNumber = dice.cheatDice(dice.getDice_p());
        }

        player.move(eyeNumber);
        checkField();

    }

    public void checkField(){

        int currentFieldnumber = currentField.getFeldnummer();
        int [] elevatorNumber = Elevator.getElevatorFields();

        for (int i = 0; i < 7; i++) {
            if (currentFieldnumber == elevatorNumber[i]){
                Elevator.moveElevator(currentFieldnumber);
            }
        }


    }


}
