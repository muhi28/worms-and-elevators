package logik;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Muhi on 11.04.2017.
 */

public class GameField  {

    public boolean mapGrid[][];

    public static final int CELL_SIZE = 25;

    public int mapwidth, mapheight;

    public GameField(int width, int height){

        mapGrid = new boolean[width][height];

        mapwidth = width * CELL_SIZE;
        mapheight = height * CELL_SIZE;
    }

    public void render(ShapeRenderer renderer){
        
        for(int x = 0; x < mapGrid.length; x++){

            for (int y = 0; y < mapGrid[x].length ; y++) {

                //renderer.line(0,0,10);
            }
        }
        
    }
}
