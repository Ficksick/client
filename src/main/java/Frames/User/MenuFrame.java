package Frames.User;

import Models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MenuFrame extends JFrame {
    private JPanel root;
    private JButton buttonMyAccount;
    private JButton buttonOrder;
    private JButton buttonMyTickets;
    private JPanel myAccountPanel;
    private JPanel orderPanel;
    private JPanel myTicketsPanel;
    private JButton buttonBack;
    private JPanel backPanel;
    private JLabel title;

    public MenuFrame(ObjectInputStream cois, ObjectOutputStream coos, User user) {
        setSize(300, 400);
        setVisible(true);
        setContentPane(root);
        setLocationRelativeTo(null);

        buttonMyAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountActions accountActions = new AccountActions(cois, coos, user);
                dispose();
            }
        });

        buttonOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrederTicket orederTicket = new OrederTicket(cois, coos, user);
                dispose();
            }
        });
        buttonMyTickets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserTickets userTickets = new UserTickets(cois, coos, user);
                dispose();
            }
        });
        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try{
                    cois.close();
                    coos.close();
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        });
    }
}
