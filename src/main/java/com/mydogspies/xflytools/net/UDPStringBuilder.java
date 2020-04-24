package com.mydogspies.xflytools.net;

import org.apache.commons.lang3.ArrayUtils;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Builds a byte array which will be the string we send off via UDP
 * @author Peter Mankowski
 * @since 0.1.0
 */
public class UDPStringBuilder {

    public static byte[] makeUDPString(ArrayList<String> drefs) {

        // get values and parse some
        byte[] header = drefs.get(2).getBytes(StandardCharsets.UTF_8);
        byte[] value = DatatypeConverter.parseBase64Binary(drefs.get(3));
        byte[] path = drefs.get(4).getBytes(StandardCharsets.UTF_8); // so that it's easier to swap paths

        // combine it all together into one byte array
        byte[] combine = ArrayUtils.addAll(header, value);
        byte[] dref = ArrayUtils.addAll(combine, path);

        // calculate the padding at the end - total length of the packet data must be 509
        int drefLength = dref.length;
        byte[] padding = new byte[509 - dref.length];
        for (int i = 0; i < 509 - dref.length; i++) {
            padding[i] = 0;
        }

        // append padding and send off
        return ArrayUtils.addAll(dref, padding);
    }
}
