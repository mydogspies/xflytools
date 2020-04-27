package com.mydogspies.xflytools.tests;

import com.mydogspies.xflytools.Initialize;
import com.mydogspies.xflytools.data.DrefData;
import com.mydogspies.xflytools.data.DrefDataContainer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AddJsonData {

    public static void main(String[] args) throws IOException {

        File jsonfile = readFile("src/main/java/com/mydogspies/xflytools/data/drefData.json");
        DrefDataContainer datalist = new DrefDataContainer();
        List<DrefData> startlist = new ArrayList<>();

        /* WRITE DATA */

        DrefData data1 = new DrefData("taxi_light", "default", "sim/cockpit2/switches/taxi_light_on", "int");
        startlist.add(data1);
        DrefData data2 = new DrefData("nav_light", "default", "sim/cockpit2/switches/navigation_lights_on", "int");
        startlist.add(data2);
        DrefData data3 = new DrefData("beacon_light", "default", "sim/cockpit2/switches/beacon_on", "int");
        startlist.add(data3);
        DrefData data4 = new DrefData("strobe_light", "default", "sim/cockpit2/switches/strobe_lights_on", "int");
        startlist.add(data4);
        DrefData data5 = new DrefData("landing_light", "default", "sim/cockpit2/switches/landing_lights_on", "int");
        startlist.add(data5);
        DrefData data6 = new DrefData("com1_freq", "default", "sim/cockpit/radios/com1_freq_hz", "double");
        startlist.add(data6);
        DrefData data7 = new DrefData("com2_freq", "default", "sim/cockpit/radios/com2_freq_hz", "double");
        startlist.add(data7);
        DrefData data8 = new DrefData("nav1_freq", "default", "sim/cockpit/radios/nav1_freq_hz", "double");
        startlist.add(data8);
        DrefData data9 = new DrefData("nav2_freq", "default", "sim/cockpit/radios/nav2_freq_hz", "double");
        startlist.add(data9);
        DrefData data10 = new DrefData("transponder_code", "default", "sim/cockpit/radios/transponder_code", "int");
        startlist.add(data10);
        DrefData data11 = new DrefData("com1_stdby_freq", "default", "sim/cockpit/radios/com1_stdby_freq_hz", "double");
        startlist.add(data11);
        DrefData data12 = new DrefData("com2_stdby_freq", "default", "sim/cockpit/radios/com2_stdby_freq_hz", "double");
        startlist.add(data12);
        DrefData data13 = new DrefData("nav1_stdby_freq", "default", "sim/cockpit/radios/nav1_stdby_freq_hz", "double");
        startlist.add(data13);
        DrefData data14 = new DrefData("nav2_stdby_freq", "default", "sim/cockpit/radios/nav2_stdby_freq_hz", "double");
        startlist.add(data14);



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
