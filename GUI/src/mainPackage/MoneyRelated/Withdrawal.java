package mainPackage.MoneyRelated;

import mainPackage.Gui;
import mainPackage.User.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Withdrawal {
    public Gui gui;
    public User user;
    private int withdrawalAmount = 0;

    public Withdrawal(Gui gui, User user){
        this.gui = gui;
        this.user = user;
    }

    public void customWithdrawal(int withdrawalAmount){
        this.withdrawalAmount = withdrawalAmount;
        double balance = user.getBalance();


        gui.serialConnection.stringOut("withdraw");
        gui.serialConnection.intOut(this.withdrawalAmount);
        System.out.println(gui.serialConnection.in.nextLine());
        if(gui.serialConnection.in.nextLine().equals(Integer.toString(withdrawalAmount))) {
            balance -= withdrawalAmount;
            user.setBalance(balance);
            System.out.println("Transaction Complete");
        }

        //todo add code that communicates this with arduino

        if(true) {//todo change true to whether or not the transaction worked (communicate with arduino)
            try {
                Statement st = user.conn.createStatement();
                System.out.println(user.getBalance());
                st.executeLargeUpdate(String.format("UPDATE account SET balance = (balance - %d) WHERE number = '%s';", this.withdrawalAmount, user.accountNumber));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("An error has occured");
        }
    }

    public void algorithm(int withdrawalAmount) { //algoritme voor keuze van biljetten
       while(withdrawalAmount > 0){
           if(withdrawalAmount > 50){
               withdrawalAmount = withdraw50(withdrawalAmount);
           }

           if(withdrawalAmount > 20){
               withdrawalAmount = withdraw20(withdrawalAmount);
           }

           if(withdrawalAmount > 10){
               withdrawalAmount = withdraw10(withdrawalAmount);
           }

           if(withdrawalAmount > 5){
               withdrawalAmount = withdraw5(withdrawalAmount);
           }

        }
    }
    private int withdraw50(int temp){
        if (gui.amounts[3] > 0) {
            temp = temp - 50;
            gui.amounts[3]--;
        }
        return temp;
    }

    private int withdraw20(int temp){
        if (gui.amounts[2] > 0) {
            temp = temp - 20;
            gui.amounts[2]--;
        }
        return temp;
    }
    private int withdraw10(int temp){
        if (gui.amounts[1] > 0) {
            temp = temp - 10;
            gui.amounts[1]--;
        }
        return temp;
    }
    private int withdraw5(int temp){
        if (gui.amounts[0] > 0) {
            temp = temp - 5;
            gui.amounts[0]--;
        }
        return temp;
    }
}