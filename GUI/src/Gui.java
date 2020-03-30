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
    private String version = "1.0";

    private User user = new User();

    private JFrame mainFrame = new JFrame("BankApp V" + version);
    private JPanel mainPanel = new JPanel();
    private JButton quickPin = new JButton("Snel €70,- pinnen");
    private JButton showBal = new JButton("Bekijk saldo");
    private JButton customPin = new JButton("Kies bedrag");
    private JButton abort = new JButton("Afbreken");
    private JTextArea ta = new JTextArea();

    void createApp(){
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 400);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        mainFrame.setUndecorated(true); // zorgt ervoor dat je niet zomaar uit het programma kan klikken
        mainFrame.add(mainPanel);
        mainPanel.add(quickPin);
        mainPanel.add(showBal);
        mainPanel.add(customPin);
        mainPanel.add(abort);


        eventHandler();

// todo maak GridBagLayout waar de knoppen worden toegevoegd.
//  gebruik mainPanel.remove(knopnaam) en mainPanel.add(knopnaam) als je knoppen wilt toevoegen/weghalen
      /*  mainFrame.getContentPane().add(BorderLayout.SOUTH, );*/
        mainFrame.getContentPane().add(BorderLayout.NORTH, ta);
        mainFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        mainFrame.setVisible(true);

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


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("abort".equalsIgnoreCase(e.getActionCommand())){
            //code om uit te loggen en naar het hoofdmenu te gaan
            System.out.println("aborting...");
            ta.setText("");
        }

        if("showBal".equalsIgnoreCase(e.getActionCommand())){
            //code om het saldo te laten zien
            System.out.println("showing balance");
            ta.setText(Integer.toString(user.balance.getBalance()));
        }

        if("pin70".equalsIgnoreCase(e.getActionCommand())){
            //code om 70 euro te pinnen (kan via dezelfde methode als die voor hetzelfde bedrag)
            System.out.println("pin €70,-");
        }

        if("customPin".equalsIgnoreCase(e.getActionCommand())){
            // code om een nieuw menu te krijgen waar je een nieuw bedrag kan kiezen (of waar je zelf een bedrag kan intikken)
            //todo voorstellen aan PO of ze meerdere voorgeselecteerde bedragen willen zien of gelijk dat ze het bedrag moeten intoetsen
            System.out.println("custom bedrag pinnen");
        }
    }
}
