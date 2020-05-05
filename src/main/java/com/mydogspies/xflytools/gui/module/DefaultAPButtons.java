package com.mydogspies.xflytools.gui.module;

import com.mydogspies.xflytools.gui.MainWindow;
import com.mydogspies.xflytools.gui.elements.AutoPilotButton;
import com.mydogspies.xflytools.io.SocketConnect;
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
public class DefaultAPButtons {

    private static final Logger log = LoggerFactory.getLogger(DefaultAPButtons.class);

    @FXML
    private GridPane apButtonGrid;

    private AutoPilotButton apToggleBtn;
    private AutoPilotButton apHeadingBtn;
    private AutoPilotButton apAltitudeBtn;
    private AutoPilotButton apVSBtn;
    private AutoPilotButton apApprBtn;
    private AutoPilotButton apNavBtn;

    /* INIT */

    @FXML
    void initialize() {

        initElems();
    }

    /**
     * This FXML method listens to all Lighting and A/P buttons and calls corresponding execution code.
     *
     * @param event An event sent by the button when clicked.
     */
    @FXML
    private void clickButton(ActionEvent event) {

        log.debug("clickButton(): ActionEvent called: " + event);

        ToggleButton b = (ToggleButton) event.getSource();
        String button_id = b.getId();

        if (SocketConnect.socket != null) {

            switch (button_id) {

                case "aptogglebtn":
                    if (apToggleBtn.selectedProperty().getValue().equals(false)) {
                        MainWindow.controller.sendToXplane("set", "ap_mode", "0");
                        log.trace("clickButton(): A/P is OFF");
                    } else {
                        MainWindow.controller.sendToXplane("set", "ap_mode", "2");
                        log.trace("clickButton(): A/P is ON");
                    }
                    break;

                case "apheadingbtn":
                        MainWindow.controller.sendToXplane("cmd", "ap_heading_mode", "");
                        log.trace("clickButton(): A/P set to Heading mode.");
                    break;

                case "apnavbtn":
                        MainWindow.controller.sendToXplane("cmd", "ap_nav_mode", "");
                        log.trace("clickButton(): A/P set to Nav mode.");
                    break;

                case "apapprbtn":
                        MainWindow.controller.sendToXplane("cmd", "ap_appr_set", "");
                    try {
                        Thread.sleep(500);
                        // MainWindow.controller.sendToXplane("cmd", "ap_appr_set", "");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.trace("clickButton(): A/P set to Apr mode.");
            }
        } else {
            b.setSelected(false);
        }
    }

    public void updateData(String command, ArrayList<String> value) {

        switch (command) {

            case "ap_mode":
                if (apToggleBtn.selectedProperty().getValue().equals(true) && value.get(0).equals("0")) {
                    apToggleBtn.setSelected(false);
                    log.trace("getFromXplane(): [" + command + "] -> " + value + " | A/P turned OFF in app.");
                } else if (apToggleBtn.selectedProperty().getValue().equals(false) && value.get(0).equals("2")) {
                    apToggleBtn.setSelected(true);
                    log.trace("getFromXplane(): [" + command + "] -> " + value + " | A/P turned ON in app.");
                }
                break;

            case "ap_heading_mode_check":
                if (value.get(0).equals("2")) { // Nav mode
                    apNavBtn.setSelected(true);
                    apNavBtn.setText("Nav");
                    apHeadingBtn.setSelected(false);
                    log.trace("getFromXplane(): [" + command + "] -> " + value + " | A/P in Nav mode.");
                } else if (value.get(0).equals("1")) { // Heading mode
                    apNavBtn.setSelected(false);
                    apHeadingBtn.setSelected(true);
                    log.trace("getFromXplane(): [" + command + "] -> " + value + " | A/P in Heading app.");
                } else if (value.get(0).equals("13")) { // GPSS mode
                    apNavBtn.setSelected(true);
                    apNavBtn.setText("GPS");
                    apHeadingBtn.setSelected(false);
                }
                break;

            case "ap_hnav_mode":
                if (value.get(0).equals("1")) {
                    apApprBtn.setSelected(true);
                    log.trace("getFromXplane(): [" + command + "] -> " + value + " | A/P in Apr mode.");
                } else if (value.get(0).equals("0")) {
                    apApprBtn.setSelected(false);
                }
        }
    }

    private void initElems() {

        apToggleBtn = new AutoPilotButton();
        apToggleBtn.setId("aptogglebtn");
        apToggleBtn.setText("A/P");
        apToggleBtn.getStyleClass().add("ap-buttons");
        apToggleBtn.setOnAction(this::clickButton);
        apButtonGrid.add(apToggleBtn, 1, 0);

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
        apButtonGrid.add(apAltitudeBtn, 3, 0);

        apVSBtn = new AutoPilotButton();
        apVSBtn.setId("apvsbtn");
        apVSBtn.setText("V/S");
        apVSBtn.getStyleClass().add("ap-buttons");
        apVSBtn.setOnAction(this::clickButton);
        apButtonGrid.add(apVSBtn, 6, 0);

        apApprBtn = new AutoPilotButton();
        apApprBtn.setId("apapprbtn");
        apApprBtn.setText("Apr");
        apApprBtn.getStyleClass().add("ap-buttons");
        apApprBtn.setOnAction(this::clickButton);
        apButtonGrid.add(apApprBtn, 5, 0);

        apNavBtn = new AutoPilotButton();
        apNavBtn.setId("apnavbtn");
        apNavBtn.setText("Nav");
        apNavBtn.getStyleClass().add("ap-buttons");
        apNavBtn.setOnAction(this::clickButton);
        apButtonGrid.add(apNavBtn, 4, 0);
    }

    /**
     * Resets all elements to their initial visual state
     */
    public void onReset() {

        apToggleBtn.setSelected(false);
        apHeadingBtn.setSelected(false);
        apAltitudeBtn.setSelected(false);
        apVSBtn.setSelected(false);
        apApprBtn.setSelected(false);
        apNavBtn.setSelected(false);
    }
}
