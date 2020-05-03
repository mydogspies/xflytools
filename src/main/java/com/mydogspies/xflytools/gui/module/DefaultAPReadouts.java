package com.mydogspies.xflytools.gui.module;

import com.mydogspies.xflytools.gui.elements.AutoPilotField;
import com.mydogspies.xflytools.gui.elements.AutopilotLabel;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

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

    public void updateData(String command, ArrayList<String> value) {

        switch (command) {

            case "ap_heading":
                String val = String.format("%03d", Integer.parseInt(value.get(0)));
                if (!apHeading.getText().equals(val)) { apHeading.setText(val + (char) 176); }
                log.trace("getFromXplane(): [" + command + "] -> AP set to heading " + val);
                break;

            case "ap_altitude":
                String val3 = value.get(0);
                if (!apLevel.getText().equals(val3)) { apLevel.setText(val3 + "'"); }
                log.trace("getFromXplane(): [" + command + "] -> AP set to altitude " + val3);
                break;

            case "ap_vertical_speed":
                String val4 = value.get(0);
                if (!apVerticalSpeed.getText().equals(val4)) { apVerticalSpeed.setText(val4); }
                log.trace("getFromXplane(): [" + command + "] -> AP set to vertical speed " + val4);
                break;

            case "nav1_course":
                String val5 = String.format("%03d", Math.round(Float.parseFloat(value.get(0))));
                if (!apCourse.getText().equals(val5)) { apCourse.setText(val5 + (char) 176); }
                log.trace("getFromXplane(): [" + command + "] -> Nav 1 course (for AP) set to " + val5);
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
        buttonGrid.add(apLevel, 0,2);

        apVerticalSpeed = new AutopilotLabel();
        apVerticalSpeed.setId("apverticalspeed");
        apVerticalSpeed.setText("");
        apVerticalSpeed.getStyleClass().add("ap-labels");
        buttonGrid.add(apVerticalSpeed, 0, 3);

        apCourseField = new AutoPilotField();
        apCourseField.setId("apcoursefield");
        apCourseField.setText("");
        apCourseField.getStyleClass().add("ap-fields");
        apCourseField.setMaxWidth(60);
        buttonGrid.add(apCourseField, 4, 0);

        apHeadingField = new AutoPilotField();
        apHeadingField.setId("apheadingfield");
        apHeadingField.setText("");
        apHeadingField.getStyleClass().add("ap-fields");
        apHeadingField.setMaxWidth(60);
        buttonGrid.add(apHeadingField, 4, 1);

        apAltitudeField = new AutoPilotField();
        apAltitudeField.setId("apaltitudefield");
        apAltitudeField.setText("");
        apAltitudeField.getStyleClass().add("ap-fields");
        apAltitudeField.setMaxWidth(60);
        buttonGrid.add(apAltitudeField, 4, 2);

        apVSField = new AutoPilotField();
        apVSField.setId("apvsfield");
        apVSField.setText("");
        apVSField.getStyleClass().add("ap-fields");
        apVSField.setMaxWidth(60);
        buttonGrid.add(apVSField, 4, 3);
    }
}
