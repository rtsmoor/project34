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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import static java.lang.Thread.sleep;


import static javax.swing.JOptionPane.showMessageDialog;

public class Gui extends JFrame implements ActionListener {
    public int array_length = 10;
    private int amount4 = 100;
    private int amount3 = 100;
    private int amount2 = 100;
    private int amount1 = 100;
    public int[] amounts = {amount1, amount2, amount3, amount4}; // volgorde biljetten: 5, 10, 20, 50

    private boolean menuStart = true; //startscherm, is true aan het begin en nadat de gebruiker is uitgelogt
    private boolean menuLogin = false; //inlogmenu waar je je wachtwoord invult
    private boolean menuMain = false; //hoofdmenu(1ste scherm nadat je bent ingelogd)
    private boolean menuBalance = false; //geen uitleg nodig
    private boolean menuChooseAmounts = false; // keuzemenu voor 20,50,100,150 of ander bedrag
    private boolean menuCustomAmount = false; //menu waar je het bedrag invult dat je wilt hebben
    private boolean menuMoneyOptions = false; // menu waar je kiest in welke biljetten je je geld wilt hebben
    private boolean menuDispensing = false; // scherm die laat zien dat de automaat aan het dispensen is
    private boolean menuBon = false; //menu waar je kan kiezen voor een bon

    private User user;
    private String version = "1.2.4";
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
     public JPanel dispensing = new JPanel();
     private JTextArea tempTa = new JTextArea("hier komt overzichtelijk de transactie informatie \nterwijl het geld uit de dispenser komt");

    private JButton yesBon = new JButton("Yes");
    private JButton noBon = new JButton("No");
    private JButton quickPin = new JButton("Snel €70,- pinnen");
    private JButton showBal = new JButton("Bekijk saldo");
    private JButton customPin = new JButton("Kies bedrag");

    JButton[] abort = new JButton[array_length];
    JButton[] naarHoofdMenu = new JButton[array_length];
    JButton[] nextPage = new JButton[array_length];
    JTextArea[] title = new JTextArea[array_length];
    JTextArea[] afbreken = new JTextArea[array_length];

    private JButton bedrag1 = new JButton("20");
    private JButton bedrag2 = new JButton("50");
    private JButton bedrag3 = new JButton("100");
    private JButton bedrag4 = new JButton("150");
    private JButton anderBedrag = new JButton("Ander bedrag");

    public JButton optie1 = new JButton("ERROR");
    public JButton optie2 = new JButton("ERROR");
    public JButton optie3 = new JButton("ERROR");
    public JButton optie4 = new JButton("ERROR");

    public Timer logoutTimer = new Timer(300000, this); //todo !!!naar 30000 zetten na het testen!!!

    public JTextField passwordTextField = new JTextField(4);
    public JFormattedTextField customBedragField = new JFormattedTextField();
    private JTextArea taShowBal = new JTextArea();
    private JTextArea taPanelStart = new JTextArea("Scan uw pas om verder te gaan");
    private JTextArea taInvalidInput = new JTextArea("Voer getallen in tussen 0-9,\nen waar het laatste getal 0 of 5 is. \nAndere karakters zijn niet toegestaan!");
    private JTextArea taInsufficientBills = new JTextArea("Er zijn niet genoeg biljetten om deze actie uit te voeren");
    private JTextArea taInsufficientMoney = new JTextArea("Er is niet genoeg saldo om deze actie uit te voeren");
    private JTextArea enterPin = new JTextArea("ENTER PIN");
    private JTextArea taDispensing = new JTextArea("Dispensing...");
    private JTextArea snelPinnen = new JTextArea("Quick €70 Withdrawal []");
    private JTextArea saldo = new JTextArea("Balance []");
    private JTextArea kiesBedrag = new JTextArea("Choose Amount []");
    private JTextArea taNoBon = new JTextArea("No []");
    private JTextArea taYesBon = new JTextArea("Yes []");
    private JTextArea receipt = new JTextArea("Do you want a receipt?");
    private Font font = new Font("Comic Sans MS", Font.BOLD, 50);
    private Font fontTitle = new Font("Segoe Script", Font.BOLD, 70);
    private ImageIcon img = new ImageIcon("/resources/background1.jpg");
    private JLabel background1 = new JLabel("", img, JLabel.CENTER);
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

    void createLayout(){
        for(int i = 0; i < array_length; i++){
            title[i] = new JTextArea("ItsFreeStønçksÉstÅte");
            title[i].setEditable(false);
            title[i].setFont(fontTitle);
            title[i].setBackground(Color.CYAN);
            title[i].setBounds(610,10,800,100);

            afbreken[i] = new JTextArea("Break []");
            afbreken[i].setEditable(false);
            afbreken[i].setBackground(Color.CYAN);
            afbreken[i].setFont(font);
            afbreken[i].setForeground(Color.RED);
            afbreken[i].setBounds(1650,700, 300,70);
        }
    }

    void createApp(){
        createLayout();
        createButonArrays();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(new Dimension(600,600));
        frame.setSize(new Dimension(1920,1080));
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.jpg"));
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        mainFrame.setUndecorated(true); // zorgt ervoor dat je niet zomaar uit het programma kan klikken
        frame.add(panelMain);
        panelMain.add(quickPin);
        panelMain.add(showBal);
        panelMain.add(customPin);


        //achtergrond
        background1.setBounds(0,0,1600,900);
        frame.add(background1);
        background1.setVisible(true);
        //PanelStart
        panelStart.setLayout(null);
        panelStart.add(nextPage[0]);
        panelStart.setBackground(Color.CYAN);
        panelStart.add(taPanelStart);
        panelStart.add(title[0]);
        taPanelStart.setFont(font);
        taPanelStart.setEditable(false);
        taPanelStart.setBackground(Color.CYAN);
        taPanelStart.setBounds(580,250,800,70);
        nextPage[0].setBounds(860,340,200,200);
        title[0].setBounds(610,10,800,100);
        //PanelPassword
        panelPassword.setLayout(null);
        panelPassword.add(enterPin);
        panelPassword.add(nextPage[1]);
        panelPassword.add(passwordTextField);
        panelPassword.add(title[1]);
        panelPassword.setBackground(Color.CYAN);
        enterPin.setFont(font);
        enterPin.setEditable(false);
        enterPin.setBackground(Color.CYAN);
        passwordTextField.setFont(font);
        passwordTextField.setBackground(Color.CYAN);
        enterPin.setBounds(820,270,400,70);
        passwordTextField.setBounds(860,340,200,40);
        nextPage[1].setBounds(860,540,200,200);
        //PanelMain
        panelMain.setLayout(null);
        panelMain.setBackground(Color.CYAN);
        panelMain.add(title[2]);
        panelMain.add(snelPinnen);
        panelMain.add(kiesBedrag);
        panelMain.add(saldo);
        panelMain.add(afbreken[0]);
        panelMain.add(quickPin);
        panelMain.add(showBal);
        panelMain.add(customPin);
        panelMain.add(abort[0]);
        snelPinnen.setBackground(Color.CYAN);
        kiesBedrag.setBackground(Color.CYAN);
        saldo.setBackground(Color.CYAN);
        snelPinnen.setFont(font);
        kiesBedrag.setFont(font);
        saldo.setFont(font);
        snelPinnen.setEditable(false);
        kiesBedrag.setEditable(false);
        saldo.setEditable(false);
        abort[0].setBounds(1100, 700, 200, 200); //temp
        quickPin.setBounds(650, 180, 200, 200); //temp
        showBal.setBounds(650, 700, 200, 200); //temp
        customPin.setBounds(1100,180, 200, 200); //temp
        snelPinnen.setBounds(50,180, 550,70);
        kiesBedrag.setBounds(1400,180, 500,70);
        saldo.setBounds(50,700, 300,70);
        //panel dispensing
        dispensing.add(taDispensing);
        taDispensing.setFont(font);
        taDispensing.setEditable(false);
        taDispensing.setBounds(860,590,200,40);
        taDispensing.setBackground(Color.CYAN);
        dispensing.setBackground(Color.CYAN);
        //panelChooseAmount
        panelChooseAmount.setBackground(Color.CYAN);

        //panelOptions
        panelOptions.setBackground(Color.CYAN);
        //panelBon
        panelBon.setLayout(null);
        panelBon.setBackground(Color.CYAN);
//        panelBon.add(abort[3]);
        panelBon.add(afbreken[1]);
        panelBon.add(taYesBon);
        panelBon.add(taNoBon);
        panelBon.add(title[3]);
        panelBon.add(receipt);
        panelBon.add(yesBon);
        panelBon.add(noBon);
        panelBon.add(abort[1]);
        receipt.setFont(font);
        receipt.setBackground(Color.CYAN);
        taYesBon.setFont(font);
        taNoBon.setFont(font);
        taYesBon.setEditable(false);
        taNoBon.setEditable(false);
        receipt.setEditable(false);
        taYesBon.setBackground(Color.CYAN);
        taNoBon.setBackground(Color.CYAN);
        yesBon.setBounds(350,180, 200, 200);
        noBon.setBounds(1300, 180, 200, 200);
        receipt.setBounds(710,100,800,70);
        taYesBon.setBounds(50,180,200,200); //temp
        taNoBon.setBounds(1650, 180, 200,200); //temp
        abort[1].setBounds(1300, 700, 200, 200); //temp


        panelChooseAmount.add(bedrag1);
        panelChooseAmount.add(bedrag2);
        panelChooseAmount.add(bedrag3);
        panelChooseAmount.add(bedrag4);
        panelChooseAmount.add(anderBedrag);
//        panelChooseAmount.add(abort[1]);
        panelChooseAmount.add(naarHoofdMenu[1]);

        panelBon.add(noBon);
        panelBon.add(yesBon);
        panelBon.add(abort[2]);
        panelBon.add(naarHoofdMenu[2]);

        panelPassword.add(new JLabel("Enter PIN:"));
        panelPassword.add(passwordTextField);
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

        panelOptions.add(optie1);
        panelOptions.add(optie2);
        panelOptions.add(optie3);
        panelOptions.add(optie4);

        panelChooseAmount.add(taInsufficientBills);
        taInsufficientBills.setEditable(false);
        taInsufficientBills.setVisible(false);
        taInsufficientBills.setForeground(Color.RED);

        panelChooseAmount.add(taInsufficientMoney);
        taInsufficientMoney.setEditable(false);
        taInsufficientMoney.setVisible(false);
        taInsufficientMoney.setForeground(Color.RED);
        // code die ervoor zorgt dat er max 4 tekens ingevuld worden (van stackoverflow gepakt)
//        PlainDocument documentPF = (PlainDocument) passwordTextField.getDocument();
//        documentPF.setDocumentFilter(new DocumentFilter() {
//
//            @Override
//            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
//                String string = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
//
//                if (string.length() <= 4) {
//                    super.replace(fb, offset, length, text, attrs); //To change body of generated methods, choose Tools | Templates.
//                }
//            }
//
//        });

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

        optie1.addActionListener(this);
        optie1.setActionCommand("optie1");
        optie2.addActionListener(this);
        optie2.setActionCommand("optie2");
        optie3.addActionListener(this);
        optie3.setActionCommand("optie3");
        optie4.addActionListener(this);
        optie4.setActionCommand("optie4");

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
    }

    public void arduinoInputHandler(String input){
        logoutTimer.restart(); //resets the 30 second logout timer

        if(!menuStart) {
        if("ArdSend_B".equals(input)){
            //code om uit te loggen en naar het startscherm te gaan
            System.out.println("aborting...");
            if(user != null) user.userLogout();
            taShowBal.setText("");
            taInvalidInput.setVisible(false);
            taInsufficientBills.setVisible(false);
            taInsufficientMoney.setVisible(false);
            panelChooseAmount.add(taInsufficientMoney);
            login.clearLoginVariables();
            resetFlags();
            menuStart = true;
            changePanel(panelStart);
//            serialConnection.stringOut("abort"); // todo arduino code voor abort
//            try {
//                sleep(100);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }



        }
            if(!menuLogin) {
                if ("ArdSend_C".equals(input)) {
                    //code om naar het hoofdmenu te gaan
                    System.out.println("naar hoofdmenu");
                    user.toMainMenu();
                    taInvalidInput.setVisible(false);
                    taInsufficientBills.setVisible(false);
                    taInsufficientMoney.setVisible(false);
                    panelChooseAmount.add(taInsufficientMoney);
                    resetFlags();
                    menuMain = true;
                    changePanel(panelMain);
//            serialConnection.stringOut("mainMenu"); //todo arduino code voor hoofdmenu
//            try {
//                sleep(100);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
                }
            }
        }

        if(menuStart && input.contains("ArdPassNr_")){
            String temp = input.replace("ArdPassNr_", "");
            login.setRfidDetected(true);
            login.setPassnumber(temp);
            System.out.println("Naar inlogscherm");
            menuLogin = true;
            menuStart = false;
            passwordTextField.setText("");
            changePanel(panelPassword);
        }

        //dit is voor de pincode invoeren
        if(menuLogin){
            if("ArdSend_*".equals(input)){
                passwordTextField.setText(passwordTextField.getText() + "*");
            }

            if("ArdSend_#".equals(input)){
                System.out.println("attempt login");
                if(login.requestLogin(login.getPassnumber())) {//todo  //test string: "2A 9F 0D 0B" //juiste code is: login.getPassnumber()
                    // user.setPasswordCheck(passwordField.getPassword()); //oude code
                    //System.out.println("passwd: " + Arrays.toString(user.getPasswordCheck()));
                    passwordTextField.setText("");
                    try {
                        user.requestUserVariables();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    changePanel(panelMain); //?moet nog veranderd worden naar inlogscherm?
                }
            }
            if("ArdSend_D".equals(input)){
                passwordTextField.setText("");
            }

            if(input.contains("ArdPinHashed_")){
                String temp = input.replace("ArdPinHashed_", "");
                login.setHashedPIN(temp); //hier een sout om te testen of het werkt
                System.out.println("hashed pin: " + temp);
            }
        }

        if(menuMain){
            if("ArdSend_1".equals(input)){
                //70 euro pinnen
            }

            if("ArdSend_2".equals(input)){
                System.out.println("showing balance");

                taShowBal.setText(Double.toString(user.getBalance()));
                changePanel(panelShowBal);
            }

            if("ArdSend_3".equals(input)){
                //pin menu
            }
         }
    }

    //TODO DEZE HELE METHODE VERVANGEN MET IETS ANDERS DAT DE ARDUINO KEYPAD INPUT GEBRUIKT (MISSCHIEN SWITCH CASE OF IF STATEMENTS)
    @Override
    public void actionPerformed(ActionEvent e) {
        logoutTimer.restart();

        if("abort".equalsIgnoreCase(e.getActionCommand())){
            //code om uit te loggen en naar het startscherm te gaan
            System.out.println("aborting...");
            if(user != null) user.userLogout();
            taShowBal.setText("");
            taInvalidInput.setVisible(false);
            taInsufficientBills.setVisible(false);
            taInsufficientMoney.setVisible(false);
            panelChooseAmount.add(taInsufficientMoney);
            login.clearLoginVariables();
            changePanel(panelStart);
//            serialConnection.stringOut("abort"); // todo arduino code voor abort
//            try {
//                sleep(100);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
        }

        if("hoofdmenu".equalsIgnoreCase(e.getActionCommand())){
            //code om naar het hoofdmenu te gaan
            System.out.println("naar hoofdmenu");
            user.toMainMenu();
            taInvalidInput.setVisible(false);
            taInsufficientBills.setVisible(false);
            taInsufficientMoney.setVisible(false);
            panelChooseAmount.add(taInsufficientMoney);
            changePanel(panelMain);
//            serialConnection.stringOut("mainMenu"); //todo arduino code voor hoofdmenu
//            try {
//                sleep(100);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
        }

        if("showBal".equalsIgnoreCase(e.getActionCommand())){
            //code om het saldo te laten zien
            System.out.println("showing balance");

            taShowBal.setText(Double.toString(user.getBalance()));
            changePanel(panelShowBal);
        }

        if("pin70".equalsIgnoreCase(e.getActionCommand())){
            //code om 70 euro te pinnen (kan via dezelfde methode als die voor hetzelfde bedrag)
            if (user.getBalance() - 20 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                panelMain.add(taInsufficientMoney);
                taInsufficientMoney.setVisible(true);
            } else {
                System.out.println("70 euro");
                user.makeWithdrawal();
                user.withdrawal.customWithdrawal(70);
                try{
                    user.withdrawal.displayOptions();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                changePanel(panelBon);
            }
        }

        if("customPin".equalsIgnoreCase(e.getActionCommand())) {

            System.out.println("custom bedrag pinnen");
            taInsufficientMoney.setVisible(false);
            panelChooseAmount.add(taInsufficientMoney);
            changePanel(panelChooseAmount);
        }

        if("yesBon".equalsIgnoreCase(e.getActionCommand()))   {
            System.out.println("Bon printen");
            //serialConnection.stringOut("printBon"); //todo uncomment (was even voor de demo gecommented)
            changePanel(panelFinalizeTransaction);

        }

        if("noBon".equalsIgnoreCase(e.getActionCommand()))   {
            System.out.println("Bon niet printen");
            changePanel(panelFinalizeTransaction);
        }
        if("customBedrag1".equalsIgnoreCase(e.getActionCommand())){
            if(amount1 > 0) {
                if (user.getBalance() - 20 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                    taInsufficientMoney.setVisible(true);
                } else {
                    System.out.println("20 euro");
                    user.makeWithdrawal();
                    user.withdrawal.customWithdrawal(20);
                    try{
                        user.withdrawal.displayOptions();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                    changePanel(panelOptions);
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
                if (user.getBalance() - 50 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                    taInsufficientMoney.setVisible(true);
                } else {
                    System.out.println("50 euro");
                    user.makeWithdrawal();
                    user.withdrawal.customWithdrawal(50);
                    try{
                        user.withdrawal.displayOptions();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                    changePanel(panelOptions);
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
                if (user.getBalance() - 100 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                    taInsufficientMoney.setVisible(true);
                } else {
                    System.out.println("100 euro");
                    user.makeWithdrawal();
                    user.withdrawal.customWithdrawal(100);
                    try{
                        user.withdrawal.displayOptions();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                    changePanel(panelOptions);
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
                if (user.getBalance() - 150 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                    taInsufficientMoney.setVisible(true);
                } else {
                    System.out.println("150 euro");
                    user.makeWithdrawal();
                    user.withdrawal.customWithdrawal(150);
                    try{
                        user.withdrawal.displayOptions();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                    changePanel(panelOptions);
                    amount4 = amount4 - 1;
                    System.out.println("Amount4:" + amount4);
                }
            }
            else{
                taInsufficientBills.setVisible(true);
            }
        }
        if("optie1".equalsIgnoreCase(e.getActionCommand())){
            changePanel(dispensing);
            try{
                user.withdrawal.sendArray(1);
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
        if("optie2".equalsIgnoreCase(e.getActionCommand())){
            changePanel(dispensing);
            try{
                user.withdrawal.sendArray(2);
            } catch(InterruptedException ex){
                ex.printStackTrace();
            }
        }
        if("optie3".equalsIgnoreCase(e.getActionCommand())){
            changePanel(dispensing);
            try{
                user.withdrawal.sendArray(3);
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
        if("optie4".equalsIgnoreCase(e.getActionCommand())){
            changePanel(dispensing);
            try{
                user.withdrawal.sendArray(4);
            }catch(InterruptedException ex){
                ex.printStackTrace();
            }
        }

        if("inlogScherm".equalsIgnoreCase((e.getActionCommand()))){
            login.setRfidDetected(true);
            System.out.println("Naar inlogscherm");
            changePanel(panelPassword);
            //moet nog veranderd worden naar inlogscherm
        }
        //if statement met string van arduino
        if("wachtwoord".equalsIgnoreCase((e.getActionCommand()))){
            System.out.println("login");
            if(login.requestLogin("2A 9F 0D 0B")) {//todo  //test string: "2A 9F 0D 0B" //juiste code is: login.getPassnumber()
               // user.setPasswordCheck(passwordField.getPassword()); //oude code
                //System.out.println("passwd: " + Arrays.toString(user.getPasswordCheck()));
                passwordTextField.setText("");
                try {
                    user.requestUserVariables();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                changePanel(panelMain); //?moet nog veranderd worden naar inlogscherm?
            }
        }

        if("anderBedrag".equalsIgnoreCase((e.getActionCommand()))){
            System.out.println("ander bedrag invullen");
            customBedragField.setText("");
            taInsufficientMoney.setVisible(false);
            changePanel(panelCustomAmount);
        }

        if("custAmountToNext".equalsIgnoreCase((e.getActionCommand()))){
            if(!("".equals(customBedragField.getText()))) {
                try {
                    int tempInt = Integer.parseInt(customBedragField.getText());
                    if(tempInt%5 != 0 || tempInt < 0) throw new NumberFormatException();
                    if (user.getBalance() - tempInt < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt, en of het getal eindigt met 0 of 5
                        panelCustomAmount.add(taInsufficientMoney);
                        taInsufficientMoney.setVisible(true);

                    }

                    else {
                        System.out.println("custom bedrag: " + tempInt);
                        user.makeWithdrawal();
                        user.withdrawal.customWithdrawal(tempInt);
                        try{
                            user.withdrawal.displayOptions();
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }

                        changePanel(panelOptions);
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
