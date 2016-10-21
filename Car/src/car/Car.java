package car;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Courtney Bodi
 */
public class Car {

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the brandAndModel
     */
    public String getBrandAndModel() {
        return brandAndModel;
    }

    /**
     * @param brandAndModel the brandAndModel to set
     */
    public void setBrandAndModel(String brandAndModel) {
        this.brandAndModel = brandAndModel;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }

    enum MenuOption {
        NULL,
        ENTER_VEHICLE,
        PRINT_BRAND_AND_MODEL,
        PRINT_AVG_AND_TOTAL,
        PRINT_YEARS,
        READ_INPUT,
        STD_DATA_DUMP,
        FILE_DATA_DUMP,
        EXIT
    }

    public static final String NEW_LINE = System.lineSeparator();

    private Scanner keyboardScanner = new Scanner(System.in);
    private ArrayList<Car> cars = new ArrayList<>();

    private int year;
    private String brandAndModel;
    private int price;

    /**
     * Car constructor
     *
     * @param year
     * @param brandAndModel
     * @param price
     */
    public Car(int year, String brandAndModel, int price) {
        this.year = year;
        this.brandAndModel = brandAndModel;
        this.price = price;
    }

    /**
     * Car constructor with default price
     *
     * @param year
     * @param brandAndModel
     */
    public Car(int year, String brandAndModel) {
        this.year = year;
        this.brandAndModel = brandAndModel;
        price = 50000;
    }

    /**
     * Default Car constructor
     */
    public Car() {
        year = 0;
        brandAndModel = "";
        price = 0;
    }
    
    

    private void printMenu() {
        System.out.println("(1) Enter the info about a new vehicle (either a car or a SUV)." + NEW_LINE
                + "(2) Print out brand and model, delimited by spaces, for all vehicles." + NEW_LINE
                + "(3) Print average vehicle cost, as well as total number of vehicles." + NEW_LINE
                + "(4) Print out all vehicle years." + NEW_LINE
                + "(5) Read input file." + NEW_LINE
                + "(6) Standard Data dump." + NEW_LINE
                + "(7) File Data dump." + NEW_LINE
                + "(8) End program");
    }

    private void promptUserAddVehicle() {
        int year, price;
        String brandAndModel;
        Car newCar;

        System.out.println("Enter vehicle brand and model:");
        brandAndModel = keyboardScanner.nextLine();
        String tokensCheck[] = brandAndModel.split(" +");
        if (tokensCheck.length != 2 || tokensCheck[0].matches("")) {
            System.out.println("Invalid input: You must enter a brand and model.");
            return;
        }

        System.out.println("Enter year:");
        String yearString = keyboardScanner.nextLine();
        if (yearString.matches("^\\d+$")) {
            year = Integer.parseInt(yearString);
        } else {
            System.out.println("Invalid input: You must enter a number.");
            return;
        }

        System.out.println("Enter price, or leave blank:");
        String priceString = keyboardScanner.nextLine();
        System.out.println("price: " + priceString);
        if (priceString.matches("^\\d+$")) {
            price = Integer.parseInt(priceString);
            newCar = new Car(year, brandAndModel, price);
        } else if (priceString.matches("")) {
            newCar = new Car(year, brandAndModel);
        } else {
            System.out.println("Invalid input: You must enter a number.");
            return;
        }

        cars.add(newCar);
    }

    private void printBrandAndModel() {
        for (Car car : cars) {
            String tokens[] = car.getBrandAndModel().split(" +");
            System.out.println("Brand: " + tokens[0] + NEW_LINE
                    + "Model: " + tokens[1] + NEW_LINE);
        }
    }

    private void printAvgAndTotal() {
        double sum = 0;
        double average = 0;

        for (Car car : cars) {
            sum += car.getPrice();
        }

        if (cars.size() > 0) {
            average = sum / (double) cars.size();
        }

        System.out.println("Average vehicle cost: " + average + NEW_LINE
                + "Total number of vehicles: " + cars.size());
    }

    private void printYears() {
        System.out.println("Vehicle years:");
        for (Car car : cars) {
            System.out.println(car.getYear());
        }
    }
    
    private void readAndParseInputFile() {
        System.out.println("Enter the name of the input file:");
        try {
            File file = new File(keyboardScanner.nextLine());
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String[] carTokens = fileScanner.nextLine().split("\\s");
                
                if (Integer.parseInt(carTokens[4]) == 0) {
                    Car car = new Car();
                    car.setBrandAndModel(carTokens[0].concat(" " + carTokens[1]));
                    car.setYear(Integer.parseInt(carTokens[2]));
                    car.setPrice(Integer.parseInt(carTokens[3]));
                    cars.add(car);
                } else {
                    SUV suv = new SUV();
                    suv.setBrandAndModel(carTokens[0].concat(" " + carTokens[1]));
                    suv.setYear(Integer.parseInt(carTokens[2]));
                    suv.setPrice(Integer.parseInt(carTokens[3]));
                    suv.setNumSeats(Integer.parseInt(carTokens[5]));
                    suv.setIsAllTerrain(Boolean.parseBoolean(carTokens[6]));
                    suv.setTireBrand(carTokens[7]);
                    cars.add(suv);
                }
            }
        } catch (Exception e) {
            System.out.println("Could not open file.");
        }
    }

    public String dataDump() {
        return getBrandAndModel() + " " + getYear() + " " + getPrice();
    }
    
    private void outputDataDumpToFile() {
        try {
            File outputFile = new File("./output.txt");
            PrintWriter fileWriter = new PrintWriter(outputFile);
            for (Car car : cars) {
                fileWriter.println(car.dataDump());
            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Could not write to output.txt");
        }
    }

    public void executeCommandLoop() {
        MenuOption userChoice = MenuOption.ENTER_VEHICLE;

        do {
            printMenu();

            String userString = keyboardScanner.nextLine();

            if (userString.matches("[12345678]")) {
                userChoice = MenuOption.values()[Integer.parseInt(userString)];
            } else {
                System.out.println("You must enter a digit between 1 and 8.");
                continue;
            }

            switch (userChoice) {
                case ENTER_VEHICLE:
                    promptUserAddVehicle();
                    break;
                case PRINT_BRAND_AND_MODEL:
                    printBrandAndModel();
                    break;
                case PRINT_AVG_AND_TOTAL:
                    printAvgAndTotal();
                    break;
                case PRINT_YEARS:
                    printYears();
                    break;
                case READ_INPUT:
                    readAndParseInputFile();
                    break;
                case STD_DATA_DUMP:
                    for (Car car : cars) {
                        System.out.println(car.dataDump()); 
                    }
                    break;
                case FILE_DATA_DUMP:
                    outputDataDumpToFile();
                    break;
                case EXIT:
                    break;
                default:
                    System.out.println("Invalid input: you must enter a number between 1 and 5.");
                    break;
            }
        } while (userChoice != MenuOption.EXIT);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Car car = new Car();

        car.executeCommandLoop();

    }
}
