package Frames.Admin;

import Models.Film;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Time;

public class FilmEditAdminFrame extends JFrame {
    private JPanel root;
    private JPanel dataPanel;
    private JPanel buttonsPanel;
    private JLabel title;
    private JButton buttonAccept;
    private JButton buttonBack;
    private JTextField textFieldTitle;
    private JComboBox comboBoxGenre;
    private JTextField textFieldDirector;
    private JTextField textFieldMainActor;
    private JComboBox comboBoxAge;
    private JPanel agePanel;
    private JSpinner spinnerHour;
    private JSpinner spinnerMinute;
    private JSpinner spinnerSecond;
    private JComboBox comboBoxTitleCheck;
    private JComboBox comboBoxGenreCheck;
    private JComboBox comboBoxDirectorCheck;
    private JComboBox comboBoxMainActorCheck;
    private JComboBox comboBoxDurationCheck;
    private JComboBox comboBoxAgeCheck;
    private JPanel durationPanel;

    public FilmEditAdminFrame(ObjectInputStream cois, ObjectOutputStream coos) {
        setVisible(true);
        setContentPane(root);
        setLocationRelativeTo(null);
        setSize(450, 350);

        buttonAccept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newTitle = null;
                if(comboBoxTitleCheck.getSelectedItem().equals("изменить")){
                    newTitle = textFieldTitle.getText();
                }

                String newGenre = null;
                if(comboBoxGenreCheck.getSelectedItem().equals("изменить")){
                    newGenre = (String) comboBoxGenre.getSelectedItem();
                }

                String newDirector = null;
                boolean isIntegerInDirector = false;
                if(comboBoxDirectorCheck.getSelectedItem().equals("изменить")){
                    newDirector = textFieldDirector.getText();
                    while(true){
                        for(int i = 0; i < newDirector.length() && !isIntegerInDirector; i++){
                            if(Character.isDigit(newDirector.charAt(i))){
                                isIntegerInDirector = true;
                            }
                        }
                        if(!isIntegerInDirector){
                            break;
                        }else{
                            JOptionPane.showMessageDialog(null, "Неверный ввод! Данные в поле \"Режиссер\" не должны содержать цифры");
                            textFieldDirector.setText("");
                            break;
                        }
                    }
                }

                String newMainActor = null;
                boolean isIntegerInMainActor = false;
                if(comboBoxMainActorCheck.getSelectedItem().equals("изменить")){
                    newMainActor = textFieldMainActor.getText();
                    while(true){
                        for(int i = 0; i < newMainActor.length() && !isIntegerInMainActor; i++){
                            if(Character.isDigit(newMainActor.charAt(i))){
                                isIntegerInMainActor = true;
                            }
                        }
                        if(!isIntegerInMainActor){
                            break;
                        }else{
                            JOptionPane.showMessageDialog(null, "Неверный ввод! Данные в поле \"Главный актёр\" не должны содержать цифры");
                            textFieldDirector.setText("");
                            break;
                        }
                    }
                }

                Time newDuration = null;
                if(comboBoxDurationCheck.getSelectedItem().equals("изменить")){
                    String duration = spinnerHour.getValue() + ":"
                            + spinnerMinute.getValue() + ":"
                            +spinnerSecond.getValue();
                    newDuration = Time.valueOf(duration);
                }

                String newAge = null;
                if(comboBoxAgeCheck.getSelectedItem().equals("изменить")){
                    newAge = (String) comboBoxAge.getSelectedItem();
                }

                Film filmToRedact = new Film();
                filmToRedact.setTitle(newTitle);
                filmToRedact.setDirector(newDirector);
                filmToRedact.setMainActor(newMainActor);
                filmToRedact.setGenre(newGenre);
                filmToRedact.setDuration(newDuration);
                filmToRedact.setAge(newAge);

                try{
                    coos.writeObject(filmToRedact);
                    String answer = (String) cois.readObject();
                    if(answer.equals("OK")){
                        JOptionPane.showMessageDialog(null, "Фильм был успешно отредактирован");
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
                FilmInformationForAdminFrame filmInformationForAdminFrame = new FilmInformationForAdminFrame(cois, coos);
            }
        });
    }
}
