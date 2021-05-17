package com.mydogspies.xflytools.data;

import java.util.List;

/**
 * This singleton is the container for our data from the drefData.json file.
 * It's instantiated in the main class and used in the data and io packages.
 * @author Peter Mankowski
 * @since 0.4.0
 * @see com.mydogspies.xflytools.Main
 */
public class DrefDatabase {

    private static DrefDatabase instance;
    private List<DrefData> database;

    private DrefDatabase() {}

    public static DrefDatabase getInstance() { //using double locking
        if (instance == null) {
            synchronized (DrefDatabase.class) {
                if (instance == null) {
                    instance = new DrefDatabase();
                }
            }
        }
        return instance;
    }

    public void setDatabase(List<DrefData> database) {
        this.database = database;
    }

    public List<DrefData> getDatabase() {
        return database;
    }
}
