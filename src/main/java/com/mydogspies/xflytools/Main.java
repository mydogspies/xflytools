package com.mydogspies.xflytools;

import com.mydogspies.xflytools.data.DrefData;
import com.mydogspies.xflytools.data.DrefDataIO;
import com.mydogspies.xflytools.gui.MainWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Default startup class Main
 * Initializes a number of things and in the end calls the MainWindow class to open the main application window
 * @author Peter Mankowski
 * @since 0.1.0
 * @see com.mydogspies.xflytools.gui.MainWindow
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static List<DrefData> database;

    public static void main(String[] args) {

        // set log level
        Initialize.logReportLevel("trace");

        // load database
        DrefDataIO io = new DrefDataIO();
        database = io.getJsonData();

        MainWindow.main(args);
        log.info("main(): Application initialized and main window called.");
    }
}
