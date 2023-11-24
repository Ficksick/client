package Frames.Admin;

import Models.Hall;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HallCreateAdminFrame extends JFrame {
    private JPanel root;
    private JPanel AcceptButtonPanel;
    private JPanel DataPanel;
    private JTextField textFieldHallName;
    private JTextField textFieldCapacityHall;
    private JLabel title;
    private JLabel titleNameHall;
    private JLabel titleCapacity;
    private JButton buttonAccept;
    private JButton buttonBack;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;

    public HallCreateAdminFrame(ObjectInputStream cois, ObjectOutputStream coos) {
        setSize(350, 400);
        setVisible(true);
        setContentPane(root);
        setLocationRelativeTo(null);

        buttonAccept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String hallName = textFieldHallName.getText();
                String capacity = textFieldCapacityHall.getText();
                int capacityInt = 0;

                boolean isIntegerHallName = false;

                while (true) {
                    for (int i = 0; i < hallName.length() && !isIntegerHallName; i++) {
                        if (Character.isDigit(hallName.charAt(i))) {
                            isIntegerHallName = true;
                        }
                    }
                    if (!isIntegerHallName) {
                        break;
                    } else {
                        textFieldHallName.setText("");
                        JOptionPane.showMessageDialog(null, "В названии зала не может быть цифр");
                        break;
                    }
                }

                if (capacity.matches("^[a-zA-Zа-яА-Я]*$")) {
                    JOptionPane.showMessageDialog(null, "Неверный ввод! Данные должны быть числовыми");
                    textFieldCapacityHall.setText("");
                } else {
                    capacityInt = Integer.parseInt(capacity.trim());
                }

                try {
                    coos.writeObject("CREATE_HALL_ADMIN");

                    Hall hall = new Hall();
                    hall.setHallName(hallName);
                    hall.setCapacity(capacityInt);
                    coos.writeObject(hall);

                    String answer = (String) cois.readObject();
                    JOptionPane.showMessageDialog(null, "Зал успешно создан");

                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

                HallInformationForAdmin hallInformationForAdmin = new HallInformationForAdmin(cois, coos);
                dispose();
            }
        });
        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                HallInformationForAdmin hallInformationForAdmin = new HallInformationForAdmin(cois, coos);
            }
        });
    }
}
