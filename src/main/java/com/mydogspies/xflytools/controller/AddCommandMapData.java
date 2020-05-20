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

        OutCommandMap outMapObject = new OutCommandMap(outMap);
        outSingleton.setMap(outMapObject);

        log.debug("initiateCommandMap(): New OutCommandMap initiated: " + inMapObject);
        log.trace("initiateCommandMap(): OutCommandMap content " + inMapObject.getInCommandMap().toString());
    }
}
