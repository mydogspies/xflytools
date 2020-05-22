package com.mydogspies.xflytools.controller.module.lamcessna172;

import com.mydogspies.xflytools.controller.APButtonsController;
import com.mydogspies.xflytools.controller.inlogic.InCommandMap;
import com.mydogspies.xflytools.controller.inlogic.InCommandMapSingleton;
import com.mydogspies.xflytools.controller.outlogic.OutCommandMap;
import com.mydogspies.xflytools.controller.outlogic.OutCommandMapSingleton;
import com.mydogspies.xflytools.data.DrefDataIO;
import com.mydogspies.xflytools.controller.elements.AutoPilotButton;
import com.mydogspies.xflytools.io.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the controller for the A/P buttons part of the GUI.
 *
 * @author Peter Mankowski
 * @since 0.4.0
 */
public class APButtons implements APButtonsController, DataObserverIO {

    private static final Logger log = LoggerFactory.getLogger(APButtons.class);
    private final DataHandler dataHandler = DataHandlerSingleton.getInstance().getHandler();
    private final InCommandMap inCommandMap = InCommandMapSingleton.getInstance().getMap();
    private final OutCommandMap outCommandMap = OutCommandMapSingleton.getInstance().getMap();

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

            if (button_id.equals("aptogglebtn")) {
                outCommandMap.getOutCommandMap().get("ap_mode").execute();
            }

            if (button_id.equals("apheadingbtn")) {
                outCommandMap.getOutCommandMap().get("ap_heading_toggle").execute();
            }

            if (button_id.equals("apnavbtn")) {
                outCommandMap.getOutCommandMap().get("ap_nav_toggle").execute();
            }

            if (button_id.equals("apvsbtn")) {
                outCommandMap.getOutCommandMap().get("ap_vs_toggle").execute();
            }

            if (button_id.equals("apaltitudebtn")) {
                outCommandMap.getOutCommandMap().get("ap_altitude_toggle").execute();
            }

            if (button_id.equals("aprevbtn")) {
                outCommandMap.getOutCommandMap().get("ap_rev_toggle").execute();
            }

            if (button_id.equals("apapprbtn")) {
                outCommandMap.getOutCommandMap().get("ap_appr_toggle").execute();
            }

        } else {
            b.setSelected(false);
        }
    }

    /**
     * The observer method for incoming data from Xplane.
     *
     * @param packet object with dataref and its value(s) sent from DataHandler.
     */
    @Override
    public void updateFromXplane(DataObserverPacket packet) {

        Platform.runLater(() -> {

            DrefDataIO io = new DrefDataIO();
            String command = io.getCmndByDataref(packet.getDref());

            if (command.equals("ap_mode")) {
                inCommandMap.getInCommandMap().get("ap_mode").execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("ap_heading_mode")) {
                inCommandMap.getInCommandMap().get("ap_heading_mode").execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("ap_altitude_mode")) {
                inCommandMap.getInCommandMap().get("ap_altitude_mode").execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("ap_backcourse")) {
                inCommandMap.getInCommandMap().get("ap_backcourse").execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("ap_appr_status")) {
                inCommandMap.getInCommandMap().get("ap_appr_status").execute(packet.getDref(), packet.getValues());
            }

        });
    }

    @Override
    public void addToField(ActionEvent event) {
        // method not implemented in this class
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

    /* GETTERS and SETTERS */

    public AutoPilotButton getApToggleBtn() {
        return apToggleBtn;
    }

    public AutoPilotButton getApHeadingBtn() {
        return apHeadingBtn;
    }

    public AutoPilotButton getApAltitudeBtn() {
        return apAltitudeBtn;
    }

    public AutoPilotButton getApVSBtn() {
        return apVSBtn;
    }

    public AutoPilotButton getApApprBtn() {
        return apApprBtn;
    }

    public AutoPilotButton getApNavBtn() {
        return apNavBtn;
    }

    public AutoPilotButton getApRevBtn() {
        return apRevBtn;
    }
}
