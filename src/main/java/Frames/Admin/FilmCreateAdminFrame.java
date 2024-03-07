package Frames.Admin;

import Models.Film;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Time;

public class FilmCreateAdminFrame extends JFrame {
    private JPanel root;
    private JLabel title;
    private JPanel dataPanel;
    private JPanel buttonsPanel;
    private JButton buttonAccept;
    private JButton buttonBack;
    private JTextField textFieldTitle;
    private JComboBox comboBoxGenre;
    private JTextField textFieldDirector;
    private JTextField textFieldMainActor;
    private JComboBox comboBoxAge;
    private JPanel agePanel;
    private JPanel durationPanel;
    private JSpinner spinnerHour;
    private JSpinner spinnerMinute;
    private JSpinner spinnerSecond;

    public FilmCreateAdminFrame(ObjectInputStream cois, ObjectOutputStream coos) {
        setSize(450, 350);
        setContentPane(root);
        setLocationRelativeTo(null);
        setVisible(true);

        SpinnerModel timeModelHour = new SpinnerNumberModel(0, 0, 24, 1);
        SpinnerModel timeModelMinutes = new SpinnerNumberModel(0, 0, 59, 1);
        SpinnerModel timeModelSeconds = new SpinnerNumberModel(0, 0, 59, 1);
        spinnerHour.setModel(timeModelHour);
        spinnerMinute.setModel(timeModelMinutes);
        spinnerSecond.setModel(timeModelSeconds);

        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FilmInformationForAdminFrame filmInformationForAdminFrame = new FilmInformationForAdminFrame(cois, coos);
            }
        });
        buttonAccept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    coos.writeObject("CREATE_FILM_ADMIN");
                    Film film = new Film();

                    film.setTitle(textFieldTitle.getText());
                    film.setDirector(textFieldDirector.getText());
                    film.setGenre((String) comboBoxGenre.getSelectedItem());
                    film.setAge((String) comboBoxAge.getSelectedItem());
                    film.setMainActor(textFieldMainActor.getText());

                    String duration = spinnerHour.getValue() + ":"
                            + spinnerMinute.getValue() + ":"
                            +spinnerSecond.getValue();

                    Time durationTime = Time.valueOf(duration);

                    film.setDuration(durationTime);

                    coos.writeObject(film);

                    JOptionPane.showMessageDialog(null, "Фильм успешно создан");
                }catch(IOException ex){
                    ex.printStackTrace();
                }

                FilmInformationForAdminFrame filmInformationForAdminFrame = new FilmInformationForAdminFrame(cois,coos);
                dispose();
            }
        });
    }
}
