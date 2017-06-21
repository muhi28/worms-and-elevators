package com.mygdx.game.maincontroller;


import com.mygdx.game.display.Worm;
import com.mygdx.game.util.CustomLogger;

class Bot implements Runnable {

    private static final CustomLogger LOGGER = new CustomLogger("BOT");

    private final Worm wormOne;
    private final Worm wormTwo;
    private final Controller controller;

    Bot(Worm wormOne, Worm wormTwo, Controller controller) {
        this.wormOne = wormOne;
        this.wormTwo = wormTwo;
        this.controller = controller;
        Thread botThread = new Thread(this);
        botThread.start();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && !Controller.getWinnerDecided()) {
            try {
                do {
                    Thread.sleep(500);
                }
                while (wormOne.stillMoving() || wormTwo.stillMoving() || controller.isPlayerOneTurn());
                Thread.sleep(2000);
                LOGGER.info("Bot rolled the dice!");
                controller.checkPlayerTurn();
                Thread.sleep(1000);


            } catch (InterruptedException e) {
                LOGGER.error("Interrupted!!", e);
                Thread.currentThread().interrupt();
            }
        }
    }
}
