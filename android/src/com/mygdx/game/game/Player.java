package com.mygdx.game.game;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhi on 11.04.2017.
 */


public class Player {

    private Field currentField;
    public static List<Integer> counterList = new ArrayList<Integer>();
    public static int currentPlayer = 0;
    private Player nextPlayer;


    public Player(Field currentField) {

        this.currentField = currentField;
        counterList.add(0);
    }

    public void move() throws NullPointerException {

        if (currentField.getNextField() == null) throw new NullPointerException("Naechstes Feld ist außerhalb des Spielfeldes !!!");

        currentField = currentField.getNextField();
    }

    public static void increaseCounter(){
        counterList.set(currentPlayer, counterList.get(currentPlayer)+1);
    }

    public static int getCounter(){
        return counterList.get(currentPlayer);
    }

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

        public static void resetCounter(){
            counterList.set(currentPlayer, 0);
        }

    public Field getCurrentField() {

        return currentField;
    }

    public void setCurrentField(Field field) {
        currentField = field;
    }

}
