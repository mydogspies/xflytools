package com.mydogspies.xflytools.controller.elements;

import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;

/**
 * A custom button so that we can easily make custom
 * css labels and differentiate between types without hacking stuff
 * @author Peter Mankowski
 * @since 0.3.0
 */
public class AutoPilotButton extends ToggleButton {

    public boolean toggleable;

    public AutoPilotButton() {

        super();
        this.toggleable = false;
    }

    @Override
    public void fire() {

        // if flag set then button can not be toggled back to inselected
        if (!isDisabled() && !toggleable) {

            if (!isSelected()) {
                setSelected(true);
            }
            fireEvent(new ActionEvent());

            // otherwise the standard behaviour
        } else if (!isDisabled()) {

            setSelected(!isSelected());
            fireEvent(new ActionEvent());
        }
    }

}
