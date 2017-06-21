package com.mygdx.game.game;


import com.mygdx.game.MyRuntimeException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhi on 11.04.2017.
 */


public class Player {

    public static final String PLAYER_ONE_ID = "ONE";
    public static final String PLAYER_TWO_ID = "TWO";

    private Field currentField;
    private final String plyerId;
    /**
     * The constant counterList.
     */
    private static final List<Integer> counterList = new ArrayList<>();
    /**
     * The constant currentPlayerIndex.
     */
    private static int currentPlayerIndex = 0;


    /**
     * Instantiates a new Player.
     *
     * @param currentField the current field
     */
    public Player(Field currentField, String plyerId) {

        this.currentField = currentField;
        counterList.add(0);
        this.plyerId = plyerId;
    }

    /**
     * Move.
     *
     * @throws NullPointerException the null pointer exception
     */
    public void move() {

        if (currentField.getNextField() == null)
            throw new MyRuntimeException("Naechstes Feld ist außerhalb des Spielfeldes !!!");

        currentField = currentField.getNextField();
    }

    /**
     * Increase counter.
     */
    public static void increaseCounter(){
        counterList.set(currentPlayerIndex, counterList.get(currentPlayerIndex)+1);
    }

    /**
     * Get counter int.
     *
     * @return the int
     */
    public static int getCounter(){
        return counterList.get(currentPlayerIndex);
    }

    /**
     * Reset counter.
     */
    public static void resetCounter(){
        counterList.set(currentPlayerIndex, 0);
    }

    /**
     * Gets current field.
     *
     * @return the current field
     */
    public Field getCurrentField() {

        return currentField;
    }

    /**
     * Sets current field.
     *
     * @param field the field
     */
    public void setCurrentField(Field field) {
        currentField = field;
    }

    public static void switchCurrentPlayerIndex(){
        if (currentPlayerIndex == 0){
            currentPlayerIndex = 1;
        }
        else if (currentPlayerIndex == 1){
            currentPlayerIndex = 0;
        }
    }

    public static int getCurrentPlayerIndex(){
        return currentPlayerIndex;
    }


    public String getPlyerId() {
        return plyerId;
    }
}
