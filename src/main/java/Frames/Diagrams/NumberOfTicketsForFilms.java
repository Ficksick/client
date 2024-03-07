package Frames.Diagrams;

import Models.Film;
import Models.Ticket;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class NumberOfTicketsForFilms extends JFrame {

    private List<Film> films;
    private int[] counter;

    public NumberOfTicketsForFilms(String title, ObjectInputStream cois, ObjectOutputStream coos) {
        super(title);

        try{
            coos.writeObject("VIEW_TICKETS_USER");
            List<Ticket> tickets = new ArrayList<>();
            tickets = (List<Ticket>) cois.readObject();

            counter = new int[tickets.size()];

            coos.writeObject("VIEW_FILM_ADMIN");
            List<Film> films = new ArrayList<>();
            films = (List<Film>) cois.readObject();

            this.films = films;

            for(int i = 0 ; i < tickets.size(); i++){
                Film film = tickets.get(i).getScreening().getFilm();
                for(int j = 0 ; j < films.size(); j++){
                    if(film.getFilmId() == tickets.get(j).getScreening().getFilm().getFilmId()){
                        counter[j]++;
                        break;
                    }
                }
            }

            setContentPane(createDemoPanel());
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        } catch (IOException |ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public JPanel createDemoPanel(){
        CategoryDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(1200, 600));
        return chartPanel;
    }

    private CategoryDataset createDataset(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(int i = 0; (i < films.size()); i++){
            dataset.addValue(counter[i], "Количество билетов",
                    films.get(i).getTitle());
        }

        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Статистика покупки билетов на фильм",
                "Название фильма",
                "Количество билетов",
                dataset);
        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        chart.getLegend().setFrame(BlockBorder.NONE);
        return chart;
    }
}
