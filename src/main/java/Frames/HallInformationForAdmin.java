package Frames;

import Models.Hall;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class HallInformationForAdmin extends JFrame{
    private JPanel root;
    private JLabel title;
    private JTable tableHall;
    private JPanel panelForTable;
    private JScrollPane scrollPanel;
    private JPanel panelForButtons;
    private JPanel editButtonPanel;
    private JPanel deleteButtonPanel;
    private JPanel backButtonPanel;
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private JPanel createButtonPanel;
    private JButton createButton;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;
    private DefaultTableModel tableModel;

    public HallInformationForAdmin(ObjectInputStream cois, ObjectOutputStream coos){
        setSize(450, 300);
        setVisible(true);
        setContentPane(root);
        setLocationRelativeTo(null);
        showHallData(cois, coos);
    }

    private void showHallData(ObjectInputStream cois, ObjectOutputStream coos){
        Object[] columnTitle ={
                "Id", "Название зала", "Вместимость"
        };
        tableModel = new DefaultTableModel(null, columnTitle);
        tableHall.setModel(tableModel);

        tableModel.getDataVector().removeAllElements();

        try{
            coos.writeObject("VIEW_HALL_ADMIN");
            List<Hall> halls = new ArrayList<>();
            halls = (List<Hall>) cois.readObject();

            for(Hall hall : halls){
                Object[] data = {
                        hall.getHall_id(),
                        hall.getHallName(),
                        hall.getCapacity()
                };
                tableModel.addRow(data);
            }
        }catch(IOException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
}
