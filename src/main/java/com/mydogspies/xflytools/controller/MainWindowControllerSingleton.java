package com.mydogspies.xflytools.controller;

/**
 * This singleton is our reference to our main window controller.
 * @author Peter Mankowski
 * @since 0.4.0
 * @see MainWindowController
 */
public class MainWindowControllerSingleton {

    private static MainWindowControllerSingleton instance;
    private MainWindowController controller;

    private MainWindowControllerSingleton() {}

    public static MainWindowControllerSingleton getInstance() { //using double locking
        if (instance == null) {
            synchronized (MainWindowControllerSingleton.class) {
                if (instance == null) {
                    instance = new MainWindowControllerSingleton();
                }
            }
        }
        return instance;
    }

    public MainWindowController getController() {
        return controller;
    }

    public void setController(MainWindowController controller) {
        this.controller = controller;
    }
}
