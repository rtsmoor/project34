package mainPackage;

import mainPackage.User.*;
import mainPackage.serialconnection.SerialConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import static java.lang.Thread.sleep;

public class Gui extends JFrame implements ActionListener {
    public int array_length = 10;
    private int amount4 = 15;
    private int amount3 = 15;
    private int amount2 = 15;
    private int amount1 = 15;
    public int[] amounts = {amount1, amount2, amount3, amount4}; // volgorde biljetten: 5, 10, 20, 50

    private boolean menuStart = true; //startscherm, is true aan het begin en nadat de gebruiker is uitgelogt
    private boolean menuLogin = false; //inlogmenu waar je je wachtwoord invult
    private boolean menuMain = false; //hoofdmenu(1ste scherm nadat je bent ingelogd)
    private boolean menuBalance = false; //geen uitleg nodig
    private boolean menuChooseAmounts = false; // keuzemenu voor 20,50,100,150 of ander bedrag
    private boolean menuCustomAmount = false; //menu waar je het bedrag invult dat je wilt hebben
    private boolean menuMoneyOptions = false; // menu waar je kiest in welke biljetten je je geld wilt hebben
    public boolean menuDispensing = false; // scherm die laat zien dat de automaat aan het dispensen is
    public boolean menuBon = false; //menu waar je kan kiezen voor een bon
    public boolean menuFinal = false;
    public boolean pointOfNoReturn = false;

    private User user;
    private String version = "1.3.2";
    private LogIn login;
    public SerialConnection serialConnection;
    public Connection conn;

    Gui(SerialConnection serialConnection, Connection conn){
        this.conn = conn;
         login = new LogIn(this, conn);
         this.serialConnection = serialConnection;
    }

    private JFrame frame = new JFrame("BankApp V" + version);
     public JPanel panelStart = new JPanel();
     public JPanel panelMain = new JPanel();
     public JPanel panelChooseAmount = new JPanel();
     public JPanel panelBon = new JPanel();
     public JPanel panelPassword = new JPanel();
     public JPanel panelCustomAmount = new JPanel();
     public JPanel panelShowBal = new JPanel();
     public JPanel panelFinalizeTransaction = new JPanel();
     public JPanel panelOptions = new JPanel();
     public JPanel panelDispensing = new JPanel();
     private JTextArea taReceiptPrinted = new JTextArea("Your receipt is being printed");

    JTextArea[] title = new JTextArea[array_length];
    JTextArea[] afbreken = new JTextArea[array_length];
    JTextArea[] taNaarHoofdmenu = new JTextArea[array_length];
    JTextArea[] taInsufficientBills = new JTextArea[array_length];//panelchooseamount = 2  panelmain = 0  panelcustomamount = 1
    JTextArea[] taInsufficientMoney = new JTextArea[array_length];//panelchooseamount = 2  panelmain = 0  panelcustomamount = 1

    public JTextArea option1 = new JTextArea("ERROR");
    public JTextArea option2 = new JTextArea("ERROR");
    public JTextArea option3 = new JTextArea("ERROR");
    public JTextArea option4 = new JTextArea("ERROR");

    public Timer logoutTimer = new Timer(30000, this); //todo !!!naar 30000 zetten na het testen!!!

    public JTextField passwordTextField = new JTextField(4);
    public JFormattedTextField customBedragField = new JFormattedTextField();
    private JTextArea taShowBal = new JTextArea();
    private JTextArea taPanelStart = new JTextArea("Scan your pass to continue");
    private JTextArea taInvalidInput = new JTextArea("Enter numbers between 0-9,\nand where the last number is 0 or 5.\nMaximum allowed amount: 250");
    private JTextArea taEnterPin = new JTextArea("ENTER PIN");
    private JTextArea taDispensing = new JTextArea("Dispensing...");
    private JTextArea taFastPin = new JTextArea("Quick €70 Withdrawal [1]");
    private JTextArea taBalance = new JTextArea("Balance [3]");
    private JTextArea taChooseAmount = new JTextArea("Choose Amount [2]");
    private JTextArea taNoBon = new JTextArea("No [2]");
    private JTextArea taYesBon = new JTextArea("Yes [1]");
    private JTextArea taAskReceipt = new JTextArea("Do you want a receipt?");
    private JTextArea taBedrag1 = new JTextArea("20 [1]");
    private JTextArea taBedrag2 = new JTextArea("50 [2]");
    private JTextArea taBedrag3 = new JTextArea("100 [3]");
    private JTextArea taBedrag4 = new JTextArea("150 [4]");
    private JTextArea taOtherAmount = new JTextArea("Other Amount [5]");
    private JTextArea taCustomAmount = new JTextArea("Enter custom amount:");
    private JTextArea continueCustomAmount = new JTextArea("Continue [A]");
    private JTextArea taThanksMessage = new JTextArea("Have a nice day!");
    private JTextArea passBlocked = new JTextArea("Your pass has been blocked, please contact your bank");
    private JTextArea wrongPassword = new JTextArea("The pin that you entered is incorrect, please try again");
    public JTextArea numberAttempts = new JTextArea("Attempts left:");
    private JTextArea errorPassNotFound = new JTextArea("Can't read the pass: put the pass close to the scanner \n " +
            "If this problem persists, please contact your bank");
    private Font font = new Font("Neo Sans", Font.BOLD, 50);
    private Font fontTitle = new Font("Segoe Script", Font.BOLD, 70);
    private Image icon = getToolkit().getImage(("GUI/resources/icon.jpg"));

    public void setUser(User user) {
        this.user = user;
    }

    void createLayout(){
        for(int i = 0; i < array_length; i++){
            title[i] = new JTextArea("ItsFreeStønçksÉstÅte");
            title[i].setEditable(false);
            title[i].setFont(fontTitle);
            title[i].setBackground(Color.CYAN);
            title[i].setBounds(610,10,800,100);

            afbreken[i] = new JTextArea("Abort [B]");
            afbreken[i].setEditable(false);
            afbreken[i].setBackground(Color.CYAN);
            afbreken[i].setFont(font);
            afbreken[i].setForeground(Color.RED);
            afbreken[i].setBounds(1400,900, 300,70);

            taNaarHoofdmenu[i] = new JTextArea("Back to main menu [C]");
            taNaarHoofdmenu[i].setEditable(false);
            taNaarHoofdmenu[i].setBackground(Color.CYAN);
            taNaarHoofdmenu[i].setFont(font);
            taNaarHoofdmenu[i].setBounds(50,900,600,70);

            taInsufficientMoney[i] = new JTextArea("There is not enough balance to perform this action");
            taInsufficientMoney[i].setBackground(Color.CYAN);
            taInsufficientMoney[i].setEditable(false);
            taInsufficientMoney[i].setFont(font);
            taInsufficientMoney[i].setVisible(false);
            taInsufficientMoney[i].setForeground(Color.RED);
            taInsufficientMoney[i].setBounds(300,230,1350,70);

            taInsufficientBills[i] = new JTextArea("There are not enough bills to perform this action");
            taInsufficientBills[i].setBackground(Color.CYAN);
            taInsufficientBills[i].setEditable(false);
            taInsufficientBills[i].setFont(font);
            taInsufficientBills[i].setVisible(false);
            taInsufficientBills[i].setForeground(Color.RED);
            taInsufficientBills[i].setBounds(300,150,1350,70);
        }
    }

    void createApp(){
        createLayout();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1920,1080));
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.jpg"));
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true); // zorgt ervoor dat je niet zomaar uit het programma kan klikken
        frame.add(panelMain);
        frame.setIconImage(icon);
        //PanelStart
        panelStart.setLayout(null);
        panelStart.setBackground(Color.CYAN);
        panelStart.add(taPanelStart);
        panelStart.add(title[0]);
        panelStart.add(errorPassNotFound);
        errorPassNotFound.setVisible(false);
        errorPassNotFound.setForeground(Color.red);
        errorPassNotFound.setFont(font);
        errorPassNotFound.setBackground(Color.CYAN);
        errorPassNotFound.setEditable(false);
        taPanelStart.setFont(font);
        taPanelStart.setEditable(false);
        taPanelStart.setBackground(Color.CYAN);
        errorPassNotFound.setBounds(360,600,1400,140);
        taPanelStart.setBounds(620,250,800,70);
        title[0].setBounds(610,10,800,100);
        //PanelPassword
        panelPassword.setLayout(null);
        panelPassword.add(taEnterPin);
        panelPassword.add(passwordTextField);
        panelPassword.add(title[1]);
        panelPassword.add(wrongPassword);
        panelPassword.add(numberAttempts);
        panelPassword.add(passBlocked);
        passBlocked.setEditable(false);
        passBlocked.setBackground(Color.CYAN);
        passBlocked.setFont(font);
        passBlocked.setForeground(Color.RED);
        passBlocked.setVisible(true);
        wrongPassword.setBackground(Color.CYAN);
        wrongPassword.setFont(font);
        wrongPassword.setEditable(false);
        wrongPassword.setForeground(Color.RED);
        wrongPassword.setVisible(false);
        numberAttempts.setBackground(Color.CYAN);
        numberAttempts.setFont(font);
        numberAttempts.setEditable(false);
        numberAttempts.setForeground(Color.orange);
        panelPassword.setBackground(Color.CYAN);
        taEnterPin.setFont(font);
        taEnterPin.setEditable(false);
        taEnterPin.setBackground(Color.CYAN);
        passwordTextField.setFont(font);
        passwordTextField.setBackground(Color.CYAN);
        numberAttempts.setBounds(750, 400,450,70);
        wrongPassword.setBounds(300, 500, 1400,70);
        taEnterPin.setBounds(820,270,400,70);
        passwordTextField.setBounds(860,340,200,40);
        passBlocked.setBounds(350, 600, 1400, 70);
        //PanelMain
        panelMain.setLayout(null);
        panelMain.setBackground(Color.CYAN);
        panelMain.add(title[2]);
        panelMain.add(taFastPin);
        panelMain.add(taChooseAmount);
        panelMain.add(taBalance);
        panelMain.add(afbreken[0]);
        panelMain.add(taInsufficientBills[0]);
        panelMain.add(taInsufficientMoney[0]);
        taFastPin.setBackground(Color.CYAN);
        taChooseAmount.setBackground(Color.CYAN);
        taBalance.setBackground(Color.CYAN);
        taFastPin.setFont(font);
        taChooseAmount.setFont(font);
        taBalance.setFont(font);
        taFastPin.setEditable(false);
        taChooseAmount.setEditable(false);
        taBalance.setEditable(false);
        taFastPin.setBounds(50,300, 640,70);
        taChooseAmount.setBounds(1400,300, 500,70);
        taBalance.setBounds(50,900, 300,70);
        //panel dispensing
        panelDispensing.setLayout(null);
        panelDispensing.add(title[7]);
        panelDispensing.add(taDispensing);
        taDispensing.setFont(font);
        taDispensing.setEditable(false);
        taDispensing.setBackground(Color.CYAN);
        panelDispensing.setBackground(Color.CYAN);
        taDispensing.setBounds(800,400,310,70);
        //panelChooseAmount
        panelChooseAmount.setBackground(Color.CYAN);
        panelChooseAmount.setLayout(null);
        panelChooseAmount.add(afbreken[3]);
        panelChooseAmount.add(taNaarHoofdmenu[0]);
        panelChooseAmount.add(title[5]);
        panelChooseAmount.add(taBedrag1);
        panelChooseAmount.add(taBedrag2);
        panelChooseAmount.add(taBedrag3);
        panelChooseAmount.add(taBedrag4);
        panelChooseAmount.add(taOtherAmount);
        panelChooseAmount.add(taInsufficientBills[2]);
        panelChooseAmount.add(taInsufficientMoney[2]);
        taBedrag1.setEditable(false);
        taBedrag2.setEditable(false);
        taBedrag3.setEditable(false);
        taBedrag4.setEditable(false);
        taOtherAmount.setEditable(false);
        taBedrag1.setFont(font);
        taBedrag2.setFont(font);
        taBedrag3.setFont(font);
        taBedrag4.setFont(font);
        taOtherAmount.setFont(font);
        taBedrag1.setBackground(Color.CYAN);
        taBedrag2.setBackground(Color.CYAN);
        taBedrag3.setBackground(Color.CYAN);
        taBedrag4.setBackground(Color.CYAN);
        taOtherAmount.setBackground(Color.CYAN);
        taBedrag1.setBounds(50,300, 620,70);
        taBedrag2.setBounds(1400, 300, 200, 70);
        taBedrag3.setBounds(50, 500, 200, 70);
        taBedrag4.setBounds(1400, 500, 200, 70);
        taOtherAmount.setBounds(50, 700, 600, 70);
        //panelOptions
        panelOptions.setBackground(Color.CYAN);
        panelOptions.setLayout(null);
        panelOptions.add(title[6]);
        panelOptions.add(afbreken[4]);
        panelOptions.add(option1);
        panelOptions.add(option2);
        panelOptions.add(option3);
        panelOptions.add(option4);
        panelOptions.add(taNaarHoofdmenu[1]);
        option1.setEditable(false);
        option2.setEditable(false);
        option3.setEditable(false);
        option4.setEditable(false);
        option1.setBackground(Color.CYAN);
        option2.setBackground(Color.CYAN);
        option3.setBackground(Color.CYAN);
        option4.setBackground(Color.CYAN);
        option1.setFont(font);
        option2.setFont(font);
        option3.setFont(font);
        option4.setFont(font);
        option1.setBounds(50,200, 310,350);
        option2.setBounds(1400, 200, 310, 350);
        option3.setBounds(50, 550, 310, 350);
        option4.setBounds(1400, 550, 310, 350);
        //panelBon
        panelBon.setLayout(null);
        panelBon.setBackground(Color.CYAN);
        panelBon.add(afbreken[1]);
        panelBon.add(taYesBon);
        panelBon.add(taNoBon);
        panelBon.add(title[3]);
        panelBon.add(taAskReceipt);
        taAskReceipt.setFont(font);
        taAskReceipt.setBackground(Color.CYAN);
        taYesBon.setFont(font);
        taNoBon.setFont(font);
        taYesBon.setEditable(false);
        taNoBon.setEditable(false);
        taAskReceipt.setEditable(false);
        taYesBon.setBackground(Color.CYAN);
        taNoBon.setBackground(Color.CYAN);
        taAskReceipt.setBounds(710,150,800,70);
        taYesBon.setBounds(50,380,200,200); //temp
        taNoBon.setBounds(1400, 380, 200,200); //temp
        //panelShowBal
        panelShowBal.setLayout(null);
        panelShowBal.add(taShowBal);
        panelShowBal.add(afbreken[2]);
        panelShowBal.add(title[4]);
        panelShowBal.add(taNaarHoofdmenu[2]);
        taShowBal.setFont(font);
        panelShowBal.setBackground(Color.CYAN);
        taShowBal.setEditable(false);
        taShowBal.setBackground(Color.CYAN);
        taShowBal.setBounds(700, 300, 900, 200);
        //panelCustomAmount
        panelCustomAmount.setLayout(null);
        panelCustomAmount.setBackground(Color.CYAN);
        panelCustomAmount.add(title[8]);
        panelCustomAmount.add(taInvalidInput);
        panelCustomAmount.add(taNaarHoofdmenu[3]);
        panelCustomAmount.add(afbreken[5]);
        panelCustomAmount.add(customBedragField);
        panelCustomAmount.add(taCustomAmount);
        panelCustomAmount.add(taInsufficientBills[1]);
        panelCustomAmount.add(taInsufficientMoney[1]);
        panelCustomAmount.add(continueCustomAmount);
        continueCustomAmount.setEditable(false);
        continueCustomAmount.setFont(font);
        continueCustomAmount.setBackground(Color.CYAN);
        customBedragField.setColumns(2);
        taInvalidInput.setEditable(false);
        taInvalidInput.setBackground(Color.CYAN);
        taInvalidInput.setFont(font);
        taInvalidInput.setForeground(Color.RED);
        taInvalidInput.setVisible(false);
        taCustomAmount.setEditable(false);
        taCustomAmount.setBackground(Color.CYAN);
        taCustomAmount.setFont(font);
        customBedragField.setBackground(Color.CYAN);
        customBedragField.setFont(font);
        taCustomAmount.setBounds(400,400,700,70);
        customBedragField.setBounds(950,400,300,70);
        taInvalidInput.setBounds(600,500,910,200);
        continueCustomAmount.setBounds(1400,400,700,70);
        //panelFinalizeTransaction
        panelFinalizeTransaction.setLayout(null);
        panelFinalizeTransaction.setBackground(Color.CYAN);
        panelFinalizeTransaction.add(title[9]);
        panelFinalizeTransaction.add(taReceiptPrinted);
        panelFinalizeTransaction.add(taThanksMessage);
        taThanksMessage.setEditable(false);
        taThanksMessage.setBackground(Color.CYAN);
        taThanksMessage.setFont(font);
        taReceiptPrinted.setEditable(false);
        taReceiptPrinted.setBackground(Color.CYAN);
        taReceiptPrinted.setFont(font);
        taReceiptPrinted.setBounds(650,150,750,70);
        taThanksMessage.setBounds(750,550,750,70);

        eventHandler();

      /*  mainFrame.getContentPane().add(BorderLayout.SOUTH, );*/
//        frame.getContentPane().add(BorderLayout.NORTH, taPanelMain);
        frame.getContentPane().add(BorderLayout.CENTER, panelStart);
        frame.setVisible(true);
    }

    public void changePanel(JPanel panel){
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    private void eventHandler(){
        logoutTimer.setActionCommand("abort");
        logoutTimer.start();
    }

    private void resetFlags(){
        menuBalance = false;
        menuLogin = false;
        menuStart = false;
        menuMain = false;
        menuBon = false;
        menuChooseAmounts = false;
        menuCustomAmount = false;
        menuDispensing = false;
        menuMoneyOptions = false;
        menuFinal = false;
    }

    public void arduinoInputHandler(String input){
        logoutTimer.restart(); //resets the 30 second logout timer

        if(!menuStart && !pointOfNoReturn) {
            if("ArdSend_B".equals(input)){
                //code om uit te loggen en naar het startscherm te gaan
                System.out.println("aborting...");
                if(user != null) user.userLogout();
                taShowBal.setText("");
                taInvalidInput.setVisible(false);
                taInsufficientBills[0].setVisible(false);
                taInsufficientMoney[0].setVisible(false);
                panelChooseAmount.add(taInsufficientMoney[2]);
                login.clearLoginVariables();
                resetFlags();
                menuStart = true;
                changePanel(panelStart);

                serialConnection.stringOut("abort");
                return;
            }

            if(!menuLogin) {
                if ("ArdSend_C".equals(input)) {
                    //code om naar het hoofdmenu te gaan
                    System.out.println("naar hoofdmenu");
                    user.toMainMenu();
                    taInvalidInput.setVisible(false);
                    taInsufficientBills[0].setVisible(false);
                    taInsufficientMoney[0].setVisible(false);
                    panelChooseAmount.add(taInsufficientMoney[2]);
                    resetFlags();
                    menuMain = true;
                    changePanel(panelMain);
//            serialConnection.stringOut("mainMenu"); //todo arduino code voor hoofdmenu
                    return;
                }
            }
        }

        if(menuStart && input.contains("ArdPassNr_")){
            String temp = input.replace("ArdPassNr_", "");
            login.checkPassnumber(temp);
            if(!("".equals(login.getPassnumber()))) {
                System.out.println("Naar inlogscherm");
                try{
                    numberAttempts.setText("Attempts left: " + login.getPogingenfromDB());
                } catch (SQLException e){
                    e.printStackTrace();
                }

                menuLogin = true;
                menuStart = false;
                passwordTextField.setText("");
                errorPassNotFound.setVisible(false);
                wrongPassword.setVisible(false);
                passBlocked.setVisible(false);
                changePanel(panelPassword);
            } else {
                errorPassNotFound.setVisible(true);
            }
        }

        //dit is voor de pincode invoeren
        if(menuLogin){
            if("ArdSend_*".equals(input)){
                passwordTextField.setText(passwordTextField.getText() + "*");
            }

            if("ArdSend_#".equals(input)){
                System.out.println("attempt login");
                try{
                    if(login.checkLogin()) {  //test string: "2A 9F 0D 0B" //juiste code is: login.getPassnumber()
                        user.requestUserVariables();
                        taShowBal.setText("Your balance is: " + (user.getBalance()));
                        wrongPassword.setVisible(false);
                        menuMain = true;
                        menuLogin = false;

                        serialConnection.stringOut("success");


                        changePanel(panelMain);
                    } else if (login.pogingen >= 3) {
                        passBlocked.setVisible(true);
                    } else {
                        wrongPassword.setVisible(true);
                        //serialConnection.stringOut("");
                        numberAttempts.setText("Attempts left: " + login.getPogingenfromDB());
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    passwordTextField.setText("");
                }
            }

            if("ArdSend_D".equals(input)){
                passwordTextField.setText("");
            }

            if(input.contains("ArdPinHashed_")){
                String temp = input.replace("ArdPinHashed_", "");
                login.setHashedPIN(temp);
                System.out.println("hashed pin: " + temp);
            }
            return;
        }

        if(menuMain){
            if("ArdSend_1".equals(input)){
                //70 euro pinnen
                if (user.getBalance() - 70 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                    panelMain.add(taInsufficientMoney[0]);
                    taInsufficientMoney[0].setVisible(true);
                } else {
                    System.out.println("70 euro");
                    user.makeWithdrawal();
                    user.withdrawal.customWithdrawal(70);
                    try {
                        user.withdrawal.displayOptions();
                    } catch (Exception ex) {
                            ex.printStackTrace();
                    }
                    menuMain = false;
                    menuMoneyOptions = true;
                    changePanel(panelOptions);
                }
            }

            if("ArdSend_2".equals(input)){
                //pin menu
                System.out.println("custom bedrag pinnen");
                taInsufficientMoney[0].setVisible(false);
                panelChooseAmount.add(taInsufficientMoney[2]);
                menuMain = false;
                menuChooseAmounts = true;
                changePanel(panelChooseAmount);
            }

            if("ArdSend_3".equals(input)){
                System.out.println("showing balance");
                //balance menu
                menuMain = false;
                menuBalance = true;
                changePanel(panelShowBal);
            }
            return;
         }

        if(menuChooseAmounts){
            if("ArdSend_1".equals(input)) {
                //pin 20
                if (amounts[0] > 4 && amounts[1] > 2 && amounts[2] > 1 && amounts[3] > 0) {
                    if (user.getBalance() - 20 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                        taInsufficientMoney[2].setVisible(true);
                    } else {
                        System.out.println("20 euro");
                        user.makeWithdrawal();
                        user.withdrawal.customWithdrawal(20);
                        try {
                            user.withdrawal.displayOptions();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        menuChooseAmounts = false;
                        menuMoneyOptions = true;
                        changePanel(panelOptions);
                    }
                    return;
                } else {
                    taInsufficientBills[2].setVisible(true);
                }
            }

            if("ArdSend_2".equals(input)) {
                //pin 50
                if (amounts[0] > 9 && amounts[1] > 3 && amounts[2] > 1 && amounts[3] > 1) {
                    if (user.getBalance() - 50 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                        taInsufficientMoney[2].setVisible(true);
                    } else {
                        System.out.println("50 euro");
                        user.makeWithdrawal();
                        user.withdrawal.customWithdrawal(50);
                        try {
                            user.withdrawal.displayOptions();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        menuChooseAmounts = false;
                        menuMoneyOptions = true;
                        changePanel(panelOptions);
                    }
                    return;
                } else {
                    taInsufficientBills[2].setVisible(true);
                }
            }

            if("ArdSend_3".equals(input)){
                //pin 100
                if (amounts[0] > 0 && amounts[1] > 7 && amounts[2] > 3 && amounts[3] > 1) {
                    if (user.getBalance() - 100 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                        taInsufficientMoney[2].setVisible(true);
                    } else {
                        System.out.println("100 euro");
                        user.makeWithdrawal();
                        user.withdrawal.customWithdrawal(100);
                        try {
                            user.withdrawal.displayOptions();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        menuChooseAmounts = false;
                        menuMoneyOptions = true;
                        changePanel(panelOptions);
                    }
                    return;
                } else {
                    taInsufficientBills[2].setVisible(true);
                }
            }

            if("ArdSend_4".equals(input)){
                //pin 150
                if (amounts[0] > 0 && amounts[1] > 10 && amounts[2] > 4 && amounts[3] > 2) {
                    if (user.getBalance() - 150 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                        taInsufficientMoney[2].setVisible(true);
                    } else {
                        System.out.println("150 euro");
                        user.makeWithdrawal();
                        user.withdrawal.customWithdrawal(150);
                        try {
                            user.withdrawal.displayOptions();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        menuChooseAmounts = false;
                        menuMoneyOptions = true;
                        changePanel(panelOptions);
                    }
                    return;
                } else {
                    taInsufficientBills[2].setVisible(true);
                }
            }

            if("ArdSend_5".equals(input)){
                // pin custom amount
                System.out.println("custom bedrag pinnen");
                taInsufficientMoney[2].setVisible(false);
                panelChooseAmount.add(taInsufficientMoney[2]);
                menuChooseAmounts = false;
                menuCustomAmount = true;
                changePanel(panelCustomAmount);
            }
            return;
        }

        if(menuMoneyOptions){
            if("ArdSend_1".equals(input)) {

                try {
                    if(option1.isVisible()){
                        menuMoneyOptions = false;
                        menuDispensing = true;
                        pointOfNoReturn = true;
                        changePanel(panelDispensing);
                        user.withdrawal.sendArray(1);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            if("ArdSend_2".equals(input)) {
                try {
                    if(option2.isVisible()){
                        menuMoneyOptions = false;
                        menuDispensing = true;
                        pointOfNoReturn = true;
                        changePanel(panelDispensing);
                        user.withdrawal.sendArray(2);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            if("ArdSend_3".equals(input)) {
                try {
                    if(option3.isVisible()) {
                        menuMoneyOptions = false;
                        menuDispensing = true;
                        pointOfNoReturn = true;
                        changePanel(panelDispensing);
                        user.withdrawal.sendArray(3);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            if("ArdSend_4".equals(input)) {
                try {
                    if(option4.isVisible()){
                        menuMoneyOptions = false;
                        menuDispensing = true;
                        pointOfNoReturn = true;
                        changePanel(panelDispensing);
                        user.withdrawal.sendArray(4);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            return;
        }

        if(menuCustomAmount){
            if("ArdSend_1".equals(input)) {
                customBedragField.setText(customBedragField.getText() + "1");
            }

            if("ArdSend_2".equals(input)) {
                customBedragField.setText(customBedragField.getText() + "2");
            }

            if("ArdSend_3".equals(input)) {
                customBedragField.setText(customBedragField.getText() + "3");
            }

            if("ArdSend_4".equals(input)) {
                customBedragField.setText(customBedragField.getText() + "4");
            }

            if("ArdSend_5".equals(input)) {
                customBedragField.setText(customBedragField.getText() + "5");
            }

            if("ArdSend_6".equals(input)) {
                customBedragField.setText(customBedragField.getText() + "6");
            }

            if("ArdSend_7".equals(input)) {
                customBedragField.setText(customBedragField.getText() + "7");
            }

            if("ArdSend_8".equals(input)) {
                customBedragField.setText(customBedragField.getText() + "8");
            }

            if("ArdSend_9".equals(input)) {
                customBedragField.setText(customBedragField.getText() + "9");
            }

            if("ArdSend_0".equals(input)) {
                customBedragField.setText(customBedragField.getText() + "0");
            }

            if("ArdSend_A".equals(input)) {
                //next page
                taInsufficientMoney[0].setVisible(false);
                taInsufficientBills[0].setVisible(false);
                taInsufficientMoney[1].setVisible(false);
                taInsufficientBills[1].setVisible(false);
                if(!("".equals(customBedragField.getText()))) {
                    try {
                        int tempInt = Integer.parseInt(customBedragField.getText());
                        if(tempInt%5 != 0 || tempInt < 0 || tempInt > 250) throw new NumberFormatException();
                        if (user.getBalance() - tempInt < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt, en of het getal eindigt met 0 of 5
                            panelCustomAmount.add(taInsufficientMoney[1]);
                            taInsufficientMoney[1].setVisible(true);
                        }

                        else {
                            System.out.println("custom bedrag: " + tempInt);
                            user.makeWithdrawal();
                            user.withdrawal.customWithdrawal(tempInt);
                            try{
                                user.withdrawal.displayOptions();
                                menuCustomAmount = false;
                                menuMoneyOptions = true;
                                changePanel(panelOptions);
                            }catch (Exception ex){
                                taInsufficientBills[1].setVisible(true);
                            }
                        }
                    } catch (NumberFormatException nfe) {
                        System.out.println("ERROR: invalid input");
                        customBedragField.setText("");
                        taInvalidInput.setVisible(true);
                    }
                }
            }

            if("ArdSend_D".equals(input)) {
                //remove line
                customBedragField.setText("");
            }
            return;
        }

        if(menuDispensing){
            if("ArdSend_finish".equals(input)){
                changePanel(panelBon);
                menuDispensing = false;
                menuBon = true;
            }
            return;
        }

        if(menuBon){
            if("ArdSend_1".equals(input)) {
                //bon printen
                System.out.println("Bon printen");
                serialConnection.stringOut("printBon");
                menuBon = false;
                menuFinal = true;
                pointOfNoReturn = false;
                taReceiptPrinted.setVisible(true);
                taThanksMessage.setVisible(true);
                changePanel(panelFinalizeTransaction);
            }

            if("ArdSend_2".equals(input)) {
                //no receipt
                System.out.println("Bon niet printen");
                menuBon = false;
                menuFinal = true;
                pointOfNoReturn = false;
                taReceiptPrinted.setVisible(false);
                taThanksMessage.setVisible(true);
                changePanel(panelFinalizeTransaction);
            }
            return;
        }
        if(menuFinal){
            if(input.contains("ArdSend_")) arduinoInputHandler("ArdSend_B");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logoutTimer.restart();

        if ("abort".equalsIgnoreCase(e.getActionCommand())) {
            arduinoInputHandler("ArdSend_B");
        }
    }
}