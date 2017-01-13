/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.FlowLayout;

public class FirstSwingDemo
{
    public static final int WIDTH = 200; 
    public static final int HEIGHT = 100;

    public static void main(String[] args)
    {
        //JFrame firstWindow = new JFrame();
        //firstWindow.setTitle("First Window");
        JFrame firstWindow = new JFrame("First Swing Demo");
        firstWindow.setSize(WIDTH, HEIGHT);

        firstWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        firstWindow.setLayout(new FlowLayout());

        JButton endButton = new JButton("Click to end program.");
        EndingListener buttonEar = new EndingListener( );
        endButton.addActionListener(buttonEar);
        firstWindow.add(endButton);

        firstWindow.setVisible(true);
    }
}

