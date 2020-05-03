package com.mydogspies.xflytools.gui.module;

import com.mydogspies.xflytools.gui.MainWindow;
import com.mydogspies.xflytools.gui.elements.RadioTextField;
import com.mydogspies.xflytools.gui.elements.SwapButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class DefaultRadios {

    private static final Logger log = LoggerFactory.getLogger(DefaultRadios.class);

    @FXML
    private Pane RadioPane;
    @FXML
    private GridPane radioGrid;

    private RadioTextField transponder;
    private RadioTextField com1Text;
    private RadioTextField com2Text;
    private RadioTextField nav1Text;
    private RadioTextField nav2Text;
    private RadioTextField com1Stby;
    private RadioTextField com2Stby;
    private RadioTextField nav1Stby;
    private RadioTextField nav2Stby;
    private SwapButton com1swap;
    private SwapButton com2swap;
    private SwapButton nav1swap;
    private SwapButton nav2swap;

    /* INIT */

    @FXML
    void initialize() {

        initElems();

    }

    public void updateData(String command, ArrayList<String> value) {

        switch (command) {

            case "com1_freq":
                String raw = value.get(0);
                String formatted = raw.substring(0, 3) + "." + raw.substring(3);
                if (!com1Text.getText().equals(formatted)) {
                    com1Text.setText(formatted);
                    log.trace("getFromXplane(): [" + command + "] -> com1 active set to " + formatted);
                }
                break;

            case "com2_freq":
                String raw2 = value.get(0);
                String formatted2 = raw2.substring(0, 3) + "." + raw2.substring(3);
                if (!com2Text.getText().equals(formatted2)) {
                    com2Text.setText(formatted2);
                    log.trace("getFromXplane(): [" + command + "] -> com2 active set to " + formatted2);
                }
                break;

            case "nav1_freq":
                String raw3 = value.get(0);
                String formatted3 = raw3.substring(0, 3) + "." + raw3.substring(3);
                if (!nav1Text.getText().equals(formatted3)) {
                    nav1Text.setText(formatted3);
                    log.trace("getFromXplane(): [" + command + "] -> nav1 active set to " + formatted3);
                }
                break;

            case "nav2_freq":
                String raw4 = value.get(0);
                String formatted4 = raw4.substring(0, 3) + "." + raw4.substring(3);
                if (!nav2Text.getText().equals(formatted4)) {
                    nav2Text.setText(formatted4);
                    log.trace("getFromXplane(): [" + command + "] -> nav2 active set to " + value);
                }
                break;

            case "transponder_code":
                if (!transponder.getText().equals(value.get(0))) {
                    transponder.setText(value.get(0));
                    log.trace("getFromXplane(): [" + command + "] -> transponder set to " + value);
                }
                break;

            case "com1_stdby_freq":
                String raw5 = value.get(0);
                String formatted5 = raw5.substring(0, 3) + "." + raw5.substring(3);
                if (!com1Stby.getText().equals(formatted5)) {
                    com1Stby.setText(formatted5);
                    log.trace("getFromXplane(): [" + command + "] -> com1 stand-by set to " + formatted5);
                }
                break;

            case "com2_stdby_freq":
                String raw6 = value.get(0);
                String formatted6 = raw6.substring(0, 3) + "." + raw6.substring(3);
                if (!com2Stby.getText().equals(formatted6)) {
                    com2Stby.setText(formatted6);
                    log.trace("getFromXplane(): [" + command + "] -> com2 stand-by set to " + formatted6);
                }
                break;

            case "nav1_stdby_freq":
                String raw7 = value.get(0);
                String formatted7 = raw7.substring(0, 3) + "." + raw7.substring(3);
                if (!nav1Stby.getText().equals(formatted7)) {
                    nav1Stby.setText(formatted7);
                    log.trace("getFromXplane(): [" + command + "] -> nav1 stand-by set to " + formatted7);
                }
                break;

            case "nav2_stdby_freq":
                String raw8 = value.get(0);
                String formatted8 = raw8.substring(0, 3) + "." + raw8.substring(3);
                if (!nav2Stby.getText().equals(formatted8)) {
                    nav2Stby.setText(formatted8);
                    log.trace("getFromXplane(): [" + command + "] -> nav2 stand-by set to " + formatted8);
                }
                break;

        }
    }

    @FXML
    private void addToField(ActionEvent event) {

        log.debug("addToField(): ActionEvent called: " + event);

        RadioTextField b = (RadioTextField) event.getSource();
        String field_id = b.getId();

        switch (field_id) {

            case "com1text":
                String val = com1Text.getText();
                if (val.matches("[1][0-9]{2}\\.[0-9]{3}")) {
                    MainWindow.controller.sendToXplane("set", "com1_freq", val.replace(".", ""));
                    log.trace("addToField(): Com1 active set to " + val + " in Xplane.");
                }
                //
                break;

            case "com1stby":
                String val2 = com1Stby.getText();
                if (val2.matches("[1][0-9]{2}\\.[0-9]{3}")) {
                    MainWindow.controller.sendToXplane("set", "com1_stdby_freq", val2.replace(".", ""));
                    log.trace("addToField(): Com1 standby set to " + val2 + " in Xplane.");
                }
                //
                break;

            case "com2text":
                String val3 = com2Text.getText();
                if (val3.matches("[1][0-9]{2}\\.[0-9]{3}")) {
                    MainWindow.controller.sendToXplane("set", "com2_freq", val3.replace(".", ""));
                    log.trace("addToField(): Com2 active set to " + val3 + " in Xplane.");
                }
                //
                break;

            case "com2stby":
                String val4 = com2Stby.getText();
                if (val4.matches("[1][0-9]{2}\\.[0-9]{3}")) {
                    MainWindow.controller.sendToXplane("set", "com2_stdby_freq", val4.replace(".", ""));
                    log.trace("addToField(): Com2 standby set to " + val4 + " in Xplane.");
                }
                //
                break;

            case "nav1text":
                String val5 = nav1Text.getText();
                if (val5.matches("[1][0-9]{2}\\.[0-9]{2}")) {
                    MainWindow.controller.sendToXplane("set", "nav1_freq", val5.replace(".", ""));
                    log.trace("addToField(): Nav1 active set to " + val5 + " in Xplane.");
                }
                //
                break;

            case "nav1stby":
                String val6 = nav1Stby.getText();
                if (val6.matches("[1][0-9]{2}\\.[0-9]{2}")) {
                    MainWindow.controller.sendToXplane("set", "nav1_stdby_freq", val6.replace(".", ""));
                    log.trace("addToField(): Nav1 standby set to " + val6 + " in Xplane.");
                }
                //
                break;

            case "nav2text":
                String val7 = nav2Text.getText();
                if (val7.matches("[1][0-9]{2}\\.[0-9]{2}")) {
                    MainWindow.controller.sendToXplane("set", "nav2_freq", val7.replace(".", ""));
                    log.trace("addToField(): Nav2 active set to " + val7 + " in Xplane.");
                }
                //
                break;

            case "nav2stby":
                String val8 = nav2Stby.getText();
                if (val8.matches("[1][0-9]{2}\\.[0-9]{2}")) {
                    MainWindow.controller.sendToXplane("set", "nav2_stdby_freq", val8.replace(".", ""));
                    log.trace("addToField(): Nav2 standby set to " + val8 + " in Xplane.");
                }
                //
                break;

            case "transponderCode":
                String val9 = transponder.getText();
                if (val9.matches("[0-9]{4}")) {
                    MainWindow.controller.sendToXplane("set", "transponder_code", val9);
                    log.trace("addToField(): Transponder set to " + val9 + " in Xplane.");
                }
                //
                break;
        }
    }

    /**
     * Swaps between two com or nav radios using the native swap function within Xplane
     * @param event upon clicked swap button
     */
    @FXML
    private void toggleRadio(ActionEvent event) {

        log.debug("toggleRadio(): ActionEvent called: " + event);

        SwapButton b = (SwapButton) event.getSource();
        String button_id = b.getId();

        switch (button_id) {

            case "com1swap":
                MainWindow.controller.sendToXplane("cmd", "com1_flip", "");
                log.trace("toggleRadio(): Com1 frequencies flipped.");
                break;

            case "com2swap":
                MainWindow.controller.sendToXplane("cmd", "com2_flip", "");
                log.trace("toggleRadio(): Com1 frequencies flipped.");
                break;

            case "nav1swap":
                MainWindow.controller.sendToXplane("cmd", "nav1_flip", "");
                log.trace("toggleRadio(): Com1 frequencies flipped.");
                break;

            case "nav2swap":
                MainWindow.controller.sendToXplane("cmd", "nav2_flip", "");
                log.trace("toggleRadio(): Com1 frequencies flipped.");
                break;
        }
    }

    private void initElems() {

        /* SWAP BUTTONS */

        com1swap = new SwapButton();
        com1swap.setId("com1swap");
        com1swap.setText("<>");
        com1swap.getStyleClass().add("swap-button");
        com1swap.setOnAction(this::toggleRadio);
        radioGrid.add(com1swap,2, 1);

        com2swap = new SwapButton();
        com2swap.setId("com2swap");
        com2swap.setText("<>");
        com2swap.getStyleClass().add("swap-button");
        com2swap.setOnAction(this::toggleRadio);
        radioGrid.add(com2swap, 2, 2);

        nav1swap = new SwapButton();
        nav1swap.setId("nav1swap");
        nav1swap.setText("<>");
        nav1swap.getStyleClass().add("swap-button");
        nav1swap.setOnAction(this::toggleRadio);
        radioGrid.add(nav1swap, 2, 3);

        nav2swap = new SwapButton();
        nav2swap.setId("nav2swap");
        nav2swap.setText("<>");
        nav2swap.getStyleClass().add("swap-button");
        nav2swap.setOnAction(this::toggleRadio);
        radioGrid.add(nav2swap,2, 4);

        /* RADIO FIELDS */

        com1Stby = new RadioTextField();
        com1Stby.setId("com1stby");
        com1Stby.setText("");
        com1Stby.getStyleClass().addAll("radio-field");
        com1Stby.setOnAction(this::addToField);
        radioGrid.add(com1Stby, 3, 1);

        com2Stby = new RadioTextField();
        com2Stby.setId("com2stby");
        com2Stby.setText("");
        com2Stby.getStyleClass().addAll("radio-field");
        com2Stby.setOnAction(this::addToField);
        radioGrid.add(com2Stby, 3, 2);

        nav1Stby = new RadioTextField();
        nav1Stby.setId("nav1stby");
        nav1Stby.setText("");
        nav1Stby.getStyleClass().addAll("radio-field");
        nav1Stby.setOnAction(this::addToField);
        radioGrid.add(nav1Stby,3, 3);

        nav2Stby = new RadioTextField();
        nav2Stby.setId("nav2stby");
        nav1Stby.setText("");
        nav2Stby.getStyleClass().addAll("radio-field");
        nav2Stby.setOnAction(this::addToField);
        radioGrid.add(nav2Stby,3, 4);

        /* RADIO LABELS */

        com1Text = new RadioTextField();
        com1Text.setId("com1text");
        com1Text.setText("");
        com1Text.getStyleClass().addAll("radio-label");
        com1Text.setOnAction(this::addToField);
        radioGrid.add(com1Text, 1, 1);

        com2Text = new RadioTextField();
        com2Text.setId("com2text");
        com2Text.setText("");
        com2Text.getStyleClass().addAll("radio-label");
        com2Text.setOnAction(this::addToField);
        radioGrid.add(com2Text, 1, 2);

        nav1Text = new RadioTextField();
        nav1Text.setId("nav1text");
        nav1Text.setText("");
        nav1Text.getStyleClass().addAll("radio-label");
        nav1Text.setOnAction(this::addToField);
        radioGrid.add(nav1Text, 1, 3);

        nav2Text = new RadioTextField();
        nav2Text.setId("nav2text");
        nav2Text.setText("");
        nav2Text.getStyleClass().addAll("radio-label");
        nav2Text.setOnAction(this::addToField);
        radioGrid.add(nav2Text, 1, 4);

        transponder = new RadioTextField();
        transponder.setId("transponderCode");
        transponder.setText("");
        transponder.getStyleClass().addAll("radio-label");
        transponder.setOnAction(this::addToField);
        radioGrid.add(transponder, 1, 5);
    }



}
