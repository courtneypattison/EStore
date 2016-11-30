package estoresearch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Adds and searches books and electronics
 *
 * @author Courtney Bodi
 */
public class EStoreSearch {

    private ArrayList<Product> products;
    private HashMap<String, HashSet<Integer>> keywords;

    public static final String TOO_MANY_NUMBERS = "Invalid input: enter one"
            + " number.";
    public static final String OUT_OF_RANGE = "Invalid input: out of range";
    public static final String NOT_AN_INTEGER = "Invalid input: enter an"
            + " integer";
    public static final String INVALID_DECIMAL_PLACE = "Invalid input: the"
            + " price must only have 2 decimal places";
    public static final String INVALID_RANGE = "Invalid input: start year must"
            + " be less than or equal to end year";
    public static final String DUPLICATE_ID = "ID already exists!";

    public static final int DECIMAL_PLACE = 2;

    /**
     * Generic EStoreSearch constructor
     *
     */
    public EStoreSearch() {
        products = new ArrayList<>();
        keywords = new HashMap<>();
    }

    /**
     * @return the products
     */
    public ArrayList<Product> getProducts() {
        return products;
    }

    /**
     * Checks if product ID already exists in EStore
     *
     * @param productID to check for
     * @return whether or not product with that productID is already in the
     * store
     */
    private Boolean idExists(String productID) {
        for (Product product : getProducts()) {
            if (productID.equals(product.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds keywords from names in products to hash map
     *
     * @param product to add keywords from
     */
    private void addKeywordsToHashMap(Product product) {
        String[] nameTokens = product.getName().toLowerCase().split("\\s+");
        for (String keyword : nameTokens) {
            if (keywords.containsKey(keyword)) {
                HashSet<Integer> ints = keywords.get(keyword);
                ints.add(getProducts().indexOf(product));
                keywords.put(keyword, ints);
            } else {
                HashSet<Integer> ints = new HashSet<>();
                ints.add(getProducts().indexOf(product));
                keywords.put(keyword, ints);
            }
        }
    }

    /**
     * Adds book to books list
     *
     * @param id is a unique 6 digit string
     * @param name of product
     * @param yearStr product released
     * @param priceStr of product in dollars CAD
     * @param author of product with first and last name
     * @param publisher of product name
     * @throws estoresearch.InvalidInputException custom input validation
     * checked exception
     */
    public void addBook(String id, String name, String yearStr,
            String priceStr, String author, String publisher)
            throws InvalidInputException {

        Book book;

        if (idExists(id)) {
            throw new InvalidInputException(DUPLICATE_ID);
        }

        try {
            int year = parseUserInt(yearStr, Product.MIN_YEAR, Product.MAX_YEAR);
            double price = parsePrice(priceStr);
            book = new Book(id, name, year, price, author, publisher);
        } catch (InvalidInputException e) {
            throw new InvalidInputException(e.getMessage());
        }

        boolean add = products.add(book);
        assert (add);
        addKeywordsToHashMap(book);
    }

    /**
     * Adds electronic to products list
     *
     * @param id is a unique 6 digit string
     * @param name of product
     * @param yearStr product released
     * @param priceStr of product in dollars CAD
     * @param maker of product string
     * @throws estoresearch.InvalidInputException custom input validation
     * checked exception
     */
    public void addElectronic(String id, String name, String yearStr,
            String priceStr, String maker)
            throws InvalidInputException {

        Electronic electronic;

        if (idExists(id)) {
            throw new InvalidInputException(DUPLICATE_ID);
        }

        try {
            int year = parseUserInt(yearStr, Product.MIN_YEAR, Product.MAX_YEAR);
            double price = parsePrice(priceStr);
            electronic = new Electronic(id, name, year, price, maker);
        } catch (InvalidInputException e) {
            throw new InvalidInputException(e.getMessage());
        }

        boolean add = products.add(electronic);
        assert (add);
        addKeywordsToHashMap(electronic);

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

        int lastIndex = userTokens[0].length() - 1;
        int decimalIndex = userTokens[0].indexOf(".");
        if (decimalIndex != -1 && lastIndex - decimalIndex != DECIMAL_PLACE) {
            throw new InvalidInputException(INVALID_DECIMAL_PLACE);
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
    private int parseUserInt(String userString, int min, int max)
            throws InvalidInputException {
        int userInt = 0;

        String[] userTokens = userString.split("\\s+");
        if (userTokens.length != 1) {
            throw new InvalidInputException(TOO_MANY_NUMBERS);
        }

        try {
            userInt = Integer.parseInt(userString);
        } catch (NumberFormatException e) {
            throw new InvalidInputException(NOT_AN_INTEGER);
        }

        if (userInt < min || userInt > max) {
            throw new InvalidInputException(OUT_OF_RANGE + " " + min + "-"
                    + max);
        }

        return userInt;
    }

    /**
     * Prompt user to search by ID and adds product with corresponding ID
     *
     * @param matchingProducts
     */
    private HashSet<Product> findMatchingIDProduct(String productID)
            throws InvalidInputException {

        HashSet<Product> matchingProducts = new HashSet<>();

        Book book = new Book();

        if (productID.equals("")) {
            return null;
        } else {
            for (Product product : getProducts()) {
                if (productID.equals(product.getId())) {
                    matchingProducts.add(product);
                    break;
                }
            }
        }
        return matchingProducts;
    }

    /**
     * Prompts user to input keywords, then searches products for keywords in
     * names, and adds product to matchingProducts if there is a match
     *
     * @param matchingProducts
     */
    private HashSet<Product> findMatchingKeywordProducts(String keywordSearch) {
        HashSet<Product> matchingProducts = new HashSet<>();

        String[] searchTokens = keywordSearch.split("\\s+");
        ArrayList<HashSet<Integer>> matches = new ArrayList<>();
        HashSet<Integer> ints = new HashSet<>();

        if (keywordSearch.equals("")) {
            return null;
        } else {
            for (String searchToken : searchTokens) {
                if (keywords.containsKey(searchToken)) {
                    matches.add(keywords.get(searchToken));
                }
            }

            if (!matches.isEmpty()) {
                ints = matches.get(0);
            }

            for (HashSet<Integer> match : matches) {
                ints.retainAll(match);
            }

            for (int i : ints) {
                matchingProducts.add(getProducts().get(i));
            }
        }
        return matchingProducts;
    }

    /**
     * Prompts user to input time period and adds products that fall within that
     * time period to the matchingProducts
     *
     * @param matchingProducts
     */
    private HashSet<Product> findMatchingTimePeriodProducts(String startYear,
            String endYear) throws InvalidInputException {
        HashSet<Product> matchingProducts = new HashSet<>();

        try {
            if (startYear.equals("") && endYear.equals("")) {
                return null;
            } else if (startYear.equals("")) {
                for (Product product : getProducts()) {
                    int end = parseUserInt(endYear, Product.MIN_YEAR,
                            Product.MAX_YEAR);

                    if (product.getYear() <= end) {
                        matchingProducts.add(product);
                    }
                }
            } else if (endYear.equals("")) {
                int start = parseUserInt(startYear, Product.MIN_YEAR,
                        Product.MAX_YEAR);

                for (Product product : getProducts()) {
                    if (product.getYear() >= start) {
                        matchingProducts.add(product);
                    }
                }
            } else {
                int end = parseUserInt(endYear, Product.MIN_YEAR,
                        Product.MAX_YEAR);
                int start = parseUserInt(startYear, Product.MIN_YEAR,
                        Product.MAX_YEAR);

                if (start == end) {
                    for (Product product : getProducts()) {
                        if (product.getYear() == start) {
                            matchingProducts.add(product);
                        }
                    }
                } else if (start > end) {
                    throw new InvalidInputException(INVALID_RANGE);
                } else {
                    for (Product product : getProducts()) {
                        if (product.getYear() >= start
                                && product.getYear() <= end) {
                            matchingProducts.add(product);
                        }
                    }
                }
            }
        } catch (InvalidInputException e) {
            throw new InvalidInputException(e.getMessage());
        }
        return matchingProducts;
    }

    /**
     * Formats matching products string
     *
     * @param matchingProducts
     */
    private String matchingProductsToString(HashSet<Product> matchingProducts) {
        String matchingProductsString = "";

        if (matchingProducts.isEmpty()) {
            return "No matches.\n";
        } else if (matchingProducts.size() == 1) {
            matchingProductsString += "Matches:\n";
        } else {
            matchingProductsString += "Matches:\n";
        }
        for (Product finalProduct : matchingProducts) {
            matchingProductsString += finalProduct + "\n";
        }

        return matchingProductsString;
    }

    /**
     * Performs search
     *
     * @param productID user input
     * @param keywords user input
     * @param startYear user input
     * @param endYear user input
     * @return string of matching products
     * @throws estoresearch.InvalidInputException custom input validation
     * checked exception
     */
    public String executeSearch(String productID, String keywords,
            String startYear, String endYear)
            throws InvalidInputException {

        HashSet<Product> productsCopy = new HashSet<>(getProducts());

        ArrayList<HashSet<Product>> matchingProducts = new ArrayList<>();

        try {
            matchingProducts.add(findMatchingIDProduct(productID));
        } catch (InvalidInputException e) {
            throw new InvalidInputException(e.getMessage());
        }

        matchingProducts.add(findMatchingKeywordProducts(keywords));

        try {
            matchingProducts.add(findMatchingTimePeriodProducts(startYear,
                    endYear));
        } catch (InvalidInputException e) {
            throw new InvalidInputException(e.getMessage());
        }

        for (HashSet<Product> matchingProduct : matchingProducts) {
            if (matchingProduct != null) {
                productsCopy.retainAll(matchingProduct);
            }
        }

        return matchingProductsToString(productsCopy);
    }

    /**
     * Saves products to output.txt
     */
    public void saveProducts() {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileOutputStream("output.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Error opening output.txt");
            System.exit(0);
        }

        for (Product product : getProducts()) {
            outputStream.println(product.toString());
        }

        outputStream.close();
    }

    /**
     * Get attribute from line where attribute = "value"
     *
     * @param line of text
     * @return attribute or newline
     */
    private String getAttribute(String line) {
        String[] lineTokens = line.split(" *=");
        if (lineTokens.length > 1) {
            return lineTokens[0];
        } else {
            return "\n";
        }
    }

    /**
     * Gets value from line where attribute = "value"
     *
     * @param line of text
     * @return value string
     */
    private String getValue(String line) {
        String[] lineTokens = line.split("(?<!\\\\)\"");
        if (lineTokens.length == 2) {
            return lineTokens[1];
        } else {
            return "";
        }
    }

    /**
     * Loads products from file in the form of attribute = "value" where
     * products are separated by a blank line
     *
     * @param filename for file filled with products
     */
    public void loadProducts(String filename) {
        Scanner fileInput = null;
        String type = "", productID = "", name = "", authors = "",
                publisher = "", maker = "";
        double price = Product.NO_PRICE;
        int year = 0;

        try {
            fileInput = new Scanner(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            System.out.println(filename + " was not found or couldn't be opened.");
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
                } else if (attribute.equals("\n") || !fileInput.hasNextLine()) {
                    Product product;

                    if (idExists(productID)) {
                        throw new InvalidInputException(DUPLICATE_ID);
                    } else if (type.equals("book")) {
                        product = new Book(productID, name, year, price,
                                authors, publisher);
                        getProducts().add(product);
                        addKeywordsToHashMap(product);
                    } else if (type.equals("electronics")) {
                        product = new Electronic(productID, name, year, price,
                                maker);
                        getProducts().add(product);
                        addKeywordsToHashMap(product);
                    }

                    type = productID = name = authors = publisher = maker = "";
                    price = Product.NO_PRICE;
                    year = 0;
                } else {
                    System.out.println("Invalid input from file");
                    System.exit(0);
                }
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
                type = productID = name = authors = publisher = maker = "";
                price = Product.NO_PRICE;
                year = 0;
            }
        }
        fileInput.close();
    }
}
