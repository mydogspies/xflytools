package com.mydogspies.xflytools.util;

import com.mydogspies.xflytools.Initialize;
import com.mydogspies.xflytools.gui.LayoutData;
import com.mydogspies.xflytools.gui.LayoutDataContainer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AddLayoutJsonData {

    public static void main(String[] args) throws IOException {

        File jsonfile = readFile("src/main/java/com/mydogspies/xflytools/gui/layout.json");
        LayoutDataContainer datalist = new LayoutDataContainer();
        List<LayoutData> startlist = new ArrayList<>();

        // structure of the LayoutData pojo
        // aircraftProfile, windowGen, windowLights, windowRadios, windowAPButtons, windowAPReadouts

        LayoutData data1 = new LayoutData("default", "defaultMain.fxml", "defaultLightButtons.fxml", "defaultRadios.fxml", "defaultAPButtons", "defaultAPReadouts.fxml");
        startlist.add(data1);


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
