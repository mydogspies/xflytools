package com.mydogspies.xflytools.gui.module.lamboeing747;

import com.mydogspies.xflytools.gui.MainWindow;
import com.mydogspies.xflytools.gui.elements.AutoPilotField;
import com.mydogspies.xflytools.gui.elements.AutopilotLabel;
import com.mydogspies.xflytools.io.SocketConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * This is the controller for the A/P readouts part of the GUI.
 *
 * @author Peter Mankowski
 * @since 0.4.0
 */
public class DefaultAPReadouts {

    private static final Logger log = LoggerFactory.getLogger(DefaultAPReadouts.class);

    @FXML
    private GridPane buttonGrid;

    private AutopilotLabel apCourse;
    private AutopilotLabel apHeading;
    private AutopilotLabel apLevel;
    private AutopilotLabel apVerticalSpeed;
    private AutoPilotField apCourseField;
    private AutoPilotField apHeadingField;
    private AutoPilotField apAltitudeField;
    private AutoPilotField apVSField;

    /* INIT */

    @FXML
    void initialize() {

        initElems();
    }

    @FXML
    private void addToField(ActionEvent event) {

        log.debug("addToField(): ActionEvent called: " + event);

        if (SocketConnect.socket != null) {

            Node b = (Node) event.getSource();
            String field_id = b.getId();

            switch (field_id) {

                case "apcoursefield":
                case "courseSelect":
                    String val1 = apCourseField.getText();
                    if (val1.matches("[0-9]{3}")) {
                        MainWindow.controller.sendToXplane("set", "nav1_course", val1);
                        log.trace("addToField(): Nav1 course set to " + val1 + " in Xplane.");
                    }
                    break;

                case "apheadingfield":
                case "headingSelect":
                    String val2 = apHeadingField.getText();
                    if (val2.matches("[0-9]{3}")) {
                        MainWindow.controller.sendToXplane("set", "ap_heading", val2);
                        log.trace("addToField(): A/P Heading course set to " + val2 + " in Xplane.");
                    }
                    break;

                case "apaltitudefield":
                case "levelSelect":
                    String val3 = apAltitudeField.getText();
                    if (val3.matches("[0-9]{3,5}")) {
                        MainWindow.controller.sendToXplane("set", "ap_altitude", val3);
                        log.trace("addToField(): A/P Altitude set to " + val3 + " in Xplane.");
                    }
                    break;

                case "apvsfield":
                case "verticalSpeedSelect":
                    String val4 = apVSField.getText();
                    if (val4.matches("[0-9]{3,4}")) {
                        MainWindow.controller.sendToXplane("set", "ap_vertical_speed", val4);
                        log.trace("addToField(): A/P vertical speed set to " + val4 + " in Xplane.");
                    }
                    break;
            }
        }
    }


    public void updateData(String command, ArrayList<String> value) {

        switch (command) {

            case "ap_heading":
                String val = String.format("%03d", Integer.parseInt(value.get(0)));
                if (!apHeading.getText().equals(val)) {
                    apHeading.setText(val + (char) 176);
                }
                log.trace("updateData(): [" + command + "] -> AP set to heading " + val);
                break;

            case "ap_altitude":
                String val3 = value.get(0);
                if (!apLevel.getText().equals(val3)) {
                    apLevel.setText(val3 + "'");
                }
                log.trace("updateData(): [" + command + "] -> AP set to altitude " + val3);
                break;

            case "ap_vertical_speed":
                String val4 = value.get(0);
                if (!apVerticalSpeed.getText().equals(val4)) {
                    apVerticalSpeed.setText(val4);
                }
                log.trace("updateData(): [" + command + "] -> AP set to vertical speed " + val4);
                break;

            case "nav1_course":
                String val5 = String.format("%03d", Math.round(Float.parseFloat(value.get(0))));
                if (!apCourse.getText().equals(val5)) {
                    apCourse.setText(val5 + (char) 176);
                }
                log.trace("updateData(): [" + command + "] -> Nav 1 course (for AP) set to " + val5);
                break;
        }
    }

    private void initElems() {

        apCourse = new AutopilotLabel();
        apCourse.setId("apcourse");
        apCourse.setText("");
        apCourse.getStyleClass().add("ap-labels");
        buttonGrid.add(apCourse, 0, 0);

        apHeading = new AutopilotLabel();
        apHeading.setId("apheading");
        apHeading.setText("");
        apHeading.getStyleClass().add("ap-labels");
        buttonGrid.add(apHeading, 0, 1);

        apLevel = new AutopilotLabel();
        apLevel.setId("aplevel");
        apLevel.setText("");
        apLevel.getStyleClass().add("ap-labels");
        buttonGrid.add(apLevel, 0, 2);

        apVerticalSpeed = new AutopilotLabel();
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

    /**
     * Resets all elements to their initial visual state
     */
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
}
