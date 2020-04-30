package com.mydogspies.xflytools.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is our TCP receiver. Note it runs in its own thread.
 *
 * @author Peter Mankowski
 * @since 0.1.0
 */
public class ReceiveData {

    private static final Logger log = LoggerFactory.getLogger(ReceiveData.class);

    public void startReceiver() {

        Runnable runnable = () -> {

            try {
                InputStream input = SocketConnect.socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                log.trace("ReceiveData.run(): Reader ready: " + reader.ready());

                while (SocketConnect.receiving) {

                    if (reader.ready()) {
                        int c;
                        StringBuilder rawString = new StringBuilder();
                        do {
                            c = input.read();
                            rawString.append((char) c);
                        } while (input.available() > 0);

                        // sen it off to the data handler
                        new DataHandler(rawString.toString());
                    }
                }
            } catch (IOException | IllegalStateException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        log.trace("ReceiveData.run(): Started a new TCP receiver thread: " + thread);
    }
}
