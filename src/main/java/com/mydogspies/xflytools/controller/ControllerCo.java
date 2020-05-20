package com.mydogspies.xflytools.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public interface ControllerCo {

    @FXML
    void initialize();

    @FXML
    void clickButton(ActionEvent event);

    @FXML
    void addToField(ActionEvent event);

    void initElements();

    void disableAll(boolean state);

    void onReset();
}
