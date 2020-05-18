package MoneyRelated;

public class Withdrawal extends Balance {
    private int amount = 0;

    int fastWithdrawal(){
    amount = 70;
    return amount;
}

    int customWithdrawal(int input){
        double balance = amount = input;
        withdrawBalance(amount);

        return amount;
    }

}