/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;

public class BorderLayoutJFrame extends JFrame
{
    public static final int WIDTH = 500;
    public static final int HEIGHT = 400;

    public BorderLayoutJFrame( )
    {
        super("BorderLayout Demonstration" );
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout( ));

        JLabel label1 = new JLabel("First label");
        add(label1, BorderLayout.NORTH); 

        JLabel label2 = new JLabel("Second label");
        add(label2, BorderLayout.SOUTH); 

        JLabel label3 = new JLabel("Third label");
        add(label3, BorderLayout.CENTER); 
        
        JLabel label4 = new JLabel("Fourth label");
        add(label4, BorderLayout.WEST); 
        
        JLabel label5 = new JLabel("Fifth label");
        add(label5, BorderLayout.EAST);
       
    }
    
    public static void main(String[] args) 
    {
        BorderLayoutJFrame gui = new BorderLayoutJFrame( );
        gui.setVisible(true);
    }

}
