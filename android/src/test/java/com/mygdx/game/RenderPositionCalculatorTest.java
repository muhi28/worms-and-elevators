package com.mygdx.game;


import com.mygdx.game.display.Coordinates;
import com.mygdx.game.display.RenderPositionCalculator;
import com.mygdx.game.game.Field;
import com.mygdx.game.game.GameField;
import com.mygdx.game.game.Player;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


/**
 * Created by muhamed on 21.06.17.
 */

public class RenderPositionCalculatorTest {

    private GameField gameField;
    private Player playerOne;
    private Coordinates playerCoordinates;
    private RenderPositionCalculator calculator;
    private Coordinates helper;


    @Before
    public void initializeItems() {

        gameField = GameField.createGameField();
        playerOne = gameField.getPlayerOne();
        playerCoordinates = new Coordinates(0, 0);
        calculator = new RenderPositionCalculator(gameField);
    }

    @After
    public void deleteItems() {
        gameField = null;
        playerOne = null;
        playerCoordinates = null;
        calculator = null;
    }


    @Test
    public void testGetter() {

        //test getCoordinates of Player
        helper = calculator.getCoordinatesOfPlayer(Player.PLAYER_ONE_ID);
        Assert.assertEquals(playerCoordinates, helper);


        Field playerField = calculator.getPlayerField(Player.PLAYER_ONE_ID);
        Assert.assertEquals(playerOne.getCurrentField(), playerField);

        playerField = movePlayer(playerOne, 5);

        helper = calculator.getCoordinatesOfField(playerField);
        playerCoordinates = new Coordinates(0, 0);
        Assert.assertEquals(playerCoordinates, helper);

        playerField = movePlayer(playerOne, 4);

        helper = calculator.getCoordinatesOfField(playerField.getFieldnumber());
        Assert.assertEquals(playerCoordinates, helper);


        //Coordinates Between
        List<Coordinates> between = calculator.getCoordinatesBetween(gameField.getStartField(), gameField.getGoal());

        for (int i = 0; i < between.size(); i++) {

            Assert.assertEquals(new Coordinates(0, 0), between.get(i));
        }

    }

    private Field movePlayer(Player player, int range) {

        for (int i = 0; i < range; i++) {

            player.move();
        }

        return player.getCurrentField();
    }
}
