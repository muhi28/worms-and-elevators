package test;

import org.junit.Before;
import org.junit.Test;

import com.mygdx.game.game.GameField;
import com.mygdx.game.game.Player;

import junit.framework.Assert;



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

        Assert.assertEquals(fieldNumberBeforeMove + 3, player.getCurrentField().getFieldnumber());
    }
}
