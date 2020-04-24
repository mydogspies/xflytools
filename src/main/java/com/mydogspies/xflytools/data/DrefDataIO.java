package com.mydogspies.xflytools.data;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mydogspies.xflytools.Initialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all the necessary methods to manipulate the json file with our datarefs
 * @author Peter Mankowski
 * @since 0.1.0
 */
public class DrefDataIO implements DrefDataDAO{

    private static final Logger log = LoggerFactory.getLogger(DrefDataIO.class);
    
    @Override
    public void getDataRefsByCommand(String command, String aircraft) {

    }

    /**
     * Reads the json file with datarefs and returns all the contents as a single DrefData object.
     * @return a DrefData object. otherwise NULL.
     */
    private List<DrefData> getJsonData() {

        String pathToJson = "";
        List<DrefData> content = new ArrayList<>();

        //DrefData content = null;

        File jsonfile = readFile(pathToJson);

        try {
            content = Initialize.mapper.readValue(jsonfile, new TypeReference<List<DrefData>>(){});
            log.info("getJsonData(): Data object has been successfully read from json file: " + content);
        } catch (JsonParseException e) {
            log.error("getJsonData(): Json parse (Jackson) failed.");
        } catch (JsonMappingException e) {
            log.error("getJsonData(): Json mapper (Jackson) failed.");
        } catch (IOException e) {
            log.error("getJsonData(): Writing to json failed.");
        }

        return content;
    }

    private void saveJsonData(List<DrefData> data) {

        ObjectWriter writer = Initialize.mapper.writer(new DefaultPrettyPrinter());

        try {
            writer.writeValue(new File("D:/cp/dataTwo.json"), data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Reads a file from the disc with either absolute path or relative path from source root.
     * Eg. /src/some/folder/data.json
     * @param filePath path to jason file
     * @return file object, or of not existent then NULL
     */
    public File readFile(String filePath) {

        log.trace("readFile(): incoming path (filePath): " + filePath);

        File file = Paths.get(filePath).toFile();

        if (file.exists()) {

            log.info("readFile(): Returned successfully file " + filePath);
            return file;
        }

        log.warn("readFile(): Failed to read file in path " + filePath + " (Does file or location exist?)");
        return null;
    }
}
