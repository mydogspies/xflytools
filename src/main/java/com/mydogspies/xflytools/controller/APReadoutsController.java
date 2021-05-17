package com.mydogspies.xflytools.controller;

import com.mydogspies.xflytools.controller.elements.AutoPilotField;
import com.mydogspies.xflytools.controller.elements.AutoPilotLabel;

/**
 * This interface is used to extend ControllerCo with module-specific getters for UI elements.
 * See ControllerCo for more in-depth explanation.
 *
 * @author Peter Mankowski
 * @see ControllerCo
 * @since 0.4.0
 */
public interface APReadoutsController extends ControllerCo {

    public AutoPilotLabel getApCourse();

    public AutoPilotLabel getApHeading();

    public AutoPilotLabel getApLevel();

    public AutoPilotLabel getApVerticalSpeed();

    public AutoPilotField getApCourseField();

    public AutoPilotField getApHeadingField();

    public AutoPilotField getApAltitudeField();

    public AutoPilotField getApVSField();
}
