package Frames.User;

import Models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EditAccountUser extends JFrame{
    private JPanel root;
    private JLabel title;
    private JPanel buttonPanel;
    private JButton buttonAccept;
    private JButton buttonBack;
    private JPanel dataPanel;
    private JPanel titlePanel;
    private JPanel changePanel;
    private JTextField textFieldUsername;
    private JTextField textFieldEmail;
    private JPasswordField passwordField;
    private JComboBox comboBoxUsernameCheck;
    private JComboBox comboBoxEmailCheck;
    private JComboBox comboBoxPasswordCheck;


    public EditAccountUser(ObjectInputStream cois, ObjectOutputStream coos, User user) {
        setVisible(true);
        setSize(450, 300);
        setContentPane(root);
        setLocationRelativeTo(null);

        buttonAccept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = null;
                if (comboBoxUsernameCheck.getSelectedItem().equals("изменить")) {
                    newUsername = textFieldUsername.getText();
                }
                String newEmail = null;
                if (comboBoxEmailCheck.getSelectedItem().equals("изменить")) {
                    newEmail = textFieldEmail.getText();
                }
                String newPassword = null;
                if (comboBoxPasswordCheck.getSelectedItem().equals("изменить")) {
                    newPassword = passwordField.getText();
                }

                try{
                    if(newUsername != null){
                        user.setUsername(newUsername);
                    }
                    if(newEmail != null){
                        user.setEmail(newEmail);
                    }
                    if(newPassword != null){
                        user.setPassword(Integer.toString(newPassword.hashCode()));
                    }

                    coos.writeObject("REDACT_ACCOUNT_USER");
                    coos.writeObject(user);
                    System.out.println(user.toString());
                    String answer = (String) cois.readObject();

                    if(answer.equals("OK")){
                        JOptionPane.showMessageDialog(null, "Ваш аккаунт был успешно отредактирован");
                        dispose();
                        MenuFrame menuFrame = new MenuFrame(cois,coos,user);
                    }

                }catch(IOException | ClassNotFoundException ex){
                    ex.printStackTrace();
                }

            }
        });
        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuFrame menuFrame = new MenuFrame(cois, coos, user);
            }
        });
    }
}
