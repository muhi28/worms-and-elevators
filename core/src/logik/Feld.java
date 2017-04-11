package logik;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Muhi on 11.04.2017.
 */

public class Feld extends Sprite {

    private static final String PATH = "background_grass.png";
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    private Feld nextFeld;


    private final int feldnummer;

    public Feld(int px, int py,TextureRegion textureRegion, int feldnummer){

        super(new Texture(Gdx.files.internal("player_blau_icon.png")));

        this.feldnummer = feldnummer;
    }

    public void setNextFeld(Feld nextFeld){

        this.nextFeld = nextFeld;
    }

    public int getFeldnummer(){

        return feldnummer;
    }

    public Feld getNextFeld(){

        return nextFeld;
    }



}
