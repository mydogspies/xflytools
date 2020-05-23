package com.mydogspies.xflytools.controller;

import com.mydogspies.xflytools.controller.inlogic.*;
import com.mydogspies.xflytools.controller.outlogic.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * This class defines the maps we use for our command patterns.
 *
 * @author Peter Mankowski
 * @see OutCommandMapSingleton
 * @see InCommandMapSingleton
 * @since 0.4.0
 */
public class AddCommandMapData {

    private static final Logger log = LoggerFactory.getLogger(AddCommandMapData.class);
    private final InCommandMapSingleton inSingleton = InCommandMapSingleton.getInstance();
    private final OutCommandMapSingleton outSingleton = OutCommandMapSingleton.getInstance();

    public void initiateCommandMap() {

        /* the incoming command definitions */

        Map<String, InCommand> inMap = new HashMap<>();

        inMap.put("hdr_status", new HdrStatusGet());
        inMap.put("flashlight_status", new FlashlightGet());
        inMap.put("ap_heading", new APHeadingGet());
        inMap.put("ap_altitude", new APAltitudeGet());
        inMap.put("ap_vertical_speed", new APVerticalSpeedGet());
        inMap.put("nav1_course", new Nav1CourseGet());
        inMap.put("ap_mode", new APModeGet());
        inMap.put("ap_heading_mode", new APHeadingModeGet());
        inMap.put("ap_altitude_mode", new APAltitudeModeGet());
        inMap.put("ap_backcourse", new APBackCourseGet());
        inMap.put("ap_appr_status", new APApprStatusGet());
        inMap.put("taxi_light", new LightsTaxiToggleGet());
        inMap.put("nav_light", new LightsNavToggleGet());
        inMap.put("beacon_light", new LightsBeaconToggleGet());
        inMap.put("strobe_light", new LightsStrobeToggleGet());
        inMap.put("landing_light", new LightsLandingToggleGet());
        inMap.put("baro_pilot_inhg", new MiscBaroGet());
        inMap.put("com1_freq", new RadiosCom1FreqGet());
        inMap.put("com2_freq", new RadiosCom2FreqGet());

        InCommandMap inMapObject = new InCommandMap(inMap);
        inSingleton.setMap(inMapObject);

        log.debug("initiateCommandMap(): New InCommandMap initiated: " + inMapObject);
        log.trace("initiateCommandMap(): InCommandMap content " + inMapObject.getInCommandMap().toString());

        /* the outgoing command definitions */

        Map<String, OutCommand> outMap = new HashMap<>();

        outMap.put("nav1_course", new Nav1CourseSend());
        outMap.put("ap_heading", new APHeadingSend());
        outMap.put("ap_altitude", new APAltitudeSend());
        outMap.put("ap_vertical_speed", new APVerticalSpeedSend());
        outMap.put("ap_mode", new APModeSend());
        outMap.put("ap_heading_toggle", new APHeadingToggleSend());
        outMap.put("ap_nav_toggle", new APNavToggleSend());
        outMap.put("ap_vs_toggle", new APVerticalSpeedToggleSend());
        outMap.put("ap_altitude_toggle", new APAltitudeToggleSend());
        outMap.put("ap_rev_toggle", new APRevToggleSend());
        outMap.put("ap_appr_toggle", new APApprToggleSend());
        outMap.put("taxi_lights_flip", new LightsTaxiToggleSend());
        outMap.put("nav_lights_flip", new LightsNavToggleSend());
        outMap.put("beacon_lights_flip", new LightsBeaconToggleSend());
        outMap.put("strobe_lights_flip", new LightsStrobeToggleSend());
        outMap.put("landing_lights_flip", new LightsLandingToggleSend());
        outMap.put("baro_units", new MiscBaroToggleSend());
        outMap.put("baro_pilot_std", new MiscBaroStandardSend());
        outMap.put("baro_pilot_inhg", new MiscBaroSend());

        OutCommandMap outMapObject = new OutCommandMap(outMap);
        outSingleton.setMap(outMapObject);

        log.debug("initiateCommandMap(): New OutCommandMap initiated: " + inMapObject);
        log.trace("initiateCommandMap(): OutCommandMap content " + inMapObject.getInCommandMap().toString());
    }
}
