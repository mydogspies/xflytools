package com.mydogspies.xflytools.system;

import ch.qos.logback.classic.LoggerContext;
import com.mydogspies.xflytools.io.DisconnectAll;
import com.mydogspies.xflytools.io.SocketConnect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cleanup of resources before exit.
 * @author Peter Mankowski
 * @since 0.1.0
 */
public class ExitApp {

    private static final Logger log = LoggerFactory.getLogger(ExitApp.class);

    public static void exitAll() {

        // release resources used by logback for a clean exit
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.stop();
        log.info("exitAll(): Logger stopped.");

        // clean up network
        if (SocketConnect.socket != null) {
            DisconnectAll.shutdown();
        }

        // TODO make the platform exit dependent on the socket reset
    }
}
