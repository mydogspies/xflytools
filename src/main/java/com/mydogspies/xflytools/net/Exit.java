package com.mydogspies.xflytools.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Exit {

    private static final Logger log = LoggerFactory.getLogger(Exit.class);

    public static void shutdown() {

        UnsubscribeDatarefs.unsubRefs();
        SocketConnect.socket = null;
        log.info("shutdown(): Xplane was disconnected and socket reset.");

    }
}
