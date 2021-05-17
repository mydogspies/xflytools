package com.mydogspies.xflytools.controller.outlogic;

public class OutCommandMapSingleton {

    private static OutCommandMapSingleton instance;
    private OutCommandMap map;

    private OutCommandMapSingleton() {}

    public static OutCommandMapSingleton getInstance() { //using double locking
        if (instance == null) {
            synchronized (OutCommandMapSingleton.class) {
                if (instance == null) {
                    instance = new OutCommandMapSingleton();
                }
            }
        }
        return instance;
    }

    public OutCommandMap getMap() {
        return map;
    }

    public void setMap(OutCommandMap map) {
        this.map = map;
    }
}
