package com.mygdx.game;

import com.mygdx.game.display.Coordinates;
import com.mygdx.game.game.Field;
import com.mygdx.game.game.GameField;
import com.mygdx.game.game.Player;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by muhamed on 20.06.17.
 */

public class CoordinatesTest {

    private String TAG1 = "PLAYER ONE -> ";
    private String TAG2 = "PLAYER TWO -> ";

    private GameField gameField;
    private Player playerOne;
    private Player playerTwo;
    private Coordinates playerOneCoordinates;
    private Coordinates playerTwoCoordinates;
    private Field helperField;

    @Before
    public void initializeCoordinates() {

        gameField = GameField.createGameField();
        playerOne = gameField.getPlayer(Player.PLAYER_ONE_ID);
        playerTwo = gameField.getPlayer(Player.PLAYER_TWO_ID);

        playerOneCoordinates = new Coordinates(gameField.getStartField().getPosX(), gameField.getStartField().getPosY());
        playerTwoCoordinates = new Coordinates(gameField.getPlayerTwo().getCurrentField().getPosX(), gameField.getPlayerTwo().getCurrentField().getPosY());

    }


    @After
    public void deleteItems() {

        gameField = null;
        playerOne = null;
        playerTwo = null;
        playerOneCoordinates = null;
        playerTwoCoordinates = null;
    }


    @Test
    public void checkPos() {

        //check if get() returns the right positions.
        int posX = playerOneCoordinates.getX();
        int posY = playerOneCoordinates.getY();

        printData(TAG1, playerOneCoordinates);

        Assert.assertEquals(gameField.getStartField().getPosX(), posX);
        Assert.assertEquals(gameField.getStartField().getPosY(), posY);

    }

    @Test
    public void checkHashCode() {
        //check hashCode()
        int hash = playerOneCoordinates.hashCode();

        System.out.printf("Hash Code -> %d\n", hash);

        Assert.assertEquals(32, hash);

    }

    @Test
    public void checkEquals() {

        //check equals()
        boolean equal = playerOneCoordinates.equals(new Coordinates(1, 1));
        Assert.assertEquals(true, equal);
    }

    @Test
    public void checkPlayerOne() {
        //check with gamefield and playerOne

        helperField = movePlayer(playerOne, 5);

        playerOneCoordinates = new Coordinates(helperField.getPosX(), helperField.getPosY());
        Assert.assertEquals(new Coordinates(6, 1), playerOneCoordinates);

        helperField = movePlayer(playerOne, 1);

        printData(TAG1, playerOneCoordinates);

        playerOneCoordinates = new Coordinates(helperField.getPosX(), helperField.getPosY());
        Assert.assertEquals(new Coordinates(7, 1), playerOneCoordinates);

        printData(TAG1, playerOneCoordinates);
    }

    @Test
    public void checkPlaerTwo() {
        //check same with player two
        helperField = movePlayer(playerTwo, 10);

        playerTwoCoordinates = new Coordinates(helperField.getPosX(), helperField.getPosY());
        Assert.assertEquals(new Coordinates(10, 2), playerTwoCoordinates);
        printData(TAG2, playerTwoCoordinates);
    }


    private Field movePlayer(Player player, int range) {

        for (int i = 0; i < range; i++) {

            player.move();
        }

        return player.getCurrentField();
    }

    private void printData(String TAG, Coordinates playerCoordinates) {
        System.out.println(TAG + playerCoordinates.toString());
    }
}
