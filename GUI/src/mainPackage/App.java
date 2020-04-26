package mainPackage;

import mainPackage.serialconnection.SerialConnection;

public class App {
    public static void main(String[] args) {
       Gui gui = new Gui();
        gui.createApp();
        SerialConnection serialConnection = new SerialConnection();
        
    }
}
