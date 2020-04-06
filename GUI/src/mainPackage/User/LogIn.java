package mainPackage.User;

import mainPackage.Gui;

public class LogIn {
    boolean rfidDetected = false;
    private Gui gui;

    public LogIn(Gui gui){
        this.gui = gui;
    }

    public void setRfidDetected(boolean rfidDetected) {
        this.rfidDetected = rfidDetected;
    }

    public void requestLogin(){
        //GUI vraagt om pas
        //wanneer pas is ingevoerd ga dan verder
        checkLogin();
    }

    public void checkLogin(){
        //als login correct is ga dan door naar het volgende scherm
        if(true){
            successFulLogin();
        }
        //todo add counter or use counter on the arduino
        else {
            System.out.println("Error: x aantal poging over");
        }

        //als login fout is, geef foutmelding weer en aantal pogingen die er nog over zijn
    }

    private void successFulLogin(){
        System.out.println("login successful");
        User user = new User(gui); // creates a new user session every time you log in
        gui.setUser(user);


        //reset counter voor aantal fouten
    }

}
