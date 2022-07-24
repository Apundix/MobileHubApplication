package com.evan.chattest2;

import java.io.*;
import java.net.*;

public class WakeOnLan {

    //UDP Packet uses a port of 9 - User Datagram protocol
    public static final int PORT = 9;


    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Usage: java WakeOnLan <broadcast-ip> <mac-address>");
            System.out.println("Example: java WakeOnLan 192.168.0.255 00:0D:61:08:22:4A");
            System.out.println("Example: java WakeOnLan 192.168.0.255 00-0D-61-08-22-4A");
            System.exit(1);
        }

        String ipStr = args[0];
        String macStr = args[1];

        try {
            //The UDP packet must be 16 times longer than the byte,
            // representation of the MAC address, plus an extra 6 for the header.
            byte[] macBytes = getMacBytes(macStr);
            byte[] bytes = new byte[6 + 16 * macBytes.length];

            //A MAC address can be specified as a string of hexadecimal digits.
            //So -  00:0D:61:08:22:4A, so can be represented using just 6 bytes.


            //The next bytes of the packet are filled with 0xff -
            // No clue why it's like this
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) 0xff;
            }

            //The next bytes are the MAC address of the target device
            //Each subsequent set of bytes is also filled with
            // MAC address of the target computer
            for (int i = 6; i < bytes.length; i += macBytes.length) {
                System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
            }


            //I need to play with this a bit more, documentation implies that
            //The magic packet will send a broadcast address
            InetAddress address = InetAddress.getByName(ipStr);
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, PORT);
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            socket.close();

            System.out.println("Wake-on-LAN packet sent.");
        }
        catch (Exception e) {
            System.out.println("Failed to send Wake-on-LAN packet: + e");
            System.exit(1);
        }

    }

    //Throwing arguments of invalid inputs

    private static byte[] getMacBytes(String macStr) throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        String[] hex = macStr.split("(\\:|\\-)");
        if (hex.length != 6) {
            throw new IllegalArgumentException("Invalid MAC address.");
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hex digit in MAC address.");
        }
        return bytes;
    }


}

