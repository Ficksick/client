package Frames.User;

import Models.Ticket;
import Models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RedactTicketUser extends JFrame {
    private JPanel root;
    private JPanel dataPanel;
    private JPanel buttonsPanel;
    private JButton backButton;
    private JPanel panelChangeScreening;
    private JPanel panelChangeSeat;
    private JButton changeScreeningButton;
    private JButton changeSeatButton;

    public RedactTicketUser(ObjectInputStream cois, ObjectOutputStream coos, User user, Ticket ticket) {
        setSize(450, 350);
        setContentPane(root);
        setLocationRelativeTo(null);
        setVisible(true);

        try{
            coos.writeObject("FIND_TICKET_USER");
            coos.writeObject(ticket);
            ticket = (Ticket) cois.readObject();
        }catch(IOException | ClassNotFoundException ex){
            ex.printStackTrace();
        }

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                UserTickets userTickets = new UserTickets(cois, coos, user);
            }
        });

        Ticket finalTicket = ticket;

        changeScreeningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreeningTicketChangeUser screeningTicketChangeUser = new ScreeningTicketChangeUser(cois, coos, user, finalTicket);
                dispose();
            }
        });
        changeSeatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SeatChangeUser seatChangeUser = new SeatChangeUser(cois,coos,user,finalTicket);
                dispose();
            }
        });
    }
}
