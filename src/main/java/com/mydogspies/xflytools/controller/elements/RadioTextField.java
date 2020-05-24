package com.mydogspies.xflytools.controller.elements;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;

import java.text.DecimalFormat;

/**
 * A custom field so that we can easily make custom
 * css labels and differentiate between types without hacking stuff
 * @author Peter Mankowski
 * @since 0.2.0
 */
public class RadioTextField extends TextField {

    public RadioTextField() {

        super();
        // addMouseScrolling(this); // TODO implement this!
    }

    public void addMouseScrolling(Node node) {

        node.setOnScroll((ScrollEvent event) -> {

            String newFreq = "";

            double value = Double.parseDouble(this.getText());

            int integer = (int) value;
            double fraction = value - integer;

            // integer part based on mouse position
            if (event.getX() < 35) {
                if (event.getDeltaY() < 0) {
                    integer -= 1;
                    if (integer < 100) { integer = 100; }
                } else {
                    integer += 1;
                    if (integer > 140) { integer = 140; }
                }
                // fractional position ditto
            } else {
                if (event.getDeltaY() < 0) {
                    fraction -= .005;
                } else {
                    fraction += .005;
                }
            }

            value = integer + fraction;

            DecimalFormat df = new DecimalFormat("0.000");
            String result = df.format(value);

            this.setText(result);
        });
    }

}
