package Frames.Admin;

import Models.Hall;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HallEditAdminFrame extends JFrame {
    private JPanel root;
    private JPanel dataPanel;
    private JPanel buttonPanel;
    private JLabel title;
    private JTextField textFieldCapacity;
    private JTextField textFieldHallName;
    private JButton buttonAccept;
    private JLabel hallNameTitle;
    private JLabel capacityTitle;
    private JComboBox comboBoxHallName;
    private JComboBox comboBoxCapacity;
    private JButton buttonBack;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;

    public HallEditAdminFrame(ObjectInputStream cois, ObjectOutputStream coos) {
        setSize(450, 300);
        setVisible(true);
        setContentPane(root);
        setLocationRelativeTo(null);

        buttonAccept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String newHallName = null;

                if (comboBoxHallName.getSelectedItem().equals("изменить")) {
                    newHallName = textFieldHallName.getText();
                }

                int newCapacityInt = 0;
                String newCapacityString = null;

                boolean isInteger = false;

                if (comboBoxCapacity.getSelectedItem().equals("изменить")) {
                    newCapacityString = textFieldCapacity.getText();
                    System.out.println(newCapacityString);

                    if(textFieldCapacity.getText() != null){
                        if (newCapacityString.matches("^[a-zA-Zа-яА-Я]*$")) {
                            JOptionPane.showMessageDialog(null, "Неверный ввод! Данные должны быть числовыми");
                            textFieldCapacity.setText("");
                        } else {
                            newCapacityInt = Integer.parseInt(newCapacityString.trim());
                        }
                    }
                }

                try{
                    Hall hallToRedact = new Hall();
                    hallToRedact.setHallName(newHallName);
                    hallToRedact.setCapacity(newCapacityInt);

                    if(hallToRedact.getHallName() == null || hallToRedact.getCapacity() == 0){
                        JOptionPane.showMessageDialog(null, "Вы не ввели новые данные");
                    }else{
                        coos.writeObject(hallToRedact);
                        String answer = (String) cois.readObject();
                        if(answer.equals("OK")){
                            JOptionPane.showMessageDialog(null, "Зал успешно отредактирован");
                            dispose();
                            HallInformationForAdmin hallInformationForAdmin = new HallInformationForAdmin(cois, coos);
                        }
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
                HallInformationForAdmin hallInformationForAdmin = new HallInformationForAdmin(cois, coos);
            }
        });
    }
}
