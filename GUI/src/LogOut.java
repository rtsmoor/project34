public class LogOut {
    private int inactivityTimer;
    private boolean logout = false;
    private int maxtime = 30;

    void userLogout(){
        if(inactivityTimer > maxtime){
            logout = true;
        }
    }
}
