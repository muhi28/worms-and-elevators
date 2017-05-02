package tests;


import org.junit.Before;
import org.junit.Test;

import game.GameField;
import game.Player;

import static org.junit.Assert.assertEquals;


/**
 * Created by Muhi on 29.04.2017.
 */


public class FieldTest {

    private GameField gameField;

    @Before
    public void createGameFields() {
        gameField = GameField.createGameField();
    }

    @Test
    public void testCurrentField() {
        Player player = gameField.getPlayer();
        int fieldNumberBeforeMove = player.getCurrentField().getFieldnumber();
        player.move();
        player.move();
        player.move();

        assertEquals(fieldNumberBeforeMove + 3, player.getCurrentField().getFieldnumber());
    }
}
