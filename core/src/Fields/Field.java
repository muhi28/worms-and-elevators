package Fields;

/**
 * Created by Muhi on 03.04.2017.
 */

public class Field {



    private float posX;
    private float posY;

    public void Field(float x, float y){

        this.posX = x;
        this.posY = y;

    }
    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }
}
