package User;
import MoneyRelated.Balance;

public class User {
    public Balance balance = new Balance();

    private int inactivityTimer;
    private boolean logout = false;
    private int maxtime = 30;

    void userLogout(){
        if(inactivityTimer > maxtime){
            User user = null;
        }
    }
}
