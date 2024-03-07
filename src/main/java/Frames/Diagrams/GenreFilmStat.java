package Frames.Diagrams;

import Models.Film;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.HorizontalAlignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GenreFilmStat extends JFrame {

    private List<Film> films;
    private int[] counter;
    Set<String> genres = new HashSet<>();
    Map<String, Integer> elementCount = new HashMap<>();
    public GenreFilmStat(String title, ObjectInputStream cois, ObjectOutputStream coos){
        super(title);
        try{
            coos.writeObject("VIEW_FILM_ADMIN");
            this.films = (List<Film>) cois.readObject();

            List<String> genre = films.stream()
                    .map(Film::getGenre)
                    .collect(Collectors.toList());

            for(String item : genre){
                if(genres.contains(item)){
                    int count = elementCount.get(item);
                    elementCount.put(item, count + 1);
                } else {
                    genres.add(item);
                    elementCount.put(item, 1);
                }
            }

            setContentPane(createDemoPanel());
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }catch(IOException | ClassNotFoundException ex){
            ex.printStackTrace();
        }

    }
    private PieDataset createDataset() {
        List<String> list = new ArrayList<>(genres);
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (int i = 0; i < list.size(); i++){
            dataset.setValue(list.get(i), elementCount.get(list.get(i)));
        }
        return dataset;
    }

    private JFreeChart createChart(PieDataset dataset)
    {
        JFreeChart chart = ChartFactory.createPieChart(
                "Жанры фильмов",
                dataset,
                false,
                true,
                false
        );
        chart.setBackgroundPaint(Color.DARK_GRAY);
        TextTitle t = chart.getTitle();
        t.setHorizontalAlignment(HorizontalAlignment.LEFT);
        t.setPaint(new Color(240, 240, 240));
        t.setFont(new Font("Arial", Font.BOLD, 26));

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(null);
        plot.setInteriorGap(0.04);
        plot.setOutlineVisible(false);

        plot.setBaseSectionOutlinePaint(Color.WHITE);
        plot.setSectionOutlinesVisible(true);
        plot.setBaseSectionOutlineStroke(new BasicStroke(2.0f));

        plot.setLabelFont(new Font("Courier New", Font.BOLD, 20));
        plot.setLabelLinkPaint(Color.WHITE);
        plot.setLabelLinkStroke(new BasicStroke(2.0f));
        plot.setLabelOutlineStroke(null);
        plot.setLabelPaint(Color.WHITE);
        plot.setLabelBackgroundPaint(null);

        return chart;
    }


    public JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(640, 300));
        return panel;
    }

}