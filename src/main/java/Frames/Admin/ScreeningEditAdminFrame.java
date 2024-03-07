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
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;

public class ScreeningEditAdminFrame extends JFrame {
    private JPanel root;
    private JLabel title;
    private JPanel dataPanel;
    private JPanel buttonsPanel;
    private JComboBox comboBoxFilmName;
    private JComboBox comboBoxHallNumber;
    private JComboBox comboBoxDay;
    private JComboBox comboBoxMonth;
    private JComboBox comboBoxYear;
    private JSpinner spinnerHour;
    private JSpinner spinnerMinutes;
    private JButton buttonAccept;
    private JButton buttonBack;
    private JPanel spinnerPanel;
    private JPanel datePanel;
    private JComboBox comboBoxTitleCheck;
    private JComboBox comboBoxHallNumberCheck;
    private JComboBox comboBoxDateCheck;
    private JComboBox comboBoxStartTimeCheck;
    private JComboBox comboBoxPriceCheck;
    private JTextField textFieldPrice;

    public ScreeningEditAdminFrame(ObjectInputStream cois, ObjectOutputStream coos, Screening screeningToRedact) {
        setSize(450, 350);
        setContentPane(root);
        setLocationRelativeTo(null);
        setVisible(true);
        SpinnerModel timeModelHour = new SpinnerNumberModel(0, 0, 24, 1);
        SpinnerModel timeModelMinutes = new SpinnerNumberModel(0, 0, 59, 1);
        spinnerHour.setModel(timeModelHour);
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
                Screening screening = new Screening();
                int checker = 0;
                try {
                    coos.writeObject("REDACT_SCREENING_ADMIN");
                    coos.writeObject(screeningToRedact);

                    Screening screeningToRedactFromServer = (Screening) cois.readObject();

                    List<Screening> screenings = new ArrayList<>();
                    screenings = (List<Screening>) cois.readObject();

                    List<Film> films = new ArrayList<>();
                    films = (List<Film>) cois.readObject();

                    List<Hall> halls = new ArrayList<>();
                    halls = (List<Hall>) cois.readObject();

                    if (comboBoxTitleCheck.getSelectedItem().equals("изменить")) {
                        for (Film film : films) {
                            if (comboBoxFilmName.getSelectedItem().equals(film.getTitle())) {
                                screeningToRedactFromServer.setFilm(film);

                                LocalTime startTime = screeningToRedactFromServer.getStart_time().toLocalTime();
                                LocalTime duration = film.getDuration().toLocalTime();

                                LocalTime endTimeLocal = startTime.plusHours(duration.getHour()).plusMinutes(duration.getMinute()).plusSeconds((duration.getSecond()));

                                Time endTime = Time.valueOf(endTimeLocal);

                                screeningToRedactFromServer.setEnd_time(endTime);
                            }
                        }
                    }


                    if (comboBoxHallNumberCheck.getSelectedItem().equals("изменить")) {
                        for (Hall hall : halls) {
                            if (comboBoxHallNumber.getSelectedItem().equals(hall.getHall_id())) {
                                screeningToRedactFromServer.setHall(hall);
                            }
                        }
                    }

                    double price = .0;

                    if (comboBoxPriceCheck.getSelectedItem().equals("изменить")) {
                        price = Double.parseDouble(textFieldPrice.getText());
                        screeningToRedactFromServer.setPrice(price);
                    }

                    String day, month, year;

                    day = (String) comboBoxDay.getSelectedItem();
                    month = (String) comboBoxMonth.getSelectedItem();
                    year = (String) comboBoxYear.getSelectedItem();

                    String time = spinnerHour.getValue() + ":" + spinnerMinutes.getValue() + ":00";
                    Time startTime = Time.valueOf(time);

                    String dateString = year + "-" + month + "-" + day;

                    Date date = Date.valueOf(dateString);

                    if (comboBoxDateCheck.getSelectedItem().equals("изменить")) {
                        screeningToRedactFromServer.setDate(date);
                    }

                    if (comboBoxStartTimeCheck.getSelectedItem().equals("изменить")) {
                        screeningToRedactFromServer.setStart_time(startTime);

                        Film film = screeningToRedactFromServer.getFilm();

                        LocalTime startTimeLocal = startTime.toLocalTime();
                        LocalTime duration = film.getDuration().toLocalTime();

                        LocalTime endTimeLocal = startTimeLocal.plusHours(duration.getHour()).plusMinutes(duration.getMinute()).plusSeconds((duration.getSecond()));

                        Time endTime = Time.valueOf(endTimeLocal);

                        screeningToRedactFromServer.setEnd_time(endTime);

                        screeningToRedactFromServer.setEnd_time(endTime);
                    }

                    coos.writeObject(screeningToRedactFromServer);

                    String answer = (String) cois.readObject();

                    if (answer.equals("OK")) {
                        JOptionPane.showMessageDialog(null, "Сеанс успешно отредактирован");
                        dispose();
                        ScreeningInformationForAdminFrame screeningInformationForAdminFrame = new ScreeningInformationForAdminFrame(cois, coos);
                    } else if (answer.equals("EXIST")) {
                        JOptionPane.showMessageDialog(null, "В это время есть сеанс");
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}

