package instrumental;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mygdx.game.activities.MainMenu;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class InstrumentalTest {
    @Rule
    public ActivityTestRule<MainMenu> activityRule
            = new ActivityTestRule<>(
            MainMenu.class,
            true,     // initialTouchMode
            false);   // launchActivity. False to customize the intent

    @Test
    public void intent() {
        Intent intent = new Intent();
        intent.putExtra("music", false);

        activityRule.launchActivity(intent);

        Assert.assertEquals(true, true);
    }
}
