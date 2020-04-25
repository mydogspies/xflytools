package com.mydogspies.xflytools.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.Socket;

public class SocketConnect {

    private static final Logger log = LoggerFactory.getLogger(SocketConnect.class);

    private final String server = "192.168.178.40"; // TODO needs to go into some settings
    private final int port = 51000;
    private Socket socket;

   public Socket connect() {

       try {
           socket = new Socket(server, port);
           socket.setSoTimeout(5*1000);
           log.info("connect(): Connected to: " + socket.getLocalAddress());
       } catch (IOException e) {
           log.error("connect(): I/O error - could not connect to: " + server);
           return null;
       }

       return socket;

   }
}
