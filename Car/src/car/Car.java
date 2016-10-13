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
        int userChoice;
        Scanner scanner = new Scanner(System.in);
        ArrayList<Car> cars = new ArrayList<>();
        
        do {
            System.out.println("(1) Enter a new vehicle" + NEW_LINE
                + "(2) Print out brand and model on two separate lines" + NEW_LINE
                + "(3) Print average vehicle cost, as well as total number of vehicles" + NEW_LINE
                + "(4) Print out all vehicle years" + NEW_LINE
                + "(5) End program");
            userChoice = Integer.parseInt(scanner.nextLine());
            
            switch (userChoice) {
                case 1:
                    System.out.println("Enter vehicle brand and model:");
                    String brandAndModel = scanner.nextLine();
                    System.out.println("Enter year:");
                    int year = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter price, or leave blank:");
                    int price = Integer.parseInt(scanner.nextLine());
                    Car newCar = new Car(year, brandAndModel, price);
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