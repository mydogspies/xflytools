package com.mydogspies.xflytools.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class ExtPlaneTCPSend extends Thread {

    private static final Logger log = LoggerFactory.getLogger(ExtPlaneTCPSend.class);

    private final String server = "192.168.178.40";
    private final int port = 51000;

    public void run() {

        try {
            // connect
            Socket socket = new Socket(server, port);
            log.debug("main(): Connected to " + socket.getRemoteSocketAddress());
            // send a message
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println("sub sim/cockpit/electrical/night_vision_on");
            writer.println("set sim/cockpit/electrical/night_vision_on 1");
            // clean exit
            writer.close();
            socket.close();
            log.debug("main(): writer and socket closed");
        } catch (IOException e) {
            log.debug("main(): Connected to " + server + " on port " + port + " failed! " + e.getMessage());
        }


    }
}
