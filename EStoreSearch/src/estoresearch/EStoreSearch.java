package estoresearch;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Courtney Bodi
 */
public class EStoreSearch {
    
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Electronic> electronics = new ArrayList<>();
    
    private Scanner scanner = new Scanner(System.in);
    
    /**
     * EStoreSearch constructor with all fields
     * 
     * @param books
     * @param electronics 
     */
    public EStoreSearch(ArrayList<Book> books, ArrayList<Electronic> electronics) {
        this.books = books;
        this.electronics = electronics;
    }
    
    /**
     * Generic EStoreSearch constructor
     */
    public EStoreSearch() {
        books = null;
        electronics = null;
    }
    
    /**
     * Prints main menu
     */
    private void printMainMenu() {
        System.out.println("Choose from the following options, or press 0 to quit" + System.lineSeparator()
            + "(1) Add" + System.lineSeparator()
            + "(2) Search");
    }
    
    /**
     * Executes main command loop
     */
    public void executeCommandLoop() {
        int userChoice = 1;
        
        do {
            printMainMenu();
            String userString = scanner.nextLine();
            Pattern inputPattern = Pattern.compile(" *([120]) *");
            Matcher intMatcher = inputPattern.matcher(userString);

            System.out.println("matcher: " + intMatcher.toString());
            /*if (!intMatcher.group(1).matches("[120]")) {
                System.out.println("Invalid input: Enter 1, 2, or 0.");
                continue;
            } else {
                userChoice = Integer.parseInt(intMatcher.group(1));
            }*/

            switch (userChoice) {
                case 1:
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        } while (userChoice != 0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Electronic> electronics = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
        EStoreSearch eStoreSearch = new EStoreSearch(books, electronics);
        
        System.out.println("Welcome to EStore Search" + System.lineSeparator());
        eStoreSearch.executeCommandLoop();
    }
    
}
