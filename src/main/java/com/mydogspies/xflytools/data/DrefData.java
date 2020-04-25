package com.mydogspies.xflytools.data;

/**
 * It's our dref data pojo. Not that field "value" is base64 en/decoded.
 * @author Peter Mankowski#
 * @since 0.1.0
 */
public class DrefData {

    private String aircraft;
    private String command;
    private String header;
    private String value; // a base64 encoded byte array
    private String path;

    public DrefData() {
    }

    public DrefData(String aircraft, String command, String header, String value, String path) {
        this.aircraft = aircraft;
        this.command = command;
        this.header = header;
        this.value = value;
        this.path = path;
    }

    public String getAircraft() {
        return aircraft;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public String getHeader() {
        return header;
    }

    public String getValue() { return value; }

    public void setValue(String value) { this.value = value; }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
