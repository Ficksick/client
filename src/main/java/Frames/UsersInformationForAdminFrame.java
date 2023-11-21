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
                User userToRedact = new User();
                userToRedact.setUser_id((int) tableInformation.getValueAt(tableInformation.getSelectedRow(), 0));
                userToRedact.setUsername((String) tableInformation.getValueAt(tableInformation.getSelectedRow(), 1));
                userToRedact.setEmail((String) tableInformation.getValueAt(tableInformation.getSelectedRow(), 2));
                userToRedact.setRole((String) tableInformation.getValueAt(tableInformation.getSelectedRow(), 3));
                userToRedact.setPassword((String) tableInformation.getValueAt(tableInformation.getSelectedRow(), 4));

                System.out.println(userToRedact.toString());

                if(tableInformation.getValueAt(tableInformation.getSelectedRow(),1).equals("mainadmin")){
                    JOptionPane.showMessageDialog(null, "Вы не можете редактировать главного админа");
                }else{
                    EditUserFrame editUserFrame = new EditUserFrame();
                    //передача информации по редактированию
                }

                //доделать редактирование пользователя
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
