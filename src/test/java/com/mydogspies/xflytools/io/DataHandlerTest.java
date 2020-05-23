package com.mydogspies.xflytools.io;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataHandlerTest {

    @Test
    void testProcessStringIntoDatarefsWithACorrectSingleStringTest() {

        String rawDataString = "uf sim/cockpit2/gauges/actuators/barometer_setting_in_hg_pilot 29.92000008";

        DataHandler handler = new DataHandler();
        List<String> processedString = handler.processStringIntoDatarefs(rawDataString);

        assertEquals("sim/cockpit2/gauges/actuators/barometer_setting_in_hg_pilot 29.92000008", processedString.get(0));
    }

    @Test
    void testProcessStringIntoDatarefsWithACorrectMultipleStringTest() {

        String rawDataString = "ui sim/cockpit2/radios/actuators/com2_right_is_selected 0\n" +
                "ui sim/cockpit2/radios/actuators/com2_frequency_hz_833 136990\n" +
                "uf sim/cockpit/radios/nav1_obs_degm 100";

        DataHandler handler = new DataHandler();
        List<String> processedString = handler.processStringIntoDatarefs(rawDataString);

        assertEquals("sim/cockpit2/radios/actuators/com2_right_is_selected 0", processedString.get(0));
        assertEquals("sim/cockpit2/radios/actuators/com2_frequency_hz_833 136990", processedString.get(1));
        assertEquals("sim/cockpit/radios/nav1_obs_degm 100", processedString.get(2));
    }

    @Test
    void makeDatarefMapWithADatalistTest() {

        List<String> incomingListofDatarefs = new ArrayList<>();
        incomingListofDatarefs.add("sim/cockpit2/radios/actuators/com2_frequency_hz_833 136990");
        incomingListofDatarefs.add("sim/cockpit2/radios/actuators/com2_right_is_selected 0");

        DataHandler handler = new DataHandler();
        Map<String, ArrayList<String>> map = handler.makeDatarefMap(incomingListofDatarefs);

        // assert first key-value pair
        List<String> resultValue = map.get("sim/cockpit2/radios/actuators/com2_frequency_hz_833");
        assertEquals(resultValue.get(0), "136990");

        // assert second key-value pair
        List<String> resultValue2 = map.get("sim/cockpit2/radios/actuators/com2_right_is_selected");
        assertEquals(resultValue2.get(0), "0");
    }
}