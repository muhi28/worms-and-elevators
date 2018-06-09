package com.mygdx.game.players;

/**
 * The enum Player color.
 */
public enum PlayerColor {
    /**
     * Blue player color.
     */
    BLAU,
    /**
     * Red player color.
     */
    ROT,
    /**
     * Yellow player color.
     */
    GELB,
    /**
     * Green player color.
     */
    GRÜN;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    /**
     * Gets from string.
     *
     * @param string the string
     * @return the from string
     */
    public static PlayerColor getFromString(String string) {
        for (PlayerColor color : PlayerColor.values()) {
            if (color.toString().equals(string)) {
                return color;
            }
        }

        return null;
    }
}