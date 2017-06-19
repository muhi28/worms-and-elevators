package com.mygdx.game;

import com.mygdx.game.game.Elevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *Created by muhamed on 06.06.17.
 */

public class ElevatorTest {

    private int[] elevs = new int[Elevator.getElevatorFields().length];

    @Before
    public void generateElevators(){

        Elevator.generateElevator();
    }

    @Test
    public void testgetElevators(){

        elevs = Elevator.getElevatorFields();

        Assert.assertArrayEquals(Elevator.getElevatorFields(),elevs);

    }

    @Test
    public void testNewElevatorFieldnumb(){

        elevs = Elevator.getElevatorFields();

        int newField = Elevator.getNewElevatorFieldnumber(15);

        boolean contain = contains(elevs,newField);

        Assert.assertEquals(true, contain);
    }

    private boolean contains(int[] data, int value){

        for (int aData : data) {

            if (aData == value)
                return true;
        }

        return false;
    }
}
