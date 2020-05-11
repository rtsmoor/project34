package mainPackage.User;

import mainPackage.Gui;

public class LogIn {
    boolean rfidDetected = false;
    private Gui gui;
    private int pogingen = 3;

    public LogIn(Gui gui){
        this.gui = gui;
    }

    public void setRfidDetected(boolean rfidDetected) {
        this.rfidDetected = rfidDetected;
    }

    public boolean requestLogin(){
        //GUI vraagt om pas
        //wanneer pas is ingevoerd ga dan verder
        if(checkLogin()) return true;
        else return false;
    }

    public boolean checkLogin(){
        //als login correct is ga dan door naar het volgende scherm

            if (true && pogingen > 0) {
                System.out.println("login successful");
                User user = new User(gui); // creates a new user session every time you log in
                gui.setUser(user);
                pogingen = 3;
                //reset counter
                return true;
            }
            //todo add counter or use counter on the arduino
            else {
                pogingen--;
                System.out.println("Error: " + pogingen + " aantal poging(en) over");
            }

        if(pogingen <= 0) {
            System.out.println("login failed, card has been blocked");
        }
        return false;
        //als login fout is, geef foutmelding weer en aantal pogingen die er nog over zijn
    }



}
