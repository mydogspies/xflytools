package com.mydogspies.xflytools.data;

/**
 * It's our dataref data pojo.
 * @author Peter Mankowski#
 * @since 0.1.0
 */
public class DrefData {

    private String command;
    private String typeOfCommand;
    private String dataref;
    private String type;
    private String io;

    public DrefData() {
    }

    public DrefData(String command, String typeOfCommand, String dataref, String type, String io) {
        this.command = command;
        this.typeOfCommand = typeOfCommand;
        this.dataref = dataref;
        this.type = type;
        this.io = io;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
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

    public String getIo() {
        return io;
    }

    public void setIo(String io) {
        this.io = io;
    }

    public String getTypeOfCommand() {
        return typeOfCommand;
    }

    public void setTypeOfCommand(String typeOfCommand) {
        this.typeOfCommand = typeOfCommand;
    }
}
