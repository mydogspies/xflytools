package com.mydogspies.xflytools.controller.inlogic;

import java.util.ArrayList;

public interface InCommand {

    void execute(String command, ArrayList<String> values);
}
