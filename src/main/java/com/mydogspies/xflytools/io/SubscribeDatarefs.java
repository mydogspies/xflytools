package com.mydogspies.xflytools.io;

import com.mydogspies.xflytools.data.DrefDatabase;
import com.mydogspies.xflytools.Main;
import com.mydogspies.xflytools.data.DrefData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Subscribes to all the datarefs in he database.
 * @author Peter Mankowski
 * @since 0.1.0
 */
public class SubscribeDatarefs {

    private static final Logger log = LoggerFactory.getLogger(SubscribeDatarefs.class);

    public static void subRefs() {

        List<DrefData> drefDatabase = DrefDatabase.getInstance().getDatabase();

        SendData sendData = new SendData();
        String string;

        // fetch from database
        for (DrefData data : drefDatabase) {

            // only fetch datarefs, not commands!
            if (data.getIo().equals("set")) {
                string = "sub " + data.getDataref();
                sendData.send(string);
                log.trace("subRefs(): Dataref (" + string + ") recalled.");
            }

        }
    }
}
