package com.mydogspies.xflytools.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class UDPSend {

    private static final Logger log = LoggerFactory.getLogger(UDPSend.class);

    public static void sendUDPPacket(byte[] datastring) {

        String host = "192.168.178.40";
        int port = 49000;

        // Get the internet address of the specified host
        InetAddress address = null;
        try {
            address = InetAddress.getByName(host);
            log.debug("sendUDPPacket(): Connected to host: " + address);
        } catch (UnknownHostException e) {
            log.error("sendUDPPacket(): No such host: " + e.getMessage());
        }

        // Create a datagram socket, send the packet through it, close it.
        DatagramPacket packet = new DatagramPacket(datastring, datastring.length, address, port);

        try {
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            log.debug("sendUDPPacket(): Packet sent with length " + packet.getLength() + ": " + Arrays.toString(datastring));
            socket.close();
        } catch (IOException e) {
            log.error("sendUDPPacket(): Packet could not be sent: " + e.getMessage());
        }
    }
}
