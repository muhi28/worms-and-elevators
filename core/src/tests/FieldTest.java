package tests;


import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import dice.Dice;
import game.Field;
import game.GameField;
import main_controler.Controler;

import static org.junit.Assert.assertEquals;


/**
 * Created by Muhi on 29.04.2017.
 */


public class FieldTest {

    private GameField gameField;
    private Dice dice;
    private Controler controler;

    @Before
    public void createGameFields(){

        gameField = GameField.createGameField();
        dice = new Dice(6);


    }

    @After
    public void clearGameField(){

        gameField = null;

    }

    @Test
    public void testCurrentField(){

        controler = new Controler();

        controler.movement(gameField.getPlayer(),dice);

        int fieldnumber = gameField.getFieldofPlayer().getFieldnumber();

        assertEquals(gameField.getPlayer().getCurrentField().getFieldnumber(),fieldnumber);
    }
}
