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
    private int[] withdrawalArray = new int[4];

    public Withdrawal(Gui gui, User user){
        this.gui = gui;
        this.user = user;
    }

    public void customWithdrawal(int withdrawalAmount){
        this.withdrawalAmount = withdrawalAmount;
        double balance = user.getBalance();
        gui.serialConnection.stringOut("withdraw");

        do{
        gui.serialConnection.intOut(); //dit in stukies sturen en aan het eind "complete" sturen
        System.out.println(gui.serialConnection.in.nextLine());


        if(gui.serialConnection.in.nextLine().equals("received")) {
            balance -= withdrawalAmount;
            user.setBalance(balance);
            System.out.println("Transaction Complete");

        //todo add code that communicates this with arduino

            try {
                Statement st = user.conn.createStatement();
                System.out.println(user.getBalance());
                st.executeLargeUpdate(String.format("UPDATE account SET balance = (balance - %d) WHERE number = '%s';", this.withdrawalAmount, user.accountNumber));
                break;
            } catch (SQLException e) {
                e.printStackTrace();
                break;
            }

        }
    } while(gui.serialConnection.in.nextLine().equals("sendMore"));
    }

    public void algorithm(int withdrawalAmount){ //algoritme voor keuze van biljetten
       while(this.withdrawalAmount > 0) {
           if (optie 1){
               if (this.withdrawalAmount > 50) {
                   this.withdrawalAmount = withdraw50(withdrawalAmount);
               }

               if (this.withdrawalAmount > 20) {
                   this.withdrawalAmount = withdraw20(withdrawalAmount);
               }

               if (this.withdrawalAmount > 10) {
                   this.withdrawalAmount = withdraw10(withdrawalAmount);
               }

               if (this.withdrawalAmount > 5) {
                   this.withdrawalAmount = withdraw5(withdrawalAmount);
               }
           }
           if (optie 2){
               if (this.withdrawalAmount > 20) {
                   this.withdrawalAmount = withdraw20(withdrawalAmount);
               }

               if (this.withdrawalAmount > 10) {
                   this.withdrawalAmount = withdraw10(withdrawalAmount);
               }

               if (this.withdrawalAmount > 5) {
                   this.withdrawalAmount = withdraw5(withdrawalAmount);
               }
           }
           if (optie 3){
               if (this.withdrawalAmount > 10) {
                   this.withdrawalAmount = withdraw10(withdrawalAmount);
               }

               if (this.withdrawalAmount > 5) {
                   this.withdrawalAmount = withdraw5(withdrawalAmount);
               }
           }

           if (optie 4){
               if (this.withdrawalAmount > 5) {
                   this.withdrawalAmount = withdraw5(withdrawalAmount);
               }
           }
       }
    }
    private int withdraw50(int temp){
        if (gui.amounts[3] > 0) {
            temp = temp - 50;
            gui.amounts[3]--;
            withdrawalArray[3]++;
        }
        return temp;
    }

    private int withdraw20(int temp){
        if (gui.amounts[2] > 0) {
            temp = temp - 20;
            gui.amounts[2]--;
            withdrawalArray[2]++;
        }
        return temp;
    }
    private int withdraw10(int temp){
        if (gui.amounts[1] > 0) {
            temp = temp - 10;
            gui.amounts[1]--;
            withdrawalArray[1]++;
        }
        return temp;
    }
    private int withdraw5(int temp){
        if (gui.amounts[0] > 0) {
            temp = temp - 5;
            gui.amounts[0]--;
            withdrawalArray[0]++;
        }
        return temp;
    }
}