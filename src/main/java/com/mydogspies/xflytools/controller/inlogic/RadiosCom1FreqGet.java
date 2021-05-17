package com.mydogspies.xflytools.controller.inlogic;

import com.mydogspies.xflytools.controller.AddCommandMapData;
import com.mydogspies.xflytools.controller.RadiosController;
import com.mydogspies.xflytools.controller.RadiosControllerSingleton;
import com.mydogspies.xflytools.controller.elements.RadioTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

/**
 * The actual implementation of the command pattern for the com 1 radio frequency in default Xplane.
 * This method updates the data in Xflytools.
 *
 * @author Peter Mankowski
 * @see AddCommandMapData
 * @see com.mydogspies.xflytools.controller.module.lamcessna172.Radios
 * @see com.mydogspies.xflytools.controller.ControllerCo
 * @since 0.4.0
 */
public class RadiosCom1FreqGet implements InCommand {

    private static final Logger log = LoggerFactory.getLogger(RadiosCom1FreqGet.class);

    @Override
    public void execute(String command, ArrayList<String> values) {

        final RadiosController controller = (RadiosController) RadiosControllerSingleton.getInstance().getController();
        RadioTextField com1Text = controller.getCom1Text();

        String raw = values.get(0);
        String formatted = raw.substring(0, 3) + "." + raw.substring(3);
        if (!com1Text.getText().equals(formatted)) {
            com1Text.setText(formatted);
            log.trace("execute(): [" + command + "] -> com1 active set to " + formatted);
        }
    }
}
