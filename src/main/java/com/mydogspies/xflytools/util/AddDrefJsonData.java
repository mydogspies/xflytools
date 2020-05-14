package com.mydogspies.xflytools.util;

import com.mydogspies.xflytools.Initialize;
import com.mydogspies.xflytools.data.DrefData;
import com.mydogspies.xflytools.data.DrefDataContainer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Json creator for the database - not part of the production code
 */
public class AddDrefJsonData {

    public static void main(String[] args) throws IOException {

        File jsonfile = readFile("src/main/java/com/mydogspies/xflytools/data/drefData.json");
        DrefDataContainer datalist = new DrefDataContainer();
        List<DrefData> startlist = new ArrayList<>();


        /* DATAREFS */

        // LIGHTS
        DrefData data1 = new DrefData("taxi_light", "lights", "sim/cockpit2/switches/taxi_light_on", "int", "set");
        startlist.add(data1);
        DrefData data2 = new DrefData("nav_light", "lights", "sim/cockpit2/switches/navigation_lights_on", "int", "set");
        startlist.add(data2);
        DrefData data3 = new DrefData("beacon_light", "lights", "sim/cockpit2/switches/beacon_on", "int", "set");
        startlist.add(data3);
        DrefData data4 = new DrefData("strobe_light", "lights", "sim/cockpit2/switches/strobe_lights_on", "int", "set");
        startlist.add(data4);
        DrefData data5 = new DrefData("landing_light", "lights", "sim/cockpit2/switches/landing_lights_on", "int", "set");

        // RADIOS
        startlist.add(data5);
        DrefData data6 = new DrefData("com1_freq", "radios", "sim/cockpit2/radios/actuators/com1_frequency_hz_833", "double", "set");
        startlist.add(data6);
        DrefData data7 = new DrefData("com2_freq", "radios", "sim/cockpit2/radios/actuators/com2_frequency_hz_833", "double", "set");
        startlist.add(data7);
        DrefData data8 = new DrefData("nav1_freq", "radios", "sim/cockpit/radios/nav1_freq_hz", "double", "set");
        startlist.add(data8);
        DrefData data9 = new DrefData("nav2_freq", "radios", "sim/cockpit/radios/nav2_freq_hz", "double", "set");
        startlist.add(data9);
        DrefData data10 = new DrefData("transponder_code", "radios", "sim/cockpit/radios/transponder_code", "int", "set");
        startlist.add(data10);
        DrefData data11 = new DrefData("com1_stdby_freq", "radios", "sim/cockpit2/radios/actuators/com1_standby_frequency_hz_833", "double", "set");
        startlist.add(data11);
        DrefData data12 = new DrefData("com2_stdby_freq", "radios", "sim/cockpit2/radios/actuators/com2_standby_frequency_hz_833", "double", "set");
        startlist.add(data12);
        DrefData data13 = new DrefData("nav1_stdby_freq", "radios", "sim/cockpit/radios/nav1_stdby_freq_hz", "double", "set");
        startlist.add(data13);
        DrefData data14 = new DrefData("nav2_stdby_freq", "radios", "sim/cockpit/radios/nav2_stdby_freq_hz", "double", "set");
        startlist.add(data14);
        DrefData data15 = new DrefData("com1_selected", "radios", "sim/cockpit2/radios/actuators/com1_right_is_selected", "int", "set");
        startlist.add(data15);
        DrefData data16 = new DrefData("com2_selected", "radios", "sim/cockpit2/radios/actuators/com2_right_is_selected", "int", "set");
        startlist.add(data16);
        DrefData data17 = new DrefData("nav1_selected", "radios", "sim/cockpit2/radios/actuators/nav1_right_is_selected", "int", "set");
        startlist.add(data17);
        DrefData data20 = new DrefData("adf1_freq", "radios", "sim/cockpit/radios/adf1_freq_hz", "int", "set");
        startlist.add(data20);
        DrefData data19 = new DrefData("adf1_stdby_freq", "radios", "sim/cockpit/radios/adf1_stdby_freq_hz", "int", "set");
        startlist.add(data19);


        // AP
        DrefData data30 = new DrefData("ap_heading", "autopilot_readout", "sim/cockpit/autopilot/heading_mag", "float", "set");
        startlist.add(data30);
        DrefData data31 = new DrefData("ap_altitude", "autopilot_readout", "sim/cockpit/autopilot/altitude", "float", "set");
        startlist.add(data31);
        DrefData data32 = new DrefData("ap_vertical_speed", "autopilot_readout", "sim/cockpit/autopilot/vertical_velocity", "float", "set");
        startlist.add(data32);
        DrefData data33 = new DrefData("ap_mode", "autopilot_switch","sim/cockpit/autopilot/autopilot_mode", "int", "set");
        startlist.add(data33);
        DrefData data18 = new DrefData("nav1_course", "autopilot_readout", "sim/cockpit/radios/nav1_obs_degm", "float", "set");
        startlist.add(data18);
        DrefData data34 = new DrefData("ap_heading_mode", "autopilot_switch", "sim/cockpit2/autopilot/heading_mode", "int", "set");
        startlist.add(data34);
        DrefData data35 = new DrefData("ap_altitude_mode", "autopilot_switch", "sim/cockpit2/autopilot/altitude_mode", "int", "set");
        startlist.add(data35);
        DrefData data36 = new DrefData("ap_backcourse", "autopilot_switch", "sim/cockpit/autopilot/backcourse_on", "int", "set");
        startlist.add(data36);
        DrefData data37 = new DrefData("ap_appr_status", "autopilot_switch", "sim/cockpit2/autopilot/approach_status", "enum", "set");
        startlist.add(data37);


        // MISC
        DrefData data40 = new DrefData("baro_pilot_inhg", "misc", "sim/cockpit2/gauges/actuators/barometer_setting_in_hg_pilot", "float", "set");
        startlist.add(data40);
        DrefData data41 = new DrefData("flashlight_status", "main", "sim/graphics/misc/white_flashlight_on", "enum", "set");
        startlist.add(data41);
        DrefData data42 = new DrefData("hdr_status", "main", "sim/graphics/settings/HDR_on", "int", "set");
        startlist.add(data42);


        /* COMMANDS*/

        // RADIOS
        DrefData data50 = new DrefData("com1_flip", "radios", "sim/radios/com1_standy_flip", "", "cmd");
        startlist.add(data50);
        DrefData data51 = new DrefData("com2_flip", "radios", "sim/radios/com2_standy_flip", "", "cmd");
        startlist.add(data51);
        DrefData data52 = new DrefData("nav1_flip", "radios", "sim/radios/nav1_standy_flip", "", "cmd");
        startlist.add(data52);
        DrefData data53 = new DrefData("nav2_flip", "radios", "sim/radios/nav2_standy_flip", "", "cmd");
        startlist.add(data53);
        DrefData data54 = new DrefData("adf1_flip", "radios", "sim/radios/adf1_standy_flip", "", "cmd");
        startlist.add(data54);



        // LIGHTS
        DrefData data60 = new DrefData("nav_lights_flip", "lights", "sim/lights/nav_lights_toggle", "", "cmd");
        startlist.add(data60);
        DrefData data61 = new DrefData("beacon_lights_flip", "lights", "sim/lights/beacon_lights_toggle", "", "cmd");
        startlist.add(data61);
        DrefData data62 = new DrefData("taxi_lights_flip", "lights", "sim/lights/taxi_lights_toggle", "", "cmd");
        startlist.add(data62);
        DrefData data63 = new DrefData("strobe_lights_flip", "lights", "sim/lights/strobe_lights_toggle", "", "cmd");
        startlist.add(data63);
        DrefData data64 = new DrefData("landing_lights_flip", "lights", "sim/lights/landing_lights_toggle", "", "cmd");
        startlist.add(data64);

        // AUTOPILOT
        DrefData data80 = new DrefData("ap_heading_toggle", "autopilot_switch", "sim/autopilot/heading", "", "cmd");
        startlist.add(data80);
        DrefData data81 = new DrefData("ap_nav_toggle", "autopilot_switch", "sim/autopilot/NAV", "", "cmd");
        startlist.add(data81);
        DrefData data82 = new DrefData("ap_vs_toggle", "autopilot_switch", "sim/autopilot/vertical_speed", "", "cmd");
        startlist.add(data82);
        DrefData data83 = new DrefData("ap_altitude_toggle", "autopilot_switch", "sim/autopilot/altitude_hold", "", "cmd");
        startlist.add(data83);
        DrefData data84 = new DrefData("ap_rev_toggle", "autopilot_switch", "sim/autopilot/back_course", "", "cmd");
        startlist.add(data84);
        DrefData data85 = new DrefData("ap_appr_toggle", "autopilot_switch", "sim/autopilot/approach", "", "cmd");
        startlist.add(data85);

        // MISC
        DrefData data110 = new DrefData("flashlight_toggle", "main", "sim/view/flashlight_wht", "", "cmd");
        startlist.add(data110);



        // add all to container
        datalist.setDrefdata(startlist);

        try {
            Initialize.mapper.writeValue(jsonfile, datalist);
            System.out.println("Data written: " + datalist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* STUFF */

    private static File readFile(String filePath) {

        File file = Paths.get(filePath).toFile();

        if (file.exists()) {

            return file;
        }
        return null;
    }
}
