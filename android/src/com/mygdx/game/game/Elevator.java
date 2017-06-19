package com.mygdx.game.game;

import java.util.Random;

/**
 * Created by Muhi on 14.04.2017.
 */
public class Elevator {

    public static Random random = new Random();

    private static final int ELEVATORS = 12;
    private static final int GAMEFIELDS = 100;

    private static final int[] elevatorFields = new int[ELEVATORS];
    private static int randomElevatorFieldnumber;


    private Elevator() {
    }

    /**
     * Generate elevator.
     */
    public static void generateElevator() {

        for (int i = 0; i < elevatorFields.length; i++) {

            int number = random.nextInt(GAMEFIELDS) + 1;             //(max - min + 1) + min

            if (checkIfGoalOrStart(number))
                number = 94;

            elevatorFields[i] = number;
        }
    }

    private static boolean checkIfGoalOrStart(int steps) {
        return steps == 91 || steps == 1;
    }

    /**
     * Gets new elevator fieldnumber.
     *
     * @param fieldnumber the fieldnumber
     * @return the new elevator fieldnumber
     */
    public static int getNewElevatorFieldnumber(int fieldnumber) {

        while (true) {

            int randomNumber = random.nextInt(ELEVATORS);         //(max - min + 1) + min
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
