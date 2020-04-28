package com.mydogspies.xflytools.io;

import com.mydogspies.xflytools.Main;
import com.mydogspies.xflytools.data.DrefData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Subscribes to all the datarefs in he database.
 * @author Peter Mankowski
 * @since 0.1.0
 */
public class SubscribeDatarefs {

    private static final Logger log = LoggerFactory.getLogger(SubscribeDatarefs.class);

    public static void subRefs() {

        SendData sendData = new SendData();
        String string;

        // fetch from database
        for (DrefData data : Main.database) {

            string = "sub " + data.getDataref();
            sendData.send(string);
            log.trace("subRefs(): Dataref (" + string + ") recalled.");
        }
    }
}
