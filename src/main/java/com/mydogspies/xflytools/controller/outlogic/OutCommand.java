package com.mydogspies.xflytools.controller.outlogic;

/**
 * This interface is the base for all commands related to sending data to Xplane.
 *
 * @author Peter Mankowski
 * @see OutCommandMap
 * @see OutCommandMapSingleton
 * @see com.mydogspies.xflytools.controller.MainWindowController
 * @since 0.4.0
 */
public interface OutCommand {

    void execute();
}
