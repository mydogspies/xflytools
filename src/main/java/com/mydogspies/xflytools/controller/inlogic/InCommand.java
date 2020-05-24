package com.mydogspies.xflytools.controller.inlogic;

import java.util.ArrayList;

/**
 * This interface is the base for all commands related to getting data from Xplane.
 *
 * @author Peter Mankowski
 * @see InCommandMap
 * @see InCommandMapSingleton
 * @see com.mydogspies.xflytools.io.DataObserverIO
 * @since 0.4.0
 */
public interface InCommand {

    void execute(String command, ArrayList<String> values);
}
