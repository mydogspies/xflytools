package com.mydogspies.xflytools.tests;

import org.apache.commons.lang3.ArrayUtils;

import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class UDPSender {

    private static char[] hex_table = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'
    };

    public static void main(String args[]) {
        try {
            String host = "192.168.178.40";
            int port = 49000;

            String s;
            Charset charset;
            byte[] header = "DREF+".getBytes(StandardCharsets.UTF_8);
            // byte[] value = {0, 0, 0, 0};
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

            // sanity check
            /*
            for (int i = 0; i < hexString.length; i++) {
                System.out.print(hexString[i] + " ");
            }
             */

            // Initialize a datagram packet with data and address
            // DatagramPacket packet = new DatagramPacket(message, message.length, address, port);
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

    private static byte[] toHexByte(String str, int offset, int length)
    {
        byte[] data = new byte[(length - offset) * 2];
        int end = offset+length;

        for (int i = offset; i < end; i++)
        {

            // convert to hex
            char ch = str.charAt(i);
            String hexCode=String.format("%H", ch);

            // convert to byte
            int firstDigit = toDigit(hexCode.charAt(0));
            int secondDigit = toDigit(hexCode.charAt(1));

            data[i] = (byte) ((firstDigit << 4) + secondDigit);
        }
        return data;
    }

    private static int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if(digit == -1) {
            throw new IllegalArgumentException(
                    "Invalid Hexadecimal Character: "+ hexChar);
        }
        return digit;
    }

}
