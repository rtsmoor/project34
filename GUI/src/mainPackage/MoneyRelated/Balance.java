package mainPackage.MoneyRelated;//variabelen worden vervangen door de gegevens uit de database en de user input (tijdelijke placeholder)

public class Balance {
    private int balance = -1;

    public int getBalance() {
        return balance;
    }
    public void setBalance(int balance){
        this.balance = balance;
    }

    void withdrawBalance(int balChange){
        System.out.println(balance);
        balance -= balChange;
        System.out.println(balance);
        //todo sql query die hte veranderd in de database
    }
}   
