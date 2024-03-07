package Frames.Admin;

import Models.Film;
import Models.Hall;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class FilmInformationForAdminFrame extends JFrame {
    private JLabel title;
    private JPanel root;
    private JPanel tablePanel;
    private JPanel buttonsPanel;
    private JScrollPane scrollPane;
    private JTable tableFilm;
    private JButton buttonCreate;
    private JButton buttonRedact;
    private JButton buttonDelete;
    private JButton buttonBack;
    private DefaultTableModel tableModel;

    public FilmInformationForAdminFrame(ObjectInputStream cois, ObjectOutputStream coos) {
        setSize(450, 300);
        setContentPane(root);
        setLocationRelativeTo(null);
        setVisible(true);

        showData(cois, coos);

        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuForAdmin menuForAdmin = new MenuForAdmin(cois, coos);
            }
        });
        buttonCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FilmCreateAdminFrame filmCreateAdminFrame = new FilmCreateAdminFrame(cois, coos);
                dispose();
            }
        });
        buttonRedact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Film filmToRedact = new Film();
                if (tableFilm.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Выберите какой фильм вы хотите отредактировать");
                } else {
                    filmToRedact.setFilm_id((int) tableFilm.getValueAt(tableFilm.getSelectedRow(), 0));
                    filmToRedact.setTitle((String) tableFilm.getValueAt(tableFilm.getSelectedRow(), 1));
                    filmToRedact.setGenre((String) tableFilm.getValueAt(tableFilm.getSelectedRow(), 2));
                    filmToRedact.setDirector((String) tableFilm.getValueAt(tableFilm.getSelectedRow(), 3));
                    filmToRedact.setMainActor((String) tableFilm.getValueAt(tableFilm.getSelectedRow(), 4));
                    filmToRedact.setDuration((Time) tableFilm.getValueAt(tableFilm.getSelectedRow(), 5));
                    filmToRedact.setAge((String) tableFilm.getValueAt(tableFilm.getSelectedRow(), 6));

                    try {
                        coos.writeObject("REDACT_FILM_ADMIN");
                        coos.writeObject(filmToRedact);
                        FilmEditAdminFrame filmEditAdminFrame = new FilmEditAdminFrame(cois, coos);
                        dispose();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Film filmToDelete = new Film();
                if (tableFilm.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Выберите какой фильм вы хотите удалить");
                } else {
                    filmToDelete.setFilm_id((int) tableFilm.getValueAt(tableFilm.getSelectedRow(), 0));
                    filmToDelete.setTitle((String) tableFilm.getValueAt(tableFilm.getSelectedRow(), 1));
                    filmToDelete.setGenre((String) tableFilm.getValueAt(tableFilm.getSelectedRow(), 2));
                    filmToDelete.setDirector((String) tableFilm.getValueAt(tableFilm.getSelectedRow(), 3));
                    filmToDelete.setMainActor((String) tableFilm.getValueAt(tableFilm.getSelectedRow(), 4));
                    filmToDelete.setDuration((Time) tableFilm.getValueAt(tableFilm.getSelectedRow(), 5));
                    filmToDelete.setAge((String) tableFilm.getValueAt(tableFilm.getSelectedRow(), 6));

                    int answer = JOptionPane.showConfirmDialog(null, "Вы уверены, что хотите удалить выбранный зал?");
                    if (answer == JOptionPane.YES_OPTION) {
                        try {
                            coos.writeObject("DELETE_FILM_ADMIN");
                            coos.writeObject(filmToDelete);
                            showData(cois, coos);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void showData(ObjectInputStream cois, ObjectOutputStream coos) {
        Object[] columnTitle = {
                "Id", "Название фильма", "Жанр", "Режиссёр", "Главный актёр", "Продолжительность", "Возрастное ограничение"
        };

        tableModel = new DefaultTableModel(null, columnTitle);
        tableFilm.setModel(tableModel);

        tableModel.getDataVector().removeAllElements();

        try {
            coos.writeObject("VIEW_FILM_ADMIN");
            List<Film> films = new ArrayList<>();
            films = (List<Film>) cois.readObject();

            for (Film film : films) {
                Object[] data = {
                        film.getFilmId(),
                        film.getTitle(),
                        film.getGenre(),
                        film.getDirector(),
                        film.getMainActor(),
                        film.getDuration(),
                        film.getAge()
                };
                tableModel.addRow(data);
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
