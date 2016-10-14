package car;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Courtney Bodi
 */
public class Car {
    
    public static final String NEW_LINE = System.lineSeparator();

    private int year;
    private String brandAndModel;
    private int price;
   
    /**
     * Car constructor
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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int userChoice = 0;
        Scanner scanner = new Scanner(System.in);
        ArrayList<Car> cars = new ArrayList<>();
        
        do {
            System.out.println("(1) Enter a new vehicle" + NEW_LINE
                + "(2) Print out brand and model on two separate lines" + NEW_LINE
                + "(3) Print average vehicle cost, as well as total number of vehicles" + NEW_LINE
                + "(4) Print out all vehicle years" + NEW_LINE
                + "(5) End program");
            
            String userString = scanner.nextLine();
            
            if (userString.matches("[12345]")) {
                userChoice = Integer.parseInt(userString);
            } else {
                System.out.println("You must enter a digit between 1 and 5.");
                continue;
            }
            
            switch (userChoice) {
                case 1:
                    int year, price;
                    String brandAndModel;
                    Car newCar;
                    
                    System.out.println("Enter vehicle brand and model:");
                    brandAndModel = scanner.nextLine();
                    String tokensCheck[] = brandAndModel.split(" +");
                    if (tokensCheck.length != 2 || tokensCheck[0].matches("")) {
                        System.out.println("Invalid input: You must enter a brand and model.");
                        continue;
                    }
                    
                    System.out.println("Enter year:");
                    String yearString = scanner.nextLine();
                    if (yearString.matches("^\\d+$")) {
                        year = Integer.parseInt(yearString);
                    } else {
                        System.out.println("Invalid input: You must enter a number.");
                        continue;
                    }
                    
                    System.out.println("Enter price, or leave blank:");
                    String priceString = scanner.nextLine();
                    System.out.println("price: " + priceString);
                    if (priceString.matches("^\\d+$")) {
                        price = Integer.parseInt(priceString);
                        newCar = new Car(year, brandAndModel, price);
                    } else if (priceString.matches("")) {
                        newCar = new Car(year, brandAndModel);
                    } else {
                        System.out.println("Invalid input: You must enter a number.");
                        continue;
                    }
                    
                    cars.add(newCar);
                    break;
                case 2:
                    for (Car car : cars) {
                        String tokens[] = car.brandAndModel.split(" +");
                        System.out.println("Brand: " + tokens[0] + NEW_LINE
                            + "Model: " + tokens[1] + NEW_LINE);
                    }
                    break;
                case 3:
                    double sum = 0;
                    double average = 0;
                    
                    for (Car car : cars) {
                        sum += car.price;
                    }
                    
                    if (cars.size() > 0) {
                        average = sum / (double)cars.size();
                    }
                    
                    System.out.println("Average vehicle cost: " + average + NEW_LINE
                        + "Total number of vehicles: " + cars.size());
                    break;
                case 4:
                    System.out.println("Vehicle years:");
                    for (Car car : cars) {
                        System.out.println(car.year);
                    }
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid input: you must enter a number between 1 and 5.");
                    break;
            }
        } while (userChoice != 5);
    }
}