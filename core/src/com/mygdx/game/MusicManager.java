package com.mygdx.game;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Patrick on 09.04.2017.
 */

public class MusicManager {

    private Music mainMusic;
    private Sound diceSound;

    public MusicManager() {

        this.mainMusic = Gdx.audio.newMusic(Gdx.files.internal("m.mp3"));
       // this.diceSound = diceSound;
    }

    public Music getMainMusic() {
        return mainMusic;
    }
}
