package com.mygdx.game.players;

/**
 * Created by Muhi on 05.04.2017.
 */
public class Player {

    private String spielername;
    private PlayerColor farbe;


    /**
     * Instantiates a new Player.
     *
     * @param name the name
     */
    public Player(String name) {

        this.spielername = name;

    }

    /**
     * Sets spielername.
     *
     * @param spielername the spielername
     */
    public void setSpielername(String spielername) {

        this.spielername = spielername;

    }

    /**
     * Gets spielername.
     *
     * @return the spielername
     */
    public String getSpielername() {

        return spielername;
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
