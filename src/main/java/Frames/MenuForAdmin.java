package Frames;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MenuForAdmin extends JFrame{
    private JPanel root;
    private JButton buttonUser;
    private JButton buttonHall;
    private JButton buttonScreening;
    private JButton buttonFilm;
    private JButton buttonExit;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;

    public MenuForAdmin(ObjectInputStream cois, ObjectOutputStream coos){
        setVisible(true);
        setContentPane(root);
        setSize(350, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);

        buttonUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsersInformationForAdminFrame usersInformationForAdminFrame = new UsersInformationForAdminFrame(cois, coos);
                setVisible(false);
            }
        });
    }
}
