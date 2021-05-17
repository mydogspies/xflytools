package com.mydogspies.xflytools.controller.inlogic;

import com.mydogspies.xflytools.controller.AddCommandMapData;
import com.mydogspies.xflytools.controller.RadiosController;
import com.mydogspies.xflytools.controller.RadiosControllerSingleton;
import com.mydogspies.xflytools.controller.elements.RadioTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * The actual implementation of the command pattern for the com 2 standby radio frequency in default Xplane.
 * This method updates the data in Xflytools.
 *
 * @author Peter Mankowski
 * @see AddCommandMapData
 * @see com.mydogspies.xflytools.controller.module.lamcessna172.Radios
 * @see com.mydogspies.xflytools.controller.ControllerCo
 * @since 0.4.0
 */
public class RadiosCom2StbyFreqGet implements InCommand {

    private static final Logger log = LoggerFactory.getLogger(RadiosCom2StbyFreqGet.class);

    @Override
    public void execute(String command, ArrayList<String> values) {

        final RadiosController controller = (RadiosController) RadiosControllerSingleton.getInstance().getController();
        RadioTextField com2Stby = controller.getCom2Stby();

        String raw = values.get(0);
        String formatted = raw.substring(0, 3) + "." + raw.substring(3);
        if (!com2Stby.getText().equals(formatted)) {
            com2Stby.setText(formatted);
            log.trace("updateFromXplane(): [" + command + "] -> com2 stand-by set to " + formatted);
        }
    }
}
