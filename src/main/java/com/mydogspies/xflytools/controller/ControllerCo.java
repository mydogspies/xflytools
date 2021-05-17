package com.mydogspies.xflytools.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * This interface defines the basic methods expected from each GUI module.
 * Together with the module-specific element interface it forms the base for each module controller.
 *
 * So for example, the APReadouts.java class in each plane-specific module must implement APReadoutsController interface
 * which in turn will extend this here.
 *
 * On initiation, depending on plane profile chosen, the current implementation of the controller is then stored in
 * the APReadoutsControllerSingleton from which we can call it throughout the application.
 *
 * Example of implementation: See the APAltitudeGet class in the ".inlogic" package. In this example we call the controller
 * that to which the elements we want to manipulate via the line; "final APReadoutsController controller =" etc...
 *
 * @see APReadoutsController
 * @see APReadoutsControllerSingleton
 * @see com.mydogspies.xflytools.controller.module.lamcessna172.APReadouts
 * @see com.mydogspies.xflytools.controller.inlogic.APAltitudeGet
 */
public interface ControllerCo {

    @FXML
    void initialize();

    @FXML
    void clickButton(ActionEvent event);

    @FXML
    void addToField(ActionEvent event);

    void initElements();

    void disableAll(boolean state);

    void onReset();
}
