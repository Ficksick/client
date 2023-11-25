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

    public ScreeningEditAdminFrame(ObjectInputStream cois, ObjectOutputStream coos, Screening screeningToRedact) {
        setSize(450, 300);
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
            coos.writeObject("VIEW_FILMS_ADMIN");
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
                    List<Screening> screenings = new ArrayList<>();
                    screenings = (List<Screening>) cois.readObject();

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

                    String time = spinnerHour.getValue() + ":" + spinnerMinutes.getValue() + ":00";
                    Time startTime = Time.valueOf(time);

                    String dateString = year + "-" + month + "-" + day;

                    Date date = Date.valueOf(dateString);

                    Hall hall = new Hall();
                    hall.setHall_id(hallNumberInt);
                    coos.writeObject(hall);
                    hall = (Hall) cois.readObject();

                    for (Film film : films) {
                        if (film.getTitle().equals(filmTitle)) {
                            if (screenings.size() == 0) {
                                screening.setFilm(film);
                                screening.setDate(date);
                                screening.setHall(hall);
                                screening.setStart_time(startTime);
                                Time filmDuration = film.getDuration();
                                long endTimeLong = startTime.getTime() - filmDuration.getTime();
                                Time endTime = new Time(endTimeLong);
                                screening.setEnd_time(endTime);

                                coos.writeObject(screening);

                                String answer = (String) cois.readObject();
                                if (answer.equals("OK")) {
                                    JOptionPane.showMessageDialog(null, "Сеанс успешно отредактирован");
                                }
                            } else {
                                for (Screening screeningSearch : screenings) {
                                    if (screeningSearch.getHall().getHall_id() == hallNumberInt) {
                                        checker++;
                                        Date compareDate = Date.valueOf(screeningSearch.getDate().toString());
                                        Time compareTime = Time.valueOf(screeningSearch.getStart_time().toString());
                                        long endTimeLong = startTime.getTime() - film.getDuration().getTime();
                                        Time endTime = new Time(endTimeLong);Time startCompareTime = Time.valueOf(screeningSearch.getStart_time().toString());
                                        Time endTCompareTime = Time.valueOf((screeningSearch.getEnd_time().toString()));

                                        if (startTime.compareTo(startCompareTime) > 0 || startTime.compareTo(startCompareTime) == 0
                                                && endTime.compareTo(endTCompareTime) < 0) {
                                            if (date.compareTo((compareDate)) == 0) {
                                                JOptionPane.showMessageDialog(null, "В это время уже есть сеанс");
                                                screening = new Screening();
                                                coos.writeObject(screening);
                                                String answer = (String) cois.readObject();
                                                if(answer.equals("EXIST")){
                                                    break;
                                                }
                                            } else {
                                                screening.setFilm(film);
                                                screening.setDate(date);
                                                screening.setHall(hall);
                                                screening.setStart_time(startTime);
                                                Time filmDuration = film.getDuration();
//                                                long endTimeLong = startTime.getTime() - filmDuration.getTime();
//                                                Time endTime = new Time(endTimeLong);
                                                screening.setEnd_time(endTime);

                                                coos.writeObject(screening);

                                                String answer = (String) cois.readObject();
                                                if (answer.equals("OK")) {
                                                    JOptionPane.showMessageDialog(null, "Сеанс успешно отредактирован");
                                                }
                                            }
                                        }
                                    } else if (checker == 0) {
                                        screening.setFilm(film);
                                        screening.setDate(date);
                                        screening.setHall(hall);
                                        screening.setStart_time(startTime);
                                        Time filmDuration = film.getDuration();
                                        long endTimeLong = startTime.getTime() - filmDuration.getTime();
                                        Time endTime = new Time(endTimeLong);
                                        screening.setEnd_time(endTime);
                                        coos.writeObject(screening);

                                        String answer = (String) cois.readObject();
                                        if (answer.equals("OK")) {
                                            JOptionPane.showMessageDialog(null, "Сеанс успешно отредактирован");
                                        }
                                    }
                                }
                            }
                        }
                    }

                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }

        });
    }
}

