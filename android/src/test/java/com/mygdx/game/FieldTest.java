package com.mygdx.game;

import com.mygdx.game.game.Field;
import com.mygdx.game.game.GameField;
import com.mygdx.game.game.Player;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.List;



/**
 *Created by Muhi on 29.04.2017.
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
        Player player = gameField.getPlayer(Player.PLAYER_ONE_ID);
        int fieldNumberBeforeMove = player.getCurrentField().getFieldnumber();
        player.move();
        player.move();
        player.move();

        Assert.assertEquals(fieldNumberBeforeMove + 3, player.getCurrentField().getFieldnumber());
    }

    /**
     * Test next field exception.
     */
    @Test(expected = MyRuntimeException.class)
    public void testNextFieldException(){

        Player player = gameField.getPlayer(Player.PLAYER_ONE_ID);

        List<Field> fields = gameField.getFields();

        for (int i = 0; i <= fields.size() ; i++) {

            player.move();

        }

        Assert.assertEquals(null, player.getCurrentField().getNextField());
    }

    /**
     * Test check field numbers.
     */
    @Test
    public void testCheckFieldNumbers(){
        List<Field> fields = gameField.getFields();

        int fieldnumber = fields.size();

        for (int i = fields.size(); i > 0; i--) {

            Assert.assertEquals(fieldnumber, gameField.getFieldFrom(i).getFieldnumber());
            boolean same = gameField.getFieldFrom(i).sameField(gameField.getFieldFrom(i));
            Assert.assertEquals(true, same);
            fieldnumber--;
        }
    }

    @Test
    public void testPos() {

        Player player = gameField.getPlayer(Player.PLAYER_ONE_ID);

        int posX = player.getCurrentField().getPosX();
        int posY = player.getCurrentField().getPosY();

        //check spawn position equality
        Assert.assertEquals(gameField.getStartField().getPosX(), posX);
        Assert.assertEquals(gameField.getStartField().getPosY(), posY);

        //check moved position
        for (int i = 0; i < 5; i++) {

            player.move();
        }

        posX = player.getCurrentField().getPosX();
        posY = player.getCurrentField().getPosY();

        Assert.assertEquals(gameField.getPlayerOne().getCurrentField().getPosX(), posX);
        Assert.assertEquals(gameField.getPlayerOne().getCurrentField().getPosY(), posY);

    }
}
