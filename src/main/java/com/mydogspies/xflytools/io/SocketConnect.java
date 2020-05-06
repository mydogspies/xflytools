package com.mydogspies.xflytools.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Creates static socket and connects to using the server and port defined here.
 * @author Peter Mankowski
 * @since 0.1.0
 */
public class SocketConnect {

    private static final Logger log = LoggerFactory.getLogger(SocketConnect.class);

    private String server;
    private final int port = 51000;
    public static Socket socket;
    public static boolean receiving = false;

   public void connect(String serverIP) {

       this.server = serverIP;

       try {
           socket = new Socket();
           socket.connect(new InetSocketAddress(server, port), 5000);
           log.info("connect(): New socket established on: " + socket.getInetAddress() + " port " + socket.getPort());
       } catch (SocketTimeoutException e_timeout) {
           log.error("connect(): Connection error (Socket timed out): " + e_timeout.getMessage());
       } catch (IOException e) {
           log.error("connect(): I/O error - could not connect to " + server + ": " + e.getMessage());
       }

       if (socket.isConnected()) {
           receiving = true;
           ReceiveData rec = new ReceiveData();
           rec.startReceiver();
       }
   }
}
