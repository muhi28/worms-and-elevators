package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Muhi on 05.04.2017.
 */

public class MyWorld {


    private Array<Rectangle> bounds;
    private Array<Vector2> objects;


    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tmr;

    private float tileSize;


    public MyWorld(){

        bounds = new Array<Rectangle>();
        objects = new Array<Vector2>();

        createTiles();
    }

    private void createTiles(){

        tiledMap = new TmxMapLoader().load("Maps/Spielbrett1.tmx");

        tmr = new OrthogonalTiledMapRenderer(tiledMap);
        tileSize = tiledMap.getProperties().get("tilewidth", Integer.class);

        TiledMapTileLayer ground;
        MapLayer object;

        ground = (TiledMapTileLayer) tiledMap.getLayers().get("Spielbrett1");
        object = tiledMap.getLayers().get("Spielfeld");

        createLayer(ground,bounds);
        createObjectLayer(object,objects);
    }

    private void createLayer(TiledMapTileLayer layer, Array<Rectangle> rectangles){

        for (int i = 0; i < layer.getHeight(); i++) {

            for (int j = 0; j < layer.getWidth(); j++) {

               Cell cell = layer.getCell(i,j);

                if(cell == null) continue;
                if(cell.getTile() == null) continue;

                rectangles.add(new Rectangle(j * tileSize,i * tileSize,tileSize, tileSize));


            }
            
        }
    }

    private void createObjectLayer(MapLayer mapLayer, Array<Vector2> objects){


        for (MapObject mo : mapLayer.getObjects()){

            Rectangle rect = ((RectangleMapObject) mo).getRectangle();

            float x = rect.x;
            float y = rect.y;

            objects.add(new Vector2(x,y));
        }

    }

    public void render(OrthographicCamera camera){

        tmr.setView(camera);
        tmr.render();
    }

    public Array<Rectangle> getBounds() {
        return bounds;
    }

    public void setBounds(Array<Rectangle> bounds) {
        this.bounds = bounds;
    }

    public Array<Vector2> getObjects() {
        return objects;
    }

    public void setObjects(Array<Vector2> objects) {
        this.objects = objects;
    }
}
