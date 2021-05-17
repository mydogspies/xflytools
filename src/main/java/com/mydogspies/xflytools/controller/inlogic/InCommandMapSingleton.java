package com.mydogspies.xflytools.controller.inlogic;

public class InCommandMapSingleton {

    private static InCommandMapSingleton instance;
    private InCommandMap map;

    private InCommandMapSingleton() {}

    public static InCommandMapSingleton getInstance() { //using double locking
        if (instance == null) {
            synchronized (InCommandMapSingleton.class) {
                if (instance == null) {
                    instance = new InCommandMapSingleton();
                }
            }
        }
        return instance;
    }

    public InCommandMap getMap() {
        return map;
    }

    public void setMap(InCommandMap map) {
        this.map = map;
    }
}
