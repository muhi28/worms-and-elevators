package com.mygdx.game.game;

import java.util.Random;

/**
 * Created by Muhi on 14.04.2017.
 */

public class Elevator {

    private static int[] elevatorFields = new int[7];

    private static int randomElevatorFieldnumber;
    static Random rn = new Random();


    public static void generateElevator() {

        for (int i = 0; i < 7; i++) {

            int number = rn.nextInt(99 - 2 + 1) + 2;             //(max - min + 1) + min
            elevatorFields[i] = number;

        }
    }

    public static int getNewElevatorFieldnumber(int fieldnumber) {

        while (true) {

//             randomElevatorFieldnumber = (int) (Math.randomElevatorFieldnumber() * elevatorFields.length);

            int randomNumber = rn.nextInt(6 - 0 + 1) + 0;         //(max - min + 1) + min
            randomElevatorFieldnumber = elevatorFields[randomNumber];

            if (randomElevatorFieldnumber != fieldnumber) {

                break;
            }

        }

        System.out.println("new elavator field" + randomElevatorFieldnumber);             //show the value of randomElevatorFieldnumber to see if there is a mistake

        return randomElevatorFieldnumber;
    }

    public static int[] getElevatorFields() {

        return elevatorFields;
    }
}
