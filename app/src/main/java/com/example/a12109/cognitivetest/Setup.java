package com.example.a12109.cognitivetest;

import android.util.Log;

import java.net.Socket;
import java.net.UnknownHostException;

public class Setup {

    private static Socket socket = new Socket();
    private static final String TAG = "Setup";

    public static void setUp() throws Exception {
        Settings setting = new Settings();
        String remoteHost = setting.getRemoteHost();
        int remotePort = setting.getRemotePort();
        try {
            socket = new Socket(remoteHost, remotePort);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Client client = new Client(socket);
        Log.d(TAG, "new client created!");

        /*
         * For testing
         */
//		client.sendData("hello world！");
        client.sendData("{\"command\":\"test\",\"message\":\"it's a test message!\"}");
        while (true) {
            String data = client.getData();
            System.out.println(data);
        }
    }
}

