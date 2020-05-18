package com.mydogspies.xflytools.data;

import java.util.List;

/**
 * This singleton is the container for our data from the layout.json file.
 * It's instantiated in the main class and used in the data and gui packages.
 * @author Peter Mankowski
 * @since 0.4.0
 * @see com.mydogspies.xflytools.Main
 */
public class LayoutDatabase {

    private static LayoutDatabase instance;
    private List<LayoutData> database;

    private LayoutDatabase() {}

    public static LayoutDatabase getInstance() { //using double locking
        if (instance == null) {
            synchronized (LayoutDatabase.class) {
                if (instance == null) {
                    instance = new LayoutDatabase();
                }
            }
        }
        return instance;
    }

    public List<LayoutData> getDatabase() {
        return database;
    }

    public void setDatabase(List<LayoutData> database) {
        this.database = database;
    }
}
