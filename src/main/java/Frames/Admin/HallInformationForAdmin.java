package Frames.Admin;

import Frames.Admin.HallCreateAdminFrame;
import Frames.Admin.HallEditAdminFrame;
import Models.Hall;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class HallInformationForAdmin extends JFrame {
    private JPanel root;
    private JLabel title;
    private JTable tableHall;
    private JPanel panelForTable;
    private JScrollPane scrollPanel;
    private JPanel panelForButtons;
    private JPanel editButtonPanel;
    private JPanel deleteButtonPanel;
    private JPanel backButtonPanel;
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private JPanel createButtonPanel;
    private JButton createButton;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private DefaultTableModel tableModel;

    public HallInformationForAdmin(ObjectInputStream cois, ObjectOutputStream coos) {
        setSize(450, 300);
        setVisible(true);
        setContentPane(root);
        setLocationRelativeTo(null);
        showHallData(cois, coos);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                HallCreateAdminFrame hallCreateAdminFrame = new HallCreateAdminFrame(cois, coos);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Hall hallToRedact = new Hall();

                if (tableHall.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Выберите кого вы хотите изменить");
                } else {
                    hallToRedact.setHall_id((int) tableHall.getValueAt(tableHall.getSelectedRow(), 0));
                    hallToRedact.setHallName((String) tableHall.getValueAt(tableHall.getSelectedRow(), 1));
                    hallToRedact.setCapacity((int) tableHall.getValueAt(tableHall.getSelectedRow(), 2));
                    HallEditAdminFrame hallEditAdminFrame = new HallEditAdminFrame(cois, coos);
                    dispose();
                    try {
                        coos.writeObject("REDACT_HALL_ADMIN");
                        coos.writeObject(hallToRedact);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Hall hallToDelete = new Hall();
                if (tableHall.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Выберите какой зал вы хотите удалить");
                } else {
                    hallToDelete.setHall_id((int) tableHall.getValueAt(tableHall.getSelectedRow(), 0));
                    hallToDelete.setHallName((String) tableHall.getValueAt(tableHall.getSelectedRow(), 1));
                    hallToDelete.setCapacity((int) tableHall.getValueAt(tableHall.getSelectedRow(), 2));

                    int answer = JOptionPane.showConfirmDialog(null, "Вы уверены, что хотите удалить выбранный зал?");
                    if (answer == JOptionPane.YES_OPTION) {
                        try {
                            coos.writeObject("DELETE_HALL_ADMIN");
                            coos.writeObject(hallToDelete);
                            showHallData(cois, coos);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuForAdmin menuForAdmin = new MenuForAdmin(cois, coos);
            }
        });
    }

    private void showHallData(ObjectInputStream cois, ObjectOutputStream coos) {
        Object[] columnTitle = {
                "Id", "Название зала", "Вместимость"
        };
        tableModel = new DefaultTableModel(null, columnTitle);
        tableHall.setModel(tableModel);

        tableModel.getDataVector().removeAllElements();

        try {
            coos.writeObject("VIEW_HALL_ADMIN");
            List<Hall> halls = new ArrayList<>();
            halls = (List<Hall>) cois.readObject();

            for (Hall hall : halls) {
                Object[] data = {
                        hall.getHall_id(),
                        hall.getHallName(),
                        hall.getCapacity()
                };
                tableModel.addRow(data);
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
