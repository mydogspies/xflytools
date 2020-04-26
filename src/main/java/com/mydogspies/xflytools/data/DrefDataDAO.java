package com.mydogspies.xflytools.data;

import java.util.ArrayList;
import java.util.List;

public interface DrefDataDAO {

    public List<DrefData> loadDatabase();
    public ArrayList<String> getDataref(String command, String aircraft);

}
