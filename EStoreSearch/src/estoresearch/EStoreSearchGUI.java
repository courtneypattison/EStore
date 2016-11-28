package estoresearch;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Courtney Bodi
 */
public class EStoreSearchGUI implements ActionListener {

    private EStoreSearch eStoreSearch = new EStoreSearch();

    private JPanel cards;
    private JTextArea memoDisplay;

    private JTextField productIDSearch,
            keywordsSearch,
            startYearSearch,
            endYearSearch;

    private JButton addButton, searchButton, resetAddButton, resetSearchButton;

    public static final int WIDTH = 809;
    public static final int HEIGHT = 500;

    public static final int LINES = 10;
    public static final int CHARS_PER_LINE = 20;

    public static final Insets BORDER_SIZE = new Insets(10, 10, 10, 10);
    public static final Dimension COMPONENT_SIZE = new Dimension(600, 40);

    public static final String ADD = "Add";
    public static final String SEARCH = "Search";
    public static final String QUIT = "Quit";

    @Override
    public void actionPerformed(ActionEvent e) {
        String menuItemName = (String) e.getActionCommand();

        if (menuItemName.equals(QUIT)) {
            for (Product product : eStoreSearch.getProducts()) {
                System.out.println(product.toString());
            }
            eStoreSearch.saveProducts();
            System.exit(0);
        }

        CardLayout cardLayout = (CardLayout) (cards.getLayout());
        cardLayout.show(cards, menuItemName);
    }

    private void addMenuItem(JMenu menu, String text) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(this);
        menu.add(menuItem);
    }

    private void addMenu(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Commands");

        addMenuItem(menu, ADD);
        addMenuItem(menu, SEARCH);
        addMenuItem(menu, QUIT);

        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
    }

    private void styleCard(JPanel card) {
        card.setBackground(Color.ORANGE);
        card.setBorder(new EmptyBorder(BORDER_SIZE));
        card.setLayout(new BorderLayout());
    }

    private void styleBoxLayoutPanel(JPanel pane) {
        pane.setBackground(Color.ORANGE);
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
    }

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

    private JPanel createAddCard() {
        JPanel addCard = new JPanel();
        styleCard(addCard);

        return addCard;
    }

    private JPanel createSearchInputPane() {
        JPanel inputPane = new JPanel();
        styleBoxLayoutPanel(inputPane);

        productIDSearch = addLabelledTextField(inputPane, "Product ID: ");
        keywordsSearch = addLabelledTextField(inputPane, "Name keywords: ");
        startYearSearch = addLabelledTextField(inputPane, "Start year: ");
        endYearSearch = addLabelledTextField(inputPane, "End year: ");

        return inputPane;
    }

    private void resetSearch() {
        productIDSearch.setText("");
        keywordsSearch.setText("");
        startYearSearch.setText("");
        endYearSearch.setText("");
    }

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

    private JPanel createSearchButtonPane() {
        JPanel buttonPane = new JPanel();
        styleBoxLayoutPanel(buttonPane);

        resetSearchButton = new JButton("Reset");
        resetSearchButton.addActionListener(e -> resetSearch());
        buttonPane.add(resetSearchButton);

        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> performSearch());
        buttonPane.add(searchButton);

        return buttonPane;
    }

    private JPanel createSearchCard() {
        JPanel searchCard = new JPanel();
        styleCard(searchCard);

        searchCard.add(createSearchInputPane(), BorderLayout.LINE_START);
        searchCard.add(createSearchButtonPane(), BorderLayout.LINE_END);

        return searchCard;
    }

    private void addCards(JFrame frame) {
        cards = new JPanel(new CardLayout());
        cards.add(createWelcomeCard());
        cards.add(createAddCard(), ADD);
        cards.add(createSearchCard(), SEARCH);

        frame.add(cards, BorderLayout.CENTER);
    }

    private void addScrollPane(JFrame frame) {
        memoDisplay = new JTextArea(LINES, CHARS_PER_LINE);
        memoDisplay.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(memoDisplay);

        frame.add(scrollPane, BorderLayout.PAGE_END);
    }

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
