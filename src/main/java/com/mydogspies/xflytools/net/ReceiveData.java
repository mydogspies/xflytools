package com.mydogspies.xflytools.net;

import com.mydogspies.xflytools.gui.MainWindowController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReceiveData extends Thread {

    private static final Logger log = LoggerFactory.getLogger(ReceiveData.class);

    @Override
    public void run() {


    }

    public void readData() {

        try {

            InputStream input = SocketConnect.socket.getInputStream();
            log.trace("readData(): New input stream: " + input);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            log.trace("readData(): New input reader: " + reader);
            log.trace("readData(): Reader ready: " + reader.ready());

            while (SocketConnect.receiving) {

                if (reader.ready()) {
                    int c;
                    String rawString = "";
                    do {
                        c = input.read();
                        rawString += (char) c;
                    } while(input.available() > 0);
                    System.out.println(rawString);
                }
            }
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

}
