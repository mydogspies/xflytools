package com.mydogspies.xflytools.system;

import ch.qos.logback.classic.LoggerContext;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExitApp {

    private static final Logger log = LoggerFactory.getLogger(ExitApp.class);

    /**
     * Exits the app cleanly
     */
    public static void exitAll() {

        log.info("exitAll(): Exit with a clean shutdown! Bye!");

        // release resources used by logback for a clean exit
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.stop();

        // exit to OS
        Platform.exit();
        System.exit(0);
    }
}
