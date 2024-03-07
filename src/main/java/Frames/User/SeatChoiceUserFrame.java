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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeatChoiceUserFrame extends JFrame {
    private JPanel root;
    private JPanel dataPanel;
    private JPanel buttonsPanel;
    private JTable tableScreenings;
    private JButton buttonAccept;
    private JButton buttonBack;
    private JPanel choicePanel;
    private JComboBox comboBoxSeat;

    public SeatChoiceUserFrame(ObjectInputStream cois, ObjectOutputStream coos, User user, Screening screening) {
        setSize(350, 400);
        setVisible(true);
        setContentPane(root);
        setLocationRelativeTo(null);
        showData(cois, coos, screening);

        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrederTicket orederTicket = new OrederTicket(cois, coos, user);
                dispose();
            }
        });

        buttonAccept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    coos.writeObject("FIND_SCREENING_USER");
                    coos.writeObject(screening);
                    Screening screeningFromTable = (Screening) cois.readObject();

                    Ticket ticket = new Ticket();
                    ticket.setUser(user);
                    ticket.setScreening(screeningFromTable);
                    int seatNumb = (int) comboBoxSeat.getSelectedItem();
                    ticket.setSeatNumber(seatNumb);
                    ticket.setPrice(screeningFromTable.getPrice());

                    System.out.println("TICKET \n" + ticket.toString());

                    coos.writeObject("ORDER_TICKET_USER");
                    coos.writeObject(ticket);
                    String answer = (String) cois.readObject();
                    if(answer.equals("OK")){
                        JOptionPane.showMessageDialog(null, "Билет успешно заказан");
                    }

                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private DefaultTableModel tableModel;

    public void showData(ObjectInputStream cois, ObjectOutputStream coos, Screening screening) {
        Object[] columnTitle = {"Название фильма", "Название зала", "Дата", "Время начала", "Время конца", "Цена билета"};
        tableModel = new DefaultTableModel(null, columnTitle);
        tableScreenings.setModel(tableModel);

        tableModel.getDataVector().removeAllElements();

        try {
            coos.writeObject("FIND_SCREENING_USER");
            coos.writeObject(screening);
            Screening screeningToTable = (Screening) cois.readObject();

            Object[] data = {
                    screeningToTable.getFilmTitle(),
                    screeningToTable.getHall().getHallName(),
                    screeningToTable.getDate(),
                    screeningToTable.getStart_time(),
                    screeningToTable.getEnd_time(),
                    screeningToTable.getPrice()
            };
            tableModel.addRow(data);

            coos.writeObject("VIEW_TICKETS_USER");
            List<Ticket> tickets = new ArrayList<>();
            tickets = (List<Ticket>) cois.readObject();

            int seats = screeningToTable.getHall().getCapacity();
            DefaultComboBoxModel<String> modelSeats = new DefaultComboBoxModel<>();

            Map<Integer, Boolean> availableSeat = new HashMap<>();

            for (int i = 1; i <= seats; i++) {
                for (Ticket ticket : tickets) {
                    if (ticket.getHallName().equals(screeningToTable.getHall().getHallName())
                            && ticket.getFilmTitle().equals(screeningToTable.getFilmTitle())
                            && ticket.getScreening().getStart_time().compareTo(screeningToTable.getStart_time()) == 0
                            && ticket.getScreening().getDate().compareTo(screeningToTable.getDate()) == 0) {
                        availableSeat.put(ticket.getSeatNumber(), false);
                    }
                }
                availableSeat.put(i, true);
            }

            for(Map.Entry<Integer, Boolean> entry : availableSeat.entrySet()){
                if(entry.getValue()){
                    comboBoxSeat.addItem(entry.getKey());
                }
            }

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
