package cheat;


import com.badlogic.gdx.Gdx;

public class CheatCountThread extends Thread {

    private final CheatCountDown cheatCountDown;

    public CheatCountThread(CheatCountDown cheatCountDown) {
        this.cheatCountDown = cheatCountDown;
    }


    public void run() {
        Gdx.app.log("CheatCountThread", "Start thread");

        try {
            while (!isInterrupted()) {
                Thread.sleep(1500);
                Gdx.app.log("CheatCountThread", "Increase dice");
                cheatCountDown.increaseCurrentDiceValue();
            }
        } catch (InterruptedException e) {
            Gdx.app.log("CheatCountThread", "Interrupt thread");
            Thread.currentThread().interrupt();
        }
    }

}
