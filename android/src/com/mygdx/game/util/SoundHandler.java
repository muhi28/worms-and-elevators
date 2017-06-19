package com.mygdx.game.util;

import com.mygdx.game.MusicManager;

/**
 * Created by muhamed on 12.06.17.
 */

public class SoundHandler {

    private SoundHandler() {
    }

    private static MusicManager musicManager;

    public static void initializeMusicManager() {
        musicManager = new MusicManager();
    }

    public static MusicManager getMusicManager() {
        return musicManager;
    }
}
