package logic;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Muhi on 10.04.2017.
 */

public class Feld extends Texture{

    private float posX;
    private float posY;
    private String typ;

    public Feld(int width, int height, Pixmap.Format format) {
        super(width, height, format);
    }


    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }
}
