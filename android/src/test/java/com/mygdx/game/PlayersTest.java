package com.mygdx.game;

import com.mygdx.game.Players.Player;
import com.mygdx.game.Players.PlayerColor;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *Created by muhamed on 06.06.17.
 */

//Unit Testing of Player Class
public class PlayersTest {

    private Player player;

    @Before
    public void setPlayer(){

        player = new Player("Max Mustermann");

    }

    @After
    public void deletePlayer(){

        player = null;
    }

    //short Test of functionality

    @Test
    public void testFunktionality(){

        //test change colors
        for (PlayerColor color: PlayerColor.values())
        {
            player.setFarbe(color);
            Assert.assertEquals(color,player.getFarbe());
            Assert.assertEquals(color.toString(), player.getFarbe().toString());

        }

        //test change player name
        player.setSpielername("Muhamed Siljic");
        Assert.assertEquals("Muhamed Siljic", player.getSpielername());

    }


}