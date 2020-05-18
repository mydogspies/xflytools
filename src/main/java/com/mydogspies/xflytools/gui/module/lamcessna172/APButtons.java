package com.mydogspies.xflytools.gui.module.lamcessna172;

import com.mydogspies.xflytools.gui.ControllerCo;
import com.mydogspies.xflytools.gui.MainWindowController;
import com.mydogspies.xflytools.gui.MainWindowControllerSingleton;
import com.mydogspies.xflytools.gui.elements.AutoPilotButton;
import com.mydogspies.xflytools.io.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * This is the controller for the A/P buttons part of the GUI.
 *
 * @author Peter Mankowski
 * @since 0.4.0
 */
public class APButtons implements ControllerCo, DataObserverIO {

    private static final Logger log = LoggerFactory.getLogger(APButtons.class);
    private MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();
    private DataHandler dataHandler = DataHandlerSingleton.getInstance().getHandler();

    @FXML
    private GridPane apButtonGrid;

    private AutoPilotButton apToggleBtn;
    private AutoPilotButton apHeadingBtn;
    private AutoPilotButton apAltitudeBtn;
    private AutoPilotButton apVSBtn;
    private AutoPilotButton apApprBtn;
    private AutoPilotButton apNavBtn;
    private AutoPilotButton apRevBtn;

    /* INIT */


    @Override
    public void initialize() {

        dataHandler.addObserver(this);
        initElements();
    }

    /**
     * This FXML method listens to all A/P buttons and calls corresponding execution code.
     *
     * @param event An event sent by the button when clicked.
     */
    @Override
    @FXML
    public void clickButton(ActionEvent event) {

        log.debug("clickButton(): ActionEvent called: " + event);

        ToggleButton b = (ToggleButton) event.getSource();
        String button_id = b.getId();

        if (SocketConnect.socket != null) {

            switch (button_id) {

                case "aptogglebtn":
                    if (apToggleBtn.selectedProperty().getValue().equals(false)) {
                        disableAll(true);
                        main_controller.sendToXplane("set", "ap_mode", "0");
                        log.trace("clickButton(): A/P is OFF");
                    } else {
                        disableAll(false);
                        main_controller.sendToXplane("set", "ap_mode", "2");
                        log.trace("clickButton(): A/P is ON");
                    }
                    break;

                case "apheadingbtn":
                    main_controller.sendToXplane("cmd", "ap_heading_toggle", "");
                    log.trace("clickButton(): A/P set to Heading mode.");
                    break;

                case "apnavbtn":
                    if (apRevBtn.selectedProperty().getValue().equals(true)) {
                        apNavBtn.setSelected(true);
                    }
                    main_controller.sendToXplane("cmd", "ap_nav_toggle", "");
                    log.trace("clickButton(): A/P set to Nav mode.");
                    break;

                case "apvsbtn":
                    main_controller.sendToXplane("cmd", "ap_vs_toggle", "");
                    log.trace("clickButton(): A/P set to Vertical Speed mode.");
                    break;

                case "apaltitudebtn":
                    main_controller.sendToXplane("cmd", "ap_altitude_toggle", "");
                    log.trace("clickButton(): A/P set to Altitude mode.");
                    break;

                case "aprevbtn":
                    main_controller.sendToXplane("cmd", "ap_rev_toggle", "");
                    log.trace("clickButton(): A/P set to REV mode.");
                    break;

                case "apapprbtn":
                    main_controller.sendToXplane("cmd", "ap_appr_toggle", "");
                    log.trace("clickButton(): A/P set to APR mode.");
                    break;


            }
        } else {
            b.setSelected(false);
        }
    }

    @Override
    public void addToField(ActionEvent event) {

    }

    @Override
    public void update(DataObserverPacket packet) {

        // TODO implement new data update method
    }

    @Override
    public void updateData(String command, ArrayList<String> value) {

        switch (command) {

            case "ap_mode":
                if (apToggleBtn.selectedProperty().getValue().equals(true) && value.get(0).equals("0")) {
                    apToggleBtn.setSelected(false);
                    disableAll(true);
                    log.trace("updateData(): [" + command + "] -> " + value + " | A/P turned OFF in app.");
                } else if (apToggleBtn.selectedProperty().getValue().equals(false) && value.get(0).equals("2")) {
                    apToggleBtn.setSelected(true);
                    disableAll(false);
                    log.trace("updateData(): [" + command + "] -> " + value + " | A/P turned ON in app.");
                }
                break;

            case "ap_heading_mode":
                if (value.get(0).equals("1")) {
                    apHeadingBtn.setSelected(true);
                    apNavBtn.setSelected(false);
                    apNavBtn.setText("Nav");
                } else if (value.get(0).equals("2")) {
                    apHeadingBtn.setSelected(false);
                    apNavBtn.setSelected(true);
                    apNavBtn.setText("Nav");
                } else if (value.get(0).equals("13")) {
                    apNavBtn.setText("GPSS");
                }
                break;

            case "ap_altitude_mode":
                if (value.get(0).equals("6")) {
                    apAltitudeBtn.setSelected(true);
                    apVSBtn.setSelected(false);
                } else if (value.get(0).equals("4")) {
                    apAltitudeBtn.setSelected(false);
                    apVSBtn.setSelected(true);
                }
                break;

            case "ap_backcourse":
                if (value.get(0).equals("1")) {
                    apRevBtn.setSelected(true);
                } else if (value.get(0).equals("0")) {
                    apRevBtn.setSelected(false);
                    apApprBtn.setSelected(false);
                }
                break;

            case "ap_appr_status":
                if (value.get(0).equals("1")) {
                    apApprBtn.setSelected(true);
                    apNavBtn.setSelected(true);
                } else if (value.get(0).equals("0")) {
                    apApprBtn.setSelected(false);
                    if (apHeadingBtn.selectedProperty().getValue().equals(true)) {
                        apNavBtn.setSelected(false);
                    }
                } else if (value.get(0).equals("2")) {
                    apApprBtn.setSelected(true);
                }
                break;

        }
    }

    @Override
    public void initElements() {

        apToggleBtn = new AutoPilotButton();
        apToggleBtn.setId("aptogglebtn");
        apToggleBtn.setText("A/P");
        apToggleBtn.getStyleClass().add("ap-buttons");
        apToggleBtn.setOnAction(this::clickButton);
        apButtonGrid.add(apToggleBtn, 1, 0);
        apToggleBtn.toggleable = true;

        apHeadingBtn = new AutoPilotButton();
        apHeadingBtn.setId("apheadingbtn");
        apHeadingBtn.setText("Hdg");
        apHeadingBtn.getStyleClass().add("ap-buttons");
        apHeadingBtn.setOnAction(this::clickButton);
        apButtonGrid.add(apHeadingBtn, 2, 0);

        apAltitudeBtn = new AutoPilotButton();
        apAltitudeBtn.setId("apaltitudebtn");
        apAltitudeBtn.setText("Alt");
        apAltitudeBtn.getStyleClass().add("ap-buttons");
        apAltitudeBtn.setOnAction(this::clickButton);
        apButtonGrid.add(apAltitudeBtn, 6, 0);

        apVSBtn = new AutoPilotButton();
        apVSBtn.setId("apvsbtn");
        apVSBtn.setText("V/S");
        apVSBtn.getStyleClass().add("ap-buttons");
        apVSBtn.setOnAction(this::clickButton);
        apButtonGrid.add(apVSBtn, 7, 0);

        apApprBtn = new AutoPilotButton();
        apApprBtn.setId("apapprbtn");
        apApprBtn.setText("Apr");
        apApprBtn.getStyleClass().add("ap-buttons");
        apApprBtn.setOnAction(this::clickButton);
        apButtonGrid.add(apApprBtn, 4, 0);
        apApprBtn.toggleable = true; // as per override -> this one acts like a standard toggle button

        apNavBtn = new AutoPilotButton();
        apNavBtn.setId("apnavbtn");
        apNavBtn.setText("Nav");
        apNavBtn.getStyleClass().add("ap-buttons");
        apNavBtn.setOnAction(this::clickButton);
        apButtonGrid.add(apNavBtn, 3, 0);

        apRevBtn = new AutoPilotButton();
        apRevBtn.setId("aprevbtn");
        apRevBtn.setText("Rev");
        apRevBtn.getStyleClass().add("ap-buttons");
        apRevBtn.setOnAction(this::clickButton);
        apButtonGrid.add(apRevBtn, 5, 0);
        apRevBtn.toggleable = true; // as per override -> this one acts like a standard toggle button

        // in the initial state our AP button is off thus all disabled
        disableAll(true);
    }

    @Override
    public void disableAll(boolean state) {

        apHeadingBtn.setDisable(state);
        apAltitudeBtn.setDisable(state);
        apVSBtn.setDisable(state);
        apApprBtn.setDisable(state);
        apNavBtn.setDisable(state);
        apRevBtn.setDisable(state);

    }

    /**
     * Resets all elements to their initial visual state
     */
    @Override
    public void onReset() {

        apToggleBtn.setSelected(false);
        apHeadingBtn.setSelected(false);
        apAltitudeBtn.setSelected(false);
        apVSBtn.setSelected(false);
        apApprBtn.setSelected(false);
        apNavBtn.setSelected(false);
        apRevBtn.setSelected(false);
        disableAll(true);
    }


}
