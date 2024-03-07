package Frames.Admin;

import Models.Film;
import Models.Hall;
import Models.Screening;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ScreeningInformationForAdminFrame extends JFrame {
    private JPanel root;
    private JPanel dataPanel;
    private JPanel buttonsPanel;
    private JScrollPane scrollPane;
    private JTable tableScreening;
    private JPanel createPanel;
    private JPanel editPanel;
    private JPanel deletePanel;
    private JPanel backPanel;
    private JButton buttonCreate;
    private JButton buttonEdit;
    private JButton buttonDelete;
    private JButton buttonBack;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private DefaultTableModel tableModel;

    public ScreeningInformationForAdminFrame(ObjectInputStream cois, ObjectOutputStream coos) {
        setSize(450, 350);
        setVisible(true);
        setContentPane(root);
        setLocationRelativeTo(null);
        showScreeningData(cois, coos);

        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuForAdmin menuForAdmin = new MenuForAdmin(cois, coos);
            }
        });
        buttonCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreeningCreateAdminFrame screeningCreateAdminFrame = new ScreeningCreateAdminFrame(cois, coos);
                dispose();
            }
        });
        buttonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Screening screeningToRedact = new Screening();
                if (tableScreening.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Выберите какой сеанс вы хотите отредактировать");
                } else {
                    screeningToRedact.setScreening_id((int) tableScreening.getValueAt(tableScreening.getSelectedRow(), 0));

                    ScreeningEditAdminFrame screeningEditAdminFrame = new ScreeningEditAdminFrame(cois, coos, screeningToRedact);
                    dispose();
                }
            }
        });
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Screening screeningToDelete = new Screening();
                if (tableScreening.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Выберите какой сеанс вы хотите удалить");
                } else {
                    screeningToDelete.setScreening_id((int) tableScreening.getValueAt(tableScreening.getSelectedRow(), 0));
                    int answer = JOptionPane.showConfirmDialog(null, "Вы уверены, что хотите удалить выбранный сеанс?");
                    if (answer == JOptionPane.YES_OPTION) {
                        try {
                            coos.writeObject("DELETE_SCREENING_ADMIN");
                            coos.writeObject(screeningToDelete);
                            showScreeningData(cois, coos);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void showScreeningData(ObjectInputStream cois, ObjectOutputStream coos) {
        Object[] columnTitle = {
                "Id", "Название фильма", "id_зала", "Дата", "Начало", "Конец", "Цена"
        };
        tableModel = new DefaultTableModel(null, columnTitle);
        tableScreening.setModel(tableModel);

        tableModel.getDataVector().removeAllElements();

        try {
            coos.writeObject("VIEW_SCREENING_ADMIN");
            List<Screening> screenings = new ArrayList<>();
            screenings = (List<Screening>) cois.readObject();

            for (Screening screening : screenings) {
                Object[] data = {
                        screening.getScreening_id(),
                        screening.getFilmTitle(),
                        screening.getHallID(),
                        screening.getDate(),
                        screening.getStart_time(),
                        screening.getEnd_time(),
                        screening.getPrice()
                };
                //System.out.println(screening.toString());
                tableModel.addRow(data);
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
