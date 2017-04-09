package logic;

/**
 * Created by Muhi on 06.04.2017.
 */

public class Spieler {

    private int posX;
    private int posY;
    private String name;


    public Spieler(String name, int posX, int posY){

        this.posX = posX;

        this.posY = posY;

        this.name = name;
    }


    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }



}
