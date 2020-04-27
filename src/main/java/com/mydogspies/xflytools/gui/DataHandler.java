package com.mydogspies.xflytools.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataHandler {

    private static final Logger log = LoggerFactory.getLogger(DataHandler.class);

    private String rawString;
    private String header;
    private String values;

    public DataHandler(String rawString) {
        this.rawString = rawString;
        processData();
    }

    public void processData() {

        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                log.trace("run(): DataHandler invoked in a new thread with raw string: [" + rawString + "]");

                List<String> dataList = new ArrayList<>();

                // find out if we received more than one command string
                int counter = 0;
                Pattern pattern = Pattern.compile("ui");
                Matcher m = pattern.matcher(rawString);
                while (m.find()) {
                    counter++;
                }

                // if more than single dataref then break them up
                if (counter > 1) {

                    // split by "ui" so that we get individual datarefs
                    String[] splitByUI = rawString.split("ui");
                    for (String split : splitByUI) {
                        // clean out leading and trailing whitespaces
                        split = split.trim();
                        // sanity check - do we have at least one real ascii char?
                        if (split.matches(".*\\w.*")) {
                            dataList.add(split);
                        }
                    }
                } else {

                    rawString = rawString.replace("ui", "");
                    rawString = rawString.trim();
                    dataList.add(rawString);
                }

                // and finally build our map of dataref:value(s)
                Map<String, ArrayList<String>> dataMap = new HashMap<>();

                for (String data : dataList) {

                    ArrayList<String> valueArray = new ArrayList<>();
                    // this time split by whitespace
                    String[] splitIntoStrings = data.split("\\s+");
                    // and put the values array together

                    for (int k = 1; k < splitIntoStrings.length; k++) {
                        valueArray.add(splitIntoStrings[k]);
                    }
                    // finally add dataref plus values array into the map
                    dataMap.put(splitIntoStrings[0], valueArray);
                }

                for (Map.Entry<String, ArrayList<String>> entry : dataMap.entrySet()) {

                    System.out.println(entry.getKey() + " : " + entry.getValue().toString());

                }

            /*
            DrefDataIO io = new DrefDataIO();
            List<String> data = io.getDatarefByActAndCmnd(MainWindowController.actProfile, split[1]);
            header = data.get(2);
            values = data.get(3);

            System.out.println(rawString);
            System.out.println();
            System.out.println("header: " + header + " / values: " + values);
            */

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }


}
