package com.mydogspies.xflytools.util;

import com.mydogspies.xflytools.Initialize;
import com.mydogspies.xflytools.data.LayoutData;
import com.mydogspies.xflytools.data.LayoutDataContainer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AddLayoutJsonData {

    public static void main(String[] args) throws IOException {

        File jsonfile = readFile("src/main/java/com/mydogspies/xflytools/data/layout.json");
        LayoutDataContainer datalist = new LayoutDataContainer();
        List<LayoutData> startlist = new ArrayList<>();

        // structure of the LayoutData pojo
        // aircraftProfile, windowGen, windowLights, windowRadios, windowAPButtons, windowAPReadouts

        LayoutData data2 = new LayoutData("Laminar_Cessna172", "lamcessna172");
        startlist.add(data2);
        /*LayoutData data3 = new LayoutData("Laminar_Baron58", "lambaron58");
        startlist.add(data3);
        LayoutData data4 = new LayoutData("Laminar_Cessna172_G1000", "lamcessna1721000");
        startlist.add(data4);*/


        // add all to container
        datalist.setLayoutData(startlist);

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
