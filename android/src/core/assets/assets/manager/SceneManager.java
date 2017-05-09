package core.assets.assets.manager;

import com.badlogic.gdx.Screen;

/**
 * Created by Muhi on 14.04.2017.
 */

public class SceneManager {

    private static Screen currentScreen;

    public static void setScreen(Screen screen) {

        if (currentScreen != null) {
            currentScreen.dispose();
        }

        currentScreen = screen;

        currentScreen.show();
    }

    public static Screen getCurrentScreen() {

        return currentScreen;
    }


}
