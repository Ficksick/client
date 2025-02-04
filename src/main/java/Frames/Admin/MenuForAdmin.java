package Frames.Admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MenuForAdmin extends JFrame {
    private JPanel root;
    private JButton buttonUser;
    private JButton buttonHall;
    private JButton buttonScreening;
    private JButton buttonFilm;
    private JButton buttonExit;
    private JLabel title;
    private JButton buttonStatistic;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;

    public MenuForAdmin(ObjectInputStream cois, ObjectOutputStream coos) {
        setVisible(true);
        setContentPane(root);
        setSize(350, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);

        buttonUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsersInformationForAdminFrame usersInformationForAdminFrame = new UsersInformationForAdminFrame(cois, coos);
                dispose();
            }
        });

        buttonHall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HallInformationForAdmin hallInformationForAdmin = new HallInformationForAdmin(cois, coos);
                dispose();
            }
        });
        buttonScreening.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreeningInformationForAdminFrame screeningInformationForAdminFrame = new ScreeningInformationForAdminFrame(cois, coos);
                dispose();
            }
        });
        buttonFilm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FilmInformationForAdminFrame filmInformationForAdminFrame = new FilmInformationForAdminFrame(cois, coos);
                dispose();
            }
        });
        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    cois.close();
                    coos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        buttonStatistic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StatisticFrame statisticFrame = new StatisticFrame(cois,coos);
                dispose();
            }
        });
    }
}
