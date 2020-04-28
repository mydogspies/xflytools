package com.mydogspies.xflytools.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Disconnects from Xplane and resets the socket.
 * @author Peter Mankowski
 * @since 0.1.0
 */
public class DisconnectAll {

    private static final Logger log = LoggerFactory.getLogger(DisconnectAll.class);

    public static void shutdown() {

        UnsubscribeDatarefs.unsubRefs();
        SocketConnect.socket = null;
        log.info("shutdown(): Xplane was disconnected and socket reset.");

    }
}
