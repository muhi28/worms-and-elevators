package com.mygdx.game.netwoking;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.net.UnknownHostException;

import networking.GameSync;

public class Client extends Thread {

    private final String ipAddress;

    public Client(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public void run() {
        try {
            System.out.println("Starting Connection");
            Socket s = new Socket(ipAddress, Server.PORT);
            System.out.println("Connection DONE");
            GameSync.useMultiplayerGame();
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeUTF("This is a message frome the client!");
            dos.flush();
            dos.close();
            s.close();
            System.out.println("Closing socket");
        } catch (UnknownHostException e) {
            System.out.println("There was an Unknown Erorr:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("There was an IOException:");
            e.printStackTrace();
        }

    }
}
