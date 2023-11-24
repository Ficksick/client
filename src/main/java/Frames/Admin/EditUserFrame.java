package Frames.Admin;

import Frames.Admin.UsersInformationForAdminFrame;
import Models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EditUserFrame extends JFrame {
    private JPanel root;
    private JLabel title;
    private JTextField textFieldUsername;
    private JTextField textFieldEmail;
    private JTextField textFieldPassword;
    private JComboBox comboBoxRole;
    private JComboBox comboBoxPassword;
    private JComboBox comboBoxEmail;
    private JComboBox comboBoxUsername;
    private JPanel dataPanel;
    private JPanel titlePanel;
    private JLabel usernameLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel roleLabel;
    private JPanel changePanel;
    private JButton buttonAccept;
    private JButton buttonBack;
    private JPanel panelButtons;

    public EditUserFrame(ObjectInputStream cois, ObjectOutputStream coos) {
        setVisible(true);
        setSize(450, 300);
        setContentPane(root);
        setLocationRelativeTo(null);
        buttonAccept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = null;
                if (comboBoxUsername.getSelectedItem().equals("изменить")) {
                    newUsername = textFieldUsername.getText();
                }
                String newEmail = null;
                if (comboBoxEmail.getSelectedItem().equals("изменить")) {
                    newEmail = textFieldEmail.getText();
                }
                String newPassword = null;
                if (comboBoxPassword.getSelectedItem().equals("изменить")) {
                    newPassword = textFieldPassword.getText();
                }
                String newRole = null;
                if (comboBoxRole.getSelectedItem().equals("сделать администратором")) {
                    newRole = "admin";
                } else if (comboBoxRole.getSelectedItem().equals("сделать пользователем")) {
                    newRole = "user";
                }

                try {
                    User userToChange = new User();
                    userToChange.setUsername(newUsername);
                    userToChange.setEmail(newEmail);
                    userToChange.setPassword(newPassword);
                    userToChange.setRole(newRole);

                    coos.writeObject(userToChange);
                    System.out.println(userToChange.toString());

                    String answer = (String) cois.readObject();
                    if (answer.equals("OK")) {
                        JOptionPane.showMessageDialog(null, "Пользователь успешно изменен");
                        dispose();
                        UsersInformationForAdminFrame usersInformationForAdminFrame = new UsersInformationForAdminFrame(cois, coos);
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                UsersInformationForAdminFrame usersInformationForAdminFrame = new UsersInformationForAdminFrame(cois, coos);
            }
        });
    }
}
