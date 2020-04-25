package com.mydogspies.xflytools.data;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mydogspies.xflytools.Initialize;
import com.mydogspies.xflytools.Main;
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
    private final File jsonfile = readFile("src/main/java/com/mydogspies/xflytools/data/drefData.json");

    /**
     * Takes aircraft type and command and looks for the corresponding values in the datarefs database.
     * NOTE: The Value field must be base64 decoded into a byte array!
     * @param command the name of the datarefs command
     * @param aircraft type of aircraft
     * @return an array of datarefs values in order -> {aircraft, command, header, value, path}
     */
    @Override
    public ArrayList<String> getDataRefsByCommand(String command, String aircraft) {

        ArrayList<String> datarefs = new ArrayList<>();

        for (DrefData data : Main.database) {

            if (data.getCommand().equals(command) && data.getAircraft().equals(aircraft)) {

                datarefs.add(aircraft);
                datarefs.add(command);
                datarefs.add(data.getHeader());
                datarefs.add(data.getValue());
                datarefs.add(data.getPath());
            }

        }

        return datarefs;
    }

    /**
     * Reads the json file with datarefs and returns all the contents as a list of DrefData object.
     * @return a DrefData object. otherwise NULL.
     */
    public List<DrefData> getJsonData() {

        List<DrefData> data = null;

        try {
            DrefDataContainer dreflist = Initialize.mapper.readValue(jsonfile, DrefDataContainer.class);
            data = dreflist.getDrefdata();
            log.info("getJsonData(): Data object has been successfully read from json file: " + data);
        } catch (JsonParseException e) {
            log.error("getJsonData(): Json parse (Jackson) failed.");
        } catch (JsonMappingException e) {
            log.error("getJsonData(): Json mapper (Jackson) failed.");
        } catch (IOException e) {
            log.error("getJsonData(): Writing to json failed.");
        }

        return data;
    }

    private static void saveJsonData(List<DrefData> data) {

        // TODO implement method
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
