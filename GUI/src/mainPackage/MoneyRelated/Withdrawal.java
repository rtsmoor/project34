package mainPackage.MoneyRelated;

public class Withdrawal extends Balance {
    private int withdrawalAmount = 0;

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