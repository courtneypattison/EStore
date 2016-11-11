package car;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Courtney Bodi
 */
public class Car {

    enum MenuOption {
        NULL,
        ENTER_VEHICLE,
        PRINT_BRAND_MODEL_YEAR,
        PRINT_AVG_AND_TOTAL,
        READ_INPUT,
        STD_DATA_DUMP,
        FILE_DATA_DUMP,
        LOOKUP,
        EXIT
    }

    public static final String NEW_LINE = System.lineSeparator();
    private static final int DEFAULT_PRICE = 50000;

    private Scanner keyboardScanner = new Scanner(System.in);

    private ArrayList<Car> cars = new ArrayList<>();
    private HashMap<String, Car> carsMap = new HashMap<>();

    private String brandAndModel;
    private int year;
    private int price;

    /**
     * Car constructor
     *
     * @param year
     * @param brandAndModel
     * @param price
     * @throws java.lang.Exception
     */
    public Car(String brandAndModel, int year, int price) throws Exception {
        this.brandAndModel = brandAndModel;
        if (year <= 1986) {
            throw new Exception("Invalid input: Car cannot be greater than 30 years old.");
        }
        this.year = year;
        if (price < 5000) {
            throw new Exception("Invalid input: Price must be $5000 or more.");
        }
        this.price = price;
    }

    /**
     * Car constructor with default price
     *
     * @param year
     * @param brandAndModel
     */
    public Car(String brandAndModel, int year) throws Exception {
        this.brandAndModel = brandAndModel;
        if (year <= 1986) {
            throw new Exception("Invalid input: Car cannot be greater than 30 years old.");
        }
        this.year = year;
        price = DEFAULT_PRICE;
    }

    /**
     * Default Car constructor
     */
    public Car() {
        brandAndModel = "";
        year = 0;
        price = DEFAULT_PRICE;
    }

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

    @Override
    public String toString() {
        return brandAndModel + " " + year + " " + price;
    }

    private void printMenu() {
        System.out.println("(1) Enter the info about a new vehicle (either a car or a SUV)." + NEW_LINE
                + "(2) Print out brand, model, and year, delimited by spaces, for all vehicles." + NEW_LINE
                + "(3) Print average vehicle cost, as well as total number of vehicles." + NEW_LINE
                + "(4) Read from input file." + NEW_LINE
                + "(5) Standard Data dump." + NEW_LINE
                + "(6) File Data dump." + NEW_LINE
                + "(7) Lookup via HashMap with brand, model, and year" + NEW_LINE
                + "(8) End program");
    }

    private String createKey(Car car) {
        String key = car.getBrandAndModel().replaceAll("\\s", "");
        key += car.getYear();
        return key;
    }

    private int promptUserGetYear() throws Exception {
        System.out.println("Enter year:");
        String yearString = keyboardScanner.nextLine();
        if (yearString.matches("^\\d+$")) {
            year = Integer.parseInt(yearString);
            return year;
        } else {
            throw new Exception("Invalid input: You must enter a number.");
        }
    }

    private String promptUserGetBrandAndModel() throws Exception {
        String brandAndModel;

        System.out.println("Enter vehicle brand and model:");
        brandAndModel = keyboardScanner.nextLine();
        String tokensCheck[] = brandAndModel.split(" +");
        if (tokensCheck.length != 2 || tokensCheck[0].matches("")) {
            throw new Exception("Invalid input: You must enter a brand and model.");
        }
        return brandAndModel;
    }

    private int promptUserGetPrice() throws Exception {
        System.out.println("Enter price, or leave blank:");
        String priceString = keyboardScanner.nextLine();
        if (priceString.matches("^\\d+$")) {
            return Integer.parseInt(priceString);
        } else if (priceString.matches("")) {
            return DEFAULT_PRICE;
        } else {
            throw new Exception("Invalid input: You must enter a number.");
        }
    }

    private int promptUserGetNumSeats() throws Exception {
        System.out.println("Enter number of seats:");
        String numSeatsString = keyboardScanner.nextLine();
        if (numSeatsString.matches("^\\d+$")) {
            return Integer.parseInt(numSeatsString);
        } else {
            throw new Exception("Invalid input: You must enter a number.");
        }
    }

    private Boolean promptUserIsAllTerrain() throws Exception {
        System.out.println("Enter 1 or 0 to indicate if the SUV is all terrain or not:");
        String isAllTerrainString = keyboardScanner.nextLine();
        if (isAllTerrainString.matches("[01]")) {
            return Boolean.parseBoolean(isAllTerrainString);
        } else {
            throw new Exception("Invalid input: You must enter 0 or 1.");
        }
    }

    private String promptUserTireBrand() throws Exception {
        System.out.println("Enter tire brand:");
        String tireBrand = keyboardScanner.nextLine();
        String[] tireBrandTokens = tireBrand.split("\\s+");
        if (tireBrandTokens.length == 1) {
            return tireBrand;
        } else {
            throw new Exception("Invalid input: You must enter a single word.");
        }
    }

    private void promptUserAddVehicle() throws Exception {
        int userChoice, year, price;
        String brandAndModel;

        System.out.println("(1) Enter car" + NEW_LINE
                + "(2) Enter SUV");

        String userString = keyboardScanner.nextLine();

        if (userString.matches("[12]")) {
            userChoice = Integer.parseInt(userString);
        } else {
            throw new Exception("Invalid input: you must enter either 1 or 2.");
        }

        try {
            brandAndModel = promptUserGetBrandAndModel();
            year = promptUserGetYear();
            String key = createKey(new Car(brandAndModel, year));
            if (carsMap.containsKey(key)) {
                throw new Exception("Invalid input: a car with this brand, model, and year already exists.");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (userChoice == 1) {
            try {
                Car car = new Car(brandAndModel, year, promptUserGetPrice());
                cars.add(car);
                carsMap.put(createKey(car), car);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            try {
                SUV suv = new SUV(brandAndModel, year, promptUserGetPrice(), promptUserGetNumSeats(), promptUserIsAllTerrain(), promptUserTireBrand());
                cars.add(suv);
                carsMap.put(createKey(suv), suv);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
    }

    private void printBrandModelYear() {
        for (Car car : cars) {
            String[] tokens = car.getBrandAndModel().split(" +");
            System.out.println(tokens[0] + " " + tokens[1] + " " + car.getYear());
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
        String filename = keyboardScanner.nextLine();
        Scanner fileInput = null;

        try {
            fileInput = new Scanner(new FileInputStream(filename));

        } catch (FileNotFoundException e) {
            System.out.println("Could not open file.");
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
                System.out.println(e.getMessage());
            }
        }

        fileInput.close();
    }

    public String dataDump() {
        return getBrandAndModel() + " " + getYear() + " " + getPrice() + " 0";
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

    private void lookupCar() {
        System.out.println("Enter the brand, model, and year of a car:");
        String userString = keyboardScanner.nextLine();
        String[] tokens = userString.split("\\s+");
        if (tokens.length != 3) {
            System.out.println("Invalid input: Lookup must be in the following format: Volvo, V70, 2002");
            return;
        }
        String key = userString.replaceAll(",\\s+", "");
        if (carsMap.containsKey(key)) {
            Car car = carsMap.get(key);
            System.out.println("Cars found:" + NEW_LINE
                    + car.toString());
        } else {
            System.out.println("No Matching cars found.");
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
                    try {
                        promptUserAddVehicle();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case PRINT_BRAND_MODEL_YEAR:
                    printBrandModelYear();
                    break;
                case PRINT_AVG_AND_TOTAL:
                    printAvgAndTotal();
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
                case LOOKUP:
                    lookupCar();
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
