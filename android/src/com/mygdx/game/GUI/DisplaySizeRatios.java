package com.mygdx.game.GUI;


import com.badlogic.gdx.Gdx;

public class DisplaySizeRatios {


    public static final int NUMBEROF_VERTICALS = 10;
    public static final int NUMBEROF_HORIZONTAL = 10;

    private static final String TAG = "DisplaySizeRatios";

    public static int FIELD_SIZE;
    public static int X_START_FIELD;
    public static int Y_START_FIELD;


    public static int DICE_SIZE;
    public static int Y_DICE;
    public static int X_DICE;

    public static int WORM_SIZE;
    public static int WORM_MOVEMENT;

    public static int X_LABEL;
    public static int Y_LABEL;


    public static void calculateRatios() {


        FIELD_SIZE = Gdx.graphics.getWidth() / NUMBEROF_VERTICALS;
        X_START_FIELD = -FIELD_SIZE;
        Y_START_FIELD = Gdx.graphics.getHeight() - ((NUMBEROF_HORIZONTAL + 1) * FIELD_SIZE);

        DICE_SIZE = Gdx.graphics.getWidth() / 5;
        X_DICE = Gdx.graphics.getWidth() / 2 - (DICE_SIZE / 2);
        Y_DICE = (int) (DICE_SIZE * 0.5);

        WORM_SIZE = FIELD_SIZE;
        WORM_MOVEMENT = 5;

        X_LABEL = Gdx.graphics.getWidth() / 5;
        Y_LABEL = Y_START_FIELD;
        printSettings();


    }

    public static void printSettings() {
        Gdx.app.log(TAG, "device Width: " + Gdx.graphics.getWidth());
        Gdx.app.log(TAG, "device Height: " + Gdx.graphics.getHeight());
        Gdx.app.log(TAG, "fieldSize: " + FIELD_SIZE);
        Gdx.app.log(TAG, "xStartOfField: " + X_START_FIELD);
        Gdx.app.log(TAG, "Y_START_FIELD: " + Y_START_FIELD);
    }

}
