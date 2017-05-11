package com.mygdx.game.networking;

import com.mygdx.game.netwoking.Client;
import com.mygdx.game.netwoking.FromNetworkProcessor;
import com.mygdx.game.netwoking.NetworkManager;
import com.mygdx.game.netwoking.NetworkTrafficReceiver;
import com.mygdx.game.netwoking.Server;
import com.mygdx.game.util.CustomLogger;
import com.mygdx.game.util.ToastNotifier;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;


/**
 * The type Network test.
 */
public class NetworkTest {

    private String receivedFromNetwork = null;

    /**
     * Test server and client.
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testServerAndClient() throws InterruptedException {
        Server.LOGGER = new CustomLogger("Server", true);
        Client.LOGGER = new CustomLogger("Client", true);

        final NetworkTest currentInstance = this;
        NetworkTrafficReceiver networkTrafficReceiver = new NetworkTrafficReceiver(new FromNetworkProcessor() {
            public void receiveMessage(String message) {
                currentInstance.processMessageFromNetwork(message);
            }
        });


        //Server
        ToastNotifier toastNotifier = mock(ToastNotifier.class);
        Mockito.doNothing().when(toastNotifier).showToast(anyString());
        final Server server = new Server(toastNotifier);
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                server.start();
            }
        });
        serverThread.start();

        //CLIENT
        Client client = new Client("127.0.0.1");
        client.start();

        String testMessage = "ThisWillBeSended";
        NetworkManager.sendWithDelay(testMessage);

        Thread.sleep(1500);
        synchronized (this) {
            Assert.assertEquals(testMessage, receivedFromNetwork);
        }
    }

    private void processMessageFromNetwork(String message) {

        synchronized (this) {
            receivedFromNetwork = message;
        }
    }
}
