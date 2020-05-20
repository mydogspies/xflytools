package com.mydogspies.xflytools;

import com.mydogspies.xflytools.data.DrefDatabase;
import com.mydogspies.xflytools.data.*;
import com.mydogspies.xflytools.controller.MainWindow;
import com.mydogspies.xflytools.io.DataHandler;
import com.mydogspies.xflytools.io.DataHandlerSingleton;
import com.mydogspies.xflytools.controller.AddCommandMapData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default startup class Main
 * Initializes a few things and in the end calls the MainWindow class to open the main application window.
 *
 * @author Peter Mankowski
 * @since 0.1.0
 * @see com.mydogspies.xflytools.controller.MainWindow
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        // set log level
        Initialize.logReportLevel("trace");
        log.trace("main(): Log level initialised.");

        // pre-loading databases
        DrefDataIO io = new DrefDataIO();
        DrefDatabase db = DrefDatabase.getInstance();
        db.setDatabase(io.loadDatabase());

        LayoutDataIO lio = new LayoutDataIO();
        LayoutDatabase ldb = LayoutDatabase.getInstance();
        ldb.setDatabase(lio.loadLayoutDatabase());
        log.trace("main(): Databases called and loaded.");

        // instantiate data handler now cause we will need it early on
        DataHandler handler = new DataHandler();
        DataHandlerSingleton singleton = DataHandlerSingleton.getInstance();
        singleton.setHandler(handler);
        log.trace("main(): DataHandler object instantiated: " + handler);

        // initiate command map
        AddCommandMapData cmdmap = new AddCommandMapData();
        cmdmap.initiateCommandMap();

        // open main window
        MainWindow.main(args);
        log.debug("main(): Application initialized and main window called.");
    }
}
