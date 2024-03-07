package Frames.Admin;

import Models.Film;
import Models.Hall;
import Models.Screening;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
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
    private JTextField textFieldPrice;
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

            if (halls.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Пока нет залов");
            } else {
                for (Hall hall : halls) {
                    modelNumberHall.addElement(Integer.toString(hall.getHall_id()));
                    comboBoxHallNumber.setModel(modelNumberHall);
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        try {
            coos.writeObject("VIEW_FILM_ADMIN");
            List<Film> films = new ArrayList<>();
            films = (List<Film>) cois.readObject();

            DefaultComboBoxModel<String> modelTitleFilm = new DefaultComboBoxModel<>();

            if (films.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Пока нет фильмов");
            } else {
                for (Film film : films) {
                    modelTitleFilm.addElement(film.getTitle());
                    comboBoxFilmName.setModel(modelTitleFilm);
                }
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
        buttonAccept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //проверка на наличие сеанса в это время и дату

                Screening screening = new Screening();
                int checker = 0;

                try {
                    coos.writeObject("CREATE_SCREENING_ADMIN");

                    List<Film> films = new ArrayList<>();
                    films = (List<Film>) cois.readObject();

                    String filmTitle = (String) comboBoxFilmName.getSelectedItem();

                    String hallNumber = (String) comboBoxHallNumber.getSelectedItem();
                    int hallNumberInt = 0;
                    hallNumberInt = Integer.parseInt(hallNumber.trim());

                    String day, month, year;

                    day = (String) comboBoxDay.getSelectedItem();
                    month = (String) comboBoxMonth.getSelectedItem();
                    year = (String) comboBoxYear.getSelectedItem();

                    String time = spinnerHours.getValue() + ":" + spinnerMinutes.getValue() + ":00";
                    Time startTime = Time.valueOf(time);

                    String dateString = year + "-" + month + "-" + day;

                    Date date = Date.valueOf(dateString);

                    Hall hall = new Hall();
                    hall.setHall_id(hallNumberInt);
                    coos.writeObject(hall);
                    hall = (Hall) cois.readObject();

                    double price = .0;
                    if(textFieldPrice.getText().equals(" ")){
                        JOptionPane.showMessageDialog(null, "Не все поля заполены");
                    }else{
                        price = Double.parseDouble(textFieldPrice.getText());

                        for(Film film : films){
                            if(film.getTitle().equals(filmTitle)){
                                screening.setFilm(film);
                                screening.setHall(hall);
                                screening.setDate(date);
                                screening.setStart_time(startTime);
                                screening.setPrice(price);

                                LocalTime startTimeLocal = startTime.toLocalTime();
                                LocalTime duration = film.getDuration().toLocalTime();

                                LocalTime endTimeLocal = startTimeLocal.plusHours(duration.getHour()).plusMinutes(duration.getMinute()).plusSeconds((duration.getSecond()));

                                Time endTime = Time.valueOf(endTimeLocal);

                                screening.setEnd_time(endTime);
                            }
                        }
                        coos.writeObject(screening);
                    }

                    String answer = (String) cois.readObject();

                    if(answer.equals("EXIST")){
                        JOptionPane.showMessageDialog(null, "Сеанс в такое время уже существует");
                    }else if(answer.equals("OK")){
                        JOptionPane.showMessageDialog(null, "Сеанс успешно создан");
                        dispose();
                        ScreeningInformationForAdminFrame screeningInformationForAdminFrame = new ScreeningInformationForAdminFrame(cois, coos);
                    }

                    //сделать проверку на ввод всех полей
                    //сделать проверку на наличие сеанса в это же время
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
