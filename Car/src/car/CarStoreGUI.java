/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author courtney
 */
public class CarStoreGUI implements ActionListener, ItemListener {

    private JPanel cards;

    public static final int WIDTH = 750;
    public static final int HEIGHT = 500;

    public static final int LINES = 10;
    public static final int CHARS_PER_LINE = 40;

    public static final Insets BORDER_SIZE = new Insets(10, 10, 10, 10);
    public static final Dimension COMPONENT_SIZE = new Dimension(600, 40);

    public static final String ADD_VEHICLE = "Enter info about new vehicle";
    public static final String PRINT_VEHICLE_INFO = "Print vehicle information";
    public static final String READ_INPUT_FILE = "Read input file";
    public static final String DATA_DUMP = "Data dump";
    public static final String LOOKUP = "Lookup via identifier";
    public static final String END_PROGRAM = "End program";

    public static final String PRINT_BRAND_AND_MODEL = "Print out brand and model, delimited by spaces, for all vehicles";
    public static final String PRINT_AVG_AND_TOTAL = "Print average vehicle cost, as well as total number of vehicles";
    public static final String STD_DATA_DUMP = "Standard data dump";
    public static final String FILE_DATA_DUMP = "File data dump";

    private ArrayList<Car> cars = new ArrayList<>();
    private HashMap<String, Car> carsMap = new HashMap<>();
    private JTextArea textArea;

    public CarStoreGUI(ArrayList<Car> cars, HashMap<String, Car> carsMap) {
        this.cars = cars;
        this.carsMap = carsMap;
    }

    private void addMenuItem(JMenu menu, String text) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(this);
        menu.add(menuItem);
    }

    private void addMenu(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Commands");

        addMenuItem(menu, ADD_VEHICLE);
        addMenuItem(menu, PRINT_VEHICLE_INFO);
        addMenuItem(menu, READ_INPUT_FILE);
        addMenuItem(menu, DATA_DUMP);
        addMenuItem(menu, LOOKUP);
        addMenuItem(menu, END_PROGRAM);

        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
    }

    private void styleCard(JPanel card) {
        card.setBackground(Color.ORANGE);
        card.setBorder(new EmptyBorder(BORDER_SIZE));
        card.setLayout(new BorderLayout());
    }

    private JPanel createWelcomeCard() {
        JPanel welcomeCard = new JPanel();
        styleCard(welcomeCard);

        JLabel welcomeMessage = new JLabel("Welcome to the Car Store! Chooose"
                + " a command from the \"Commands\" menu above.");
        welcomeCard.add(welcomeMessage);

        return welcomeCard;
    }

    private void styleBoxLayoutComponent(JComponent component) {
        component.setMaximumSize(COMPONENT_SIZE);
        component.setAlignmentX(Component.LEFT_ALIGNMENT);
        component.setAlignmentY(Component.TOP_ALIGNMENT);
    }

    private JTextField addTextField(Container pane, String text) {
        JTextField textField = new JTextField("   " + text, CHARS_PER_LINE);
        styleBoxLayoutComponent(textField);
        pane.add(textField);
        return textField;
    }

    private void addComboBox(JPanel pane) {
        String[] comboBoxItems = {"Car", "SUV"};
        JComboBox comboBox = new JComboBox(comboBoxItems);
        styleBoxLayoutComponent(comboBox);

        comboBox.setEditable(false);
        comboBox.addItemListener(this);

        pane.add(comboBox);
    }

    private JPanel createAddVehicleCard() {
        JPanel addVehicleCard = new JPanel();
        styleCard(addVehicleCard);

        JPanel addVehicleInputFields = new JPanel();
        addVehicleInputFields.setLayout(new BoxLayout(addVehicleInputFields, BoxLayout.Y_AXIS));
        addVehicleInputFields.setBackground(Color.ORANGE);
        addComboBox(addVehicleInputFields);

        JTextField brandAndModel = addTextField(addVehicleInputFields, "Brand and model: ");
        JTextField year = addTextField(addVehicleInputFields, "Year: ");
        JTextField price = addTextField(addVehicleInputFields, "Price: ");

        JPanel addVehicleButtons = new JPanel();
        addVehicleButtons.setBackground(Color.ORANGE);
        addVehicleButtons.add(new JLabel("yo foo"));

        addVehicleCard.add(addVehicleInputFields, BorderLayout.LINE_START);
        addVehicleCard.add(addVehicleButtons, BorderLayout.LINE_END);

        return addVehicleCard;
    }

    private JPanel createPrintVehicleInfo() {
        JPanel printVehicleInfo = new JPanel();
        styleCard(printVehicleInfo);
        printVehicleInfo.setLayout(new BoxLayout(printVehicleInfo, BoxLayout.Y_AXIS));

        JButton printBrandAndModel = new JButton(PRINT_BRAND_AND_MODEL);
        printBrandAndModel.addActionListener(e -> {
            for (Car car : cars) {
                String[] tokens = car.getBrandAndModel().split(" +");
                textArea.append(tokens[0] + " " + tokens[1] + "\n");
            }
        });
        printVehicleInfo.add(printBrandAndModel);

        JButton printAvgAndTotal = new JButton(PRINT_AVG_AND_TOTAL);
        printAvgAndTotal.addActionListener(e -> {
            double sum = 0;
            double average = 0;

            for (Car car : cars) {
                sum += car.getPrice();
            }

            if (cars.size() > 0) {
                average = sum / (double) cars.size();
            }

            textArea.append("Average vehicle cost: " + average + "\n"
                    + "Total number of vehicles: " + cars.size() + "\n");
        });
        printVehicleInfo.add(printAvgAndTotal);

        return printVehicleInfo;
    }
    
    private String createKey(Car car) {
        String key = car.getBrandAndModel().replaceAll("\\s", "");
        key += car.getYear();
        return key;
    }

    private JPanel createReadInputFile() {
        JPanel readInputFileCard = new JPanel();
        styleCard(readInputFileCard);
        readInputFileCard.setLayout(new BoxLayout(readInputFileCard, BoxLayout.Y_AXIS));

        JPanel button = new JPanel();
        button.add(new JLabel("File name: "));
        JTextField readInputFileText = new JTextField(CHARS_PER_LINE);
        button.add(readInputFileText);
        JButton readInputFileButton = new JButton("Submit");
        readInputFileButton.addActionListener(ae -> {
            String filename = readInputFileText.getText();
            Scanner fileInput = null;

            try {
                fileInput = new Scanner(new FileInputStream(filename));

            } catch (FileNotFoundException e) {
                textArea.append("Could not open file.\n");
                System.exit(0);
            }

            while (fileInput.hasNextLine()) {
                String[] carTokens = fileInput.nextLine().split("\\s");

                String brandAndModel = carTokens[0].concat(" " + carTokens[1]);
                try {
                    if (Integer.parseInt(carTokens[4]) == 0) {
                        Car car = new Car(brandAndModel, Integer.parseInt(carTokens[2]), Integer.parseInt(carTokens[3]));
                        cars.add(car);
                        carsMap.put(createKey(car), car);
                    } else {
                        SUV suv = new SUV(brandAndModel, Integer.parseInt(carTokens[2]), Integer.parseInt(carTokens[3]), Integer.parseInt(carTokens[5]), Boolean.parseBoolean(carTokens[6]), carTokens[7]);
                        cars.add(suv);
                        carsMap.put(createKey(suv), suv);
                    }
                } catch (Exception e) {
                    textArea.append(e.getMessage() + "\n");
                }
            }

            fileInput.close();
        });
        button.add(readInputFileButton);
        
        readInputFileCard.add(button);

        return readInputFileCard;
    }

    private void addCards(Container pane) {
        cards = new JPanel(new CardLayout());
        cards.add(createWelcomeCard());
        cards.add(createAddVehicleCard(), ADD_VEHICLE);
        cards.add(createPrintVehicleInfo(), PRINT_VEHICLE_INFO);
        cards.add(createReadInputFile(), READ_INPUT_FILE);

        pane.add(cards, BorderLayout.CENTER);
    }

    private void addScrollPane(Container pane) {
        textArea = new JTextArea(LINES, CHARS_PER_LINE);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);

        pane.add(scrollPane, BorderLayout.PAGE_END);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (((String) e.getActionCommand()).equals(END_PROGRAM)) {
            System.exit(0);
        }

        CardLayout cardLayout = (CardLayout) (cards.getLayout());
        cardLayout.show(cards, (String) e.getActionCommand());
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Car Store");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CarStoreGUI carStoreGUI = new CarStoreGUI(new ArrayList<Car>(), new HashMap<String, Car>());
        carStoreGUI.addMenu(frame);
        carStoreGUI.addCards(frame);
        carStoreGUI.addScrollPane(frame);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
