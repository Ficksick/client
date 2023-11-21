package Frames;

import Models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class UsersInformationForAdminFrame extends JFrame {
    private JPanel root;
    private JPanel panelTable;
    private JPanel panelButtons;
    private JLabel title;
    private JScrollPane scrollPane;
    private JTable tableInformation;
    private JButton buttonRedact;
    private JButton buttonDelete;
    private JButton buttonBack;
    private JPanel panelRedact;
    private JPanel panelDelete;
    private JPanel panelBack;
    private DefaultTableModel tableModel;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;

    public UsersInformationForAdminFrame(ObjectInputStream cois, ObjectOutputStream coos) {
        setSize(450, 300);
        setVisible(true);
        setContentPane(root);
        setLocationRelativeTo(null);
        showAccountData(cois, coos);

        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                MenuForAdmin menuForAdmin = new MenuForAdmin(cois, coos);
            }
        });

        buttonRedact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] row = new Object[5];

                row[0] = tableInformation.getValueAt(tableInformation.getSelectedRow(), 0);
                row[1] = tableInformation.getValueAt(tableInformation.getSelectedRow(), 1);
                row[2] = tableInformation.getValueAt(tableInformation.getSelectedRow(), 2);
                row[3] = tableInformation.getValueAt(tableInformation.getSelectedRow(), 3);
                row[4] = tableInformation.getValueAt(tableInformation.getSelectedRow(), 4);

                System.out.println(row);

                EditUserFrame editUserFrame = new EditUserFrame();
            }
        });
    }

    private void showAccountData(ObjectInputStream cois, ObjectOutputStream coos) {
        Object[] columnTitle = {"Id", "Имя пользователя", "email", "Роль", "Пароль"};
        tableModel = new DefaultTableModel(null, columnTitle);
        tableInformation.setModel(tableModel);

        tableModel.getDataVector().removeAllElements();

        try {
            coos.writeObject("VIEW_USERS_ADMIN");
            List<User> users = new ArrayList();
            users = (List<User>) cois.readObject();
            System.out.println(users);

            for (User user : users) {
                Object[] data = {
                        user.getUser_id(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole(),
                        user.getPassword()
                };
                tableModel.addRow(data);
            }


        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        //принять информацию с сервера про пользователя
        //вывести информацию о пользователе
    }
}
