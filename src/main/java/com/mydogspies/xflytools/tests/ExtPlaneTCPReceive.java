package com.mydogspies.xflytools.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExtPlaneTCPReceive extends Thread{

    private static final Logger log = LoggerFactory.getLogger(ExtPlaneTCPReceive.class);

    private final String server = "192.168.178.40";
    private final int port = 51000;
    private ExecutorService pool = Executors.newFixedThreadPool(2);
    Socket socket;

    public void run() {

        String data;

        try {
            // connect
            socket = new Socket(server, port);
            log.debug("main(): Connected to " + socket.getRemoteSocketAddress());
            // receive
            BufferedReader fromXplane = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(true){
                data = fromXplane.readLine();
                pool.execute(new ExtPlaneDataHandler(data));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }


    }

}
