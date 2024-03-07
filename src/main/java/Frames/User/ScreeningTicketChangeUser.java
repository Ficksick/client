package Frames.User;

import Models.Screening;
import Models.Ticket;
import Models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ScreeningTicketChangeUser extends JFrame {
    private JPanel root;
    private JLabel title;
    private JPanel dataPanel;
    private JPanel buttonsPanel;
    private JTable tableScreening;
    private JButton buttonAccept;
    private JButton buttonBack;
    private DefaultTableModel tableModel;

    public ScreeningTicketChangeUser(ObjectInputStream cois, ObjectOutputStream coos, User user, Ticket ticket) {
        setContentPane(root);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(450, 300);

        showData(cois, coos);

        buttonAccept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //передать сеанс
                if(tableScreening.getSelectedRow() == -1){

                }else{
                    Screening screening = new Screening();
                    screening.setScreening_id((int) tableScreening.getValueAt(tableScreening.getSelectedRow(), 0));
                    RedactScreeningTicketSeatChoiceUser redactScreeningTicketSeatChoiceUser = new RedactScreeningTicketSeatChoiceUser(cois,coos,user,ticket,screening);
                    dispose();
                }


            }
        });
        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                UserTickets userTickets = new UserTickets(cois, coos, user);
            }
        });

    }

    public void showData(ObjectInputStream cois, ObjectOutputStream coos) {
        Object[] columnTitle = {"ID сеанса", "Название фильма", "Название зала", "Дата", "Время начала", "Время конца", "Цена билета"};
        tableModel = new DefaultTableModel(null, columnTitle);
        tableScreening.setModel(tableModel);

        tableModel.getDataVector().removeAllElements();

        try {
            coos.writeObject("VIEW_SCREENING_USER");
            List<Screening> screenings = new ArrayList<>();
            screenings = (List<Screening>) cois.readObject();

            for (Screening screening : screenings) {
                Object[] data = {
                        screening.getScreening_id(),
                        screening.getFilmTitle(),
                        screening.getHall().getHallName(),
                        screening.getDate(),
                        screening.getStart_time(),
                        screening.getEnd_time(),
                        screening.getPrice()
                };
                tableModel.addRow(data);
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
