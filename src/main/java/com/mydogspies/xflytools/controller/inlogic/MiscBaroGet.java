package com.mydogspies.xflytools.controller.inlogic;

import com.mydogspies.xflytools.controller.AddCommandMapData;
import com.mydogspies.xflytools.controller.MiscController;
import com.mydogspies.xflytools.controller.MiscControllerSingleton;
import com.mydogspies.xflytools.controller.module.lamcessna172.APButtons;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * The actual implementation of the command pattern for the barometer display value in default Xplane.
 * This method sends data to Xplane.
 *
 * @author Peter Mankowski
 * @see APButtons
 * @see AddCommandMapData
 * @see com.mydogspies.xflytools.controller.ControllerCo
 * @since 0.4.0
 */
public class MiscBaroGet implements InCommand {

    private static final Logger log = LoggerFactory.getLogger(MiscBaroGet.class);

    @Override
    public void execute(String command, ArrayList<String> values) {

        final MiscController controller = (MiscController) MiscControllerSingleton.getInstance().getController();
        boolean baroSettingInHg = controller.isBaroSettingInHg();
        TextField baroField = controller.getBaroField();

        if (baroSettingInHg) {
            String val = values.get(0);
            baroField.setText(controller.formatToInHG(val));
            log.trace("updateFromXplane(): [" + command + "] -> Barometer set to " + val + " inHg.");
        } else {
            baroField.setText(controller.convertToMillibar(values.get(0)));
        }
    }
}
