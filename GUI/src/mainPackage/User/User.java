package mainPackage.User;
import mainPackage.Gui;
import mainPackage.MoneyRelated.Balance;

import java.util.Arrays;

public class User {
    public Balance balance = new Balance();
    public Gui gui;

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

    public void userLogout(){
        gui.setUser(null); // deletes connections to the user, so the garbage collector deletes the user.
        gui.customBedragField.setText("");
        Arrays.fill(passwordCheck, '0'); // zet het wachtwoord weer op 0000 voor security redenen.
        System.out.println("user is logged out");
    }
}
