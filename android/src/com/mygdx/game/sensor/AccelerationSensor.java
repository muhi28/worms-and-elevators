package com.mygdx.game.sensor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by muhamed on 19.06.17.
 */

/**
 * Class which provides the acceleration sensor.
 */
public class AccelerationSensor {

    /**
     * Sensor Items
     */
    float accelLast;  //Current acceleration value and gravity
    float accelVal; //Last acceleration value and gravity
    float shake; //Acceleration value differ from gravity

    Long lastTimeShaken;

    /**
     * Constructor.
     */
    public AccelerationSensor() {
    }

    /**
     * Used to check whether the device was accelerated or not.
     *
     * @param winnerDecided - the current winner
     * @param gameStarted - game started or not
     * @return - true -> acceleration given
     *           false -> no acceleration
     */
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

    /**
     * Check whether the device was shaken or not.
     *
     * @param lastTimeShaken - the last time the device was shaken
     * @return - true -> device shaken
     *           false -> device wasn't shaken
     */
    public boolean deviceShaken(Long lastTimeShaken){
        return TimeUtils.millis() > (lastTimeShaken + 350);
    }
}
