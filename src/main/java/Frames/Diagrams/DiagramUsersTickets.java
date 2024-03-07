package Frames.Diagrams;

import Models.Ticket;
import Models.User;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DiagramUsersTickets extends JFrame {
    private List<Ticket> tickets;
    private int[] counter;

    public DiagramUsersTickets(String title, ObjectInputStream cois, ObjectOutputStream coos){
        super(title);

        try{
            coos.writeObject("VIEW_TICKETS_USER");
            List<Ticket> tickets = new ArrayList<>();
            tickets = (List<Ticket>) cois.readObject();
            counter = new int[tickets.size()];

            this.tickets = tickets;

            coos.writeObject("VIEW_USERS_ADMIN");
            List<User> users = new ArrayList<>();
            users = (List<User>) cois.readObject();

            for (int i = 0; i < tickets.size(); i++) {
                User user = tickets.get(i).getUser();
                for (int j = 0; j < users.size(); j++) {
                    if (user.getUser_id() == tickets.get(j).getId()) {
                        counter[j]++;
                        break;
                    }
                }
            }

            setContentPane(createDemoPanel());
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        }catch(IOException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    public JPanel createDemoPanel() {
        CategoryDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(1200, 600));
        return chartPanel;
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; (i < tickets.size()); i++){
            dataset.addValue(counter[i], "Количество билетов",
                    tickets.get(i).getUser().getUsername());
        }
        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Пользователи и их билеты",
                "Пользователь",
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
