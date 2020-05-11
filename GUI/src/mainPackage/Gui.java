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
import java.util.Arrays;
import java.util.Timer;

import static javax.swing.JOptionPane.showMessageDialog;

public class Gui extends JFrame implements ActionListener {
    int array_length = 10;
    private User user;
    private String version = "1.0.7";
    private LogIn login;
    public SerialConnection serialConnection;

    Gui(SerialConnection serialConnection){
         login = new LogIn(this);
         this.serialConnection = serialConnection;
    }

    private JFrame frame = new JFrame("BankApp V" + version);
     public JDialog dialog = new JDialog(frame, "Er is niet genoeg saldo om deze actie uit te voeren", true);
     public JPanel panelStart = new JPanel();
     public JPanel panelMain = new JPanel();
     public JPanel panelChooseAmount = new JPanel();
     public JPanel panelBon = new JPanel();
     public JPanel panelPassword = new JPanel();
     public JPanel panelCustomAmount = new JPanel();
     public JPanel panelShowBal = new JPanel();
     public JPanel panelFinalizeTransaction = new JPanel();
     private JTextArea tempTa = new JTextArea("hier komt overzichtelijk de transactie informatie \nterwijl het geld uit de dispenser komt");

    private JButton dialogClose = new JButton("Sluit");
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
    private JButton anderBedrag = new JButton("ander bedrag");

    public JPasswordField passwordField = new JPasswordField(4);
    public JTextField customBedragField = new JTextField(10);
    private JTextArea taShowBal = new JTextArea();
    private JTextArea taPanelStart = new JTextArea("Scan uw pas om verder te gaan");

//    public void setSerialConnection(SerialConnection serialConnection){
//        this.serialConnection = serialConnection;
//    }

    private void createButonArrays(){
        for(int i = 0; i < array_length; i++){
            naarHoofdMenu[i] = new JButton("Terug naar het hoofdmenu");
            abort[i] = new JButton("afbreken");
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

        dialog.add(dialogClose);

        panelBon.add(noBon);
        panelBon.add(yesBon);
        panelBon.add(abort[2]);
        panelBon.add(naarHoofdMenu[2]);

        panelPassword.add(new JLabel("Enter PIN:"));
        panelPassword.add(passwordField);
        panelPassword.add(nextPage[1]);

        panelCustomAmount.add(new JLabel("Voer aangepast bedrag in:"));
        panelCustomAmount.add(customBedragField);
        panelCustomAmount.add(nextPage[2]);
        panelCustomAmount.add(abort[3]);
        panelCustomAmount.add(naarHoofdMenu[3]);

        panelShowBal.add(taShowBal);
        taShowBal.setEditable(false);
        panelShowBal.add(abort[4]);
        panelShowBal.add(naarHoofdMenu[4]);

        panelFinalizeTransaction.add(tempTa);
        panelFinalizeTransaction.add(abort[6]);

        // code die ervoor zorgt dat er max 4 tekens ingevuld worden (van stackoverflow gepakt)
        PlainDocument document = (PlainDocument) passwordField.getDocument();
        document.setDocumentFilter(new DocumentFilter() {

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

        dialogClose.addActionListener(this);
        dialogClose.setActionCommand("dialogClose");

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if("abort".equalsIgnoreCase(e.getActionCommand())){
            //code om uit te loggen en naar het startscherm te gaan
            System.out.println("aborting...");
            if(user != null) user.userLogout();
            taShowBal.setText("");
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
            serialConnection.stringOut("withdraw");
        }

        if("customPin".equalsIgnoreCase(e.getActionCommand())) {

            System.out.println("custom bedrag pinnen");
            user.makeWithdrawal();
            changePanel(panelChooseAmount);
        }

        if("yesBon".equalsIgnoreCase(e.getActionCommand()))   {
             System.out.println("Bon printen");
             user.withdrawal.setReceipt(true);
             changePanel(panelFinalizeTransaction);

        }

        if("noBon".equalsIgnoreCase(e.getActionCommand()))   {
             System.out.println("Bon niet printen");
             user.withdrawal.setReceipt(false);
             changePanel(panelFinalizeTransaction);
        }
        if("dialogClose".equalsIgnoreCase(e.getActionCommand())){
            dialog.setVisible(false);
            changePanel(panelChooseAmount);
        }

        if("customBedrag1".equalsIgnoreCase(e.getActionCommand())){
            if(user.balance.getBalance() - 20 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                dialog.setSize(350, 150);
                dialog.setLocationRelativeTo(panelChooseAmount);
                dialog.setVisible(true);
            }
            else{
                System.out.println("20 euro");
                changePanel(panelBon);
            }
        }
        if("customBedrag2".equalsIgnoreCase(e.getActionCommand())){
            if(user.balance.getBalance() - 50 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                dialog.setSize(350, 150);
                dialog.setLocationRelativeTo(panelChooseAmount);
                dialog.setVisible(true);
            }
            else{
                System.out.println("50 euro");
                changePanel(panelBon);
            }
        }
        if("customBedrag3".equalsIgnoreCase(e.getActionCommand())){
            if(user.balance.getBalance() - 100 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                dialog.setSize(350, 150);
                dialog.setLocationRelativeTo(panelChooseAmount);
                dialog.setVisible(true);
            }
            else{
                System.out.println("100 euro");
                changePanel(panelBon);
            }
        }
        if("customBedrag4".equalsIgnoreCase(e.getActionCommand())){
            if(user.balance.getBalance() - 150 < 0) { //kijken of saldo lager is dan bedrag dat gepind wordt
                dialog.setSize(350, 150);
                dialog.setLocationRelativeTo(panelChooseAmount);
                dialog.setVisible(true);
            }
            else{
                System.out.println("150 euro");
                changePanel(panelBon);
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
                //serialConnection.stringOut("getUser");
               // user.userName = serialConnection.stringIn();
                user.requestUserVariables();
                user.setUserVariables();
                changePanel(panelMain); //?moet nog veranderd worden naar inlogscherm?
            }
        }

        if("anderBedrag".equalsIgnoreCase((e.getActionCommand()))){
            System.out.println("ander bedrag invullen");
            customBedragField.setText("");
            changePanel(panelCustomAmount);
        }

        if("custAmountToNext".equalsIgnoreCase((e.getActionCommand()))){
            changePanel(panelBon);
            //todo take input from the textField and use it in the transaction
        }
    }
}
