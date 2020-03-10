public class LogOut {
    int inactivityTimer;
    boolean logout = false;
    int maxtime;

    void userLogout(){
        if(inactivityTimer > maxtime){
            logout = true;
        }
    }
}
