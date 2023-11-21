package Frames;

import Models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class CreateAccountFrame extends JFrame{
    private JPanel root;
    private JPanel dataPanel;
    private JPanel buttonPanel;
    private JLabel lableCreateAcc;
    private JButton createAccButton;
    private JTextField textFieldUsername;
    private JTextField textFieldEmail;
    private JPasswordField passwordFieldAccepted;
    private JPasswordField passwordField;
    private JButton buttonBack;
    private JPanel backButtonPanel;
    private JButton buttonCreate;

    private ObjectInputStream cois;
    private ObjectOutputStream coos;

    public CreateAccountFrame(ObjectInputStream cois, ObjectOutputStream coos){
        setVisible(true);
        setSize(350,400);
        setContentPane(root);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);

        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                LoginFrame loginFrame = new LoginFrame(cois, coos);
            }
        });

        createAccButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User userToDB = new User();

                String password = passwordField.getText();
                String passwordAccepted = passwordFieldAccepted.getText();

                if(!password.equals(passwordAccepted)){
                    JOptionPane.showMessageDialog(null,"Неверный пароль");
                }else{
                    userToDB.setUsername(textFieldUsername.getText());
                    userToDB.setPassword(password);
                    userToDB.setEmail(textFieldEmail.getText());
                    userToDB.setRole("user");

                    System.out.println(userToDB.toString());
                    try {
                        coos.writeObject("REGISTRATION");
                        coos.writeObject(userToDB);

                        String answer = (String) cois.readObject();

                        if(answer.equals("OK")){
                            JOptionPane.showMessageDialog(null, "Аккаунт успешно создан");
                        } else if (answer.equals("ERROR")) {
                            JOptionPane.showMessageDialog(null, "Такой аккаунт уже существует");
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    //отправить на сервер
                    //принять с сервера ответ
                    //вернуться обратно на loginFrame
                }
            }
        });
    }
}
