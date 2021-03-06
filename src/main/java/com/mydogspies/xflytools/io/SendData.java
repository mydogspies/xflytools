package com.mydogspies.xflytools.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Sends data strings to ExPlane plugin via TCP using existing socket.
 * @author Peter Mankowski
 * @since 0.1.0
 */
public class SendData {

    private static final Logger log = LoggerFactory.getLogger(SendData.class);

    public void send(String dataref) {

        Runnable runnable = () -> {

            try {
                OutputStream out = SocketConnect.socket.getOutputStream();
                PrintWriter writer = new PrintWriter(out, true);
                writer.println(dataref);
                log.trace("send(): String (" + dataref + ") sent via TCP to Xplane using socket: " + SocketConnect.socket);
            } catch (IOException e) {
                log.error("send(): IO Error! String could not be sent: " + e.getMessage());
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        log.trace("send.run(): Started a new thread: " + thread);
    }
}
