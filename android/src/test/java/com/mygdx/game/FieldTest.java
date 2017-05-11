package com.mygdx.game;

import com.mygdx.game.game.Field;
import com.mygdx.game.game.GameField;
import com.mygdx.game.game.Player;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by Muhi on 29.04.2017.
 */
public class FieldTest {

    private GameField gameField;

    /**
     * Create game fields.
     */
    @Before
    public void createGameFields() {
        gameField = GameField.createGameField();
    }

    /**
     * Test current field.
     */
    @Test
    public void testCurrentField() {
        Player player = gameField.getPlayer();
        int fieldNumberBeforeMove = player.getCurrentField().getFieldnumber();
        player.move();
        player.move();
        player.move();

        Assert.assertEquals(fieldNumberBeforeMove + 3, player.getCurrentField().getFieldnumber());
    }

    /**
     * Test next field exception.
     */
    @Test (expected = NullPointerException.class)
    public void testNextFieldException(){

        Player player = gameField.getPlayer();

        List<Field> fields = gameField.getFields();

        for (int i = 0; i <= fields.size() ; i++) {

            player.move();

        }

        assertEquals(null,player.getCurrentField().getNextField());
    }

    /**
     * Test check field numbers.
     */
    @Test
    public void testCheckFieldNumbers(){
        List<Field> fields = gameField.getFields();

        int fieldnumber = fields.size();

        for (int i = fields.size(); i > 0; i--) {

            assertEquals(fieldnumber,gameField.getFieldFrom(i).getFieldnumber());
            fieldnumber--;
        }
    }
}
