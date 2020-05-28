package mainPackage.User;
import mainPackage.Gui;
import mainPackage.MoneyRelated.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class User {
    public Balance balance = new Balance();
    public Gui gui;
    private Connection conn;
    public Withdrawal withdrawal;
    public String passNumber = "";

    private char[] passwordCheck = new char[4];

    User(Gui gui, Connection conn){
        this.gui = gui;
        this.conn = conn;
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
        Arrays.fill(passwordCheck, '0'); // zet het wachtwoord weer op 0000 voor security.
        System.out.println("user is logged out");
        this.withdrawal = null;
        this.balance = null;
        this.gui = null;
    }
    //methode verwijdert alle gegevens die zijn ingevoerd en reset alle variabelen die zijn veranderd
    public void toMainMenu(){
        if(withdrawal != null) withdrawal = null;
        gui.customBedragField.setText("");

    }
    //todo fix this method: when trying to use the input it just breaks with an arrayOutOfIndexException
    public void requestUserVariables() throws SQLException {
        //todo compare passnumber sent by arduino with database
        //todo use query to get balance
//        Statement st = conn.createStatement();
//        ResultSet rs = st.executeQuery("SELECT * FROM login");
//        while (rs.next())
//        {
//
//            String firstName = rs.getString("pass_number");
//            String lastName = rs.getString("account_number");
//            String dateCreated = rs.getString("pincode");
//
//
//            // print the results
//            System.out.format("%s, %s, %s\n",firstName, lastName, dateCreated);
//        }
//        st.close();

    }

    public void setUserVariables(){


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
    public void sendAmount4() {
        gui.serialConnection.stringOut("50");
    }
}
