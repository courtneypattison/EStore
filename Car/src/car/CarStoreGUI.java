/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car;

import static car.Car.DEFAULT_PRICE;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author courtney
 */
public class CarStoreGUI implements ActionListener {

    private JPanel cards;

    public static final int WIDTH = 750;
    public static final int HEIGHT = 500;

    public static final int LINES = 10;
    public static final int CHARS_PER_LINE = 20;

    public static final Insets BORDER_SIZE = new Insets(10, 10, 10, 10);
    public static final Dimension COMPONENT_SIZE = new Dimension(600, 40);

    public static final String ADD_VEHICLE = "Enter info about new car";
    public static final String ADD_SUV = "Enter info about new suv";
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
        addMenuItem(menu, ADD_SUV);
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
        JPanel panel = new JPanel();
        panel.setBackground(Color.ORANGE);
        panel.add(new JLabel("   " + text));
        JTextField textField = new JTextField(CHARS_PER_LINE);
        panel.add(textField);
        styleBoxLayoutComponent(textField);
        pane.add(panel);
        return textField;
    }

    private JPanel createAddSUVCard() {
        JPanel addVehicleCard = new JPanel();
        styleCard(addVehicleCard);

        JPanel addVehicleInputFields = new JPanel();
        addVehicleInputFields.setLayout(new BoxLayout(addVehicleInputFields, BoxLayout.Y_AXIS));
        addVehicleInputFields.setBackground(Color.ORANGE);

        JTextField brandAndModelTextField = addTextField(addVehicleInputFields, "Brand and model: ");
        JTextField yearTextField = addTextField(addVehicleInputFields, "Year: ");
        JTextField priceTextField = addTextField(addVehicleInputFields, "Price: ");
        JTextField numSeatsTextField = addTextField(addVehicleInputFields, "Number of seats: ");
        JTextField isAllTerrainTextField = addTextField(addVehicleInputFields, "Is all terrain: ");
        JTextField tireBrandTextField = addTextField(addVehicleInputFields, "Tire brand: ");

        JPanel addVehicleButtons = new JPanel();
        addVehicleButtons.setBackground(Color.ORANGE);
        JButton submit = new JButton("Add");
        submit.addActionListener(ae -> {
            String brandAndModel = brandAndModelTextField.getText();
            String tokensCheck[] = brandAndModel.split(" +");
            if (tokensCheck.length != 2 || tokensCheck[0].matches("")) {
                textArea.append("Invalid input: You must enter a brand and model.\n");
            }

            String yearString = yearTextField.getText();
            int year = 0;
            if (yearString.matches("^\\d+$")) {
                year = Integer.parseInt(yearString);
            } else {
                textArea.append("Invalid input: You must enter a number.\n");
            }

            String priceString = priceTextField.getText();
            int price = DEFAULT_PRICE;
            if (priceString.matches("^\\d+$")) {
                price = Integer.parseInt(priceString);
            } else if (priceString.matches("")) {
                price = DEFAULT_PRICE;
            } else {
                textArea.append("Invalid input: You must enter a number.\n");
            }

            String numSeatsString = numSeatsTextField.getText();
            int numSeats = 0;
            if (numSeatsString.matches("^\\d+$")) {
                numSeats = Integer.parseInt(numSeatsString);
            } else {
                textArea.append("Invalid input: You must enter a number.\n");
            }

            String isAllTerrainString = isAllTerrainTextField.getText();
            Boolean isAllTerrain = true;
            if (isAllTerrainString.matches("[01]")) {
                isAllTerrain = Boolean.parseBoolean(isAllTerrainString);
            } else {
                textArea.append("Invalid input: You must enter 0 or 1.\n");
            }

            String tireBrand = tireBrandTextField.getText();
            String[] tireBrandTokens = tireBrand.split("\\s+");
            if (tireBrandTokens.length == 1) {
                tireBrand = tireBrand;
            } else {
                textArea.append("Invalid input: You must enter a single word.\n");
            }

            try {
                String key = createKey(new Car(brandAndModel, year));
                if (carsMap.containsKey(key)) {
                    textArea.append("Invalid input: a car with this brand, model, and year already exists.\n");
                }
                SUV suv = new SUV(brandAndModel, year, price, numSeats, isAllTerrain, tireBrand);
                cars.add(suv);
                carsMap.put(createKey(suv), suv);
            } catch (Exception e) {
                textArea.append(e.getMessage() + "\n");
            }
        });
        addVehicleButtons.add(submit);

        addVehicleCard.add(addVehicleInputFields, BorderLayout.LINE_START);
        addVehicleCard.add(addVehicleButtons, BorderLayout.LINE_END);

        return addVehicleCard;
    }

    private JPanel createAddVehicleCard() {
        JPanel addVehicleCard = new JPanel();
        styleCard(addVehicleCard);

        JPanel addVehicleInputFields = new JPanel();
        addVehicleInputFields.setLayout(new BoxLayout(addVehicleInputFields, BoxLayout.Y_AXIS));
        addVehicleInputFields.setBackground(Color.ORANGE);

        JTextField brandAndModelTextField = addTextField(addVehicleInputFields, "Brand and model: ");
        JTextField yearTextField = addTextField(addVehicleInputFields, "Year: ");
        JTextField priceTextField = addTextField(addVehicleInputFields, "Price: ");

        JPanel addVehicleButtons = new JPanel();
        addVehicleButtons.setBackground(Color.ORANGE);
        JButton submit = new JButton("Add");
        submit.addActionListener(ae -> {
            String brandAndModel = brandAndModelTextField.getText();
            String tokensCheck[] = brandAndModel.split(" +");
            if (tokensCheck.length != 2 || tokensCheck[0].matches("")) {
                textArea.append("Invalid input: You must enter a brand and model.\n");
            }

            String yearString = yearTextField.getText();
            int year = 0;
            if (yearString.matches("^\\d+$")) {
                year = Integer.parseInt(yearString);
            } else {
                textArea.append("Invalid input: You must enter a number.\n");
            }

            String priceString = priceTextField.getText();
            int price = DEFAULT_PRICE;
            if (priceString.matches("^\\d+$")) {
                price = Integer.parseInt(priceString);
            } else if (priceString.matches("")) {
                price = DEFAULT_PRICE;
            } else {
                textArea.append("Invalid input: You must enter a number.\n");
            }

            try {
                String key = createKey(new Car(brandAndModel, year));
                if (carsMap.containsKey(key)) {
                    textArea.append("Invalid input: a car with this brand, model, and year already exists.\n");
                }
                Car car = new Car(brandAndModel, year, price);
                cars.add(car);
                carsMap.put(createKey(car), car);
            } catch (Exception e) {
                textArea.append(e.getMessage() + "\n");
            }
        });
        addVehicleButtons.add(submit);

        addVehicleCard.add(addVehicleInputFields, BorderLayout.LINE_START);
        addVehicleCard.add(addVehicleButtons, BorderLayout.LINE_END);

        return addVehicleCard;
    }

    private JPanel createPrintVehicleInfoCard() {
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

    private JPanel createReadInputFileCard() {
        JPanel readInputFileCard = new JPanel();
        styleCard(readInputFileCard);
        readInputFileCard.setLayout(new BoxLayout(readInputFileCard, BoxLayout.Y_AXIS));

        JPanel button = new JPanel();
        button.setBackground(Color.ORANGE);
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

    private JPanel createDataDumpCard() {
        JPanel dataDumpCard = new JPanel();
        styleCard(dataDumpCard);
        dataDumpCard.setLayout(new BoxLayout(dataDumpCard, BoxLayout.Y_AXIS));

        JButton stdDataDump = new JButton(STD_DATA_DUMP);
        stdDataDump.addActionListener(ae -> {
            for (Car car : cars) {
                textArea.append(car.dataDump() + "\n");
            }
        });
        dataDumpCard.add(stdDataDump);

        JButton fileDataDump = new JButton(FILE_DATA_DUMP);
        fileDataDump.addActionListener(ae -> {
            try {
                File outputFile = new File("./output.txt");
                PrintWriter fileWriter = new PrintWriter(outputFile);
                for (Car car : cars) {
                    fileWriter.println(car.dataDump());
                }
                fileWriter.close();
            } catch (Exception e) {
                textArea.append("Could not write to output.txt\n");
            }
        });
        dataDumpCard.add(fileDataDump);

        return dataDumpCard;
    }

    private JPanel createLookupCard() {
        JPanel lookupCard = new JPanel();
        styleCard(lookupCard);
        lookupCard.setLayout(new BoxLayout(lookupCard, BoxLayout.Y_AXIS));

        JPanel lookup = new JPanel();
        lookup.setBackground(Color.ORANGE);
        lookup.add(new JLabel("Enter the brand, model, and year of a car:"));
        JTextField lookupTextField = new JTextField(CHARS_PER_LINE);
        lookup.add(lookupTextField);
        JButton lookupButton = new JButton("Submit");
        lookupButton.addActionListener(e -> lookupActionPerformed(e, lookupTextField));
        lookup.add(lookupButton);

        lookupCard.add(lookup);

        return lookupCard;
    }

    private void addCards(Container pane) {
        cards = new JPanel(new CardLayout());
        cards.add(createWelcomeCard());
        cards.add(createAddVehicleCard(), ADD_VEHICLE);
        cards.add(createAddSUVCard(), ADD_SUV);
        cards.add(createPrintVehicleInfoCard(), PRINT_VEHICLE_INFO);
        cards.add(createReadInputFileCard(), READ_INPUT_FILE);
        cards.add(createDataDumpCard(), DATA_DUMP);
        cards.add(createLookupCard(), LOOKUP);

        pane.add(cards, BorderLayout.CENTER);
    }

    private void addScrollPane(Container pane) {
        textArea = new JTextArea(LINES, CHARS_PER_LINE);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);

        pane.add(scrollPane, BorderLayout.PAGE_END);
    }
    
    private void lookupActionPerformed(ActionEvent e, JTextField lookupTextField) {
        String userString = lookupTextField.getText();
            String[] tokens = userString.split("\\s+");
            if (tokens.length != 3) {
                textArea.append("Invalid input: Lookup must be in the following format: Volvo, V70, 2002\n");
                return;
            }
            String key = userString.replaceAll(",\\s+", "");
            if (carsMap.containsKey(key)) {
                Car car = carsMap.get(key);
                textArea.append("Cars found:\n"
                        + car.toString() + "\n");
            } else {
                textArea.append("No Matching cars found.\n");
            }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (((String) e.getActionCommand()).equals(END_PROGRAM)) {
            System.exit(0);
        }

        CardLayout cardLayout = (CardLayout) (cards.getLayout());
        cardLayout.show(cards, (String) e.getActionCommand());
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
