package Frames.Admin;

import Frames.Diagrams.*;
import Models.Film;
import Models.Ticket;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StatisticFrame extends JFrame {
    private JPanel root;
    private JPanel buttonsPanel;
    private JButton diagramAllTicketsBoughtByUserButton;
    private JButton diagramAllTicketsBoughtFilmButton;
    private JButton sumAllTicketsBoughtButton;
    private JButton diagramScreeningsInHallsButton;
    private JButton diagramAgeAllFilmButton;
    private JButton diagramGenreAllFilmsButton;
    private JButton allFilmsInFileButton;
    private JButton backButton;

    public StatisticFrame(ObjectInputStream cois, ObjectOutputStream coos) {
        setContentPane(root);
        setSize(650, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        diagramAllTicketsBoughtFilmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NumberOfTicketsForFilms numberOfTicketsForFilms = new NumberOfTicketsForFilms("Диаграмма покупки билетов на фильмы", cois, coos);
                numberOfTicketsForFilms.pack();
                RefineryUtilities.centerFrameOnScreen(numberOfTicketsForFilms);
                numberOfTicketsForFilms.setVisible(true);
            }
        });
        diagramScreeningsInHallsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreeningsInHalls screeningsInHalls = new ScreeningsInHalls("Статистика сеансов в залах", cois, coos);
                screeningsInHalls.pack();
                RefineryUtilities.centerFrameOnScreen(screeningsInHalls);
                screeningsInHalls.setVisible(true);
            }
        });
        diagramGenreAllFilmsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenreFilmStat genreFilmStat = new GenreFilmStat("Статистика фильмов по жанрам", cois, coos);
                genreFilmStat.pack();
                RefineryUtilities.centerFrameOnScreen(genreFilmStat);
                genreFilmStat.setVisible(true);
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuForAdmin menuForAdmin = new MenuForAdmin(cois, coos);
                dispose();
            }
        });
        allFilmsInFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    coos.writeObject("VIEW_ALL_FILM");
                    List<Film> films = new ArrayList<>();

                    films = (List<Film>) cois.readObject();

                    FileWriter writer = new FileWriter("allFilms.txt");
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);

                    if (!films.isEmpty()) {
                        for (Film film : films) {
                            bufferedWriter.write(film.toString());
                            bufferedWriter.write("\n__________________________");
                            bufferedWriter.newLine();
                        }
                        bufferedWriter.close();
                        JOptionPane.showMessageDialog(null, "Ваши билеты сохранены в файл");
                    } else {
                        JOptionPane.showMessageDialog(null, "Пока нет фильмов");
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        diagramAllTicketsBoughtByUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DiagramUsersTickets diagramUsersTickets = new DiagramUsersTickets("Статистика покупки билетов пользователями", cois, coos);
                diagramUsersTickets.pack();
                RefineryUtilities.centerFrameOnScreen(diagramUsersTickets);
                diagramUsersTickets.setVisible(true);
            }
        });
        sumAllTicketsBoughtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    coos.writeObject("VIEW_TICKETS_USER");
                    List<Ticket> tickets = (List<Ticket>) cois.readObject();

                    double sum = 0;

                    for(Ticket ticket : tickets){
                        sum += ticket.getPrice();
                    }

                    JOptionPane.showMessageDialog(null, "Сумма всех проданных билетов: " + sum + " денег");

                }catch(IOException | ClassNotFoundException ex){
                    ex.printStackTrace();
                }
            }
        });
        diagramAgeAllFilmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgeFilmDiagram ageFilmDiagram = new AgeFilmDiagram("Статистика возрастного ограничения", cois, coos);
                ageFilmDiagram.pack();
                RefineryUtilities.centerFrameOnScreen(ageFilmDiagram);
                ageFilmDiagram.setVisible(true);
            }
        });
    }
}
