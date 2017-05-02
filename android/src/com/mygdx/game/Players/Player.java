package com.mygdx.game.Players;

/**
 * Created by Muhi on 05.04.2017.
 */

public class Player {

    private String spielername;
    private PlayerColor farbe;


    public void Player(String name) {

        this.spielername = name;

    }

    public void setSpielername(String spielername) {

        this.spielername = spielername;

    }

    public String getSpielername() {

        return spielername;
    }


    public PlayerColor getFarbe() {
        return farbe;
    }

    public void setFarbe(PlayerColor farbe) {
        this.farbe = farbe;
    }
}
