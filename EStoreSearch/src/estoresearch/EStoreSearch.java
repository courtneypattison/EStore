package estoresearch;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.BiConsumer;

/**
 * @author Courtney Bodi
 */
public class EStoreSearch {

    public enum MainMenuOption {
        QUIT, ADD, SEARCH
    }

    public enum AddMenuOption {
        QUIT, ADD_BOOK, ADD_ELECTRONIC
    }

    private static final int MAX_CHOICE = 2;
    private static final int MIN_CHOICE = 0;

    private static final int MAX_TRIES = 3;
    private static final String MAX_TRIES_MSG = "Too many attempts!";
    private static final String INVALID_CHOICE = "Invalid input: you must enter 0, 1, or 2.";

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
     * Prints main menu
     */
    private void printAddMenu() {
        System.out.println("Choose from the following options, or press 0 to return to the main menu" + System.lineSeparator()
                + "(1) Add book" + System.lineSeparator()
                + "(2) Add electronic");
    }
    
    private void promptUserSetField(Book book, String prompt, BiConsumer<Book, String> setMethod) {
        int numTries = 0;
        boolean exceptionFlag;

        do {
            exceptionFlag = false;
            System.out.println(prompt);
            try {
                setMethod.accept(book, scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                numTries++;
                exceptionFlag = true;
                if (numTries == MAX_TRIES) {
                    throw new IllegalArgumentException(e.getMessage());
                }
            }
        } while (exceptionFlag == true && numTries != MAX_TRIES);
    }

    private void addBook() {
        Book book = new Book();
        
        try {
            promptUserSetField(book, "Enter book id:", Book::setId);
            promptUserSetField(book, "Enter book name:", Book::setName);
            promptUserSetField(book, "Enter book year:",
                (Book bk, String userString) -> {
                    int year;
                    try {
                        year = parseUserInt(userString, Product.MIN_YEAR, Product.MAX_YEAR);
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                    bk.setYear(year);});
            promptUserSetField(book, "Enter book price:",
                (Book bk, String userString) -> {
                    double price;
                    try {
                        price = parsePrice(userString);
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                    bk.setPrice(price);});
        } catch (IllegalArgumentException e) {
            System.out.println(MAX_TRIES_MSG);
            return;
        }

        this.books.add(book);
    }

    /**
     * Executes add menu loop
     */
    private void executeAddMenuLoop() {
        AddMenuOption userChoice = AddMenuOption.ADD_BOOK;

        do {
            int userInt;

            printAddMenu();

            try {
                userInt = parseUserInt(scanner.nextLine(), MIN_CHOICE, MAX_CHOICE);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }
            userChoice = AddMenuOption.values()[userInt];

            switch (userChoice) {
                case QUIT:
                    break;
                case ADD_BOOK:
                    addBook();
                    break;
                case ADD_ELECTRONIC:
                    System.out.println("ADD ELECTRONIC Case");
                    break;
                default:
                    System.out.println(INVALID_CHOICE);
                    break;
            }
        } while (userChoice != AddMenuOption.QUIT);
    }

    private double parsePrice(String userString) {
        double price;
        
        String[] userTokens = userString.split(" +");
        if (userTokens.length != 1) {
            throw new IllegalArgumentException(Product.INVALID_PRICE);
        }
        
        try {
            price = Double.parseDouble(userString);
        } catch (Exception e) {
            throw new IllegalArgumentException(Product.INVALID_PRICE);
        }

        if (price <= 0) {
            throw new IllegalArgumentException(Product.INVALID_PRICE);
        }
        
        return price;
    }
    
    /**
     * Gets an integer from the user
     *
     * @return user entered integer between min and max, or throws an exception
     */
    private int parseUserInt(String userString, int min, int max) {
        int userInt = 0;

        String[] userTokens = userString.split(" +");
        if (userTokens.length != 1) {
            throw new IllegalArgumentException(INVALID_CHOICE);
        }

        try {
            userInt = Integer.parseInt(userString);
        } catch (Exception e) {
            throw new IllegalArgumentException(INVALID_CHOICE);
        }

        if (userInt < min || userInt > max) {
            throw new IllegalArgumentException(INVALID_CHOICE);
        }

        return userInt;
    }

    /**
     * Executes main command loop
     */
    public void executeMainMenuLoop() {
        MainMenuOption userChoice = MainMenuOption.ADD;

        do {
            int userInt = 1;

            printMainMenu();

            try {
                userInt = parseUserInt(scanner.nextLine(), MIN_CHOICE, MAX_CHOICE);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }

            userChoice = MainMenuOption.values()[userInt];
            switch (userChoice) {
                case QUIT:
                    break;
                case ADD:
                    executeAddMenuLoop();
                    break;
                case SEARCH:
                    System.out.println("SEARCH Case");
                    break;
                default:
                    System.out.println(INVALID_CHOICE);
                    break;
            }
        } while (userChoice != MainMenuOption.QUIT);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Electronic> electronics = new ArrayList<>();

        EStoreSearch eStoreSearch = new EStoreSearch(books, electronics);

        System.out.println("Welcome to EStore Search" + System.lineSeparator());
        eStoreSearch.executeMainMenuLoop();
        System.out.println(eStoreSearch.books.get(0).toString());
    }

}
