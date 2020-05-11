package mainPackage.MoneyRelated;

import mainPackage.Gui;
import mainPackage.User.User;

public class Withdrawal extends Balance {
    public Gui gui;
    public User user;
    private int withdrawalAmount = 0;
    private boolean receipt;

    public Withdrawal(Gui gui, User user){
        this.gui = gui;
        this.user = user;
    }

    public int getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(int withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public boolean getReceipt() {
        return receipt;
    }

    public void setReceipt(boolean receipt) {
        this.receipt = receipt;
    }

    public void fastWithdrawal(){
    withdrawalAmount = 70;

        //todo add code that communicates this with arduino
}

    public void customWithdrawal(int input){
        withdrawalAmount = input;
        user.balance.withdrawBalance(withdrawalAmount);

        gui.serialConnection.stringOut("withdraw");
        gui.serialConnection.intOut(withdrawalAmount);
        gui.serialConnection.in.nextLine();
        //todo add code that communicates this with arduino
    }
}