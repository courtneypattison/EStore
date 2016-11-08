package estoresearch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Adds and searches books and electronics
 *
 * @author Courtney Bodi
 */
public class EStoreSearch {

    /**
     * Main menu selection options
     */
    public enum MainMenuOption {
        QUIT, ADD, SEARCH
    }

    /**
     * Add menu selection options
     */
    public enum AddMenuOption {
        RETURN, ADD_BOOK, ADD_ELECTRONIC
    }

    public static final int MAX_CHOICE = 2;
    public static final int MIN_CHOICE = 0;

    public static final int MAX_TRIES = 3;
    public static final String MAX_TRIES_MSG = "Too many attempts!";

    public static final String INVALID_CHOICE = "Invalid input: you must enter 0, 1, or 2.";
    public static final String INVALID_TIME_PERIOD = "Invalid input: time period must be in the format of -1999, 1999-, or 1999-2000.";

    private ArrayList<Product> products = new ArrayList<>();
    private HashMap<String, ArrayList<Integer>> keywords = new HashMap<>();

    private Scanner scanner = new Scanner(System.in);

    /**
     * EStoreSearch constructor with all fields
     *
     * @param products including books and electronics
     */
    public EStoreSearch(ArrayList<Product> products) {
        this.products = products;
    }

    /**
     * Generic EStoreSearch constructor
     */
    public EStoreSearch() {
        products = null;
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

    /**
     * Prompts user for string to add to field specified by set method
     *
     * @param prompt
     * @param setMethod
     */
    private void promptUserSetField(String prompt, ThrowingConsumer<String> setMethod) throws InvalidInputException {
        int numTries = 0;
        boolean exceptionFlag;

        do {
            exceptionFlag = false;
            System.out.println(prompt);
            try {
                setMethod.acceptThrows(scanner.nextLine());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                numTries++;
                exceptionFlag = true;
                if (numTries == MAX_TRIES) {
                    throw new InvalidInputException(MAX_TRIES_MSG);
                }
            }
        } while (exceptionFlag);
    }

    /**
     * Checks if product ID already exists in EStore
     *
     * @param product
     * @return
     */
    private Product checkIfIdExists(Product product) {
        for (Product existingProduct : products) {
            if (product.getId().equals(existingProduct.getId())) {
                return product;
            }
        }
        return null;
    }

    /**
     * Sets product id, name, year, and price if applicable
     *
     * @param product
     * @param productName
     */
    private void populateProduct(Product product, String productName) throws InvalidInputException {
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
                        throw new InvalidInputException(MAX_TRIES_MSG);
                    }
                }
            } while (productDuplicateId != null);

            promptUserSetField("Enter " + productName + " name:", (String userString) -> product.setName(userString));
            promptUserSetField("Enter " + productName + " year:",
                    (String userString) -> product.setYear(parseUserInt(userString, Product.MIN_YEAR, Product.MAX_YEAR)));
            promptUserSetField("Enter " + productName + " price:", (String userString) -> product.setPrice(parsePrice(userString)));
        } catch (InvalidInputException e) {
            throw new InvalidInputException(e.getMessage());
        }

    }
    
    private void addKeywordsToHashMap(Product product) {
        String[] nameTokens = product.getName().toLowerCase().split("\\s+");
        for (String keyword : nameTokens) {
            if (keywords.containsKey(keyword)) {
                ArrayList<Integer> ints = keywords.get(keyword);
                ints.add(products.indexOf(product));
                keywords.put(keyword, ints);
            } else {
                ArrayList<Integer> ints = new ArrayList<>();
                ints.add(products.indexOf(product));
                keywords.put(keyword, ints);
            }
        }
    }

    /**
     * Adds book to books list
     */
    private void addBook() {
        Book book;

        try {
            book = new Book();
            populateProduct(book, "book");
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
            return;
        }

        try {
            promptUserSetField("Enter book author:", (String userString) -> book.setAuthor(userString));
            promptUserSetField("Enter book publisher:", (String userString) -> book.setPublisher(userString));
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
            return;
        }

        boolean add = products.add(book);
        assert (add);
        addKeywordsToHashMap(book);
        
    }

    /**
     * Adds electronic to electronics list
     */
    private void addElectronic() {
        Electronic electronic;

        try {
            electronic = new Electronic();
            populateProduct(electronic, "electronic");
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
            return;
        }

        try {
            promptUserSetField("Enter electronic maker:", (String userString) -> electronic.setMaker(userString));
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
            return;
        }

        boolean add = products.add(electronic);
        assert (add);
        addKeywordsToHashMap(electronic);
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
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
                continue;
            }
            userChoice = AddMenuOption.values()[userInt];

            switch (userChoice) {
                case RETURN:
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
        } while (userChoice != AddMenuOption.RETURN);
    }

    /**
     * Validates and parses price into an integer
     *
     * @param userString
     * @return valid price
     */
    private double parsePrice(String userString) throws InvalidInputException {
        double price;

        String[] userTokens = userString.split("\\s+");
        if (userTokens.length != 1) {
            throw new InvalidInputException(Product.INVALID_PRICE);
        }

        if (userString.equals("")) {
            return Product.NO_PRICE;
        }

        try {
            price = Double.parseDouble(userString);
        } catch (Exception e) {
            throw new InvalidInputException(Product.INVALID_PRICE);
        }

        if (price < 0) {
            throw new InvalidInputException(Product.INVALID_PRICE);
        }

        return price;
    }

    /**
     * Gets an integer from the user
     *
     * @return user entered integer between min and max, or throws an exception
     */
    private int parseUserInt(String userString, int min, int max) throws InvalidInputException {
        int userInt = 0;

        String[] userTokens = userString.split("\\s+");
        if (userTokens.length != 1) {
            throw new InvalidInputException("Invalid input: only enter one number.");
        }

        try {
            userInt = Integer.parseInt(userString);
        } catch (Exception e) {
            throw new InvalidInputException("Invalid input");
        }

        if (userInt < min || userInt > max) {
            throw new InvalidInputException("Invalid input: enter a number between " + min + " and " + max);
        }

        return userInt;
    }

    /**
     * Prompt user to search by ID and adds product with corresponding ID
     *
     * @param matchingProducts
     */
    private void promptUserAddMatchingId(ArrayList<Product> matchingProducts) throws InvalidInputException {
        int numTries = 0;
        boolean exceptionFlag;

        Product product = new Product();

        do {
            exceptionFlag = false;
            System.out.println("Enter product ID to be matched, or leave blank:");
            String id = scanner.nextLine();
            if (!id.equals("")) {
                try {
                    product.setId(id);
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                    numTries++;
                    exceptionFlag = true;
                    if (numTries == MAX_TRIES) {
                        throw new InvalidInputException(MAX_TRIES_MSG);
                    }
                    continue;
                }
                Product matchingProduct = checkIfIdExists(product);
                if (matchingProduct != null) {
                    matchingProducts.add(matchingProduct);
                }
            }

        } while (exceptionFlag);
    }

    /**
     * Prompts user to input keywords, then searches products for keywords in
     * names, and adds product to matchingProducts if there is a match
     *
     * @param matchingProducts
     */
    private void promptUserAddMatchingKeyword(ArrayList<Product> matchingProducts) {
        System.out.println("Enter keyword to be searched in name, or leave blank:");
        String keyword = scanner.nextLine();
        String[] keywordTokens = keyword.split("\\s+");

        if (keywordTokens.length > 0) {
            for (Product product : products) {
                String[] nameTokens = product.getName().split("\\s+");
                int matchCount = 0;
                for (String nameToken : nameTokens) {
                    for (String keywordToken : keywordTokens) {
                        if (keywordToken.equalsIgnoreCase(nameToken)) {
                            matchCount++;
                        }
                    }
                }
                if (matchCount == keywordTokens.length) {
                    matchingProducts.add(product);
                }
            }
        }
    }

    /**
     * Prompts user to input time period and adds products that fall within that
     * time period to the matchingProducts
     *
     * @param matchingProducts
     */
    private void promptUserAddMatchingTimePeriod(ArrayList<Product> matchingProducts) throws InvalidInputException {
        int numTries = 0;
        boolean exceptionFlag;

        do {
            exceptionFlag = false;

            System.out.println("Enter time period, e.g., 1999-2001, or leave blank:");
            String timePeriod = scanner.nextLine();

            if (!timePeriod.equals("")) {
                switch (timePeriod.indexOf("-")) {
                    case 0:
                        if (timePeriod.length() != 5) {
                            System.out.println(INVALID_TIME_PERIOD);
                            numTries++;
                            exceptionFlag = true;
                        } else {
                            for (Product product : products) {
                                if (product.getYear() <= parseUserInt(timePeriod.substring(1, 5), Product.MIN_YEAR, Product.MAX_YEAR)) {
                                    matchingProducts.add(product);
                                }
                            }
                        }
                        break;
                    case 4:
                        if (timePeriod.length() == 5) {
                            for (Product product : products) {
                                if (product.getYear() >= parseUserInt(timePeriod.substring(0, 4), Product.MIN_YEAR, Product.MAX_YEAR)) {
                                    matchingProducts.add(product);
                                }
                            }
                        } else if (timePeriod.length() == 9) {
                            for (Product product : products) {
                                if (product.getYear() >= parseUserInt(timePeriod.substring(0, 4), Product.MIN_YEAR, Product.MAX_YEAR)
                                        && product.getYear() <= parseUserInt(timePeriod.substring(5, 9), Product.MIN_YEAR, Product.MAX_YEAR)) {
                                    matchingProducts.add(product);
                                }
                            }
                        } else {
                            System.out.println(INVALID_TIME_PERIOD);
                            numTries++;
                            exceptionFlag = true;
                        }
                        break;
                    case -1:
                        if (timePeriod.length() == 4) {
                            for (Product product : products) {
                                if (product.getYear() == parseUserInt(timePeriod.substring(0, 4), Product.MIN_YEAR, Product.MAX_YEAR)) {
                                    matchingProducts.add(product);
                                }
                            }
                        } else {
                            System.out.println(INVALID_TIME_PERIOD);
                            numTries++;
                            exceptionFlag = true;
                        }
                        break;
                    default:
                        System.out.println(INVALID_TIME_PERIOD);
                        numTries++;
                        exceptionFlag = true;
                        break;
                }

            }
            if (numTries == MAX_TRIES) {
                throw new InvalidInputException(MAX_TRIES_MSG);
            }
        } while (exceptionFlag);
    }

    /**
     * Prints matching products
     *
     * @param matchingProducts
     */
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

    /**
     * Performs search
     */
    private void executeSearch() {
        ArrayList<Product> matchingProducts = new ArrayList<>();
        ArrayList<Product> matchingIdProducts = new ArrayList<>();
        ArrayList<Product> matchingKeywordProducts = new ArrayList<>();
        ArrayList<Product> matchingTimePeriodProducts = new ArrayList<>();

        try {
            promptUserAddMatchingId(matchingIdProducts);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
            return;
        }
        promptUserAddMatchingKeyword(matchingKeywordProducts);

        try {
            promptUserAddMatchingTimePeriod(matchingTimePeriodProducts);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
            return;
        }

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
            if (matchingKeywordProducts.size() == 0 && matchingTimePeriodProducts.size() == 0) {
                matchingProducts.add(matchingIdProducts.get(0));
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

    private void printProducts() {
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }

    private void saveProducts() {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileOutputStream("output.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Error opening output.txt");
            System.exit(0);
        }

        for (Product product : products) {
            outputStream.println(product.toString());
        }

        outputStream.close();
    }

    /**
     * Executes main command loop
     */
    public void executeMainMenuLoop() {
        MainMenuOption userChoice = MainMenuOption.ADD;
        System.out.println("Welcome to EStore Search");

        do {
            int userInt;

            printMainMenu();

            try {
                userInt = parseUserInt(scanner.nextLine(), MIN_CHOICE, MAX_CHOICE);
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
                continue;
            }

            userChoice = MainMenuOption.values()[userInt];
            switch (userChoice) {
                case QUIT:
                    saveProducts();
                    System.out.println("Thank you for using EStoreSearch!");
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

    private String getAttribute(String line) {
        String[] lineTokens = line.split(" ?=");
        if (lineTokens.length > 1) {
            return lineTokens[0];
        } else {
            return "\n";
        }
    }

    private String getValue(String line) {
        String[] lineTokens = line.split("(?<!\\\\)\"");
        if (lineTokens.length == 2) {
            return lineTokens[1];
        } else {
            return "";
        }
    }

    private void loadProducts(String filename) {
        Scanner fileInput = null;
        String type = "", productID = "", name = "", authors = "", publisher = "", maker = "";
        double price = Product.NO_PRICE;
        int year = 0;

        try {
            fileInput = new Scanner(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            System.out.println(filename + " was not found or could not be opened.");
            System.exit(0);
        }

        while (fileInput.hasNextLine()) {
            String line = fileInput.nextLine();
            String attribute = getAttribute(line);
            String value = getValue(line);

            try {
                if (attribute.equals("type")) {
                    type = value;
                } else if (attribute.equals("productID")) {
                    productID = value;
                } else if (attribute.equals("name")) {
                    name = value;
                } else if (attribute.equals("price")) {
                    price = parsePrice(value);
                } else if (attribute.equals("year")) {
                    year = parseUserInt(value, Product.MIN_YEAR, Product.MAX_YEAR);
                } else if (attribute.equals("authors")) {
                    authors = value;
                } else if (attribute.equals("publisher")) {
                    publisher = value;
                } else if (attribute.equals("maker")) {
                    maker = value;
                } else if (attribute.equals("\n")) {
                } else {
                    System.out.println("Invalid input from file");
                    System.exit(0);
                }

                if (attribute.equals("\n") || !fileInput.hasNextLine()) {
                    if (type.equals("book")) {
                        Book book = new Book(productID, name, year, price, authors, publisher);
                        products.add(book);
                        addKeywordsToHashMap(book);
                    } else {
                        Electronic electronic = new Electronic(productID, name, year, price, maker);
                        products.add(electronic);
                        addKeywordsToHashMap(electronic);
                    }

                }
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
        fileInput.close();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 1) {
            EStoreSearch eStoreSearch = new EStoreSearch(new ArrayList<>());
            eStoreSearch.loadProducts(args[0]);

            eStoreSearch.executeMainMenuLoop();
        } else {
            System.out.println("You must enter a filename when running this program.");
        }
    }
}
