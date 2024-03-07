package Frames.User;

import Frames.LoginFrame;
import Models.Film;
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

public class AccountActions extends JFrame {
    private JPanel root;
    private JLabel title;
    private JTable userInformation;
    private JPanel tablePanel;
    private JScrollPane scrollPane;
    private JPanel buttonsPanel;
    private JButton buttonEdit;
    private JButton buttonDelete;
    private JButton buttonBack;
    private JPanel editPanel;
    private JPanel deletePanel;
    private JPanel backPanel;

    public AccountActions(ObjectInputStream cois, ObjectOutputStream coos, User user) {
        setSize(350, 400);
        setVisible(true);
        setContentPane(root);
        setLocationRelativeTo(null);
        showAccountData(cois, coos, user);

        buttonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditAccountUser editAccountUser = new EditAccountUser(cois, coos, user);
                dispose();
            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Вы уверены, что хотите удалить аккаунт?");

                if (choice == 0) {
                    try {
                        coos.writeObject("DELETE_ACCOUNT_USER");
                        coos.writeObject(user);
                        String answer = (String) cois.readObject();

                        if (answer.equals("OK")) {
                            JOptionPane.showMessageDialog(null, "Ваш аккаунт был удален");
                        }
                        dispose();
                        LoginFrame loginFrame = new LoginFrame(cois, coos);
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
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
    }

    private DefaultTableModel tableModel;

    private void showAccountData(ObjectInputStream cois, ObjectOutputStream coos, User userCheck) {
        Object[] columnTitle = {"Имя пользователя", "email", "Пароль (хэшированный)"};
        tableModel = new DefaultTableModel(null, columnTitle);
        userInformation.setModel(tableModel);

        tableModel.getDataVector().removeAllElements();

        try {
            coos.writeObject("VIEW_ACCOUNT_USER");
            List<User> users = new ArrayList<>();
            users = (List<User>) cois.readObject();

            for (User user : users) {
                if (user.getUser_id() == userCheck.getUser_id()) {
                    Object[] data = {
                            user.getUsername(),
                            user.getEmail(),
                            user.getPassword()
                    };
                    tableModel.addRow(data);
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
