/*
Made by: Ruben Smoor
Studentnummer: 0990534
klas: TI1C
*/

import User.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame implements ActionListener {
    int array_length = 10;
    private String version = "1.0.3";

    private User user = new User();

    private JFrame frame = new JFrame("BankApp V" + version);

    private JPanel panelStart = new JPanel();
    private JPanel panelMain = new JPanel();
    private JPanel panelChooseAmount = new JPanel();
    private JPanel panelTransaction = new JPanel();
    private JPanel panelPassword = new JPanel();
    private JPanel panelCustomAmount = new JPanel();
    private JPanel panelShowBal = new JPanel();

    private JButton yesBon = new JButton("Yes");
    private JButton noBon = new JButton("No");
    private JButton quickPin = new JButton("Snel €70,- pinnen");
    private JButton showBal = new JButton("Bekijk saldo");
    private JButton customPin = new JButton("Kies bedrag");

    JButton[] abort = new JButton[array_length];
    JButton[] naarHoofdMenu = new JButton[array_length];

    private JButton bedrag1 = new JButton("20");
    private JButton bedrag2 = new JButton("50");
    private JButton bedrag3 = new JButton("100");
    private JButton bedrag4 = new JButton("150");
    private JButton anderBedrag = new JButton("ander bedrag");
    private JButton nextPage = new JButton("Volgende");
    private JButton nextPage1 = new JButton("Volgende");

    private JPasswordField passwordField = new JPasswordField(10);
    private JTextField customBedragField = new JTextField(10);
    private JTextArea taShowBal = new JTextArea();
    private JTextArea taPanelStart = new JTextArea("Scan uw pas om verder te gaan");

    void createAbortButton(){
        for(int i = 0; i < abort.length; i++){
            abort[i] = new JButton("afbreken");
        }
    }

    void createHoofdmenuButton(){
        for(int i = 0; i < naarHoofdMenu.length; i++){
            naarHoofdMenu[i] = new JButton("Terug naar het hoofdmenu");
        }
    }

    void createApp(){
        createAbortButton();
        createHoofdmenuButton();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        mainFrame.setUndecorated(true); // zorgt ervoor dat je niet zomaar uit het programma kan klikken
        frame.add(panelMain);
        panelMain.add(quickPin);
        panelMain.add(showBal);
        panelMain.add(customPin);
        panelMain.add(abort[0]);

        panelStart.add(taPanelStart);
        panelStart.add(nextPage);

        panelChooseAmount.add(bedrag1);
        panelChooseAmount.add(bedrag2);
        panelChooseAmount.add(bedrag3);
        panelChooseAmount.add(bedrag4);
        panelChooseAmount.add(anderBedrag);
        panelChooseAmount.add(abort[1]);
        panelChooseAmount.add(naarHoofdMenu[1]);

        panelTransaction.add(noBon);
        panelTransaction.add(yesBon);
        panelTransaction.add(abort[2]);
        panelTransaction.add(naarHoofdMenu[2]);

        panelPassword.add(passwordField);
        panelPassword.add(nextPage1);

        panelCustomAmount.add(customBedragField);
        panelCustomAmount.add(abort[3]);
        panelCustomAmount.add(naarHoofdMenu[3]);

        panelShowBal.add(taShowBal);
        panelShowBal.add(abort[4]);
        panelShowBal.add(naarHoofdMenu[4]);

        eventHandler();

// todo maak GridBagLayout waar de knoppen in worden toegevoegd.
//  remove panels van het JFrame als je een nieuwe erop wilt zetten
      /*  mainFrame.getContentPane().add(BorderLayout.SOUTH, );*/
//        frame.getContentPane().add(BorderLayout.NORTH, taPanelMain);
        frame.getContentPane().add(BorderLayout.CENTER, panelStart);
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

        nextPage.addActionListener(this);
        nextPage.setActionCommand("volgende");
        nextPage1.addActionListener(this);
        nextPage1.setActionCommand("volgendee");




    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if("abort".equalsIgnoreCase(e.getActionCommand())){
            //code om uit te loggen en naar het startscherm te gaan
            System.out.println("aborting...");
            taShowBal.setText("");
            changePanel(panelStart);
        }

        if("hoofdmenu".equalsIgnoreCase(e.getActionCommand())){
            //code om naar het hoofdmenu te gaan
            System.out.println("naar hoofdmenu");
            changePanel(panelMain);
        }

        if("showBal".equalsIgnoreCase(e.getActionCommand())){
            //code om het saldo te laten zien
            System.out.println("showing balance");
            changePanel(panelShowBal);
            taShowBal.setText(Integer.toString(user.balance.getBalance()));
        }

        if("pin70".equalsIgnoreCase(e.getActionCommand())){
            //code om 70 euro te pinnen (kan via dezelfde methode als die voor hetzelfde bedrag)
            System.out.println("pin €70,-");
        }

        if("customPin".equalsIgnoreCase(e.getActionCommand())) {
            // code om een nieuw menu te krijgen waar je een nieuw bedrag kan kiezen (of waar je zelf een bedrag kan intikken)
            //todo voorstellen aan PO of ze meerdere voorgeselecteerde bedragen willen zien of gelijk dat ze het bedrag moeten intoetsen
            System.out.println("custom bedrag pinnen");
            changePanel(panelChooseAmount);
        }
         if("yesBon".equalsIgnoreCase(e.getActionCommand()))   {
             System.out.println("Bon printen");
             changePanel(panelPassword);

            }
         if("noBon".equalsIgnoreCase(e.getActionCommand()))   {
                System.out.println("Bon niet printen");
                changePanel(panelPassword);
        }
         if("twintig".equalsIgnoreCase(e.getActionCommand())){
             System.out.println("Bon niet printen");
             changePanel(panelTransaction);
         }
         if("volgende".equalsIgnoreCase((e.getActionCommand()))){
             System.out.println("Naar inlogscherm");
             changePanel(panelPassword); //moet nog veranderd worden naar inlogscherm
         }
        if("volgendee".equalsIgnoreCase((e.getActionCommand()))){
            System.out.println("Naar hoofdscherm");
            changePanel(panelMain); //moet nog veranderd worden naar inlogscherm
        }
        if("anderBedrag".equalsIgnoreCase((e.getActionCommand()))){
            System.out.println("ander bedrag invullen");
            changePanel(panelCustomAmount);
        }

    }
}
