package com.mydogspies.xflytools.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.ArrayList;

public interface ControllerCo {

    @FXML
    void initialize();

    @FXML
    void clickButton(ActionEvent event);

    @FXML
    void addToField(ActionEvent event);

    void updateData(String command, ArrayList<String> value);

    void initElements();

    void disableAll(boolean state);

    void onReset();
}
