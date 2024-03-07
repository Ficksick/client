package Frames.User;

import Models.Ticket;
import Models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class UserTickets extends JFrame {
    private JPanel root;
    private JLabel title;
    private JButton buttonRedact;
    private JButton buttonBack;
    private JPanel buttonsPanel;
    private JPanel tablePanel;
    private JTable tableTickets;
    private JButton buttonDelete;
    private JButton buttonSave;

    public UserTickets(ObjectInputStream cois, ObjectOutputStream coos, User user) {
        setContentPane(root);
        setLocationRelativeTo(null);
        setSize(450, 300);
        setVisible(true);

        showData(cois, coos, user);

        buttonRedact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ticket ticket = new Ticket();

                if (tableTickets.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Выберите какой билет вы хотите отредактировать");
                } else {
                    ticket.setId((int) tableTickets.getValueAt(tableTickets.getSelectedRow(), 0));
                    RedactTicketUser redactTicketUser = new RedactTicketUser(cois, coos, user, ticket);
                    dispose();
                }
            }
        });
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ticket ticket = new Ticket();

                if (tableTickets.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Выберите какой билет вы хотите удалить");
                } else {
                    int choice = JOptionPane.showConfirmDialog(null, "Вы уверены, что хотите удалить выбранный билет?");

                    if (choice == 0) {
                        ticket.setId((int) tableTickets.getValueAt(tableTickets.getSelectedRow(), 0));


                        try {
                            coos.writeObject("FIND_TICKET_USER");
                            coos.writeObject(ticket);

                            Ticket ticketFromServer = (Ticket) cois.readObject();

                            coos.writeObject("DELETE_TICKET_USER");
                            coos.writeObject(ticketFromServer);

                            String answer = (String) cois.readObject();

                            if (answer.equals("OK")) {
                                JOptionPane.showMessageDialog(null, "Билет был удален");
                                showData(cois, coos, user);
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuFrame menuFrame = new MenuFrame(cois, coos, user);
                dispose();
            }
        });
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    coos.writeObject("VIEW_TICKETS_USER");
                    List<Ticket> tickets = (List<Ticket>) cois.readObject();

                    FileWriter writer = new FileWriter(user.getUsername() + ".txt");
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);

                    if (!tickets.isEmpty()) {
                        for (Ticket ticket : tickets) {
                            if (user.getUser_id() == ticket.getUser().getUser_id()) {
                                bufferedWriter.write(ticket.toString());
                                bufferedWriter.write("\n__________________________");
                                bufferedWriter.newLine();
                            }
                        }
                        bufferedWriter.close();
                        JOptionPane.showMessageDialog(null, "Ваши билеты сохранены в файл");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "У вас пока нет билетов");
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private DefaultTableModel tableModel;

    public void showData(ObjectInputStream cois, ObjectOutputStream coos, User user) {
        Object[] columnTitle = {"ID", "Название фильма", "Название зала", "Дата", "Время начала", "Время конца", "Номер места", "Цена билета"};
        tableModel = new DefaultTableModel(null, columnTitle);
        tableTickets.setModel(tableModel);

        tableModel.getDataVector().removeAllElements();

        try {
            coos.writeObject("VIEW_TICKETS_USER");
            List<Ticket> tickets = (List<Ticket>) cois.readObject();

            for (Ticket ticket : tickets) {
                if (user.getUser_id() == ticket.getUser().getUser_id()) {
                    Object[] data = {
                            ticket.getId(),
                            ticket.getFilmTitle(),
                            ticket.getHallName(),
                            ticket.getScreening().getDate(),
                            ticket.getScreening().getStart_time(),
                            ticket.getScreening().getEnd_time(),
                            ticket.getSeatNumber(),
                            ticket.getPrice()
                    };
                    tableModel.addRow(data);
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
