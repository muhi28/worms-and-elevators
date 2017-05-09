package com.mygdx.game.game;

import android.util.Log;

import java.util.Random;

/**
 * Created by Muhi on 14.04.2017.
 */

public class Elevator {

    private static int[] elevatorFields = new int[7];

    private static int randomElevatorFieldnumber;
    private static Random rn = new Random();
    private static final String TAG = "New Elevator Field";


    private Elevator(){}

    public static void generateElevator() {

        for (int i = 0; i < 7; i++) {

            int number = rn.nextInt(99 - 2 + 1) + 2;             //(max - min + 1) + min
            elevatorFields[i] = number;

        }
    }

    public static int getNewElevatorFieldnumber(int fieldnumber) {

        while (true) {

            int randomNumber = rn.nextInt(6 - 0 + 1) + 0;         //(max - min + 1) + min
            randomElevatorFieldnumber = elevatorFields[randomNumber];

            if (randomElevatorFieldnumber != fieldnumber) {

                break;
            }

        }

        Log.d(TAG,"New Elevator Field: " + randomElevatorFieldnumber); //show the value of randomElevatorFieldnumber to see if there is a mistake

        return randomElevatorFieldnumber;
    }

    public static int[] getElevatorFields() {

        return elevatorFields;
    }
}
