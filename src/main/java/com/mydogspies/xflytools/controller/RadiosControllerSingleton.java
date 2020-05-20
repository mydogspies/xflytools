package com.mydogspies.xflytools.controller;

/**
 * This singleton is our reference to the Radios controller which itself uses the ControllerCo interface.
 *
 * @author Peter Mankowski
 * @see com.mydogspies.xflytools.controller.ControllerCo
 * @see com.mydogspies.xflytools.controller.module.lamcessna172.Radios
 * Note that the lamcessna172 package is only one of many possible sets of gui classes. Link here in the docs only as a generic example.
 * @since 0.4.0
 */
public class RadiosControllerSingleton {

    private static RadiosControllerSingleton instance;
    private ControllerCo controller;

    private RadiosControllerSingleton() {
    }

    public static RadiosControllerSingleton getInstance() { //using double locking
        if (instance == null) {
            synchronized (RadiosControllerSingleton.class) {
                if (instance == null) {
                    instance = new RadiosControllerSingleton();
                }
            }
        }
        return instance;
    }

    public ControllerCo getController() {
        return controller;
    }

    public void setController(ControllerCo controller) {
        this.controller = controller;
    }
}
