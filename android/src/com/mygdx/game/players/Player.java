package com.mygdx.game.players;

/**
 * Created by Muhi on 05.04.2017.
 */
public class Player {

    private String playerName;
    private PlayerColor farbe;


    /**
     * Instantiates a new Player.
     *
     * @param name the name
     */
    public Player(String name) {

        this.playerName = name;

    }

    /**
     * Sets playerName.
     *
     * @param playerName the playerName
     */
    public void setPlayerName(String playerName) {

        this.playerName = playerName;

    }

    /**
     * Gets playerName.
     *
     * @return the playerName
     */
    public String getPlayerName() {

        return playerName;
    }


    /**
     * Gets farbe.
     *
     * @return the farbe
     */
    public PlayerColor getFarbe() {
        return farbe;
    }

    /**
     * Sets farbe.
     *
     * @param farbe the farbe
     */
    public void setFarbe(PlayerColor farbe) {
        this.farbe = farbe;
    }
}
