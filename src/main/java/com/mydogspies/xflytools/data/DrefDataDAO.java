package com.mydogspies.xflytools.data;

import java.util.ArrayList;
import java.util.List;

public interface DrefDataDAO {

    public List<DrefData> loadDatabase();
    public String getDatarefByCmnd(String command);
    public String getCmndByDataref(String dataref);
    public String getCmndTypeByDataref(String dataref);

}
