package mainPackage;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import mainPackage.serialconnection.SerialConnection;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class App {

    static boolean received;
    static boolean arduinoReady = false;

    public static void main(String[] args) {

        SerialPort port = SerialPort.getCommPort("COM5");
        port.setComPortParameters(9600, 8, 1, 0);
        port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        System.out.println("Open port: " + port.openPort());
        Scanner in = new Scanner(port.getInputStream()); // dit is de input van de arduino
        PrintWriter out = new PrintWriter(port.getOutputStream(), true); // dit is de output wat naar arduino gaat
        port.addDataListener(new SerialPortDataListener() { // deze methode is vgm optioneel, misschien nodig voor een interrupt?

            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
//                String input;
//
//                input = in.nextLine();
//
//                System.out.println("received: " + input);
////                received = true;
//                String input;
//
//                input = in.nextLine();
//
//                System.out.println("received: " + input);
//                received = true;
            }
        });

        while(true) {
            if (in.nextLine().equalsIgnoreCase("ready")) {
                //in.nextLine();
                System.out.println("ready");
                arduinoReady = true; // alleen als de arduino gereed is, dan mogen er pas dingen gebeuren
                break;
            }
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Connection sqlConn = DriverManager.getConnection("jdbc:mysql://145.24.222.230:3306/bank", "administrator", "realestatedatabase");
            Connection sqlConn = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/gooHxZWiSx", "gooHxZWiSx", "r2Gf2810Tu");

        } catch (Exception e) {
            e.printStackTrace();
        }

        Gui gui = new Gui(new SerialConnection(in, out));
        gui.createApp();
//        gui.setSerialConnection(new SerialConnection(in, out));
//        SerialConnection serialConnection = new SerialConnection(in, out);


//        gui.serialConnection.dataOut("withdraw");


    }
}
