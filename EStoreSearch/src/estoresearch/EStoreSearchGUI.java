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

    private JPanel cards, authorsPane, publisherPane, makerPane, messages,
            searchResults;
    private JTextArea messagesDisplay, searchResultsDisplay;

    private JTextField productID, name, price, year, authors, publisher, maker,
            productIDSearch, keywordsSearch, startYearSearch, endYearSearch;

    private JButton addButton, searchButton, resetAddButton, resetSearchButton;
    
    public static final int LINES = 10;
    public static final int CHARS_PER_LINE = 40;

    public static final Insets BORDER_SIZE = new Insets(10, 10, 10, 10);
    public static final Dimension BETWEEN_BUTTONS = new Dimension(20, 20);
    public static final Dimension BETWEEN_TEXT_FIELDS = new Dimension(20, 20);
    
    public static final int VGAP = 10;
    public static final int HGAP = 10;

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
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menu = new JMenu("Commands");

        addMenuItem(menu, ADD);
        addMenuItem(menu, SEARCH);
        addMenuItem(menu, QUIT);

        menuBar.add(menu);
        
        return menuBar;
    }

    /**
     * Create a card that welcomes the user to the EStore
     * 
     * @return welcome card
     */
    private JPanel createWelcomeCard() {
        JPanel welcomeCard = new JPanel();
        welcomeCard.setLayout(new BoxLayout(welcomeCard, BoxLayout.Y_AXIS));

        welcomeCard.add(new JLabel("Welcome to the eStore!"));
        welcomeCard.add(new JLabel("Chooose a command from the \"Commands\""
                + " menu above for adding a product, searching products, or"
                + " quitting the program."));

        return welcomeCard;
    }
    
    private void styleLabelledTextField(JPanel pane) {
        pane.setLayout(new BorderLayout(HGAP, VGAP));
    }
    
    private void styleButton(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        styleLabelledTextField(panel);

        JLabel label = new JLabel("   " + text);
        JTextField textField = new JTextField(CHARS_PER_LINE);

        panel.add(label, BorderLayout.LINE_START);
        panel.add(textField, BorderLayout.LINE_END);

        pane.add(panel);

        return textField;
    }
    
    /**
     * Create a panel with combo box, labels, text fields, and buttons used to
     * add products to ArrayList of products
     * 
     * @return panel 
     */
    private JPanel createAddInputPane() {
        JPanel addInputPane = new JPanel();
        addInputPane.setLayout(new BoxLayout(addInputPane, BoxLayout.Y_AXIS));
        
        String[] comboBoxItems = {BOOK, ELECTRONIC};
        JComboBox comboBox = new JComboBox(comboBoxItems);
        comboBox.setEditable(false);
        comboBox.addItemListener(this);
        addInputPane.add(comboBox); 
        
        productID = addLabelledTextField(addInputPane, "Product ID: ");
        name = addLabelledTextField(addInputPane, "Name: ");
        price = addLabelledTextField(addInputPane, "Price: ");
        year = addLabelledTextField(addInputPane, "Year: ");
        
        authorsPane = new JPanel();
        styleLabelledTextField(authorsPane);
        JLabel authorsLabel = new JLabel("   Authors: ");
        authors = new JTextField(CHARS_PER_LINE);
        authorsPane.add(authorsLabel, BorderLayout.LINE_START);
        authorsPane.add(authors, BorderLayout.LINE_END);
        addInputPane.add(authorsPane);
        
        publisherPane = new JPanel();
        styleLabelledTextField(publisherPane);
        JLabel publisherLabel = new JLabel("   Publisher: ");
        publisher = new JTextField(CHARS_PER_LINE);
        publisherPane.add(publisherLabel, BorderLayout.LINE_START);
        publisherPane.add(publisher, BorderLayout.LINE_END);
        addInputPane.add(publisherPane);
        
        makerPane = new JPanel();
        styleLabelledTextField(makerPane);
        JLabel makerLabel = new JLabel("   Maker: ");
        maker = new JTextField(CHARS_PER_LINE);
        makerPane.add(makerLabel, BorderLayout.LINE_START);
        makerPane.add(maker, BorderLayout.LINE_END);
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
                        year.getText(), price.getText(), authors.getText(),
                        publisher.getText());
                messagesDisplay.setText("Successfully added book!");
            } catch (InvalidInputException e) {
                messagesDisplay.setText(e.getMessage());
            }
        } else {
            try {
                eStoreSearch.addElectronic(productID.getText(), name.getText(),
                        year.getText(), price.getText(), maker.getText());
                messagesDisplay.setText("Successfully added electronic!");
            } catch (InvalidInputException e) {
                messagesDisplay.setText(e.getMessage());
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
        addButtonPane.setLayout(new BoxLayout(addButtonPane, BoxLayout.Y_AXIS));

        resetAddButton = new JButton("Reset");
        styleButton(resetAddButton);
        addButtonPane.add(Box.createRigidArea(BETWEEN_BUTTONS));
        resetAddButton.addActionListener(e -> resetAdd());
        addButtonPane.add(resetAddButton);

        addButton = new JButton("Add");
        styleButton(addButton);
        addButtonPane.add(Box.createRigidArea(BETWEEN_BUTTONS));
        addButton.addActionListener(e -> addProduct());
        addButtonPane.add(addButton);

        return addButtonPane;
    }
    
    /**
     * Create a panel for messages
     * 
     */
    private JPanel createMessagesPane() {
        messages = new JPanel();
        messages.setLayout(new BoxLayout(messages, BoxLayout.Y_AXIS));
        
        messagesDisplay = new JTextArea(LINES, CHARS_PER_LINE);
        messagesDisplay.setEditable(false);

        JLabel messagesLabel = new JLabel("Messages");
        messagesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JScrollPane scrollPane = new JScrollPane(messagesDisplay);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        messages.add(messagesLabel);
        messages.add(scrollPane);

        return messages;
    }

    /**
     * Create a card used for adding products
     */
    private JPanel createAddCard() {
        JPanel addCard = new JPanel();
        addCard.setLayout(new BorderLayout(HGAP, VGAP));

        addCard.add(new JLabel("Adding a product"), BorderLayout.PAGE_START);
        addCard.add(createAddInputPane(), BorderLayout.CENTER);
        addCard.add(createAddButtonPane(), BorderLayout.LINE_END);
        addCard.add(createMessagesPane(), BorderLayout.PAGE_END);

        return addCard;
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
            searchResultsDisplay.setText(matchingProducts);
        } catch (InvalidInputException e) {
            searchResultsDisplay.setText(e.getMessage());
        }
    }
    
    /**
     * Create a panel used for inputing search terms
     * 
     * @return a panel used for inputing search terms
     */
    private JPanel createSearchInputPane() {
        JPanel searchInputPane = new JPanel();
        searchInputPane.setLayout(new BoxLayout(searchInputPane, BoxLayout.Y_AXIS));

        productIDSearch = addLabelledTextField(searchInputPane, "Product ID: ");
        keywordsSearch = addLabelledTextField(searchInputPane, "Name keywords: ");
        startYearSearch = addLabelledTextField(searchInputPane, "Start year: ");
        endYearSearch = addLabelledTextField(searchInputPane, "End year: ");

        return searchInputPane;
    }

    /**
     * Create panel filled with buttons used for searching
     * 
     * @return panel filled with buttons used for searching
     */
    private JPanel createSearchButtonPane() {
        JPanel searchButtonPane = new JPanel();
        searchButtonPane.setLayout(new BoxLayout(searchButtonPane, BoxLayout.Y_AXIS));

        resetSearchButton = new JButton("Reset");
        styleButton(resetSearchButton);
        resetSearchButton.addActionListener(e -> resetSearch());
        searchButtonPane.add(Box.createRigidArea(BETWEEN_BUTTONS));
        searchButtonPane.add(resetSearchButton);

        searchButton = new JButton("Search");
        styleButton(searchButton);
        searchButton.addActionListener(e -> performSearch());
        searchButtonPane.add(Box.createRigidArea(BETWEEN_BUTTONS));
        searchButtonPane.add(searchButton);

        return searchButtonPane;
    }
    
    /**
     * Create a panel to show search results
     */
    private JPanel createSearchResultsPane() {
        searchResults = new JPanel();
        searchResults.setLayout(new BoxLayout(searchResults, BoxLayout.Y_AXIS));
        
        searchResultsDisplay = new JTextArea(LINES, CHARS_PER_LINE);
        searchResultsDisplay.setEditable(false);

        JLabel searchResultsLabel = new JLabel("Search results");
        searchResultsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JScrollPane scrollPane = new JScrollPane(searchResultsDisplay);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchResults.add(searchResultsLabel);
        searchResults.add(scrollPane);
        
        return searchResults;
    }

    /**
     * Create card used for searching
     * 
     * @return card used for searching
     */
    private JPanel createSearchCard() {
        JPanel searchCard = new JPanel();
        searchCard.setLayout(new BorderLayout(HGAP, VGAP));

        searchCard.add(new JLabel("Searching products"), BorderLayout.PAGE_START);
        searchCard.add(createSearchInputPane(), BorderLayout.CENTER);
        searchCard.add(createSearchButtonPane(), BorderLayout.LINE_END);
        searchCard.add(createSearchResultsPane(), BorderLayout.PAGE_END);

        return searchCard;
    }

    /**
     * Creates cards and adds them to cards panel
     * 
     * @ return a set of cards
     */
    private JPanel createCards() {
        cards = new JPanel(new CardLayout());
        cards.setBorder(new EmptyBorder(BORDER_SIZE));
        
        // Add cards that correspond to menu items
        cards.add(createWelcomeCard());
        cards.add(createAddCard(), ADD);
        cards.add(createSearchCard(), SEARCH);

        return cards;
    }

    /**
     * Create window and show it
     */
    private void createAndShowGUI() {
        JFrame frame = new JFrame("eStore");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLayout(new BorderLayout(HGAP, VGAP));
        
        // Save products before exiting
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                eStoreSearch.saveProducts();
                System.exit(0);
            }
        });

        frame.add(createMenuBar(), BorderLayout.PAGE_START);
        frame.add(createCards(), BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Check for command line arguments and start the GUI
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        EStoreSearchGUI gui = new EStoreSearchGUI();
        
        if (args.length == 1) {
            gui.eStoreSearch.loadProducts(args[0]);
        }
        
        javax.swing.SwingUtilities.invokeLater(() -> gui.createAndShowGUI());
    }
}
