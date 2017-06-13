package com.mygdx.game;

import com.mygdx.game.dice.Dice;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;


/**
 *Created by Muhi on 28.04.2017.
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

        assertEquals(dice.getResult(), range);

    }

    /**
     * Test max dice result.
     */
    @Test
    public void testMaxDiceResult() {

        Integer range = dice.rollTheDice();

        assertEquals(dice.getResult(), range);

        Assert.assertNotEquals(null, dice.getResult());

    }

    /**
     * Test zero result.
     */
    @Test(expected = AssertionError.class)
    public void testZeroResult() {

        int range = dice.rollTheDice();

        assertEquals(0, range);
    }
}
