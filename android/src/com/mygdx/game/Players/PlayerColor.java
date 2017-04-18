package com.mygdx.game.Players;

public enum PlayerColor {
    BLUE,
    RED,
    YELLOW,
    GREEN;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    public static PlayerColor getFromString(String string) {
        for (PlayerColor color : PlayerColor.values()) {
            if (color.toString().equals(string)) {
                return color;
            }
        }

        return null;
    }
}