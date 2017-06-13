package com.mygdx.game.game;

import java.util.Random;

/**
 * Created by Muhi on 14.04.2017.
 */
public class Elevator {

    public static Random random = new Random();

    private static int[] elevatorFields = new int[7];
    private static int randomElevatorFieldnumber;


    private Elevator() {
    }

    /**
     * Generate elevator.
     */
    public static void generateElevator() {

        for (int i = 0; i < 7; i++) {

            int number = random.nextInt(99 - 2 + 1) + 2;             //(max - min + 1) + min
            elevatorFields[i] = number;

        }
    }

    /**
     * Gets new elevator fieldnumber.
     *
     * @param fieldnumber the fieldnumber
     * @return the new elevator fieldnumber
     */
    public static int getNewElevatorFieldnumber(int fieldnumber) {

        while (true) {

            int randomNumber = random.nextInt(6 - 0 + 1) + 0;         //(max - min + 1) + min
            randomElevatorFieldnumber = elevatorFields[randomNumber];

            if (randomElevatorFieldnumber != fieldnumber) {

                break;
            }

        }
        return randomElevatorFieldnumber;
    }

    /**
     * Get elevator fields int [ ].
     *
     * @return the int [ ]
     */
    public static int[] getElevatorFields() {

        return elevatorFields;
    }
}
