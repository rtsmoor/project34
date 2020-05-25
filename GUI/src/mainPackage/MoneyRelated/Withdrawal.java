package mainPackage.MoneyRelated;

import mainPackage.Gui;
import mainPackage.User.User;

public class Withdrawal extends Balance {
    public Gui gui;
    public User user;
    private int withdrawalAmount = 0;

    public Withdrawal(Gui gui, User user){
        this.gui = gui;
        this.user = user;
    }

    public void customWithdrawal(int withdrawalAmount){
        this.withdrawalAmount = withdrawalAmount;
        user.balance.withdrawBalance(this.withdrawalAmount);

        gui.serialConnection.stringOut("withdraw");
        gui.serialConnection.intOut(this.withdrawalAmount);
        gui.serialConnection.in.nextLine();
        //todo add code that communicates this with arduino
    }

    public void algorithm(int withdrawalAmount) { //algoritme voor keuze van biljetten
       while(withdrawalAmount > 0){
            withdrawalAmount = withdraw50(withdrawalAmount);

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