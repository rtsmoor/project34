package mainPackage.User;
import mainPackage.Gui;
import mainPackage.MoneyRelated.Balance;

import java.util.Arrays;

public class User {
    public Balance balance = new Balance();
    public Gui gui;

    private int inactivityTimer;
    private boolean logout = false;
    private int maxtime = 30;
    private char[] passwordCheck = new char[4];

    public User(Gui gui){
        this.gui = gui;
    }

    public char[] getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(char[] passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    void userLogout(){
        if(inactivityTimer > maxtime){
            User user = null;
        }

        Arrays.fill(passwordCheck, '0');
    }
}
