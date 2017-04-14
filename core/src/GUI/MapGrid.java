package GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Muhi on 12.04.2017.
 */

public class MapGrid {



    public static final int CELL_SIZE = 32;

    public int mapWidth, mapHeight;


    public MapGrid(int width, int height){


        mapWidth = width;
        mapHeight = height;

    }

    public void render(ShapeRenderer renderer){

        for(int x = 0; x <= mapWidth; x += CELL_SIZE){

            for (int y = 0; y <= mapHeight - 150; y+=CELL_SIZE) {


                renderer.rect(x,y,CELL_SIZE,CELL_SIZE);

            }
        }

    }

}
