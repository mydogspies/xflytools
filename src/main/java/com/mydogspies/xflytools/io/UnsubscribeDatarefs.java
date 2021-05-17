package com.mydogspies.xflytools.io;

import com.mydogspies.xflytools.data.DrefData;
import com.mydogspies.xflytools.data.DrefDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Unsubscribes from all the datarefs.
 * @author Peter Mankowski
 * @since 0.1.0
 */
public class UnsubscribeDatarefs {

    private static final Logger log = LoggerFactory.getLogger(UnsubscribeDatarefs.class);

    public static void unsubRefs() {

        List<DrefData> drefDatabase = DrefDatabase.getInstance().getDatabase();

        SendData sendData = new SendData();
        String string;

        // fetch from database
        for (DrefData data : drefDatabase) {

            // only unsub datarefs, not commands
            if (data.getIo().equals("set")) {
                string = "unsub " + data.getDataref();
                sendData.send(string);
                log.trace("unsubRefs(): Dataref (" + string + ") recalled.");
            }
        }
    }
}
