package com.mydogspies.xflytools.controller.module.lamcessna172;

import com.mydogspies.xflytools.controller.inlogic.InCommand;
import com.mydogspies.xflytools.controller.inlogic.InCommandMap;
import com.mydogspies.xflytools.controller.inlogic.InCommandMapSingleton;
import com.mydogspies.xflytools.controller.outlogic.OutCommand;
import com.mydogspies.xflytools.controller.outlogic.OutCommandMap;
import com.mydogspies.xflytools.controller.outlogic.OutCommandMapSingleton;
import com.mydogspies.xflytools.data.DrefDataIO;
import com.mydogspies.xflytools.controller.ControllerCo;
import com.mydogspies.xflytools.controller.MainWindowController;
import com.mydogspies.xflytools.controller.MainWindowControllerSingleton;
import com.mydogspies.xflytools.controller.elements.AutoPilotField;
import com.mydogspies.xflytools.controller.elements.AutoPilotLabel;
import com.mydogspies.xflytools.io.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the controller for the A/P readouts part of the GUI.
 * It's loaded into the parent window as part of the initiation process in the MainWindowController
 *
 * @author Peter Mankowski
 * @since 0.4.0
 * @see MainWindowController
 */
public class APReadouts implements ControllerCo, DataObserverIO {

    private static final Logger log = LoggerFactory.getLogger(APReadouts.class);
    private final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();
    private final DataHandler dataHandler = DataHandlerSingleton.getInstance().getHandler();
    private final InCommandMap inCommandMap = InCommandMapSingleton.getInstance().getMap();
    private final OutCommandMap outCommandMap = OutCommandMapSingleton.getInstance().getMap();

    @FXML
    private GridPane buttonGrid;

    private AutoPilotLabel apCourse;
    private AutoPilotLabel apHeading;
    private AutoPilotLabel apLevel;
    private AutoPilotLabel apVerticalSpeed;
    private AutoPilotField apCourseField;
    private AutoPilotField apHeadingField;
    private AutoPilotField apAltitudeField;
    private AutoPilotField apVSField;

    /* INIT */

    @Override
    @FXML
    public void initialize() {

        // necessary to receive data from Xplane
        dataHandler.addObserver(this);
        initElements();
    }

    @Override
    @FXML
    public void addToField(ActionEvent event) {

        log.debug("addToField(): ActionEvent called: " + event);

        if (SocketConnect.socket != null) {

            Node b = (Node) event.getSource();
            String field_id = b.getId();

            if (field_id.equals("apcoursefield") || field_id.equals("courseSelect")) {
                outCommandMap.getOutCommandMap().get("nav1_course").execute();
            }

            if (field_id.equals("apheadingfield") || field_id.equals("headingSelect")) {
                outCommandMap.getOutCommandMap().get("ap_heading").execute();
            }

            if (field_id.equals("apaltitudefield") || field_id.equals("levelSelect")) {
                outCommandMap.getOutCommandMap().get("ap_altitude").execute();
            }

            if (field_id.equals("apvsfield") || field_id.equals("verticalSpeedSelect")) {
                outCommandMap.getOutCommandMap().get("ap_vertical_speed").execute();
            }
        }
    }

    @Override
    public void updateFromXplane(DataObserverPacket packet) {

        Platform.runLater(() -> {

            DrefDataIO io = new DrefDataIO();
            String command = io.getCmndByDataref(packet.getDref());

            if (command.equals("ap_heading")) {

                InCommand cmd = inCommandMap.getInCommandMap().get("ap_heading");
                cmd.execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("ap_altitude")) {

                InCommand cmd = inCommandMap.getInCommandMap().get("ap_altitude");
                cmd.execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("ap_vertical_speed")) {

                InCommand cmd = inCommandMap.getInCommandMap().get("ap_vertical_speed");
                cmd.execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("nav1_course")) {

                InCommand cmd = inCommandMap.getInCommandMap().get("nav1_course");
                cmd.execute(packet.getDref(), packet.getValues());
            }

        });
    }

    @Override
    public void initElements() {

        apCourse = new AutoPilotLabel();
        apCourse.setId("apcourse");
        apCourse.setText("");
        apCourse.getStyleClass().add("ap-labels");
        buttonGrid.add(apCourse, 0, 0);

        apHeading = new AutoPilotLabel();
        apHeading.setId("apheading");
        apHeading.setText("");
        apHeading.getStyleClass().add("ap-labels");
        buttonGrid.add(apHeading, 0, 1);

        apLevel = new AutoPilotLabel();
        apLevel.setId("aplevel");
        apLevel.setText("");
        apLevel.getStyleClass().add("ap-labels");
        buttonGrid.add(apLevel, 0, 2);

        apVerticalSpeed = new AutoPilotLabel();
        apVerticalSpeed.setId("apverticalspeed");
        apVerticalSpeed.setText("");
        apVerticalSpeed.getStyleClass().add("ap-labels");
        buttonGrid.add(apVerticalSpeed, 0, 3);

        apCourseField = new AutoPilotField();
        apCourseField.setId("apcoursefield");
        apCourseField.setText("");
        apCourseField.getStyleClass().add("ap-fields");
        apCourseField.setOnAction(this::addToField);
        buttonGrid.add(apCourseField, 2, 0);

        apHeadingField = new AutoPilotField();
        apHeadingField.setId("apheadingfield");
        apHeadingField.setText("");
        apHeadingField.getStyleClass().add("ap-fields");
        apHeadingField.setOnAction(this::addToField);
        buttonGrid.add(apHeadingField, 2, 1);

        apAltitudeField = new AutoPilotField();
        apAltitudeField.setId("apaltitudefield");
        apAltitudeField.setText("");
        apAltitudeField.getStyleClass().add("ap-fields");
        apAltitudeField.setOnAction(this::addToField);
        buttonGrid.add(apAltitudeField, 2, 2);

        apVSField = new AutoPilotField();
        apVSField.setId("apvsfield");
        apVSField.setText("");
        apVSField.getStyleClass().add("ap-fields");
        apVSField.setOnAction(this::addToField);
        buttonGrid.add(apVSField, 2, 3);
    }

    @Override
    public void disableAll(boolean state) {
        // not implemented in this class
    }

    @Override
    public void clickButton(ActionEvent event) {
        // not implemented in this class
    }

    @Override
    public void onReset() {

        apCourse.setText("");
        apHeading.setText("");
        apLevel.setText("");
        apVerticalSpeed.setText("");
        apCourseField.setText("");
        apHeadingField.setText("");
        apAltitudeField.setText("");
        apVSField.setText("");
    }

    /* GETTERS and SETTERS */

    public AutoPilotLabel getApCourse() {
        return apCourse;
    }

    public AutoPilotLabel getApHeading() {
        return apHeading;
    }

    public AutoPilotLabel getApLevel() {
        return apLevel;
    }

    public AutoPilotLabel getApVerticalSpeed() {
        return apVerticalSpeed;
    }

    public AutoPilotField getApCourseField() {
        return apCourseField;
    }

    public AutoPilotField getApHeadingField() {
        return apHeadingField;
    }

    public AutoPilotField getApAltitudeField() {
        return apAltitudeField;
    }

    public AutoPilotField getApVSField() {
        return apVSField;
    }
}
