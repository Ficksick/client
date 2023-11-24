package Frames.Admin;

import Models.Film;
import Models.Hall;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScreeningCreateAdminFrame extends JFrame {
    private JLabel title;
    private JPanel root;
    private JPanel dataPanel;
    private JPanel buttonsPanel;
    private JButton buttonAccept;
    private JButton buttonBack;
    private JComboBox comboBoxFilmName;
    private JComboBox comboBoxHallNumber;
    private JComboBox comboBoxDay;
    private JComboBox comboBoxMonth;
    private JComboBox comboBoxYear;
    private JPanel datePanel;
    private JPanel timePanel;
    private JSpinner spinnerHours;
    private JSpinner spinnerMinutes;
    private JSpinner test;

    public ScreeningCreateAdminFrame(ObjectInputStream cois, ObjectOutputStream coos) {
        setSize(350, 400);
        setVisible(true);
        setContentPane(root);
        setLocationRelativeTo(null);

        SpinnerModel timeModelHour = new SpinnerNumberModel(0, 0, 24, 1);
        SpinnerModel timeModelMinutes = new SpinnerNumberModel(0, 0, 59, 1);
        spinnerHours.setModel(timeModelHour);
        spinnerMinutes.setModel(timeModelMinutes);

        DefaultComboBoxModel<String> modelDay = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<String> modelMonth = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<String> modelYear = new DefaultComboBoxModel<>();

        for (int i = 1; i <= 31; i++) {
            modelDay.addElement(Integer.toString(i));
            comboBoxDay.setModel(modelDay);
        }

        for (int i = 1; i <= 12; i++) {
            modelMonth.addElement(Integer.toString(i));
            comboBoxMonth.setModel(modelMonth);
        }

        for (int i = 2023; i <= 2100; i++) {
            modelYear.addElement(Integer.toString(i));
            comboBoxYear.setModel(modelYear);
        }

        try {
            coos.writeObject("VIEW_HALL_ADMIN");
            List<Hall> halls = new ArrayList<>();
            halls = (List<Hall>) cois.readObject();

            DefaultComboBoxModel<String> modelNumberHall = new DefaultComboBoxModel<>();

            for(Hall hall : halls){
                modelNumberHall.addElement(Integer.toString(hall.getHall_id()));
                comboBoxHallNumber.setModel(modelNumberHall);
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        try {
            coos.writeObject("VIEW_FILMS_ADMIN");
            List<Film> films = new ArrayList<>();
            films = (List<Film>) cois.readObject();

            DefaultComboBoxModel<String> modelTitleFilm = new DefaultComboBoxModel<>();

            for (Film film : films) {

                modelTitleFilm.addElement(film.getTitle());
                comboBoxFilmName.setModel(modelTitleFilm);
            }

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ScreeningInformationForAdminFrame screeningInformationForAdminFrame = new ScreeningInformationForAdminFrame(cois, coos);
            }
        });
    }
}
