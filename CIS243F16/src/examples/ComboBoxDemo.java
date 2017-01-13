/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ComboBoxDemo extends JFrame
                          implements ActionListener {

public static final int WIDTH = 300;
public static final int HEIGHT = 200;

    JLabel message;

    public static void main(String[] args) {
        ComboBoxDemo gui = new ComboBoxDemo();
        gui.setVisible(true);
    }

    public ComboBoxDemo() {
        super("Combo Box Demo");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
       
        String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };
        JComboBox petList = new JComboBox(petStrings);
        petList.setSelectedIndex(4);
        petList.addActionListener(this);

        message = new JLabel("I Love " + petStrings[4] + ".");
        message.setFont(message.getFont().deriveFont(Font.ITALIC));
        message.setHorizontalAlignment(JLabel.CENTER);
        
        add(petList, BorderLayout.NORTH);
        add(message, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String petName = (String)cb.getSelectedItem();
        message.setText("I Love " + petName + ".");
    }
}

