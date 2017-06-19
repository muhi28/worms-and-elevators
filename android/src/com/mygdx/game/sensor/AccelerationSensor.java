package com.mygdx.game.sensor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.maincontroller.Controller;

/**
 * Created by muhamed on 19.06.17.
 */

public class AccelerationSensor {

    private Long lastTimeShaken;
    /**
     * Sensor Items
     */
    float accelLast;  //Current acceleration value and gravity
    float accelVal; //Last acceleration value and gravity
    float shake; //Acceleration value differ from gravity
    public AccelerationSensor(){

        lastTimeShaken = TimeUtils.millis();
    }



    public boolean checkAcceleration(boolean winnerDecided, boolean gameStarted){
        if (!winnerDecided && Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)) {

            float x = Gdx.input.getAccelerometerX();
            float y = Gdx.input.getAccelerometerY();

            accelLast = accelVal;
            accelVal = (float) Math.sqrt((double) (x * x + y * y));
            float delta = accelVal - accelLast;
            shake = shake * 0.9f + delta;

            if (shake > 6 && gameStarted) {

                return true;

            }
        }

        return false;
    }

    public boolean deviceShaken(Long lastTimeShaken){
        return TimeUtils.millis() > (lastTimeShaken + 350);
    }
}
