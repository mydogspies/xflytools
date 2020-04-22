package com.mydogspies.xflytools;

import com.mydogspies.xflytools.gui.MainWindow;

/**
 * Default startup class Main
 * Initializes a number of things and in the end calls the MainWindow class to open the main application window
 * @author Peter Mankowski
 * @since 0.1.0
 * @see com.mydogspies.xflytools.gui.MainWindow
 */
public class Main {

    public static void main(String[] args) {

        // set log level
        Initialize.logReportLevel("trace");

        MainWindow.main(args);

    }
}
