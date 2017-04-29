package game;

import java.util.Random;

/**
 * Created by Muhi on 14.04.2017.
 */

public class Elevator {

    private static int [] elevatorFields = new int[7];

    private static boolean newfield = true;
    private static int randomElevatorFieldnumber;



    public static void generateElevator(){

        for (int i = 0; i < 7; i++) {

            int number= (int) (Math.random() * 62+1);

            elevatorFields[i] = number;

        }
    }

    public static int getNewElevatorFieldnumber(int fieldnumber){

        while(newfield){

//             randomElevatorFieldnumber = (int) (Math.randomElevatorFieldnumber() * elevatorFields.length);

            Random rn = new Random();
            int randomNumber = rn.nextInt(6-0+1) + 0;         //(max - min + 1) + min
            randomElevatorFieldnumber = elevatorFields[randomNumber];

            if(randomElevatorFieldnumber != fieldnumber){

                newfield = false;
            }

        }

        System.out.println("new elavator field" + randomElevatorFieldnumber);             //show the value of randomElevatorFieldnumber to see if there is a mistake

        return randomElevatorFieldnumber;
    }
    public static int [] getElevatorFields(){

        return elevatorFields;
    }
}
