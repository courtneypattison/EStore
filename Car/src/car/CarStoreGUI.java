/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author courtney
 */
public class CarStoreGUI implements ActionListener {

    JPanel cards;

    public static final int WIDTH = 750;
    public static final int HEIGHT = 500;
    
    public static final Insets BORDER_SIZE = new Insets(10, 10, 10, 10);
    
    public static final String ADD_VEHICLE = "Enter info about new vehicle";
    public static final String PRINT_BRAND_AND_MODEL = "Print out brand and model, delimited by spaces, for all vehicles";
    public static final String PRINT_AVG_AND_TOTAL = "Print average vehicle cost, as well as total number of vehicles";
    public static final String READ_INPUT_FILE = "Read input file";
    public static final String STD_DATA_DUMP = "Standard data dump";
    public static final String FILE_DATA_DUMP = "File data dump";
    public static final String LOOKUP = "Lookup via identifier";
    public static final String END_PROGRAM = "End program";

    
    private void addMenu(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu commandsMenu = new JMenu("Commands");
        menuBar.add(commandsMenu);  

        JMenuItem newVehicle = new JMenuItem(ADD_VEHICLE);
        newVehicle.addActionListener(this);
        commandsMenu.add(newVehicle);

        JMenuItem printBrandAndModel = new JMenuItem(PRINT_BRAND_AND_MODEL);
        printBrandAndModel.addActionListener(this);
        commandsMenu.add(printBrandAndModel);

        JMenuItem printAvgAndTotal = new JMenuItem(PRINT_AVG_AND_TOTAL);
        printAvgAndTotal.addActionListener(this);
        commandsMenu.add(printAvgAndTotal);

        JMenuItem readInputFile = new JMenuItem(READ_INPUT_FILE);
        readInputFile.addActionListener(this);
        commandsMenu.add(readInputFile);

        JMenuItem stdDataDump = new JMenuItem(STD_DATA_DUMP);
        stdDataDump.addActionListener(this);
        commandsMenu.add(stdDataDump);

        JMenuItem fileDataDump = new JMenuItem(FILE_DATA_DUMP);
        fileDataDump.addActionListener(this);
        commandsMenu.add(fileDataDump);

        JMenuItem lookup = new JMenuItem(LOOKUP);
        lookup.addActionListener(this);
        commandsMenu.add(lookup);

        JMenuItem quit = new JMenuItem(END_PROGRAM);
        quit.addActionListener(this);
        commandsMenu.add(quit);
        
        frame.setJMenuBar(menuBar);
    }
    
    private JPanel createWelcomeCard() {
        JPanel welcomeCard = new JPanel();
        
        JLabel welcomeMessage = new JLabel("Welcome to the Car Store!\n"
                + "Choose a command from the \"Commands\" menu above.");
        welcomeCard.add(welcomeMessage);
        
        return welcomeCard;
    }
    
    private JPanel createAddVehicleCard() {
        JPanel addVehicleCard = new JPanel();
        addVehicleCard.setLayout(new BoxLayout(addVehicleCard, BoxLayout.Y_AXIS));
        addVehicleCard.setBorder(new EmptyBorder(BORDER_SIZE));
        
        JLabel addVehicleMessage = new JLabel("Adding a vehicle");
        addVehicleCard.add(addVehicleMessage);
        
        JLabel message = new JLabel("message2");
        addVehicleCard.add(message);
        
        return addVehicleCard;
    }
    
    private JPanel createBrandAndModelCard() {
        JPanel brandAndModelCard = new JPanel();
        
        
        
        return brandAndModelCard;
    }
    
    private void addCards(Container pane) {
        cards = new JPanel(new CardLayout());
        cards.add(createWelcomeCard());
        cards.add(createAddVehicleCard(), ADD_VEHICLE);
        cards.add(createBrandAndModelCard(), PRINT_BRAND_AND_MODEL);
        
        pane.add(cards, BorderLayout.CENTER);     
    }
       
    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cardLayout = (CardLayout) (cards.getLayout());
        cardLayout.show(cards, (String) e.getActionCommand());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Car Store");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.orange);

        CarStoreGUI carStoreGUI = new CarStoreGUI();
        carStoreGUI.addMenu(frame);
        carStoreGUI.addCards(frame);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
