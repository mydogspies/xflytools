package com.mydogspies.xflytools.data;

import java.util.List;

/**
 * The interface for classes related to the window layout json database.
 * @author Peter Mankowski
 * @since 0.4.0
 */
public interface LayoutDataDAO {

    public String getLayout(String profile);
    public List<LayoutData> loadLayoutDatabase();
    public List<String> getAllProfileNames();

}
