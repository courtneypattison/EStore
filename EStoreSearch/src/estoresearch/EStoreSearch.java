package estoresearch;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;

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
    private static final String INVALID_TIME_PERIOD = "Invalid input: time period must be in the format of -1999, 1999-, or 1999-2000.";

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

    private void promptUserSetField(String prompt, Consumer<String> setMethod) {
        int numTries = 0;
        boolean exceptionFlag;

        do {
            exceptionFlag = false;
            System.out.println(prompt);
            try {
                setMethod.accept(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                numTries++;
                exceptionFlag = true;
                if (numTries == MAX_TRIES) {
                    throw new IllegalArgumentException(MAX_TRIES_MSG);
                }
            }
        } while (exceptionFlag);
    }

    private Product checkIfIdExists(Product product) {
        for (Book book : books) {
            if (product.getId().equals(book.getId())) {
                return book;
            }
        }
        for (Electronic electronic : electronics) {
            if (product.getId().equals(electronic.getId())) {
                return electronic;
            }
        }
        return null;
    }

    private void populateProduct(Product product, String productName) {
        try {
            int numTries = 0;
            Product productDuplicateId;

            do {
                promptUserSetField("Enter " + productName + " id:", (String userString) -> product.setId(userString));

                productDuplicateId = checkIfIdExists(product);
                if (productDuplicateId != null) {
                    System.out.println("ID already exists!");
                    numTries++;
                    if (numTries == MAX_TRIES) {
                        throw new IllegalArgumentException(MAX_TRIES_MSG);
                    }
                }
            } while (productDuplicateId != null);

            promptUserSetField("Enter " + productName + " name:", (String userString) -> product.setName(userString));
            promptUserSetField("Enter " + productName + " year:",
                    (String userString) -> product.setYear(parseUserInt(userString, Product.MIN_YEAR, Product.MAX_YEAR)));
            promptUserSetField("Enter " + productName + " price:", (String userString) -> product.setPrice(parsePrice(userString)));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    private void addBook() {
        Book book = new Book();

        populateProduct(book, "book");

        try {
            promptUserSetField("Enter book author:", (String userString) -> book.setAuthor(userString));
            promptUserSetField("Enter book publisher:", (String userString) -> book.setPublisher(userString));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        boolean add = books.add(book);
        assert (add);
    }

    private void addElectronic() {
        Electronic electronic = new Electronic();

        populateProduct(electronic, "electronic");

        try {
            promptUserSetField("Enter electronic maker:", (String userString) -> electronic.setMaker(userString));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        boolean add = electronics.add(electronic);
        assert (add);
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
                    addElectronic();
                    break;
                default:
                    System.out.println(INVALID_CHOICE);
                    break;
            }
        } while (userChoice != AddMenuOption.QUIT);
    }

    private double parsePrice(String userString) {
        double price;

        String[] userTokens = userString.split("\\s+");
        if (userTokens.length != 1) {
            throw new IllegalArgumentException(Product.INVALID_PRICE);
        }

        if (userString.equals("")) {
            return Product.NO_PRICE;
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

        String[] userTokens = userString.split("\\s+");
        if (userTokens.length != 1) {
            throw new IllegalArgumentException("Invalid input: only enter one number.");
        }

        try {
            userInt = Integer.parseInt(userString);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid input");
        }

        if (userInt < min || userInt > max) {
            throw new IllegalArgumentException("Invalid input: enter a number between " + min + " and " + max);
        }

        return userInt;
    }

    private void promptUserAddMatchingId(ArrayList<Product> matchingProducts) {
        Product product = new Product();

        System.out.println("Enter product ID to be matched, or leave blank:");
        String id = scanner.nextLine();
        if (!id.equals("")) {
            try {
                product.setId(id);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            Product matchingProduct = checkIfIdExists(product);
            if (matchingProduct != null) {
                matchingProducts.add(matchingProduct);
            }
        }
    }

    private void promptUserAddMatchingKeyword(ArrayList<Product> matchingProducts) {
        System.out.println("Enter keyword to be searched in name, or leave blank:");
        String keyword = scanner.nextLine();
        String[] keywordTokens = keyword.split("\\s+");

        if (keywordTokens.length != 0) {
            for (Book book : books) {
                String[] nameTokens = book.getName().split("\\s+");
                int matchCount = 0;
                for (String nameToken : nameTokens) {
                    for (String keywordToken : keywordTokens) {
                        if (keywordToken.equalsIgnoreCase(nameToken)) {
                            matchCount++;
                        }
                    }
                }
                if (matchCount == keywordTokens.length) {
                    matchingProducts.add(book);
                }
            }

            for (Electronic electronic : electronics) {
                String[] nameTokens = electronic.getName().split("\\s+");
                int matchCount = 0;
                for (String nameToken : nameTokens) {
                    for (String keywordToken : keywordTokens) {
                        if (keywordToken.equalsIgnoreCase(nameToken)) {
                            matchCount++;
                        }
                    }
                }
                if (matchCount == keywordTokens.length) {
                    matchingProducts.add(electronic);
                }
            }
        }
    }

    private void promptUserAddMatchingTimePeriod(ArrayList<Product> matchingProducts) {
        System.out.println("Enter time period, e.g., 1999-2001, or leave blank:");
        String timePeriod = scanner.nextLine();

        if (!timePeriod.equals("")) {
            switch (timePeriod.indexOf("-")) {
                case 0:
                    if (timePeriod.length() != 5) {
                        System.out.println(INVALID_TIME_PERIOD);
                    } else {
                        for (Book book : books) {
                            if (book.getYear() <= parseUserInt(timePeriod.substring(1, 5), Product.MIN_YEAR, Product.MAX_YEAR)) {
                                matchingProducts.add(book);
                            }
                        }

                        for (Electronic electronic : electronics) {
                            if (electronic.getYear() <= parseUserInt(timePeriod.substring(1, 5), Product.MIN_YEAR, Product.MAX_YEAR)) {
                                matchingProducts.add(electronic);
                            }
                        }
                    }
                    break;
                case 4:
                    if (timePeriod.length() == 5) {
                        for (Book book : books) {
                            if (parseUserInt(timePeriod.substring(0, 4), Product.MIN_YEAR, Product.MAX_YEAR) <= book.getYear()) {
                                matchingProducts.add(book);
                            }
                        }

                        for (Electronic electronic : electronics) {
                            if (parseUserInt(timePeriod.substring(0, 4), Product.MIN_YEAR, Product.MAX_YEAR) <= electronic.getYear()) {
                                matchingProducts.add(electronic);
                            }
                        }
                    } else if (timePeriod.length() == 9) {
                        for (Book book : books) {
                            if (parseUserInt(timePeriod.substring(0, 4), Product.MIN_YEAR, Product.MAX_YEAR) <= book.getYear()
                                    && parseUserInt(timePeriod.substring(5, 9), Product.MIN_YEAR, Product.MAX_YEAR) >= book.getYear()) {
                                matchingProducts.add(book);
                            }
                        }

                        for (Electronic electronic : electronics) {
                            if (parseUserInt(timePeriod.substring(0, 4), Product.MIN_YEAR, Product.MAX_YEAR) <= electronic.getYear()
                                    && parseUserInt(timePeriod.substring(5, 9), Product.MIN_YEAR, Product.MAX_YEAR) <= electronic.getYear()) {
                                matchingProducts.add(electronic);
                            }
                        }
                    } else {
                        System.out.println(INVALID_TIME_PERIOD);
                    }
                    break;
                case -1:
                    if (timePeriod.length() == 4) {
                        for (Book book : books) {
                            if (book.getYear() == parseUserInt(timePeriod.substring(0, 4), Product.MIN_YEAR, Product.MAX_YEAR)) {
                                matchingProducts.add(book);
                            }
                        }

                        for (Electronic electronic : electronics) {
                            if (electronic.getYear() == parseUserInt(timePeriod.substring(0, 4), Product.MIN_YEAR, Product.MAX_YEAR)) {
                                matchingProducts.add(electronic);
                            }
                        }
                    } else {
                        System.out.println(INVALID_TIME_PERIOD);
                    }
                    break;
                default:
                    System.out.println(INVALID_TIME_PERIOD);
                    break;
            }

        }
    }

    private void printMatchingProducts(ArrayList<Product> matchingProducts) {
        if (matchingProducts.isEmpty()) {
            System.out.println("None of the products match your search criteria.");
        } else {
            if (matchingProducts.size() == 1) {
                System.out.println("The following product matches your search criteria:");
            } else {
                System.out.println("The following products match your search criteria:");
            }
            for (Product finalProduct : matchingProducts) {
                System.out.println(finalProduct.toString());
            }
        }
    }

    private void executeSearch() {
        ArrayList<Product> matchingProducts = new ArrayList<>();
        ArrayList<Product> matchingIdProducts = new ArrayList<>();
        ArrayList<Product> matchingKeywordProducts = new ArrayList<>();
        ArrayList<Product> matchingTimePeriodProducts = new ArrayList<>();

        promptUserAddMatchingId(matchingIdProducts);
        promptUserAddMatchingKeyword(matchingKeywordProducts);
        promptUserAddMatchingTimePeriod(matchingTimePeriodProducts);

        if (matchingIdProducts.size() == 1) {
            if (matchingKeywordProducts.size() > 0) {
                for (Product matchingKeywordProduct : matchingKeywordProducts) {
                    if (matchingIdProducts.get(0).equals(matchingKeywordProduct)) {
                        matchingProducts.add(matchingKeywordProduct);
                    }
                }
            }
            if (matchingTimePeriodProducts.size() > 0) {
                for (Product matchingTimePeriodProduct : matchingTimePeriodProducts) {
                    if (matchingIdProducts.get(0).equals(matchingTimePeriodProduct)) {
                        matchingProducts.add(matchingTimePeriodProduct);
                    }
                }
            }
        } else if (matchingKeywordProducts.size() > 0) {
            if (matchingTimePeriodProducts.size() > 0) {
                for (Product matchingKeywordProduct : matchingKeywordProducts) {
                    for (Product matchingTimePeriodProduct : matchingTimePeriodProducts) {
                        if (matchingKeywordProduct.equals(matchingTimePeriodProduct)) {
                            matchingProducts.add(matchingKeywordProduct);
                        }
                    }
                }
            } else {
                for (Product matchingKeywordProduct : matchingKeywordProducts) {
                    matchingProducts.add(matchingKeywordProduct);
                }
            }
        } else {
            for (Product matchingTimePeriodProduct : matchingTimePeriodProducts) {
                matchingProducts.add(matchingTimePeriodProduct);
            }
        }
        printMatchingProducts(matchingProducts);
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
                    executeSearch();
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

        for (Book book : books) {
            System.out.println(book.toString());
        }
        for (Electronic electronic : electronics) {
            System.out.println(electronic.toString());
        }
    }

}
