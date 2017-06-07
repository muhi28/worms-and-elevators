package com.mygdx.game;

import com.mygdx.game.game.Field;
import com.mygdx.game.game.GameField;
import com.mygdx.game.game.Player;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

/**
 *Created by muhamed on 06.06.17.
 */

public class PlayerTest {

    private GameField gameField;
    private Player player;

    private Field current;

    private int nextPos;

    private Random random;

    @Before
    public void createGameField(){

        gameField = GameField.createGameField();

        player = gameField.getPlayer(Player.PLAYER_ONE_ID);

        current = player.getCurrentField();

        random = new Random();

    }

    @After
    public void deleteGameField(){

        gameField = null;
    }

    @Test
    public void testPlayerMove(){

        Field field = doMovement();

        Assert.assertEquals(current.getFieldnumber() + nextPos,field.getFieldnumber());

    }


    @Test
    public void testGetCurrentField(){

         Field field = doMovement();

        Assert.assertEquals(gameField.getFieldFrom(current.getFieldnumber() + nextPos),field);
    }

    @Test
    public void testsetCurrentField(){

        player.setCurrentField(gameField.getFieldFrom(15));

        Assert.assertEquals(gameField.getFieldFrom(15),player.getCurrentField());

    }


    private Field doMovement(){

        nextPos = random.nextInt(6 - 1 + 1) + 1;

        for (int i = 0; i < nextPos; i++) {

            player.move();
        }

        return player.getCurrentField();
    }

}
