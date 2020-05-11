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
    private Scanner in;
    private PrintWriter out;

    public SerialConnection(Scanner in, PrintWriter out){
        this.in = in;
        this.out = out;
    }

    public void stringOut(String stringOut){ //string die naar de arduino gaat
        out.print(stringOut);
        System.out.println("command sent: " + stringOut);
        out.flush();

        try{
            sleep(3000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public boolean hasString(){
        return in.hasNextLine();
    }

    public String stringIn(){ // string die van de arduino komt
        String temp = "ERROR";


            try {
                temp = in.nextLine();
            } catch (Exception e) {
                System.out.println("An error has occured: " + e);
            }

        System.out.println(temp);
        return temp;
    }

    public int intIn(){
        int temp = -1;

        if(in.hasNextInt()){
            temp = in.nextInt();
            in.nextLine();
        }

        return temp;
    }

}