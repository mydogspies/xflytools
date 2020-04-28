package com.mydogspies.xflytools.data;

/**
 * It's our dataref data pojo.
 * @author Peter Mankowski#
 * @since 0.1.0
 */
public class DrefData {

    private String command;
    private String aircraft;
    private String dataref;
    private String type;

    public DrefData() {
    }

    public DrefData(String command, String aircraft, String dataref, String type) {
        this.command = command;
        this.aircraft = aircraft;
        this.dataref = dataref;
        this.type = type;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public String getDataref() {
        return dataref;
    }

    public void setDataref(String dataref) {
        this.dataref = dataref;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}