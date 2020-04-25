package com.mydogspies.xflytools.tests;

import com.mydogspies.xflytools.Initialize;
import com.mydogspies.xflytools.data.DrefData;
import com.mydogspies.xflytools.data.DrefDataContainer;

import javax.xml.bind.DatatypeConverter;
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

        // DATA 1
        byte[] value1 = {0, 0, -128, 63};
        String base64Encoded = DatatypeConverter.printBase64Binary(value1);
        DrefData data1 = new DrefData("LamCessna172", "taxi_light_on", "DREF+", base64Encoded, "sim/cockpit2/switches/taxi_light_on");
        startlist.add(data1);

        // DATA 2
        byte[] value2 = {0, 0, 0, 0};
        String base64Encoded2 = DatatypeConverter.printBase64Binary(value2);
        DrefData data2 = new DrefData("LamCessna172", "taxi_light_off", "DREF+", base64Encoded2, "sim/cockpit2/switches/taxi_light_off");
        startlist.add(data2);

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
            List<DrefData> stuff = dreflist.getDrefdata();
            byte[] gog = DatatypeConverter.parseBase64Binary(stuff.get(0).getValue());
            System.out.println(gog[2]);
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
