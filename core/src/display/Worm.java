package display;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class Worm extends Actor {

    private final Sprite player;
    private final RenderPositionCalculator renderPositionCalculator;

    public Worm(Sprite player, RenderPositionCalculator renderPositionCalculator) {

        this.player = player;
        this.renderPositionCalculator = renderPositionCalculator;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        Coordinates coordinatesOfPlayer = renderPositionCalculator.getCoordinatesOfPlayer();
        batch.draw(player, coordinatesOfPlayer.getX(), coordinatesOfPlayer.getY());
    }
}