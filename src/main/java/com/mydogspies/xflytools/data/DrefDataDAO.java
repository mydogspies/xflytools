package com.mydogspies.xflytools.data;

import java.util.ArrayList;

public interface DrefDataDAO {

    public ArrayList<String> getDataRefsByCommand(String command, String aircraft);

}
