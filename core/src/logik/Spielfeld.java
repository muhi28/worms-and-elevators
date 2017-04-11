package logik;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhi on 11.04.2017.
 */

public class Spielfeld {


    private final int numberofVerticals;
    private final int numberofHorizontal;

    private Feld startFeld;
    private Player player;

    private List<Feld> gamefield = new ArrayList<Feld>();

    public Spielfeld(int numberofVerticals, int numberofHorizontal){

        this.numberofVerticals = numberofVerticals;
        this.numberofHorizontal = numberofHorizontal;

    }

    public int getCameraWidth(){

        return Feld.WIDTH * numberofHorizontal;
    }

    public int getCamerHeight(){

        return Feld.HEIGHT * numberofVerticals;
    }

    public Player addPlayer(TextureRegion textureRegion, Feld feld){

         player = new Player(textureRegion,feld);

        return player;
    }

    public Feld createFieldsandReturnStartField(TextureRegion textureRegion){

        List<Feld> fields = new ArrayList<Feld>();

        Feld goal = new Feld(0,0,textureRegion,fields.size());

        fields.add(goal);

        for (int i = 0; i < numberofVerticals; i++) {

            for (int j = 0; j < numberofHorizontal; j++) {

                if(i == 0 && j == 0){
                    continue;
                }

                Feld feld = new Feld(Feld.WIDTH * i, Feld.HEIGHT * j, textureRegion, fields.size());

                feld.setNextFeld(fields.get(fields.size() - 1));

                fields.add(feld);
            }
        }

        this.startFeld = fields.get(fields.size() - 1);

        return startFeld;
    }
}
