package com.mydogspies.xflytools.net;

import com.mydogspies.xflytools.gui.DataHandler;
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
public class ReceiveData extends Thread {

    private static final Logger log = LoggerFactory.getLogger(ReceiveData.class);

    @Override
    public void run() {

        try {

            InputStream input = SocketConnect.socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            log.trace("ReceiveData.run(): Reader ready: " + reader.ready());

            List<String> dataList = new ArrayList<>();

            while (SocketConnect.receiving) {

                if (reader.ready()) {
                    int c;
                    String rawString = "";
                    do {
                        c = input.read();
                        rawString += (char) c;
                    } while (input.available() > 0);

                    // sen it off to the data handler
                    new DataHandler(rawString);
                }
            }
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
        }
    }
}
