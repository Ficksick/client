package Frames.User;

import Models.Screening;
import Models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class OrederTicket extends JFrame {
    private JPanel root;
    private JPanel dataPanel;
    private JPanel buttonsPanel;
    private JLabel title;
    private JTable tableScreenings;
    private JButton buttonAccept;
    private JButton buttonBack;

    public OrederTicket(ObjectInputStream cois, ObjectOutputStream coos, User user) {
        setContentPane(root);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(450, 300);
        showData(cois, coos);

        buttonAccept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Screening screening = new Screening();

                screening.setScreening_id((int) tableScreenings.getValueAt(tableScreenings.getSelectedRow(), 0));
                SeatChoiceUserFrame seatChoiceUserFrame = new SeatChoiceUserFrame(cois, coos, user, screening);
                dispose();
            }
        });
        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuFrame menuFrame = new MenuFrame(cois, coos, user);
            }
        });
    }

    private DefaultTableModel tableModel;

    public void showData(ObjectInputStream cois, ObjectOutputStream coos) {
        Object[] columnTitle = {"ID сеанса", "Название фильма", "Название зала", "Дата", "Время начала", "Время конца", "Цена билета"};
        tableModel = new DefaultTableModel(null, columnTitle);
        tableScreenings.setModel(tableModel);

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
