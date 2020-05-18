package com.mydogspies.xflytools.io;

/**
 * This singleton is the reference to the data handler object.
 * @author Peter Mankowski
 * @since 0.4.0
 * @see DataHandler
 */
public class DataHandlerSingleton {

    private static DataHandlerSingleton instance;
    private DataHandler handler;

    private DataHandlerSingleton() {
    }

    public static DataHandlerSingleton getInstance() {
        if (instance == null) {
            synchronized (DataHandlerSingleton.class) {
                if (instance == null) {
                    instance = new DataHandlerSingleton();
                }
            }
        }
        return instance;
    }

    public DataHandler getHandler() {
        return handler;
    }

    public void setHandler(DataHandler handler) {
        this.handler = handler;
    }
}
