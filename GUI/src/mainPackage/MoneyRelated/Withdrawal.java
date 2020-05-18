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
}