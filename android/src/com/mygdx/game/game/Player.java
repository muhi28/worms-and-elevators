package com.mygdx.game.game;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhi on 11.04.2017.
 */


public class Player {

    private Field currentField;
    /**
     * The constant counterList.
     */
    public static List<Integer> counterList = new ArrayList<Integer>();
    /**
     * The constant currentPlayer.
     */
    public static int currentPlayer = 0;
    private Player nextPlayer;


    /**
     * Instantiates a new Player.
     *
     * @param currentField the current field
     */
    public Player(Field currentField) {

        this.currentField = currentField;
        counterList.add(0);
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
     * Switch player.
     */
    public static void switchPlayer(){
        if (counterList.size()==2){
            if (currentPlayer ==0){
                currentPlayer =1;
            }
            else{
                currentPlayer = 0;
            }
        }
        if (counterList.size()==3){
            if (currentPlayer==0){
                currentPlayer = 1;
            }
            else if (currentPlayer == 1){
                currentPlayer = 2;
            }
            else{
                currentPlayer = 0;
            }
        }
        if (counterList.size()==4){
            if (currentPlayer==0){
                currentPlayer = 1;
            }
            else if (currentPlayer == 1){
                currentPlayer = 2;
            }
            else if(currentPlayer == 2){
                currentPlayer = 3;
            }
            else{
                currentPlayer = 0;
            }
        }
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

}
