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

        // add all to container
        datalist.setDrefdata(startlist);


        try {
            Initialize.mapper.writeValue(jsonfile, datalist);
            System.out.println("Data written: " + datalist);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* READ BACK DATA */



        try {
            DrefDataContainer dreflist = Initialize.mapper.readValue(jsonfile, DrefDataContainer.class);
            System.out.println(dreflist.getDrefdata());
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
