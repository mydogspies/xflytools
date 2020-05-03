package com.mydogspies.xflytools.gui;

import java.util.List;

/**
 * The container that contains a list of all our LayoutData objects.
 * @author Peter Mankowski
 * @since 0.4.0
 */
public class LayoutDataContainer {

    private List<LayoutData> layoutData;

    public LayoutDataContainer() {}

    public LayoutDataContainer(List<LayoutData> layoutData) {
        this.layoutData = layoutData;
    }

    public List<LayoutData> getLayoutData() {
        return layoutData;
    }

    public void setLayoutData(List<LayoutData> layoutData) {
        this.layoutData = layoutData;
    }
}
