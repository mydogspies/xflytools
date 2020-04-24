package com.mydogspies.xflytools.tests;

import org.apache.commons.lang3.ArrayUtils;

import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class UDPSender {

    public static void main(String args[]) {
        try {
            String host = "192.168.178.40";
            int port = 49000;

            String s;
            Charset charset;
            byte[] header = "DREF+".getBytes(StandardCharsets.UTF_8);
            byte[] value = {0, 0, -128, 63};
            byte[] path = "sim/cockpit2/switches/taxi_light_on".getBytes(StandardCharsets.UTF_8); // so that it's easier to swap paths

            byte[] combine = ArrayUtils.addAll(header, value);
            byte[] dref = ArrayUtils.addAll(combine, path);

            int drefLength = dref.length;
            byte[] padding = new byte[509 - dref.length];
            for (int i =0; i < 509 - dref.length; i++) {
                padding[i] = 0;
            }

            byte[] hexString = ArrayUtils.addAll(dref, padding);

            // Get the internet address of the specified host
            InetAddress address = InetAddress.getByName(host);

            // Initialize a datagram packet with data and address
            DatagramPacket packet = new DatagramPacket(hexString, hexString.length, address, port);

            // Create a datagram socket, send the packet through it, close it.
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            System.out.println("Packet sent length: " + packet.getLength());
            socket.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
