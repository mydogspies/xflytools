package com.mydogspies.xflytools.data;

import java.lang.reflect.Array;

public class DrefPojo {

    private String aircraft;
    private String command;
    private String header;
    private Array value;
    private String path;

    public DrefPojo() {
    }

    public DrefPojo(String aircraft, String command,  String header, Array value, String path) {
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

    public void setHeader(String header) {
        this.header = header;
    }

    public Array getValue() {
        return value;
    }

    public void setValue(Array value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
