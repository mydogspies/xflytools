package com.mydogspies.xflytools.io;

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
    private List<DataObserverIO> observers = new ArrayList<>();
    private String rawString;

    public DataHandler() {

        log.debug("DataHandler(): New DataHandler object instantiated: " + this);
    }

    /**
     * Takes the raw string from the TCP buffer and splits it up into a map of dataref:valueArray entries.
     * When done all the magic it sends it off to the observers.
     * @param rawString takes the raw string as sent from Xplane.
     */
    public void processWithDataHandler(String rawString) {

        this.rawString = rawString;

        Runnable runnable = () -> {
            sendToObservers(makedatarefMap(processStringIntoDatarefs()));
        };

        Thread thread = new Thread(runnable);
        thread.start();
        log.trace("run(): DataHandler [" + this + "] invoked in thread [" + thread + "] with raw string: [" + rawString + "]");
    }

    /**
     * Process the raw string by first breaking it up in individual datarefs and then putting them into a single list.
     * @return a list of datarefs
     *
     */
    private List<String> processStringIntoDatarefs() {

        List<String> dataList = new ArrayList<>();

        // get the 2 or three first characters with datatype, eg. "ui" in "ui sim/some/dataref"
        // and then find out if we received more than one command string.
        String regex = "\\b[u][a-z]{1,2}\\s\\b";
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

            // in case of a single dref only string we just need some housekeeping
            rawString = rawString.replaceAll(regex, "");
            rawString = rawString.trim();
            dataList.add(rawString);
        }

        return dataList;
    }

    /**
     * Takes the string of raw datarefs and splits apart from the values after which it builds a new
     * hash map with the structure: dataref:value(s) -> (string:arraylist)
     * @param dataList the list of raw dataref commands processed by processStringIntoDatarefs().
     */
    private Map<String, ArrayList<String>> makedatarefMap(List<String> dataList) {

        // Then build our map of dataref:value(s)
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

        log.trace("run(): DataHandler mapped the TCP stream to map: " + dataMap);

        return dataMap;
    }

    /**
     * Takes the dref data map produced by makeDatarefMap and sends it of to the observer methods.
     * @param dataMap the final dref map in format dref:value(s)
     */
    private void sendToObservers(Map<String, ArrayList<String>> dataMap) {

        for (Map.Entry<String, ArrayList<String>> entry : dataMap.entrySet()) {

            // send off to our observers
            setData(entry.getKey(), entry.getValue());
        }
    }

    /* The observable methods */

    public void addObserver(DataObserverIO dataio) {

        this.observers.add(dataio);
    }

    public void removeObserver(DataObserverIO dataio) {

        this.observers.remove(dataio);
    }

    /**
     * Send the the dref data and its values to the observers (GUI modules).
     * @param dref the dref string
     * @param value the value(s) of the dref string as a list
     */
    public void setData(String dref, ArrayList<String> value) {

        DataObserverPacket packet = new DataObserverPacket(dref, value);
        for (DataObserverIO dataio : this.observers) {
            dataio.updateFromXplane(packet);
        }
    }
}
