package com.mygdx.game;

import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.dice.Dice;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



/**
 *Created by Muhamed on 28.04.2017.
 */
public class DiceTest {

    private Dice dice;

    /**
     * Create dice.
     */
    @Before
    public void createDice() {
        dice = new Dice(6);
    }

    /**
     * Delete dice.
     */
    @After
    public void deleteDice() {
        dice = null;
    }


    /**
     * Test min dice result.
     */
    @Test
    public void testMinDiceResult() {

        Integer range = dice.rollTheDice();

        Assert.assertEquals(dice.getResult(), range);

    }

    /**
     * Test max dice result.
     */
    @Test
    public void testMaxDiceResult() {

        Integer range = dice.rollTheDice();

        Assert.assertEquals(dice.getResult(), range);

        Assert.assertNotEquals(null, dice.getResult());

    }

    /**
     * Test zero result.
     */
    @Test(expected = AssertionError.class)
    public void testZeroResult() {

        int range = dice.rollTheDice();

        Assert.assertEquals(0, range);
    }

    @Test
    public void testRange() {

        int range = 6;

        dice = new Dice(range, TimeUtils.millis());

        Assert.assertEquals(range, dice.getRange());
    }
}
