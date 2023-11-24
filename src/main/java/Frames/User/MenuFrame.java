package Frames.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFrame extends JFrame{
    private JPanel root;
    private JButton buttonMyAccount;
    private JButton buttonOrder;
    private JButton buttonMyTickets;
    private JPanel myAccountPanel;
    private JPanel orderPanel;
    private JPanel myTicketsPanel;
    private JButton buttonBack;
    private JPanel backPanel;

    public MenuFrame(){
        setSize(300,400);
        setVisible(true);
        setContentPane(root);
        setLocationRelativeTo(null);

        buttonMyAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountActions accountActions = new AccountActions();
            }
        });

        buttonOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        buttonMyTickets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }
}
