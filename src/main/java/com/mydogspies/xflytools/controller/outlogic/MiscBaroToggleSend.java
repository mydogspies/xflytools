package com.mydogspies.xflytools.controller.outlogic;

import com.mydogspies.xflytools.controller.AddCommandMapData;
import com.mydogspies.xflytools.controller.MiscController;
import com.mydogspies.xflytools.controller.MiscControllerSingleton;
import com.mydogspies.xflytools.controller.module.lamcessna172.APButtons;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The actual implementation of the command pattern for the barometer unit setting in Xflytools.
 * NOTE! This method does not change any state in Xplane itself!
 *
 * @author Peter Mankowski
 * @see APButtons
 * @see AddCommandMapData
 * @see ControllerCo
 * @since 0.4.0
 */
public class MiscBaroToggleSend implements OutCommand {

    private static final Logger log = LoggerFactory.getLogger(MiscBaroToggleSend.class);

    @Override
    public void execute() {

        final MiscController controller = (MiscController) MiscControllerSingleton.getInstance().getController();
        boolean baroSettingInHg = controller.isBaroSettingInHg();

        TextField baroField = controller.getBaroField();
        ToggleButton pressType = controller.getPressType();

        if (baroSettingInHg) {
            controller.setBaroSettingInHg(false);
            pressType.setText("mb");
            baroField.setText(controller.convertToMillibar(baroField.getText()));
            log.trace("execute(): Barometric display in Xflytools set to millibar.");
        } else {
            controller.setBaroSettingInHg(true);
            pressType.setText("inHg");
            baroField.setText(controller.formatToInHG(controller.convertToInchesHG(baroField.getText())));
            log.trace("execute(): Barometric display in Xflytools set to inches Hg.");
        }
    }
}
