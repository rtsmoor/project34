/*
Made by: Ruben Smoor
Studentnummer: 0990534
klas: TI1C
*/

import User.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame implements ActionListener {
    private String version = "1.0.1";

    private User user = new User();

    private JFrame frame = new JFrame("BankApp V" + version);
    private JPanel panelStart = new JPanel();
    private JPanel panelMain = new JPanel();
    private JPanel panelCustomAmount = new JPanel();
    private JPanel transaction = new JPanel();
    private JPanel password = new JPanel();
    private JButton yesBon = new JButton("Yes");
    private JButton noBon = new JButton("No");
    private JButton quickPin = new JButton("Snel €70,- pinnen");
    private JButton showBal = new JButton("Bekijk saldo");
    private JButton customPin = new JButton("Kies bedrag");
    private JButton abort = new JButton("Afbreken");
    private JButton abort2 = new JButton("Afbreken");
    private JButton abort3 = new JButton("Afbreken");
    private JButton b1 = new JButton("20");
    private JButton b2 = new JButton("50");
    private JButton b3 = new JButton("100");
    private JButton b4 = new JButton("150");
    private JPasswordField passwordField = new JPasswordField(10);
    private JTextArea taPanelMain = new JTextArea();
    private JTextArea taPanelStart = new JTextArea("Scan uw pas om verder te gaan");

    void createApp(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        mainFrame.setUndecorated(true); // zorgt ervoor dat je niet zomaar uit het programma kan klikken
        frame.add(panelMain);
        panelMain.add(quickPin);
        panelMain.add(showBal);
        panelMain.add(customPin);
        panelMain.add(abort);

        panelStart.add(taPanelStart);

        panelCustomAmount.add(b1);
        panelCustomAmount.add(b2);
        panelCustomAmount.add(b3);
        panelCustomAmount.add(b4);
        panelCustomAmount.add(abort2);

        transaction.add(noBon);
        transaction.add(yesBon);
        transaction.add(abort3);

        password.add(passwordField);


        eventHandler();

// todo maak GridBagLayout waar de knoppen in worden toegevoegd.
//  remove panels van het JFrame als je een nieuwe erop wilt zetten
      /*  mainFrame.getContentPane().add(BorderLayout.SOUTH, );*/
//        frame.getContentPane().add(BorderLayout.NORTH, taPanelMain);
        frame.getContentPane().add(BorderLayout.CENTER, panelMain);
        frame.setVisible(true);
    }

    void changePanel(JPanel panel){
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
        abort.addActionListener(this);
        abort.setActionCommand("abort");
        abort2.addActionListener(this);
        abort2.setActionCommand("abort");
        abort3.addActionListener(this);
        abort3.setActionCommand("abort");
        yesBon.addActionListener(this);
        yesBon.setActionCommand("yesBon");
        noBon.addActionListener(this);
        noBon.setActionCommand("noBon");
        b1.addActionListener(this);
        b1.setActionCommand("twintig");



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("abort".equalsIgnoreCase(e.getActionCommand())){
            //code om uit te loggen en naar het hoofdmenu te gaan
            System.out.println("aborting...");
            taPanelMain.setText("");
            changePanel(panelMain);
        }

        if("showBal".equalsIgnoreCase(e.getActionCommand())){
            //code om het saldo te laten zien
            System.out.println("showing balance");
            taPanelMain.setText(Integer.toString(user.balance.getBalance()));
        }

        if("pin70".equalsIgnoreCase(e.getActionCommand())){
            //code om 70 euro te pinnen (kan via dezelfde methode als die voor hetzelfde bedrag)
            System.out.println("pin €70,-");
        }

        if("customPin".equalsIgnoreCase(e.getActionCommand())) {
            // code om een nieuw menu te krijgen waar je een nieuw bedrag kan kiezen (of waar je zelf een bedrag kan intikken)
            //todo voorstellen aan PO of ze meerdere voorgeselecteerde bedragen willen zien of gelijk dat ze het bedrag moeten intoetsen
            System.out.println("custom bedrag pinnen");
            changePanel(panelCustomAmount);
        }
         if("yesBon".equalsIgnoreCase(e.getActionCommand()))   {
             System.out.println("Bon printen");
             changePanel(password);

            }
         if("noBon".equalsIgnoreCase(e.getActionCommand()))   {
                System.out.println("Bon niet printen");
                changePanel(password);
        }
         if("twintig".equalsIgnoreCase(e.getActionCommand())){
             System.out.println("Bon niet printen");
             changePanel(transaction);
         }
    }
}
