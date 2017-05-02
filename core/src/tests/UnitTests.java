package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dice.Dice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Muhi on 28.04.2017.
 */

public class UnitTests {

    private Dice dice;

    @Before
    public void createDice() {

        dice = new Dice(6, false);

    }

    @After
    public void deleteDice() {

        dice = null;
    }


    @Test
    public void testMinDiceResult() {

        int range = dice.rollTheDice();

        assertEquals(dice.getResult(), range);

    }

    @Test
    public void testMaxDiceResult() {

        int range = dice.rollTheDice();

        assertEquals(dice.getResult(), range);

        assertNotEquals(89, dice.getResult());

    }

    @Test(expected = AssertionError.class)
    public void testZeroResult() {

        int range = dice.rollTheDice();

        assertEquals(0, range);
    }
}
