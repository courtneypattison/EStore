package estoresearch;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *  GUI for EStoreSearch
 * 
 * @author Courtney Bodi
 */
public class EStoreSearchGUI implements ActionListener, ItemListener {

    private final EStoreSearch eStoreSearch = new EStoreSearch();

    private JPanel cards, authorsPane, publisherPane, makerPane;
    private JTextArea memoDisplay;

    private JTextField productID, name, price, year, authors, publisher, maker,
            productIDSearch, keywordsSearch, startYearSearch, endYearSearch;

    private JButton addButton, searchButton, resetAddButton, resetSearchButton;
    
    public static final int WIDTH = 809;
    public static final int HEIGHT = 500;

    public static final int LINES = 10;
    public static final int CHARS_PER_LINE = 20;

    public static final Insets BORDER_SIZE = new Insets(10, 10, 10, 10);
    public static final Dimension COMPONENT_SIZE = new Dimension(800, 30);

    public static final String ADD = "Add";
    public static final String SEARCH = "Search";
    public static final String QUIT = "Quit";
    
    public static final String BOOK = "Book";
    public static final String ELECTRONIC = "Electronic";
    
    private String productType = BOOK;

    /**
     * Switches between cards
     * 
     * @param e event from card listener
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String menuItemName = (String) e.getActionCommand();

        if (menuItemName.equals(QUIT)) {
            eStoreSearch.saveProducts();
            System.exit(0);
        }

        CardLayout cardLayout = (CardLayout) (cards.getLayout());
        cardLayout.show(cards, menuItemName);
    }

    /**
     * Add menu item to menu
     * 
     * @param menu to be added to
     * @param text to add the to menu item
     */
    private void addMenuItem(JMenu menu, String text) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(this);
        menu.add(menuItem);
    }

    /**
     * Add menu to window
     * 
     * @param frame window to add menu to
     */
    private void addMenu(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Commands");

        addMenuItem(menu, ADD);
        addMenuItem(menu, SEARCH);
        addMenuItem(menu, QUIT);

        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
    }

    /**
     * Style card orange, with a border, and using BorderLayout
     * 
     * @param card panel to be styled
     */
    private void styleCard(JPanel card) {
        card.setBackground(Color.ORANGE);
        card.setBorder(new EmptyBorder(BORDER_SIZE));
        card.setLayout(new BorderLayout());
    }

    /**
     * Set panel as orange and using BoxLayout
     * 
     * @param pane to be styled
     */
    private void styleBoxLayoutPanel(JPanel pane) {
        pane.setBackground(Color.ORANGE);
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
    }

    /**
     * Create a card that welcomes the user to the EStore
     * 
     * @return welcome card
     */
    private JPanel createWelcomeCard() {
        JPanel welcomeCard = new JPanel();
        styleCard(welcomeCard);
        styleBoxLayoutPanel(welcomeCard);

        welcomeCard.add(new JLabel("Welcome to the eStore!"));
        welcomeCard.add(new JLabel("Chooose a command from the \"Commands\""
                + " menu above for adding a product, searching products, or"
                + " quitting the program."));

        return welcomeCard;
    }

    /**
     * Add text field with label to a panel
     * 
     * @param pane to be added to
     * @param text to label the text field with
     * @return a panel containing a label and text field
     */
    private JTextField addLabelledTextField(JPanel pane, String text) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.ORANGE);
        panel.setLayout(new BorderLayout());
        panel.setMaximumSize(COMPONENT_SIZE);

        JLabel label = new JLabel("   " + text);
        JTextField textField = new JTextField(CHARS_PER_LINE);

        panel.add(label, BorderLayout.LINE_START);
        panel.add(textField);

        pane.add(panel);

        return textField;
    }
    
    /**
     * Makes Book or Electronic fields visible or not depending of combo box
     * 
     * @param e event when combo box item toggled
     */
    @Override
    public void itemStateChanged(ItemEvent e) { 
        String comboBoxItemName = (String) e.getItem();

        if (comboBoxItemName.equals(BOOK)) {
            productType = BOOK;
            authorsPane.setVisible(true);
            publisherPane.setVisible(true);
            makerPane.setVisible(false);
        } else {
            productType = ELECTRONIC;
            authorsPane.setVisible(false);
            publisherPane.setVisible(false);
            makerPane.setVisible(true);
        }
    }
    
    /**
     * Create a panel with combo box, labels, text fields, and buttons used to
     * add products to ArrayList of products
     * 
     * @return panel 
     */
    private JPanel createAddInputPane() {
        JPanel addInputPane = new JPanel();
        styleBoxLayoutPanel(addInputPane);
        
        String[] comboBoxItems = {BOOK, ELECTRONIC};
        JComboBox comboBox = new JComboBox(comboBoxItems);
        comboBox.setEditable(false);
        comboBox.addItemListener(this);
        comboBox.setMaximumSize(COMPONENT_SIZE);
        addInputPane.add(comboBox, BorderLayout.PAGE_START); 
        
        productID = addLabelledTextField(addInputPane, "Product ID: ");
        name = addLabelledTextField(addInputPane, "Name: ");
        price = addLabelledTextField(addInputPane, "Price: ");
        year = addLabelledTextField(addInputPane, "Year: ");
        
        
        authorsPane = new JPanel();
        authorsPane.setBackground(Color.ORANGE);
        authorsPane.setLayout(new BorderLayout());
        authorsPane.setMaximumSize(COMPONENT_SIZE);

        JLabel authorsLabel = new JLabel("   Authors: ");
        authors = new JTextField(CHARS_PER_LINE);

        authorsPane.add(authorsLabel, BorderLayout.LINE_START);
        authorsPane.add(authors);

        addInputPane.add(authorsPane);
        
        
        publisherPane = new JPanel();
        publisherPane.setBackground(Color.ORANGE);
        publisherPane.setLayout(new BorderLayout());
        publisherPane.setMaximumSize(COMPONENT_SIZE);

        JLabel publisherLabel = new JLabel("   Publisher: ");
        publisher = new JTextField(CHARS_PER_LINE);

        publisherPane.add(publisherLabel, BorderLayout.LINE_START);
        publisherPane.add(publisher);

        addInputPane.add(publisherPane);
        
        
        makerPane = new JPanel();
        makerPane.setBackground(Color.ORANGE);
        makerPane.setLayout(new BorderLayout());
        makerPane.setMaximumSize(COMPONENT_SIZE);

        JLabel makerLabel = new JLabel("   Publisher: ");
        maker = new JTextField(CHARS_PER_LINE);

        makerPane.add(makerLabel, BorderLayout.LINE_START);
        makerPane.add(maker);

        addInputPane.add(makerPane);

        makerPane.setVisible(false); // default is Book
        
        return addInputPane;
    }
    
    /**
     * Reset the text fields for adding products
     */
    private void resetAdd() {
        productID.setText("");
        name.setText("");
        price.setText("");
        year.setText("");
        authors.setText("");
        publisher.setText("");
        maker.setText("");
    }
    
    /**
     * Add product to EStoreSearch
     */
    private void addProduct() {
        if (productType.equals(BOOK)) {
            try {
                eStoreSearch.addBook(productID.getText(), name.getText(),
                        price.getText(), year.getText(), authors.getText(),
                        publisher.getText());
                memoDisplay.setText("Successfully added book!");
            } catch (InvalidInputException e) {
                memoDisplay.setText(e.getMessage());
            }
        } else {
            try {
                eStoreSearch.addElectronic(productID.getText(), name.getText(),
                        price.getText(), year.getText(), maker.getText());
                memoDisplay.setText("Successfully added electronic!");
            } catch (InvalidInputException e) {
                memoDisplay.setText(e.getMessage());
            }
        }
    }
    
    /**
     * Create a panel of buttons used for adding products
     * 
     * @return panel of buttons used for adding products
     */
    private JPanel createAddButtonPane() {
        JPanel addButtonPane = new JPanel();
        styleBoxLayoutPanel(addButtonPane);

        resetAddButton = new JButton("Reset");
        resetAddButton.addActionListener(e -> resetAdd());
        addButtonPane.add(resetAddButton);

        addButton = new JButton("Add");
        addButton.addActionListener(e -> addProduct());
        addButtonPane.add(addButton);

        return addButtonPane;
    }

    /**
     * Create a card used for adding products
     * 
     * @return a card used for adding products
     */
    private JPanel createAddCard() {
        JPanel addCard = new JPanel();
        styleCard(addCard);
        
        addCard.add(createAddInputPane(), BorderLayout.LINE_START);
        addCard.add(createAddButtonPane(), BorderLayout.LINE_END);

        return addCard;
    }

    /**
     * Create a panel used for inputing search terms
     * 
     * @return a panel used for inputing search terms
     */
    private JPanel createSearchInputPane() {
        JPanel searchInputPane = new JPanel();
        styleBoxLayoutPanel(searchInputPane);

        productIDSearch = addLabelledTextField(searchInputPane, "Product ID: ");
        keywordsSearch = addLabelledTextField(searchInputPane, "Name keywords: ");
        startYearSearch = addLabelledTextField(searchInputPane, "Start year: ");
        endYearSearch = addLabelledTextField(searchInputPane, "End year: ");

        return searchInputPane;
    }

    /**
     * Remove text from search fields
     */
    private void resetSearch() {
        productIDSearch.setText("");
        keywordsSearch.setText("");
        startYearSearch.setText("");
        endYearSearch.setText("");
    }

    /**
     * Perform search using user inputted search terms
     */
    private void performSearch() {
        try {
            String matchingProducts = eStoreSearch.executeSearch(
                    productIDSearch.getText(), keywordsSearch.getText(),
                    startYearSearch.getText(), endYearSearch.getText());
            memoDisplay.setText(matchingProducts);
        } catch (InvalidInputException e) {
            memoDisplay.setText(e.getMessage());
        }
    }

    /**
     * Create panel filled with buttons used for searching
     * 
     * @return panel filled with buttons used for searching
     */
    private JPanel createSearchButtonPane() {
        JPanel searchButtonPane = new JPanel();
        styleBoxLayoutPanel(searchButtonPane);

        resetSearchButton = new JButton("Reset");
        resetSearchButton.addActionListener(e -> resetSearch());
        searchButtonPane.add(resetSearchButton);

        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> performSearch());
        searchButtonPane.add(searchButton);

        return searchButtonPane;
    }

    /**
     * Create card used for searching
     * 
     * @return card used for searching
     */
    private JPanel createSearchCard() {
        JPanel searchCard = new JPanel();
        styleCard(searchCard);

        searchCard.add(createSearchInputPane(), BorderLayout.LINE_START);
        searchCard.add(createSearchButtonPane(), BorderLayout.LINE_END);

        return searchCard;
    }

    /**
     * Add cards to window
     * 
     * @param frame main window
     */
    private void addCards(JFrame frame) {
        cards = new JPanel(new CardLayout());
        cards.add(createWelcomeCard());
        cards.add(createAddCard(), ADD);
        cards.add(createSearchCard(), SEARCH);

        frame.add(cards, BorderLayout.CENTER);
    }

    /**
     * Add a scroll pane used for program output
     * 
     * @param frame main window
     */
    private void addScrollPane(JFrame frame) {
        memoDisplay = new JTextArea(LINES, CHARS_PER_LINE);
        memoDisplay.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(memoDisplay);

        frame.add(scrollPane, BorderLayout.PAGE_END);
    }

    /**
     * Create window and show it
     */
    private void createAndShowGUI() {
        JFrame frame = new JFrame("Car Store");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                eStoreSearch.saveProducts();
                System.exit(0);
            }
        });

        addMenu(frame);
        addCards(frame);
        addScrollPane(frame);

        frame.setVisible(true);
    }

    /**
     * Check for command line arguments and start the GUI
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 1) {
            EStoreSearchGUI gui = new EStoreSearchGUI();
            gui.eStoreSearch.loadProducts(args[0]);

            javax.swing.SwingUtilities.invokeLater(() -> gui.createAndShowGUI());
        } else {
            System.out.println("You must enter a filename when running this program.");
        }
    }
}
