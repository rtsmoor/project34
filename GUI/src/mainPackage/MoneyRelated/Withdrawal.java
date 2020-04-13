package mainPackage.MoneyRelated;

public class Withdrawal extends Balance {
    private int withdrawalAmount = 0;
    private boolean receipt;

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
        double balance = withdrawalAmount = input;
        withdrawBalance(withdrawalAmount);

        //todo add code that communicates this with arduino
    }
}