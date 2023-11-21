package Frames;

import javax.swing.*;

public class EditUserFrame extends JFrame{
    private JPanel root;
    private JLabel title;
    private JTextField textFieldUsername;
    private JTextField textFieldEmail;
    private JTextField textFieldPassword;
    private JComboBox comboBoxRole;
    private JComboBox comboBoxPassword;
    private JComboBox comboBoxEmail;
    private JComboBox comboBoxUsername;
    private JPanel dataPanel;
    private JPanel titlePanel;
    private JLabel usernameLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel roleLabel;
    private JPanel changePanel;

    public EditUserFrame(){
        setVisible(true);
        setSize(450,300);
        setContentPane(root);
        setLocationRelativeTo(null);
    }
}
