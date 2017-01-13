/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MultiPanelDemo extends JFrame
{
    public static final int WIDTH = 300;
    public static final int HEIGHT = 200;

    private JPanel redPanel;
    private JPanel whitePanel;
    private JPanel bluePanel;

    private class RedListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            whitePanel.setVisible(false);
            redPanel.setVisible(true);
            bluePanel.setVisible(false);
        }
    } //End of RedListener inner class

    private class WhiteListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            whitePanel.setVisible(true);
           redPanel.setVisible(false);
            bluePanel.setVisible(false);
        }
    } //End of WhiteListener inner class

    private class BlueListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            whitePanel.setVisible(false);
            redPanel.setVisible(false);
            bluePanel.setVisible(true);
        }
    } //End of BlueListener inner class

    public static void main(String[] args)
    {
        MultiPanelDemo gui = new MultiPanelDemo( );
        gui.setVisible(true);
    }

    public MultiPanelDemo( )
    {
        super("Multi-Panel Demonstration");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLayout(new BorderLayout());
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        redPanel = new JPanel( );
        redPanel.setBackground(Color.RED);
        redPanel.setVisible(false);
        //add(redPanel, BorderLayout.CENTER);
        add(redPanel);

        whitePanel = new JPanel( );
        whitePanel.setBackground(Color.WHITE);
        whitePanel.setVisible(false);
        //add(whitePanel, BorderLayout.CENTER);
        add(whitePanel);

        bluePanel = new JPanel( );
        bluePanel.setBackground(Color.BLUE);
        bluePanel.setVisible(false);
        //add(bluePanel, BorderLayout.CENTER);
        add(bluePanel);

        JMenu colorMenu = new JMenu("Add Colors");

        JMenuItem redChoice = new JMenuItem("Red");
        redChoice.addActionListener(new RedListener( ));
        colorMenu.add(redChoice);

        JMenuItem whiteChoice = new JMenuItem("White");
        whiteChoice.addActionListener(new WhiteListener( ));
        colorMenu.add(whiteChoice);

        JMenuItem blueChoice = new JMenuItem("Blue");
        blueChoice.addActionListener(new BlueListener( ));
        colorMenu.add(blueChoice);

        JMenuBar bar = new JMenuBar( );
        bar.add(colorMenu);
        setJMenuBar(bar);
    }

}



