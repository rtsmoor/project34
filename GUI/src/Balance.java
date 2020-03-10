import java.math.BigDecimal;


//variabelen worden vervangen door de gegevens uit de database en de user input (tijdelijke placeholder)

public class Balance {
    private int balance = 0;

    public int getBalance() {
        return balance;
    }

    void withdrawBalance(int balChange){
        System.out.println(balance);
        balance -= balChange;
        System.out.println(balance);
    }
}   
