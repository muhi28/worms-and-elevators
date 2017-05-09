package test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mygdx.game.dice.Dice;

import static junit.framework.Assert.assertEquals;


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

        Assert.assertNotEquals(89, dice.getResult());

    }

    @Test(expected = AssertionError.class)
    public void testZeroResult() {

        int range = dice.rollTheDice();

        assertEquals(0, range);
    }
}
