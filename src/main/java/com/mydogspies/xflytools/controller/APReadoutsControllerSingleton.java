package com.mydogspies.xflytools.controller;

/**
 * This singleton is our reference to the AP Readouts controller which itself uses the ControllerCo interface.
 *
 * @author Peter Mankowski
 * @see com.mydogspies.xflytools.controller.ControllerCo
 * @see com.mydogspies.xflytools.controller.module.lamcessna172.APReadouts
 * Note that the lamcessna172 package is only one of many possible sets of gui classes. Link here in the docs only as a generic example.
 * @since 0.4.0
 */
public class APReadoutsControllerSingleton {

    private static APReadoutsControllerSingleton instance;
    private ControllerCo controller;

    private  APReadoutsControllerSingleton() {}

    public static APReadoutsControllerSingleton getInstance() { //using double locking
        if (instance == null) {
            synchronized (APReadoutsControllerSingleton.class) {
                if (instance == null) {
                    instance = new APReadoutsControllerSingleton();
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
