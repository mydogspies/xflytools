package com.mydogspies.xflytools.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class SendData {

    private static final Logger log = LoggerFactory.getLogger(SendData.class);

    public void send(Socket socket, String dataref) {

        try {
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);
            writer.println(dataref);
            log.debug("send(): Dataref (" + dataref +") sent via TCP to Xplane using socket: " + socket);
        } catch (IOException e) {
            log.error("send(): IO Error! Dataref could not be sent: " + e.getMessage());
        }

    }
}
