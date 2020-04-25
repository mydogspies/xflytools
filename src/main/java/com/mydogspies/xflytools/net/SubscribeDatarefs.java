package com.mydogspies.xflytools.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SubscribeDatarefs {

    private static final Logger log = LoggerFactory.getLogger(SubscribeDatarefs.class);

    private static String taxi = "sub sim/cockpit2/switches/taxi_light_on"; // TODO will come from database
    private static String nav = "sub sim/cockpit2/switches/navigation_lights_on";
    private static String beacon = "sub sim/cockpit2/switches/beacon_on";
    private static String strobe = "sub sim/cockpit2/switches/strobe_lights_on";
    private static String landing = "sub sim/cockpit2/switches/landing_lights_on";

    public static void subRefs(Socket socket) {

        List<String> list = new ArrayList<>();
        list.add(taxi);
        list.add(nav);
        list.add(beacon);
        list.add(strobe);
        list.add(landing);

        for (String ref : list) {

            SendData send = new SendData();
            send.send(socket, ref);
            log.trace("subRefs(): Dataref (" + taxi + ") recalled.");

        }


    }
}
