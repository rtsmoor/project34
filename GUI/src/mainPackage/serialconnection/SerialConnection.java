package mainPackage.serialconnection;

import com.fazecast.jSerialComm.SerialPort;
import mainPackage.App;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

import static java.lang.Thread.sleep;

public class SerialConnection {
    public Scanner in;
    private PrintWriter out;
    public String input = "";

    public SerialConnection(Scanner in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    public void stringOut(String stringOut) { //string die naar de arduino gaat
        out.print(stringOut);
        System.out.println("command sent: " + stringOut);
        out.flush();

        //if (in.hasNextLine()) ; // arduino moet altijd iets terugsturen, en dat moet ook worden opgevangen in de GUI //redundant na v1.2.2
    }

    public boolean hasString() {
        return in.hasNextLine();
    }

    public void stringIn(String input) { // string die van de arduino komt
//        String input = "ERROR"; //oude code (pre-v1.2.2)


//        try {                         //oude code (pre-v1.2.2)
//            input = in.nextLine();
//        } catch (Exception e) {
//            System.out.println("An error has occured: " + e);
//        }
        this.input = input;
        System.out.println(this.input);

    }
}
