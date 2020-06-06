package mainPackage.User;

import mainPackage.Gui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LogIn {
    private Gui gui;
    private Connection conn;
    private String passnumber = "";
    private String hashedPIN = "";
    public int pogingen = -1;

    public LogIn(Gui gui, Connection conn){
        this.gui = gui;
        this.conn = conn;
    }

    public String getHashedPIN() {
        return hashedPIN;
    }

    public void setHashedPIN(String hashedPIN) {
        this.hashedPIN = hashedPIN;
    }

    public String getPassnumber() {
        return passnumber;
    }

    public void setPassnumber(String passnumber) {
        this.passnumber = passnumber;
    }

    public void checkPassnumber(String passnumber){
        String temp = "";
        String query = String.format("SELECT pass_number FROM login WHERE pass_number = '%s'", passnumber);
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            if(rs.next()) temp = rs.getString("pass_number");
        } catch (SQLException ex){
            System.out.println("kan pass niet lezen/pas staat niet in de DB");
        } finally {
            this.passnumber = temp;
        }
    }

    public String getPogingenfromDB() throws SQLException {
        String checkMistakeQuery = "SELECT mistakes, number FROM account INNER JOIN login ON number = account_number WHERE login.pass_number = '" + this.passnumber + "';";

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(checkMistakeQuery);
        if(rs.next()) pogingen = rs.getInt("mistakes");
        String temp = "";
        if(pogingen != -1) temp = Integer.toString((3 - pogingen));
        st.close();
        return temp;
    }


    public boolean checkLogin() throws SQLException {

        String accountNumber = "";
        //als login correct is ga dan door naar het volgende scherm

        String checkMistakeQuery = "SELECT mistakes, number FROM account INNER JOIN login ON account.number = login.account_number WHERE login.pass_number = '" + this.passnumber + "';";

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(checkMistakeQuery);
        while(rs.next()) {
            pogingen = rs.getInt("mistakes");
            accountNumber = rs.getString("number");
            System.out.println("aantal pogingen: " + pogingen + "\naccountNumber: " + accountNumber);
        }

        if (comparePassword() && pogingen < 3) {
            System.out.println("login successful");
            User user = new User(gui, conn, accountNumber); // creates a new user session every time you log in
            gui.setUser(user);
            //reset counter and send to DB
            pogingen = 0;
            //System.out.println(mistakeUpdate);
            System.out.println("rows affected: " + st.executeUpdate(String.format("UPDATE account SET account.mistakes = %d WHERE number = '%s';", pogingen, accountNumber)));
            return true;
        }

        else if(pogingen <= 2){
            pogingen++;
            System.out.println(st.executeUpdate(String.format("UPDATE account SET account.mistakes = %d WHERE number = '%s';", pogingen, accountNumber)));
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

    private boolean comparePassword() throws SQLException{
        String query = String.format("SELECT pincode FROM login WHERE pass_number = '%s'", passnumber);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);

        if(rs.next()){
            String temp = rs.getString("pincode");
            if(temp.equals(this.hashedPIN)) return true;
        }
        return false;
    }

    public void clearLoginVariables(){
        passnumber = "";
        hashedPIN = "";
        pogingen = 0;
    }
}
