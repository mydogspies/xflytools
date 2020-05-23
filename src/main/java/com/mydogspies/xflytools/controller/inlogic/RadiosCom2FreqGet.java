package com.mydogspies.xflytools.controller.inlogic;

import com.mydogspies.xflytools.controller.AddCommandMapData;
import com.mydogspies.xflytools.controller.RadiosController;
import com.mydogspies.xflytools.controller.RadiosControllerSingleton;
import com.mydogspies.xflytools.controller.elements.RadioTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * The actual implementation of the command pattern for the com 2 radio frequency in default Xplane.
 * This method updates the data in Xflytools.
 *
 * @author Peter Mankowski
 * @see AddCommandMapData
 * @see com.mydogspies.xflytools.controller.module.lamcessna172.Radios
 * @see com.mydogspies.xflytools.controller.ControllerCo
 * @since 0.4.0
 */
public class RadiosCom2FreqGet implements InCommand {

    private static final Logger log = LoggerFactory.getLogger(RadiosCom2FreqGet.class);

    @Override
    public void execute(String command, ArrayList<String> values) {

        final RadiosController controller = (RadiosController) RadiosControllerSingleton.getInstance().getController();
        RadioTextField com2Text = controller.getCom1Text();

        String raw2 = values.get(0);
        String formatted2 = raw2.substring(0, 3) + "." + raw2.substring(3);
        if (!com2Text.getText().equals(formatted2)) {
            com2Text.setText(formatted2);
            log.trace("execute(): [" + command + "] -> com2 active set to " + formatted2);
        }
    }
}
