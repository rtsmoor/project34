package mainPackage.User;
import mainPackage.Gui;
import mainPackage.MoneyRelated.*;

import java.util.Arrays;

public class User {
    public Balance balance = new Balance();
    public Gui gui;
    public Withdrawal withdrawal;
    public String userName = "";

//    private int inactivityTimer;
//    private int maxtime = 30;
    private boolean logout = false;
    private char[] passwordCheck = new char[4];

    User(Gui gui){
        this.gui = gui;
    }

    public void setLogout(boolean logout) {
        this.logout = logout;
    }

    public char[] getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(char[] passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public void makeWithdrawal(){
        withdrawal = new Withdrawal(gui, this);
    }

    public void userLogout(){
        gui.setUser(null); // deletes connections to the user, so the garbage collector deletes the user.
        gui.customBedragField.setText("");
        Arrays.fill(passwordCheck, '0'); // zet het wachtwoord weer op 0000 voor security redenen.
        System.out.println("user is logged out");
    }
    //methode verwijdert alle gegevens die zijn ingevoerd en reset alle variabelen die zijn veranderd
    public void toMainMenu(){
        if(withdrawal != null) withdrawal = null;
        gui.customBedragField.setText("");

    }
    //todo fix this method: when trying to use the input it just breaks with an arrayOutOfIndexException
    public void requestUserVariables(){
        gui.serialConnection.stringOut("getUser");
        gui.serialConnection.stringOut("getBal");

    }
    public void setUserVariables(){
        if(gui.serialConnection.hasString()) {
            userName = gui.serialConnection.stringIn();
            balance.setBalance(gui.serialConnection.intIn());
        }
        else System.out.println("Error");
    }
    public void sendAmount(){
        gui.serialConnection.stringOut("sendAmount");
    }
    public void sendAmount1(){
        gui.serialConnection.stringOut("5");
    }
    public void sendAmount2(){
        gui.serialConnection.stringOut("10");
    }
    public void sendAmount3(){
        gui.serialConnection.stringOut("20");
    }
    public void sendAmount4(){
        gui.serialConnection.stringOut("50");
    }
    public void complete(){
        gui.serialConnection.stringOut("complete");
    }
}
