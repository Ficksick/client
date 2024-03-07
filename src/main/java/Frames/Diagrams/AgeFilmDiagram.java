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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class AgeFilmDiagram extends JFrame {
    private List<Film> films;
    private int[] counter;
    Set<String> ages = new HashSet<>();
    Map<String, Integer> elementCounter = new HashMap<>();

    public AgeFilmDiagram(String title, ObjectInputStream cois, ObjectOutputStream coos) {
        super(title);

        try {
            coos.writeObject("VIEW_FILM_ADMIN");
            this.films = (List<Film>) cois.readObject();
            counter = new int[films.size()];

            List<String> fimlAge = films.stream()
                    .map(Film::getAge)
                    .collect(Collectors.toList());

            for (String item : fimlAge) {
                if (ages.contains(item)) {
                    int count = elementCounter.get(item);
                    elementCounter.put(item, count + 1);
                } else {
                    ages.add(item);
                    elementCounter.put(item, 1);
                }
            }
        }catch(IOException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
        setContentPane(createDemoPanel());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private PieDataset createDataset() {
        List<String> list = new ArrayList<>(ages);
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (int i = 0; i < list.size(); i++){
            dataset.setValue(list.get(i), elementCounter.get(list.get(i)));
        }
        return dataset;
    }

    private JFreeChart createChart(PieDataset dataset)
    {
        JFreeChart chart = ChartFactory.createPieChart(
                "Возрастное ограничение фильмов",
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
