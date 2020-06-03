package mainPackage.User;

import mainPackage.Gui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LogIn {
    boolean rfidDetected = false;
    private Gui gui;
    private Connection conn;
    private String passnumber = "";
    private int pogingen = 0;

    public LogIn(Gui gui, Connection conn){
        this.gui = gui;
        this.conn = conn;
    }

    public void setRfidDetected(boolean rfidDetected) {
        this.rfidDetected = rfidDetected;
    }

    public String getPassnumber() {
        return passnumber;
    }

    public void setPassnumber(String passnumber) {
        this.passnumber = passnumber;
    }

    public boolean requestLogin(String passnumber){
        //GUI vraagt om pas
        //wanneer pas is ingevoerd ga dan verder
        try {
            return checkLogin(passnumber);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    //TODO String passNumber hoeft niet meer meegegeven te worden in het eindproduct, dit is nu alleen voor het testen nog zo
    public boolean checkLogin(String passNumber) throws SQLException { //todo password check toevoegen
        this.passnumber = passNumber;
        int accountNumber = -1;
        //als login correct is ga dan door naar het volgende scherm

        String checkMistakeQuery = "SELECT mistakes, number FROM account INNER JOIN login ON account.number = login.account_number WHERE login.pass_number = '" + this.passnumber + "';";

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(checkMistakeQuery);
        while(rs.next()) {
            pogingen = rs.getInt("mistakes");
            accountNumber = rs.getInt("number");
            System.out.println("aantal pogingen: " + pogingen + "\naccountNumber: " + accountNumber);
        }

        if (true && pogingen < 3) {//todo verander true naar de waarde die de arduino doorstuurt als de login correct is, of zorg ervoor dat het java programma het checkt
            System.out.println("login successful");
            User user = new User(gui, conn, accountNumber); // creates a new user session every time you log in
            gui.setUser(user);
            //reset counter and send to DB
            pogingen = 0;
            //System.out.println(mistakeUpdate);
            System.out.println("rows affected: " + st.executeUpdate(String.format("UPDATE account SET account.mistakes = %d WHERE number = %d;", pogingen, accountNumber)));
            return true;
        }

        else if(pogingen <= 2){
            pogingen++;
            System.out.println(st.executeUpdate(String.format("UPDATE account SET account.mistakes = %d WHERE number = %d;", pogingen, accountNumber)));
            System.out.println("Error: " + (3-pogingen) + " poging(en) over");
        }

        else {
            System.out.println("login failed, card has been blocked");
            return false;
        }


        st.close();
        return false;
        //als login fout is, geef foutmelding weer en aantal pogingen die er nog over zijn
    }

    public void clearLoginVariables(){
        passnumber = "";
        pogingen = 0;
        rfidDetected = false;
    }
}
