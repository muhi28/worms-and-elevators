package com.mygdx.game.util;

import com.badlogic.gdx.utils.Logger;


/**
 * The type Custom logger.
 */
public class CustomLogger extends Logger {

    private final boolean writeToConsole;
    private final String tag;

    /**
     * Instantiates a new Custom logger.
     *
     * @param tag the tag
     */
    public CustomLogger(String tag) {
        this(tag, false);
    }

    /**
     * Instantiates a new Custom logger.
     *
     * @param tag            the tag
     * @param writeToConsole the write to console
     */
    public CustomLogger(String tag, boolean writeToConsole) {
        super(tag);
        this.writeToConsole = writeToConsole;
        this.tag = tag;
    }

    @Override
    public void debug(String message) {
        writeToConsole(message);
        super.debug(message);
    }

    @Override
    public void info(String message) {
        writeToConsole(message);
        super.info(message);
    }

    @Override
    public void error(String message) {
        writeToConsole(message);
        super.error(message);
    }

    private void writeToConsole(String message) {
        if (writeToConsole) {
            System.out.println(tag + ": " + message);
        }
    }
}
