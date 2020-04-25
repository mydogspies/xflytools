package com.mydogspies.xflytools.tests;

import com.mydogspies.xflytools.extplanei.ExtPlaneInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EpiCommands {

    private static final Logger log = LoggerFactory.getLogger(EpiCommands.class);

    private final String server = "192.168.178.40";
    private final int port = 51000;

    public void sendData() {

        ExtPlaneInterface epi = new ExtPlaneInterface(server, port);
        try {
            epi.start();
            // subscribe
            epi.includeDataRef("sim/cockpit/electrical/night_vision_on");
            // get valeu
            epi.getDataRefValue("sim/cockpit/electrical/night_vision_on");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
