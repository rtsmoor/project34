package mainPackage.serialconnection;
import gnu.io.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Vector;

public class SerialConnection implements Network_iface{
    // set the speed of the serial port
    public static int speed = 115200;
    private static Network network;

    private static boolean resend_active = false;

    public static void main(String[] args) {
        network = new Network(0, new SerialConnection(), 255);

        // reading the speed if
        if (args.length > 0) {
            try {
                speed = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("the speed must be an integer\n");
                System.exit(1);
            }
        }

        // initializing reader from command line
        int i, inp_num = 0;
        String input;
        BufferedReader in_stream = new BufferedReader(new InputStreamReader(
                System.in));

        // getting a list of the available serial ports
        Vector<String> ports = network.getPortList();
    }

    @Override
    public void writeLog(int id, String text) {

    }

    @Override
    public void parseInput(int id, int numBytes, int[] message) {

    }

    @Override
    public void networkDisconnected(int id) {

    }
}