package mainPackage.MoneyRelated;

import mainPackage.Gui;
import mainPackage.User.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class Withdrawal {
    public Gui gui;
    public User user;
    private int withdrawalAmount = 0;
    private int[] withdrawalArray1 = new int[4];
    private int[] withdrawalArray2 = new int[4];
    private int[] withdrawalArray3 = new int[4];
    private int[] withdrawalArray4 = new int[4];
    private boolean algorithmDone = false;
    short option;
    StringBuilder option1 = new StringBuilder("Optie 1:\n");
    StringBuilder option2 = new StringBuilder("Optie 2:\n");
    StringBuilder option3 = new StringBuilder("Optie 3:\n");
    StringBuilder option4 = new StringBuilder("Optie 4:\n");

    public Withdrawal(Gui gui, User user){
        this.gui = gui;
        this.user = user;
    }

    public void customWithdrawal(int withdrawalAmount){
        this.withdrawalAmount = withdrawalAmount;
        double balance = user.getBalance();
        gui.serialConnection.stringOut("withdraw");
        gui.serialConnection.stringIn();

        do{
        gui.serialConnection.stringOut("twenty"); //dit in stukies sturen en aan het eind "complete" sturen

        //if() stringOut
        String temp = gui.serialConnection.stringIn();

       //if(temp.equals("received")) {
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

       // }
    } while(true);
        algorithmDone = false;
    }

    public void algorithm(int withdrawalAmount, int[] withdrawalArray) { //algoritme voor keuze van biljetten

            if (option == 1) { //optie 1
                while (withdrawalAmount > 0) {
                    if (withdrawalAmount >= 50) {
                        withdrawalAmount = withdraw50(withdrawalAmount, withdrawalArray);
                    }

                    if (withdrawalAmount >= 20) {
                        withdrawalAmount = withdraw20(withdrawalAmount, withdrawalArray);
                    }

                    if (withdrawalAmount >= 10) {
                        withdrawalAmount = withdraw10(withdrawalAmount, withdrawalArray);
                    }

                    if (withdrawalAmount >= 5) {
                        withdrawalAmount = withdraw5(withdrawalAmount, withdrawalArray);
                    }
                }
            }
            if (option == 2) { // optie 2
                while (withdrawalAmount > 0) {
                    if (withdrawalAmount >= 20) {
                        withdrawalAmount = withdraw20(withdrawalAmount, withdrawalArray);
                    }

                    if (withdrawalAmount >= 10) {
                        withdrawalAmount = withdraw10(withdrawalAmount, withdrawalArray);
                    }

                    if (withdrawalAmount >= 5) {
                        withdrawalAmount = withdraw5(withdrawalAmount, withdrawalArray);
                    }
                }
            }
            if (option == 3 && withdrawalAmount <= 150) { //optie 3
                while (withdrawalAmount > 0) {
                    if (withdrawalAmount >= 10) {
                        withdrawalAmount = withdraw10(withdrawalAmount, withdrawalArray);
                    }

                    if (withdrawalAmount >= 5) {
                        withdrawalAmount = withdraw5(withdrawalAmount, withdrawalArray);
                    }
                }
            }

            if (option == 4 && withdrawalAmount <= 50) { //optie 4
                while (withdrawalAmount > 0) {
                    if (withdrawalAmount >= 5) {
                        withdrawalAmount = withdraw5(withdrawalAmount, withdrawalArray);
                    }
                }
            }

        algorithmDone = true;//todo ???
        //hier iets met string complete doen ???
    }

    private int withdraw50(int temp, int[] withdrawalArray){
        if (gui.amounts[3] > 0) {
            temp = temp - 50;
            gui.amounts[3]--; // houd bij hoeveel biljetten er in de dispenser zit
            withdrawalArray[0]++; // houd bij hoeveel biljetten hij straks moet gaan printen
        }
        return temp;
    }

    private int withdraw20(int temp, int[] withdrawalArray){
        if (gui.amounts[2] > 0) {
            temp = temp - 20;
            gui.amounts[2]--;
            withdrawalArray[1]++;
        }
        return temp;
    }
    private int withdraw10(int temp, int[] withdrawalArray){
        if (gui.amounts[1] > 0) {
            temp = temp - 10;
            gui.amounts[1]--;
            withdrawalArray[2]++;
        }
        return temp;
    }
    private int withdraw5(int temp, int[] withdrawalArray){
        if (gui.amounts[0] > 0) {
            temp = temp - 5;
            gui.amounts[0]--;
            withdrawalArray[3]++;
        }
        return temp;
    }

    public void displayOptions(){
        gui.optie1.setText("");
        gui.optie2.setText("");
        gui.optie3.setText("");
        gui.optie4.setText("");
        Arrays.fill(withdrawalArray1, 0);
        Arrays.fill(withdrawalArray2, 0);
        Arrays.fill(withdrawalArray3, 0);
        Arrays.fill(withdrawalArray4, 0);
        option = 1;
        algorithm(withdrawalAmount, withdrawalArray1);
        if(withdrawalArray1[0] > 0) option1.append(withdrawalArray1[0]).append("x50\n"); // als de JButtons naar JTextField of JTextArea worden veranderd, dan werkt dit
        if(withdrawalArray1[1] > 0) option1.append(withdrawalArray1[1]).append("x20\n");
        if(withdrawalArray1[2] > 0)option1.append(withdrawalArray1[2]).append("x10\n");
        if(withdrawalArray1[3] > 0)option1.append(withdrawalArray1[3]).append("x5\n");
        gui.optie1.setText(option1.toString());

        option = 2;
        algorithm(withdrawalAmount, withdrawalArray2);
        if(withdrawalArray2[0] > 0)option2.append(withdrawalArray2[0]).append("x50\n");
        if(withdrawalArray2[1] > 0) option2.append(withdrawalArray2[1]).append("x20\n");
        if(withdrawalArray2[2] > 0) option2.append(withdrawalArray2[2]).append("x10\n");
        if(withdrawalArray2[3] > 0)option2.append(withdrawalArray2[3]).append("x5\n");
        gui.optie2.setText(option2.toString());


        option = 3;
        algorithm(withdrawalAmount, withdrawalArray3);
        if(withdrawalArray3[0] > 0)option3.append(withdrawalArray3[0]).append("x50\n");
        if(withdrawalArray3[1] > 0)option3.append(withdrawalArray3[1]).append("x20\n");
        if(withdrawalArray3[2] > 0)option3.append(withdrawalArray3[2]).append("x10\n");
        if(withdrawalArray3[3] > 0)option3.append(withdrawalArray3[3]).append("x5\n");
        gui.optie3.setText(option3.toString());

        option = 4;
        algorithm(withdrawalAmount, withdrawalArray4);
        if(withdrawalArray4[0] > 0)option4.append(withdrawalArray4[0]).append("x50\n");
        if(withdrawalArray4[1] > 0)option4.append(withdrawalArray4[1]).append("x20\n");
        if(withdrawalArray4[2] > 0)option4.append(withdrawalArray4[2]).append("x10\n");
        if(withdrawalArray4[3] > 0)option4.append(withdrawalArray4[3]).append("x5\n");
        gui.optie4.setText(option4.toString());


    }
}