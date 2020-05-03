package com.mydogspies.xflytools.data;

import java.util.ArrayList;
import java.util.List;

public interface DrefDataDAO {

    public List<DrefData> loadDatabase();
    public List<String> getDatarefByActAndCmnd(String command, String aircraft); // returns {dataref, type}
    public List<DrefData> getDatarefsByAct(String aircraft);
    public String getCmndByDataref(String dataref);
    public String getCmndTypeByDataref(String dataref);

}
