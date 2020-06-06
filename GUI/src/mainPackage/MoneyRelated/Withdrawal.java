package mainPackage.MoneyRelated;

import mainPackage.Gui;
import mainPackage.User.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import static java.lang.Thread.sleep;

public class Withdrawal {
    public Gui gui;
    public User user;
    private int withdrawalAmount = 0;
    public int[] withdrawalArray1 = new int[4];
    public int[] withdrawalArray2 = new int[4];
    public int[] withdrawalArray3 = new int[4];
    public int[] withdrawalArray4 = new int[4];
    short option;
    StringBuilder option1 = new StringBuilder("Optie 1 [1]\n");
    StringBuilder option2 = new StringBuilder("Optie 2 [2]\n");
    StringBuilder option3 = new StringBuilder("Optie 3 [3]\n");
    StringBuilder option4 = new StringBuilder("Optie 4 [4]\n");

    public Withdrawal(Gui gui, User user){
        this.gui = gui;
        this.user = user;
    }

    public void customWithdrawal(int withdrawalAmount){ //restant van een oude methode: niet verwijderen
        this.withdrawalAmount = withdrawalAmount;


    }

    public void sendArray(int arrayNumber) throws InterruptedException{
        gui.serialConnection.stringOut("withdraw");
        sleep(2000);

        if(arrayNumber == 1){
            for(int i = 0; i < withdrawalArray1.length; i++) {
                while (withdrawalArray1[i] > 0){
                    if(i == 0) {
                        gui.serialConnection.stringOut("fifty");
                        gui.amounts[3]--;
                        withdrawalArray1[i]--;
                    }
                    if(i == 1) {
                        gui.serialConnection.stringOut("twenty");
                        gui.amounts[2]--;
                        withdrawalArray1[i]--;
                    }
                    if(i == 2) {
                        gui.serialConnection.stringOut("ten");
                        gui.amounts[1]--;
                        withdrawalArray1[i]--;
                    }
                    if(i == 3) {
                        gui.serialConnection.stringOut("five");
                        gui.amounts[0]--;
                        withdrawalArray1[i]--;
                    }
                    sleep(2500);
                }
            }

            gui.serialConnection.stringOut("complete");
            sleep(2500);
        }

        if(arrayNumber == 2){
            for(int i = 0; i < withdrawalArray2.length; i++) {
                while (withdrawalArray2[i] > 0){
                    if(i == 0) {
                        gui.serialConnection.stringOut("fifty");
                        gui.amounts[3]--;
                        withdrawalArray2[i]--;
                    }
                    if(i == 1) {
                        gui.serialConnection.stringOut("twenty");
                        gui.amounts[2]--;
                        withdrawalArray2[i]--;
                    }
                    if(i == 2) {
                        gui.serialConnection.stringOut("ten");
                        gui.amounts[1]--;
                        withdrawalArray2[i]--;
                    }
                    if(i == 3) {
                        gui.serialConnection.stringOut("five");
                        gui.amounts[0]--;
                        withdrawalArray2[i]--;
                    }
                    sleep(2500);
                }
            }

            gui.serialConnection.stringOut("complete");
            sleep(2500);
        }

        if(arrayNumber == 3){
            for(int i = 0; i < withdrawalArray3.length; i++) {
                while (withdrawalArray3[i] > 0){
                    if(i == 0) {
                        gui.serialConnection.stringOut("fifty");
                        gui.amounts[3]--;
                        withdrawalArray3[i]--;
                    }
                    if(i == 1) {
                        gui.serialConnection.stringOut("twenty");
                        gui.amounts[2]--;
                        withdrawalArray3[i]--;
                    }
                    if(i == 2) {
                        gui.serialConnection.stringOut("ten");
                        gui.amounts[1]--;
                        withdrawalArray3[i]--;
                    }
                    if(i == 3) {
                        gui.serialConnection.stringOut("five");
                        gui.amounts[0]--;
                        withdrawalArray3[i]--;
                    }
                    sleep(2500);
                }
            }

            gui.serialConnection.stringOut("complete");
            sleep(2500);
        }

        if(arrayNumber == 4){
            for(int i = 0; i < withdrawalArray4.length; i++) {
                while (withdrawalArray4[i] > 0){
                    if(i == 0) {
                        gui.serialConnection.stringOut("fifty");
                        gui.amounts[3]--;
                        withdrawalArray4[i]--;
                    }
                    if(i == 1) {
                        gui.serialConnection.stringOut("twenty");
                        gui.amounts[2]--;
                        withdrawalArray4[i]--;
                    }
                    if(i == 2) {
                        gui.serialConnection.stringOut("ten");
                        gui.amounts[1]--;
                        withdrawalArray4[i]--;
                    }
                    if(i == 3) {
                        gui.serialConnection.stringOut("five");
                        gui.amounts[0]--;
                        withdrawalArray4[i]--;
                    }
                    sleep(2500);
                }
            }

            gui.serialConnection.stringOut("complete");
            sleep(2500);
    }

         //todo toevoegen dat de dispenser aangeeft wanneer het geld gedispenst is
            if(gui.menuDispensing){
                gui.changePanel(gui.panelBon);
                gui.menuDispensing = false;
                gui.menuBon = true;
            }

            double balance = user.getBalance();
            balance -= withdrawalAmount;
            user.setBalance(balance);
            System.out.println("Transaction Complete");
            gui.changePanel(gui.panelBon);//na het dispensen

            try {
                Statement st = user.conn.createStatement();
                System.out.println(user.getBalance());
                st.executeLargeUpdate(String.format("UPDATE account SET balance = (balance - %d) WHERE number = '%s';", this.withdrawalAmount, user.accountNumber));
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    public void algorithm(int withdrawalAmount, int[] withdrawalArray) throws Exception { //algoritme voor keuze van biljetten

            if (option == 1) { //bereken optie 1
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
            if (option == 2) { // bereken optie 2
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
            if (option == 3 && withdrawalAmount <= 150) { //bereken optie 3
                while (withdrawalAmount > 0) {
                    if (withdrawalAmount >= 10) {
                        withdrawalAmount = withdraw10(withdrawalAmount, withdrawalArray);
                    }

                    if (withdrawalAmount >= 5) {
                        withdrawalAmount = withdraw5(withdrawalAmount, withdrawalArray);
                    }
                }
            }

            if (option == 4 && withdrawalAmount <= 50) { //bereken optie 4
                while (withdrawalAmount > 0) {
                    if (withdrawalAmount >= 5) {
                            withdrawalAmount = withdraw5(withdrawalAmount, withdrawalArray);
                    }
                }
            }
    }
//todo ervoor zorgen dat hij niet gelijk gui.amounts dingen veranderd hier, misschien opschuiven naar de sendArrays methode?
    private int withdraw50(int temp, int[] withdrawalArray)throws Exception{
        if (gui.amounts[3] > 0) {
            temp = temp - 50;
            withdrawalArray[0]++; // houd bij hoeveel biljetten hij straks moet gaan printen
        } else throw new Exception();
        return temp;
    }

    private int withdraw20(int temp, int[] withdrawalArray)throws Exception{
        if (gui.amounts[2] > 0) {
            temp = temp - 20;
            withdrawalArray[1]++;
        } else throw new Exception();
        return temp;
    }
    private int withdraw10(int temp, int[] withdrawalArray)throws Exception{
        if (gui.amounts[1] > 0) {
            temp = temp - 10;
            withdrawalArray[2]++;
        } else throw new Exception();
        return temp;
    }
    private int withdraw5(int temp, int[] withdrawalArray) throws Exception{
        if (gui.amounts[0] > 0) {
            temp = temp - 5;
            withdrawalArray[3]++;
        } else throw new Exception();

        return temp;
    }

    public void displayOptions()throws Exception {
        gui.optie1.setText("Optie 1 []\n");
        gui.optie2.setText("Optie 2 []\n");
        gui.optie3.setText("Optie 3 []\n");
        gui.optie4.setText("Optie 4 []\n");
        Arrays.fill(withdrawalArray1, 0);
        Arrays.fill(withdrawalArray2, 0);
        Arrays.fill(withdrawalArray3, 0);
        Arrays.fill(withdrawalArray4, 0);

        option = 1;
        algorithm(withdrawalAmount, withdrawalArray1);
        if(withdrawalArray1[0] > 0)option1.append(withdrawalArray1[0]).append("x50\n"); // als de JButtons naar JTextField of JTextArea worden veranderd, dan werkt dit
        if(withdrawalArray1[1] > 0)option1.append(withdrawalArray1[1]).append("x20\n");
        if(withdrawalArray1[2] > 0)option1.append(withdrawalArray1[2]).append("x10\n");
        if(withdrawalArray1[3] > 0)option1.append(withdrawalArray1[3]).append("x5\n");
        if(option1.toString().equalsIgnoreCase("Optie 1 []\n")) gui.optie1.setVisible(false);
        else {
            gui.optie1.setText(option1.toString());
            gui.optie1.setVisible(true);
        }


        option = 2;
        algorithm(withdrawalAmount, withdrawalArray2);
        if(withdrawalArray2[0] > 0)option2.append(withdrawalArray2[0]).append("x50\n");
        if(withdrawalArray2[1] > 0)option2.append(withdrawalArray2[1]).append("x20\n");
        if(withdrawalArray2[2] > 0)option2.append(withdrawalArray2[2]).append("x10\n");
        if(withdrawalArray2[3] > 0)option2.append(withdrawalArray2[3]).append("x5\n");
        if(option2.toString().equalsIgnoreCase("Optie 2:\n")) gui.optie2.setVisible(false);
        else {
            gui.optie2.setText(option2.toString());
            gui.optie2.setVisible(true);
        }


        option = 3;
        algorithm(withdrawalAmount, withdrawalArray3);
        if(withdrawalArray3[0] > 0)option3.append(withdrawalArray3[0]).append("x50\n");
        if(withdrawalArray3[1] > 0)option3.append(withdrawalArray3[1]).append("x20\n");
        if(withdrawalArray3[2] > 0)option3.append(withdrawalArray3[2]).append("x10\n");
        if(withdrawalArray3[3] > 0)option3.append(withdrawalArray3[3]).append("x5\n");
        if(option3.toString().equalsIgnoreCase("Optie 3:\n")) gui.optie3.setVisible(false);
        else {
            gui.optie3.setText(option3.toString());
            gui.optie3.setVisible(true);
        }

        option = 4;
        algorithm(withdrawalAmount, withdrawalArray4);
        if(withdrawalArray4[0] > 0)option4.append(withdrawalArray4[0]).append("x50\n");
        if(withdrawalArray4[1] > 0)option4.append(withdrawalArray4[1]).append("x20\n");
        if(withdrawalArray4[2] > 0)option4.append(withdrawalArray4[2]).append("x10\n");
        if(withdrawalArray4[3] > 0)option4.append(withdrawalArray4[3]).append("x5\n");
        if(option4.toString().equalsIgnoreCase("Optie 4:\n")) gui.optie4.setVisible(false);
        else {
            gui.optie4.setText(option4.toString());
            gui.optie4.setVisible(true);
        }
    }
}