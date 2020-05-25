package mainPackage;

import mainPackage.User.*;
import mainPackage.serialconnection.SerialConnection;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Arrays;
import static java.lang.Thread.sleep;


import static javax.swing.JOptionPane.showMessageDialog;

public class Gui extends JFrame implements ActionListener {
    public int array_length = 10;
    private int amount4 = 10; //
    private int amount3 = 10;
    private int amount2 = 10;
    private int amount1 = 10;
    public int[] amounts = {amount1, amount2, amount3, amount4}; // volgorde biljetten: 5, 10, 20, 50

    private User user;
    private String version = "1.1.7";
    private LogIn login;
    public SerialConnection serialConnection;

    Gui(SerialConnection serialConnection){
         login = new LogIn(this);
         this.serialConnection = serialConnection;
    }

    private JFrame frame = new JFrame("BankApp V" + version);
     public JPanel panelStart = new JPanel();
     public JPanel panelMain = new JPanel();
     public JPanel options1 = new JPanel();
     private JButton option1 = new JButton("4x5");
     private JButton option2 = new JButton("2x10");
     private JButton option3 = new JButton("1x20");
     private JButton test = new JButton("TEST");
     public JPanel panelChooseAmount = new JPanel();
     public JPanel panelBon = new JPanel();
     public JPanel panelPassword = new JPanel();
     public JPanel panelCustomAmount = new JPanel();
     public JPanel panelShowBal = new JPanel();
     public JPanel panelFinalizeTransaction = new JPanel();
     private JTextArea tempTa = new JTextArea("hier komt overzichtelijk de transactie informatie \nterwijl het geld uit de dispenser komt");

    private JButton yesBon = new JButton("Yes");
    private JButton noBon = new JButton("No");
    private JButton quickPin = new JButton("Snel €70,- pinnen");
    private JButton showBal = new JButton("Bekijk saldo");
    private JButton customPin = new JButton("Kies bedrag");

    JButton[] abort = new JButton[array_length];
    JButton[] naarHoofdMenu = new JButton[array_length];
    JButton[] nextPage = new JButton[array_length];

    private JButton bedrag1 = new JButton("20");
    private JButton bedrag2 = new JButton("50");
    private JButton bedrag3 = new JButton("100");
    private JButton bedrag4 = new JButton("150");
    private JButton anderBedrag = new JButton("Ander bedrag");

    public Timer logoutTimer = new Timer(30000, this);

    public JPasswordField passwordField = new JPasswordField(4);
    public JFormattedTextField customBedragField = new JFormattedTextField();
    private JTextArea taShowBal = new JTextArea();
    private JTextArea taPanelStart = new JTextArea("Scan uw pas om verder te gaan");
    private JTextArea taInvalidInput = new JTextArea("Voer getallen in tussen 0-9,\nen waar het laatste getal 0 of 5 is. \nAndere karakters zijn niet toegestaan!");
    private JTextArea taInsufficientBills = new JTextArea("Er zijn niet genoeg biljetten om deze actie uit te voeren");
    private JTextArea taInsufficientMoney = new JTextArea("Er is niet genoeg saldo om deze actie uit te voeren");
//    public void setSerialConnection(SerialConnection serialConnection){
//        this.serialConnection = serialConnection;
//    }

    private void createButonArrays(){
        for(int i = 0; i < array_length; i++){
            naarHoofdMenu[i] = new JButton("Terug naar het hoofdmenu");
            abort[i] = new JButton("Afbreken");
            nextPage[i] = new JButton("Volgende");// temp array, zal wss niet nodig zijn bij het eindproduct
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

    void createApp(){
        createButonArrays();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        mainFrame.setUndecorated(true); // zorgt ervoor dat je niet zomaar uit het programma kan klikken
        frame.add(panelMain);
        panelMain.add(quickPin);
        panelMain.add(showBal);
        panelMain.add(customPin);
        panelMain.add(abort[0]);

        panelStart.add(taPanelStart);
        taPanelStart.setEditable(false);
        panelStart.add(nextPage[0]);

        panelChooseAmount.add(bedrag1);
        panelChooseAmount.add(bedrag2);
        panelChooseAmount.add(bedrag3);
        panelChooseAmount.add(bedrag4);
        panelChooseAmount.add(anderBedrag);
        panelChooseAmount.add(abort[1]);
        panelChooseAmount.add(naarHoofdMenu[1]);

        panelBon.add(noBon);
        panelBon.add(yesBon);
        panelBon.add(abort[2]);
        panelBon.add(naarHoofdMenu[2]);

        panelPassword.add(new JLabel("Enter PIN:"));
        panelPassword.add(passwordField);
        panelPassword.add(nextPage[1]);

        panelCustomAmount.add(new JLabel("Voer aangepast bedrag in:"));
        panelCustomAmount.add(customBedragField);
        customBedragField.setColumns(10);
        panelCustomAmount.add(taInvalidInput);
        taInvalidInput.setEditable(false);
        taInvalidInput.setVisible(false);
        taInvalidInput.setForeground(Color.RED);

        panelCustomAmount.add(nextPage[2]);
        panelCustomAmount.add(abort[3]);
        panelCustomAmount.add(naarHoofdMenu[3]);

        panelShowBal.add(taShowBal);
        taShowBal.setEditable(false);
        panelShowBal.add(abort[4]);
        panelShowBal.add(naarHoofdMenu[4]);

        panelFinalizeTransaction.add(tempTa);
        panelFinalizeTransaction.add(abort[6]);

        options1.add(option1);
        options1.add(option2);
        options1.add(option3);
        panelMain.add(test);
        options1.add(abort[5]);
        options1.add(naarHoofdMenu[5]);

        options1.add(taInsufficientBills);
        panelChooseAmount.add(taInsufficientBills);
        taInsufficientBills.setEditable(false);
        taInsufficientBills.setVisible(false);
        taInsufficientBills.setForeground(Color.RED);

        options1.add(taInsufficientMoney);
        panelChooseAmount.add(taInsufficientMoney);
        taInsufficientMoney.setEditable(false);
        taInsufficientMoney.setVisible(false);
        taInsufficientMoney.setForeground(Color.RED);
        // code die ervoor zorgt dat er max 4 tekens ingevuld worden (van stackoverflow gepakt)
        PlainDocument documentPF = (PlainDocument) passwordField.getDocument();
        documentPF.setDocumentFilter(new DocumentFilter() {

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String string = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;

                if (string.length() <= 4) {
                    super.replace(fb, offset, length, text, attrs); //To change body of generated methods, choose Tools | Templates.
                }
            }

        });

        eventHandler();

// todo maak GridBagLayout waar de knoppen in worden toegevoegd.
//  remove panels van het JFrame als je een nieuwe erop wilt zetten
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
        quickPin.addActionListener(this);
        quickPin.setActionCommand("pin70");
        showBal.addActionListener(this);
        showBal.setActionCommand("showBal");
        customPin.addActionListener(this);
        customPin.setActionCommand("customPin");

        for(int i = 0; i < array_length; i++){
            abort[i].addActionListener(this);
            abort[i].setActionCommand("abort");
            abort[i].setBackground(Color.RED);
            naarHoofdMenu[i].addActionListener(this);
            naarHoofdMenu[i].setActionCommand("hoofdmenu");
        }

        yesBon.addActionListener(this);
        yesBon.setActionCommand("yesBon");
        noBon.addActionListener(this);
        noBon.setActionCommand("noBon");

        bedrag1.addActionListener(this);
        bedrag1.setActionCommand("customBedrag1");
        bedrag2.addActionListener(this);
        bedrag2.setActionCommand("customBedrag2");
        bedrag3.addActionListener(this);
        bedrag3.setActionCommand("customBedrag3");
        bedrag4.addActionListener(this);
        bedrag4.setActionCommand("customBedrag4");
        anderBedrag.addActionListener(this);
        anderBedrag.setActionCommand("anderBedrag");

        nextPage[0].addActionListener(this);
        nextPage[0].setActionCommand("inlogScherm");
        nextPage[1].addActionListener(this);
        nextPage[1].setActionCommand("wachtwoord");
        nextPage[2].addActionListener(this);
        nextPage[2].setActionCommand("custAmountToNext");

        test.addActionListener(this);
        test.setActionCommand("test");

        option1.addActionListener(this);
        option1.setActionCommand("option1");
        option2.addActionListener(this);
        option2.setActionCommand("option2");
        option3.addActionListener(this);
        option3.setActionCommand("option3");

        logoutTimer.setActionCommand("abort");
        logoutTimer.start();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logoutTimer.restart();
        if("option1".equalsIgnoreCase(e.getActionCommand())){
           if(amounts[0] < 4){
               taInsufficientBills.setVisible(true);
           }
                if (user.balance.getBalance() - 20 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                    taInsufficientMoney.setVisible(true);
                }
                   else {
                       amounts[0] = amounts[0] - 4;
                       System.out.println("20 euro");
                       user.makeWithdrawal();
                       //user.withdrawal.customWithdrawal(20);
                       changePanel(panelBon);
                       try {
                           sleep(1000);
                       } catch (Exception ex) {
                           ex.printStackTrace();
                       }


                       for (int i = 4; i > 0; i--) {
                           try {
                               sleep(2000);
                           } catch (Exception ez) {
                               ez.printStackTrace();
                           }
                           user.sendAmount1();
                       }
                       serialConnection.stringIn();
                       serialConnection.stringIn();
                       serialConnection.stringIn();
                       serialConnection.stringIn();
                       System.out.println("Array Amounts: " + amounts[0]);
                   }
        }
        if("option2".equalsIgnoreCase(e.getActionCommand())){
            if(amounts[1] < 2){
                taInsufficientBills.setVisible(true);
            }
                if (user.balance.getBalance() - 20 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                    taInsufficientMoney.setVisible(true);
                }
                else {
                    amounts[1] = amounts[1] - 2;
                    System.out.println("20 euro");
                    user.makeWithdrawal();
                    //user.withdrawal.customWithdrawal(20);
                    changePanel(panelBon);
                    try {
                        sleep(1000);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    for (int i = 2; i > 0; i--) {
                        try {
                            sleep(2000);
                        } catch (Exception ey) {
                            ey.printStackTrace();
                        }
                        user.sendAmount2();
                    }
                    serialConnection.stringIn();
                    serialConnection.stringIn();
                    System.out.println("Array Amounts: " + amounts[1]);
                }
        }
        if("option3".equalsIgnoreCase(e.getActionCommand())){
            if(amounts[2] < 1){
                taInsufficientBills.setVisible(true);
            }
                if (user.balance.getBalance() - 20 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                    taInsufficientMoney.setVisible(true);
                }
                    else {
                        amounts[3] = amounts[3] - 1;
                        System.out.println("20 euro");
                        user.makeWithdrawal();
                        //user.withdrawal.customWithdrawal(20);
                        changePanel(panelBon);
                        try {
                            sleep(2000);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        for (int i = 1; i > 0; i--) {
                            try {
                                sleep(2000);
                            } catch (Exception ey) {
                                ey.printStackTrace();
                            }
                            user.sendAmount3();
                        }
                        serialConnection.stringIn();
                        System.out.println("Array Amounts: " + amounts[3]);
                    }
        }
        if("option4".equalsIgnoreCase(e.getActionCommand())){
            if(amounts[3] < 1){
                taInsufficientBills.setVisible(true);
            }
            else {
                System.out.println("20 euro");
                user.makeWithdrawal();
                //user.withdrawal.customWithdrawal(20);
                changePanel(panelBon);
                try {
                    sleep(2000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                for (int i = 1; i > 0; i--) {
                    try {
                        sleep(2000);
                    } catch (Exception ey) {
                        ey.printStackTrace();
                    }
                    user.sendAmount4();
                }
                serialConnection.stringIn();
                serialConnection.stringIn();
                serialConnection.stringIn();
                serialConnection.stringIn();
                System.out.println("Array Amounts: " + amounts[3]);
            }
        }
        if("test".equalsIgnoreCase(e.getActionCommand())){
            changePanel(options1);

        }
        if("abort".equalsIgnoreCase(e.getActionCommand())){
            //code om uit te loggen en naar het startscherm te gaan
            System.out.println("aborting...");
            if(user != null) user.userLogout();
            taShowBal.setText("");
            taInvalidInput.setVisible(false);
            taInsufficientBills.setVisible(false);
            taInsufficientMoney.setVisible(false);
            changePanel(panelStart);
//            serialConnection.dataOut("abort"); // todo arduino code voor abort
        }

        if("hoofdmenu".equalsIgnoreCase(e.getActionCommand())){
            //code om naar het hoofdmenu te gaan
            System.out.println("naar hoofdmenu");
            user.toMainMenu();
            changePanel(panelMain);
//            serialConnection.dataOut("hoofdmenu"); //todo arduino code voor hoofdmenu
        }

        if("showBal".equalsIgnoreCase(e.getActionCommand())){
            //code om het saldo te laten zien
            System.out.println("showing balance");

            taShowBal.setText(Integer.toString(user.balance.getBalance()));
            changePanel(panelShowBal);
        }

        if("pin70".equalsIgnoreCase(e.getActionCommand())){
            //code om 70 euro te pinnen (kan via dezelfde methode als die voor hetzelfde bedrag)
            System.out.println("pin €70,-");
            user.makeWithdrawal();
            user.withdrawal.customWithdrawal(70);
            changePanel(panelBon);
        }

        if("customPin".equalsIgnoreCase(e.getActionCommand())) {

            System.out.println("custom bedrag pinnen");
            changePanel(panelChooseAmount);
        }

        if("yesBon".equalsIgnoreCase(e.getActionCommand()))   {
             System.out.println("Bon printen");
             serialConnection.stringOut("printBon");
             changePanel(panelBon);

        }

        if("noBon".equalsIgnoreCase(e.getActionCommand()))   {
             System.out.println("Bon niet printen");
             changePanel(panelFinalizeTransaction);
        }
        if("customBedrag1".equalsIgnoreCase(e.getActionCommand())){
            if(amount1 > 0) {
                if (user.balance.getBalance() - 20 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                    taInsufficientMoney.setVisible(true);
                } else {
                    System.out.println("20 euro");
                    user.makeWithdrawal();
                    user.withdrawal.customWithdrawal(20);
                    changePanel(panelBon);
                    amount1 = amount1 - 1;
                    System.out.println("Amount1:" + amount1);
                }
            }
            else{
                taInsufficientBills.setVisible(true);
            }
        }
        if("customBedrag2".equalsIgnoreCase(e.getActionCommand())){
            if(amount2 > 0) {
                if (user.balance.getBalance() - 50 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                    taInsufficientMoney.setVisible(true);
                } else {
                    System.out.println("50 euro");
                    user.makeWithdrawal();
                    user.withdrawal.customWithdrawal(50);
                    changePanel(panelBon);
                    amount2 = amount2 - 1;
                    System.out.println("Amount2:" + amount2);
                }
            }
            else{
                taInsufficientBills.setVisible(true);
            }
        }
        if("customBedrag3".equalsIgnoreCase(e.getActionCommand())){
            if(amount3 > 0) {
                if (user.balance.getBalance() - 100 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                    taInsufficientMoney.setVisible(true);
                } else {
                    System.out.println("100 euro");
                    user.makeWithdrawal();
                    user.withdrawal.customWithdrawal(100);
                    changePanel(panelBon);
                    amount3 = amount3 - 1;
                    System.out.println("Amount3:" + amount3);
                }
            }
            else{
                taInsufficientBills.setVisible(true);
            }
        }
        if("customBedrag4".equalsIgnoreCase(e.getActionCommand())){
            if(amount4 > 0) {
                if (user.balance.getBalance() - 150 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                    taInsufficientMoney.setVisible(true);
                } else {
                    System.out.println("150 euro");
                    user.makeWithdrawal();
                    user.withdrawal.customWithdrawal(150);
                    changePanel(panelBon);
                    amount4 = amount4 - 1;
                    System.out.println("Amount4:" + amount4);
                }
            }
            else{
                taInsufficientBills.setVisible(true);
                }
        }

        if("inlogScherm".equalsIgnoreCase((e.getActionCommand()))){
             login.setRfidDetected(true);
             System.out.println("Naar inlogscherm");
             changePanel(panelPassword);
              //moet nog veranderd worden naar inlogscherm
        }

        if("wachtwoord".equalsIgnoreCase((e.getActionCommand()))){
            System.out.println("login");
            if(login.requestLogin()) {
                user.setPasswordCheck(passwordField.getPassword());
                System.out.println("passwd: " + Arrays.toString(user.getPasswordCheck()));
                passwordField.setText("");
//                user.requestUserVariables(); // old methods (do not use)
//                user.setUserVariables(); // old methods (do not use)
                changePanel(panelMain); //?moet nog veranderd worden naar inlogscherm?
            }
        }

        if("anderBedrag".equalsIgnoreCase((e.getActionCommand()))){
            System.out.println("ander bedrag invullen");
            customBedragField.setText("");
            changePanel(panelCustomAmount);
        }

        if("custAmountToNext".equalsIgnoreCase((e.getActionCommand()))){
            if(!("".equals(customBedragField.getText()))) {
                try {
                    int tempInt = Integer.parseInt(customBedragField.getText());
                    if(tempInt%5 != 0 || tempInt < 0) throw new NumberFormatException();
                    if (user.balance.getBalance() - tempInt < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt, en of het getal eindigt met 0 of 5
                        taInsufficientMoney.setVisible(true);
                    }

                    else {
                        System.out.println("custom bedrag: " + tempInt);
                        user.makeWithdrawal();
                        user.withdrawal.customWithdrawal(tempInt);
                        changePanel(panelBon);
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("ERROR: invalid input");
                    customBedragField.setText("");
                    taInvalidInput.setVisible(true);
                }
            }
        }
    }
}
