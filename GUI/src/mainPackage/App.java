package mainPackage;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.jcraft.jsch.*;
import mainPackage.serialconnection.SerialConnection;

import java.io.PrintWriter;
import java.sql.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class App {

    static boolean received;
    static boolean arduinoReady = false;


    public static void main(String[] args) {

        SerialPort port = SerialPort.getCommPort("COM5"); // edit dit als je een andere com port gebruikt //todo maak een for loop om te checken op de juiste com port
        port.setComPortParameters(115200, 8, 1, 0);
        port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        System.out.println("Open port: " + port.openPort());
        Scanner in = new Scanner(port.getInputStream()); // dit is de input van de arduino
        PrintWriter out = new PrintWriter(port.getOutputStream(), true); // dit is de output wat naar arduino gaat


        while(true) {
            try {
                sleep(2000);
                if (in.nextLine().equalsIgnoreCase("ready")) {
                    //in.nextLine();
                    System.out.println("ready");
                    arduinoReady = true; // alleen als de arduino gereed is, dan mogen er pas dingen gebeuren
                    break;
                }
            } catch (Exception e) {
                System.out.println("ERROR connection to the arduino");
                Scanner s = new Scanner(System.in);
                System.out.print("GUI toch starten? y/n:");
                String input = s.nextLine();
                if("yes".equalsIgnoreCase(input) || "y".equalsIgnoreCase(input)) break;
                if("no".equalsIgnoreCase(input) || "n".equalsIgnoreCase(input)) return;
            }
        }

        int lport=5656;
        String rhost="127.0.0.1";
        String host="145.24.222.230";
        int rport=3306;
        String user="ubuntu-0997274";
        String password="V883cY";
        String dbuserName = "dispenser";
        String dbpassword = "0NfYC0jfNteUAZj4$";
        String url = "jdbc:mysql://localhost:"+lport+"/bank?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Connection conn = null;
        Session session= null;
        try{
            //Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            session=jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            System.out.println("Connected");
            int assinged_port=session.setPortForwardingL(lport, rhost, rport);
            System.out.println("localhost:"+assinged_port+" -> "+rhost+":"+rport);
            System.out.println("Port Forwarded");

            //mysql database connectivity
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn =  DriverManager.getConnection (url, dbuserName, dbpassword);
            System.out.println ("Database connection established");
            System.out.println("DONE");
        }catch(Exception e){
            e.printStackTrace();
        }

//        finally{
//            if(conn != null && !conn.isClosed()){
//                System.out.println("Closing Database Connection");
//                conn.close();
//            }
//            if(session !=null && session.isConnected()){
//                System.out.println("Closing SSH Connection");
//                session.disconnect();
//            }
//        }

        Gui gui = new Gui(new SerialConnection(in, out), conn);
        gui.createApp();

        port.addDataListener(new SerialPortDataListener() {

            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) { //ik denk dat deze methode activeert iedere keer dat er iets nieuws binnenkomt op de serial port

                if (in.hasNextLine()) {
                    String input = in.nextLine();
                    // ArdSend word gebruikt als java niet eerder om input heeft gevraagd.
                    // Omdat veel methodes gebruik maken van serialConnection.stringIn() doe ik het op deze manier zodat ook zij de juiste input zullen krijgen
                    //if (input.contains("Ard")) { //!!!opletten dat je niet Ard in een andere string verstuurt!!!
                        gui.arduinoInputHandler(input);

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
            }
        });


    }
}
