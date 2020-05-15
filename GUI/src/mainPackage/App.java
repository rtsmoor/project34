package mainPackage;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import mainPackage.serialconnection.SerialConnection;

import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class App {

    static boolean received;
    static boolean arduinoReady = false;

    public static void main(String[] args) {

        SerialPort port = SerialPort.getCommPort("COM5"); // edit dit als je een andere com port gebruikt //todo maak een for loop om te checken op de juiste com port
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
            try {
                if (in.nextLine().equalsIgnoreCase("ready")) {
                    //in.nextLine();
                    System.out.println("ready");
                    arduinoReady = true; // alleen als de arduino gereed is, dan mogen er pas dingen gebeuren
                    break;
                }
            } catch (NoSuchElementException e) {
                System.out.println("ERROR connection to the arduino");
                Scanner s = new Scanner(System.in);
                System.out.print("GUI toch starten? y/n:");
                String input = s.nextLine();
                if("yes".equalsIgnoreCase(input) || "y".equalsIgnoreCase(input)) break;
                if("no".equalsIgnoreCase(input) || "n".equalsIgnoreCase(input)) return;
            }
        }

        Gui gui = new Gui(new SerialConnection(in, out));
        gui.createApp();
//        gui.setSerialConnection(new SerialConnection(in, out));
//        SerialConnection serialConnection = new SerialConnection(in, out);


//        gui.serialConnection.dataOut("withdraw");


    }
}
