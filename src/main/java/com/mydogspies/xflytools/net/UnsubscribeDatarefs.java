package com.mydogspies.xflytools.net;

import com.mydogspies.xflytools.Main;
import com.mydogspies.xflytools.data.DrefData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unsubscribes from all the datarefs.
 * @author Peter Mankowski
 * @since 0.1.0
 */
public class UnsubscribeDatarefs {

    private static final Logger log = LoggerFactory.getLogger(UnsubscribeDatarefs.class);

    public static void unsubRefs() {

        SendData sendData = new SendData();
        String string;

        // fetch from database
        for (DrefData data : Main.database) {

            string = "unsub " + data.getDataref();
            sendData.send(string);
            log.trace("unsubRefs(): Dataref (" + string + ") recalled.");
        }
    }
}
