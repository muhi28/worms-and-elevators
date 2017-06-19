package com.mygdx.game.util;

import android.util.Log;

import com.badlogic.gdx.utils.Logger;


/**
 * The type Custom logger.
 */
public class CustomLogger extends Logger {

    private final boolean writeToConsole;
    private final boolean writeToAndroid;
    private final String tag;

    /**
     * Instantiates a new Custom logger.
     *
     * @param tag the tag
     */
    public CustomLogger(String tag) {
        this(tag, false, true);
    }

    /**
     * Instantiates a new Custom logger.
     *
     * @param tag            the tag
     * @param writeToConsole the write to console
     */
    public CustomLogger(String tag, boolean writeToConsole, boolean writeToAndroid) {
        super(tag);
        this.writeToConsole = writeToConsole;
        this.tag = tag;
        this.writeToAndroid = writeToAndroid;
    }

    @Override
    public void debug(String message) {
        if(writeToAndroid){
            Log.d(tag, message);
        }
        writeToConsole(message);

        super.debug(message);
    }

    @Override
    public void info(String message) {
        if(writeToAndroid){
            Log.i(tag, message);
        }
        writeToConsole(message);
        super.info(message);
    }

    @Override
    public void error(String message) {
        if(writeToAndroid){
            Log.e(tag, message);
        }
        writeToConsole(message);
        super.error(message);
    }

    private void writeToConsole(String message) {
        if (writeToConsole) {
            Log.d(tag, ":" + message);
        }
    }
}
