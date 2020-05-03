package com.mydogspies.xflytools.io;

import com.mydogspies.xflytools.gui.MainWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Takes the raw string from the TCP buffer and splits it up into a map of dataref:valueArray.
 * Divides up datarefs by type and calls the classes responsible for changes to the GUI.
 * @author Peter Mankowski
 * @since 0.1.0
 */
public class DataHandler {

    private static final Logger log = LoggerFactory.getLogger(DataHandler.class);

    private String rawString;

    public DataHandler(String rawString) {
        this.rawString = rawString;
        processData();
    }

    /**
     * Takes the raw string from the TCP buffer and splits it up into a map of dataref:valueArray entries.
     * Map has the format String:ArrayList<String>
     */
    public void processData() {

        Runnable runnable = () -> {

            String datatype = rawString.substring(0, 2); // TODO should be implemented as part fo return map
            String regex = "\\b[u][a-z]{1,2}\\s\\b"; // get the 2 or three first characters with datatype, eg. "ui sim/some/dataref"
            List<String> dataList = new ArrayList<>();

            // find out if we received more than one command string
            int counter = 0;
            Pattern pattern = Pattern.compile(regex);
            Matcher m = pattern.matcher(rawString);
            while (m.find()) {
                counter++;
            }

            // if more than single dataref then break them up
            if (counter > 1) {

                // split by "ui" so that we get individual datarefs
                String[] splitByDtype = rawString.split(regex);
                for (String split : splitByDtype) {
                    // clean out leading and trailing whitespaces
                    split = split.trim();
                    // sanity check - do we have at least one real ascii char?
                    if (split.matches(".*\\w.*")) {
                        dataList.add(split);
                    }
                }
            } else {

                rawString = rawString.replaceAll(regex, "");
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

            log.trace("run(): DataHandler mapped the TCP stream to: " + dataMap);

            // send off the data to the receiving method of the GUI controller
            for (Map.Entry<String, ArrayList<String>> entry : dataMap.entrySet()) {

                MainWindow.controller.receiveFromXplane(entry.getKey(), entry.getValue());
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        log.trace("run(): DataHandler invoked in thread [" + thread + "] with raw string: [" + rawString + "]");
    }


}
