package Frames.Admin;

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
                dispose();
                MenuForAdmin menuForAdmin = new MenuForAdmin(cois, coos);
            }
        });

        buttonRedact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User userToRedact = new User();
                if (tableInformation.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Выберите кого вы хотите изменить");
                } else {
                    userToRedact.setUser_id((int) tableInformation.getValueAt(tableInformation.getSelectedRow(), 0));
                    userToRedact.setUsername((String) tableInformation.getValueAt(tableInformation.getSelectedRow(), 1));
                    userToRedact.setEmail((String) tableInformation.getValueAt(tableInformation.getSelectedRow(), 2));
                    userToRedact.setRole((String) tableInformation.getValueAt(tableInformation.getSelectedRow(), 3));
                    userToRedact.setPassword((String) tableInformation.getValueAt(tableInformation.getSelectedRow(), 4));

                    if (tableInformation.getValueAt(tableInformation.getSelectedRow(), 1).equals("mainadmin")) {
                        JOptionPane.showMessageDialog(null, "Вы не можете редактировать главного админа");
                    } else {
                        EditUserFrame editUserFrame = new EditUserFrame(cois, coos);
                        dispose();
                        try {
                            coos.writeObject("REDACT_USER_ADMIN");
                            coos.writeObject(userToRedact);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User userToDelete = new User();

                if (tableInformation.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Выберите кого вы хотите удалить");
                }else{
                    userToDelete.setUser_id((int) tableInformation.getValueAt(tableInformation.getSelectedRow(), 0));
                    userToDelete.setUsername((String) tableInformation.getValueAt(tableInformation.getSelectedRow(), 1));
                    userToDelete.setEmail((String) tableInformation.getValueAt(tableInformation.getSelectedRow(), 2));
                    userToDelete.setRole((String) tableInformation.getValueAt(tableInformation.getSelectedRow(), 3));
                    userToDelete.setPassword((String) tableInformation.getValueAt(tableInformation.getSelectedRow(), 4));

                    if (tableInformation.getValueAt(tableInformation.getSelectedRow(), 1).equals("mainadmin")) {
                        JOptionPane.showMessageDialog(null, "Вы не можете удалить главного админа");
                    } else {
                        int result = JOptionPane.showConfirmDialog(null, "Вы уверены, что хотите удалить пользователя?");
                        if (result == JOptionPane.YES_OPTION) {
                            try {
                                coos.writeObject("DELETE_USER_ADMIN");
                                coos.writeObject(userToDelete);
                                String answer = (String) cois.readObject();

                                if(answer.equals("OK")){
                                    JOptionPane.showMessageDialog(null, "Пользователь был удален");
                                }

                                showAccountData(cois, coos);
                            } catch (IOException | ClassNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
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
    }
}