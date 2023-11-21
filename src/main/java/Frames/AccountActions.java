package Frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountActions extends JFrame{
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

    public AccountActions(){
        setSize(350,400);
        setVisible(true);
        setContentPane(root);
        setLocationRelativeTo(null);
        showAccountData();

        buttonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

    private DefaultTableModel tableModel;

    private void showAccountData(){
            Object[] columnTitle = {"Id, Имя пользователя","email", "Роль", "Пароль"};
            tableModel = new DefaultTableModel(null, columnTitle);
            userInformation.setModel(tableModel);

            tableModel.getDataVector().removeAllElements();

            //принять информацию с сервера про пользователя
            //вывести информацию о пользователе
            Object[] inf = {"test", "test", "test"};
            tableModel.addRow(inf);
    }
}
