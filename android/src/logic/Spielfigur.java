package logic;

import android.graphics.Bitmap;



import Fields.Dice;

/**
 * Created by Muhi on 06.04.2017.
 */

public class Spielfigur {



    private String name;
    private String farbe;
    private int position;
    private boolean host;
    private int id;


    public Spielfigur(String name, int id){

        this.name = name;
        this.id = id;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFarbe() {
        return farbe;
    }

    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
