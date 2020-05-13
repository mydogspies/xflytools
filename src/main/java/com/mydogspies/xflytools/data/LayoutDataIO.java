package com.mydogspies.xflytools.data;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mydogspies.xflytools.Initialize;
import com.mydogspies.xflytools.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The methods for working on the window layout json database.
 * @author Peter Mankowski
 * @since 0.4.0
 */
public class LayoutDataIO implements LayoutDataDAO {

    private static final Logger log = LoggerFactory.getLogger(LayoutDataIO.class);
    private final String jsonfile = readFileAsStream("com/mydogspies/xflytools/data/layout.json");

    @Override
    public String getLayout(String profile) {

        String result = "";

        for (LayoutData data : Main.layout) {
            if (data.getActProfile().equals(profile)) {
                result = data.getPath();
            }
        }

        return result;
    }

    @Override
    public List<String> getAllProfileNames() {

        List<String> nameList = new ArrayList<>();

        for (LayoutData data : Main.layout) {
            nameList.add(data.getActProfile());
        }

        return nameList;
    }

    @Override
    public List<LayoutData> loadLayoutDatabase() {
        List<LayoutData> data = null;

        try {
            LayoutDataContainer layoutList = Initialize.mapper.readValue(jsonfile, LayoutDataContainer.class);
            data = layoutList.getLayoutData();
            log.debug("getJsonData(): Data object has been successfully read from json file: " + data);
        } catch (JsonParseException e) {
            log.error("getJsonData(): Json parse (Jackson) failed.");
        } catch (JsonMappingException e) {
            log.error("getJsonData(): Json mapper (Jackson) failed.");
        } catch (IOException e) {
            log.error("getJsonData(): Writing to json failed.");
        }

        return data;
    }

    /**
     * Reads a file as a stream and returns the content as a string.
     * @param pathToFile path to file
     * @return file content as a string
     */
    public String readFileAsStream(String pathToFile) {

        InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream(pathToFile);

        return new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining());
    }
}
