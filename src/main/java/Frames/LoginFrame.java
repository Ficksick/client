package Frames;

import Models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class LoginFrame extends JFrame {
    private JPanel rootPanel;
    private JLabel titleFrame;
    private JPanel panelForLoginPass;
    private JPanel panelForLogin;
    private JTextField usernameTextField;
    private JLabel usernameLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel panelCreate;
    private JButton createButton;
    private JLabel passwordLabel;

    public LoginFrame(ObjectInputStream cois, ObjectOutputStream coos) {
        setSize(350, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(3);
        setContentPane(rootPanel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = usernameTextField.getText();
                String password = passwordField.getText();

                User userCheck = new User();
                User user = new User();
                try {
                    coos.writeObject("LOGIN");
                    user.setUsername(username);
                    user.setPassword(password);
                    System.out.println(user.toString());
                    coos.writeObject(user);
                    userCheck = (User) cois.readObject();
                    System.out.println(userCheck.toString());

                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                if(userCheck.getRole() == null){
                    JOptionPane.showMessageDialog(null, "Неверный логин или пароль");
                }else{
                    switch(userCheck.getRole()){
                        case "admin":
                            JOptionPane.showMessageDialog(null, "Добро пожаловать, " + username);
                            MenuForAdmin menuForAdmin = new MenuForAdmin(cois, coos);
                            setVisible(false);
                            break;
                        case "user":
                            JOptionPane.showMessageDialog(null, "Добро пожаловать, " + username);
                            MenuFrame menuUser = new MenuFrame();//user
                            break;
                    }
                }
            }
        });

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAccountFrame createAccountFrame = new CreateAccountFrame(cois, coos);
                setVisible(false);
            }
        });
    }
}
