package instrumental;

import android.support.test.runner.AndroidJUnit4;

import com.mygdx.game.netwoking.Server;
import com.mygdx.game.util.ToastNotifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;


@RunWith(AndroidJUnit4.class)
public class InstrumentalTest {
    /**
     * Test min dice result.
     */
    @Test
    public void testServer() {
        ToastNotifier toastNotifier = mock(ToastNotifier.class);
        Mockito.doNothing().when(toastNotifier).showToast(anyString());
        Server server = new Server(toastNotifier);

        server.start();


    }
}

