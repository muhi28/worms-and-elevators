package com.mygdx.game.game;


/**
 * Created by Muhi on 11.04.2017.
 */


public class Player {

    private Field currentField;


    public Player(Field currentField) {

        this.currentField = currentField;
    }

    public void move() throws NullPointerException {

        if (currentField.getNextField() == null) throw new NullPointerException("Naechstes Feld ist außerhalb des Spielfeldes !!!");

        currentField = currentField.getNextField();
    }

    public Field getCurrentField() {

        return currentField;
    }

    public void setCurrentField(Field field) {
        currentField = field;
    }

}
