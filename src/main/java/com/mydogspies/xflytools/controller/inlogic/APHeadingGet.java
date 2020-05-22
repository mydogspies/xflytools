package com.mydogspies.xflytools.controller.inlogic;

import com.mydogspies.xflytools.controller.APReadoutsController;
import com.mydogspies.xflytools.controller.APReadoutsControllerSingleton;
import com.mydogspies.xflytools.controller.AddCommandMapData;
import com.mydogspies.xflytools.controller.elements.AutoPilotLabel;
import com.mydogspies.xflytools.controller.module.lamcessna172.APReadouts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

/**
 * The actual implementation of the command pattern for the auto pilot heading in default Xplane.
 * This method updates the data in Xflytools.
 *
 * @author Peter Mankowski
 * @see APReadouts
 * @see AddCommandMapData
 * @see com.mydogspies.xflytools.controller.ControllerCo
 * @since 0.4.0
 */
public class APHeadingGet implements InCommand {

    private static final Logger log = LoggerFactory.getLogger(APHeadingGet.class);

    @Override
    public void execute(String command, ArrayList<String> values) {

        final APReadoutsController controller = (APReadoutsController) APReadoutsControllerSingleton.getInstance().getController();
        AutoPilotLabel apHeading = controller.getApHeading();

        String val = String.format("%03d", Integer.parseInt(values.get(0)));
        if (!apHeading.getText().equals(val)) {
            apHeading.setText(val + (char) 176);
        }
        log.trace("execute(): [" + command + "] -> AP set to heading " + val);

    }
}
