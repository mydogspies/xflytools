package com.mydogspies.xflytools;

import ch.qos.logback.classic.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A number of methods that need to initialize upon start
 * @author Peter Mankowski
 * @since 0.1.0
 */
public class Initialize {

    private static final Logger log = LoggerFactory.getLogger(Initialize.class);

    /**
     * Sets the reporting level of Logback
     * @param level the level of logging
     * @since 0.1.0
     * @see <a href="http://logback.qos.ch/">Logback</a>
     */
    public static void logReportLevel(String level) {

        ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.toLevel(level));
    }
}
