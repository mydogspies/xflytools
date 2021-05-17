package com.mydogspies.xflytools.controller;

/**
 * This singleton is our reference to the Misc controller which itself uses the ControllerCo interface.
 *
 * @author Peter Mankowski
 * @see com.mydogspies.xflytools.controller.ControllerCo
 * @see com.mydogspies.xflytools.controller.module.lamcessna172.Misc
 * Note that the lamcessna172 package is only one of many possible sets of gui classes. Link here in the docs only as a generic example.
 * @since 0.4.0
 */
public class MiscControllerSingleton {

    private static MiscControllerSingleton instance;
    private ControllerCo controller;

    private MiscControllerSingleton() {}

    public static MiscControllerSingleton getInstance() { //using double locking
        if (instance == null) {
            synchronized (MiscControllerSingleton.class) {
                if (instance == null) {
                    instance = new MiscControllerSingleton();
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
