package com.mygdx.game.game;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhi on 11.04.2017.
 */


public class Player {

    public final static String PLAYER_ONE_ID = "ONE";
    public final static String PLAYER_TWO_ID = "TWo";

    private Field currentField;
    private final String plyerId;
    /**
     * The constant counterList.
     */
    public static List<Integer> counterList = new ArrayList<Integer>();
    /**
     * The constant currentPlayer.
     */
    public static int currentPlayer = 0;


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
    public void move() throws NullPointerException {

        if (currentField.getNextField() == null) throw new NullPointerException("Naechstes Feld ist außerhalb des Spielfeldes !!!");

        currentField = currentField.getNextField();
    }

    /**
     * Increase counter.
     */
    public static void increaseCounter(){
        counterList.set(currentPlayer, counterList.get(currentPlayer)+1);
    }

    /**
     * Get counter int.
     *
     * @return the int
     */
    public static int getCounter(){
        return counterList.get(currentPlayer);
    }

    /**
     * Reset counter.
     */
    public static void resetCounter(){
            counterList.set(currentPlayer, 0);
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


    public String getPlyerId() {
        return plyerId;
    }
}
