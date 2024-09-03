package stock;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;

/**
 * The GUI view that handles the operation of displaying the GUI.
 */
public class GuiView extends JFrame implements IGuiView {
  private CardLayout cardLayout;
  private JPanel homePanel, newPortfolioPanel, optionsPanel, addStockPanel,
          removeStockPanel, getValuePanel, compPanel, retrievePanel;
  private JButton button, createButton, clearButton, addStockButton, removeStockButton, getValueButton,
          getCompositionButton, savePortfolioButton, homeButton, homeButton2, homeButton3,
          addButton, homeButton4, removeButton, homeButton5, valueButton, homeButton6, compButton,
          clearButton2, clearButton3, clearButton4, clearButton5, retrieveButton, retrieveEnter,
          homeButton7;
  private JTextField nameField, symbolField, sharesField, monthField, dayField, yearField,
          symbolFieldAdd, sharesFieldAdd, monthFieldAdd, dayFieldAdd, yearFieldAdd,
          symbolFieldRemove, sharesFieldRemove, monthFieldRemove, dayFieldRemove, yearFieldRemove,
          monthFieldValue, dayFieldValue, yearFieldValue, monthFieldComp, dayFieldComp, yearFieldComp,
          retrieveField;

  /**
   * Constructs the GUI view.
   */
  public GuiView() {
    // Initialize the frame
    setTitle("GUI View");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 300);
    setLocation(200, 200);
    cardLayout = new CardLayout();
    setLayout(cardLayout);

    setHomePanel();
    setNewPortfolioPanel();
    setOptionsPanel();
    setAddStockPanel();
    setRemoveStockPanel();
    setGetValuePanel();
    setCompPanel();
    setRetrievePanel();

    // Add panels to the frame
    add(homePanel, "HomePanel");
    add(newPortfolioPanel, "PortfolioPanel");
    add(optionsPanel, "OptionsPanel");
    add(addStockPanel, "AddStockPanel");
    add(removeStockPanel, "RemoveStockPanel");
    add(getValuePanel, "GetValuePanel");
    add(compPanel, "CompPanel");
    add(retrievePanel, "RetrievePanel");

    showHome();
    setVisible(true);

  }

  private JButton setHomeButton() {
    ImageIcon originalIcon = new ImageIcon(getClass().getResource("/home.jpeg"));
    Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    ImageIcon scaledIcon = new ImageIcon(scaledImage);

    JButton homeButton = new JButton(scaledIcon);
    homeButton.setToolTipText("Go to Home");

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.NORTHWEST;
    gbc.insets = new Insets(10, 10, 0, 0);

    return homeButton;

  }

  private void setHomePanel() {
    homePanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(2, 2, 2, 2);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel label = new JLabel("Create Portfolio");
    gbc.gridx = 0;
    gbc.gridy = 0;
    homePanel.add(label, gbc);

    button = new JButton("+");
    button.setPreferredSize(new Dimension(50, 50));
    gbc.gridx = 1;
    gbc.gridy = 0;
    homePanel.add(button, gbc);

    retrieveButton = new JButton("Retrieve Portfolio");
    gbc.gridx = 0;
    gbc.gridy = 1;
    homePanel.add(retrieveButton, gbc);
  }


  private void setNewPortfolioPanel() {
    newPortfolioPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    homeButton2 = setHomeButton();
    newPortfolioPanel.add(homeButton2, gbc);

    gbc.insets = new Insets(6, 6, 6, 6);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel nameLabel = new JLabel("Name:");
    nameLabel.setToolTipText("Enter the name of the stock.");
    gbc.gridx = 0;
    gbc.gridy = 1;
    newPortfolioPanel.add(nameLabel, gbc);

    nameField = new JTextField(10);
    nameField.setToolTipText("Enter the ticker symbol of the stock.");
    gbc.gridx = 1;
    gbc.gridy = 1;
    newPortfolioPanel.add(nameField, gbc);

    JLabel symbolLabel = new JLabel("Symbol:");
    symbolLabel.setToolTipText("Enter the ticker symbol of the stock.");
    gbc.gridx = 0;
    gbc.gridy = 2;
    newPortfolioPanel.add(symbolLabel, gbc);

    symbolField = new JTextField(10);
    symbolField.setToolTipText("Enter the ticker symbol of the stock.");
    gbc.gridx = 1;
    gbc.gridy = 2;
    newPortfolioPanel.add(symbolField, gbc);

    JLabel sharesLabel = new JLabel("Shares:");
    sharesLabel.setToolTipText("Enter the number of shares.");
    gbc.gridx = 0;
    gbc.gridy = 3;
    newPortfolioPanel.add(sharesLabel, gbc);

    sharesField = new JTextField(10);
    sharesField.setToolTipText("Enter the number of shares.");
    gbc.gridx = 1;
    gbc.gridy = 3;
    newPortfolioPanel.add(sharesField, gbc);

    JLabel dateLabel = new JLabel("Date:");
    dateLabel.setToolTipText("Enter the date in MM/DD/YYYY format.");
    gbc.gridx = 0;
    gbc.gridy = 4;
    newPortfolioPanel.add(dateLabel, gbc);

    JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
    monthField = new JTextField(2);
    monthField.setToolTipText("Month (MM)");
    datePanel.add(monthField);
    datePanel.add(new JLabel("/"));
    dayField = new JTextField(2);
    dayField.setToolTipText("Day (DD)");
    datePanel.add(dayField);
    datePanel.add(new JLabel("/"));
    yearField = new JTextField(4);
    yearField.setToolTipText("Year (YYYY)");
    datePanel.add(yearField);
    gbc.gridx = 1;
    gbc.gridy = 4;
    newPortfolioPanel.add(datePanel, gbc);

    createButton = new JButton("Create");
    gbc.gridx = 1;
    gbc.gridy = 5;
    gbc.anchor = GridBagConstraints.CENTER;
    createButton.setForeground(new Color(0, 128, 0));
    newPortfolioPanel.add(createButton, gbc);

    clearButton = new JButton("Clear");
    gbc.gridx = 1;
    gbc.gridy = 6;
    gbc.anchor = GridBagConstraints.CENTER;
    clearButton.setForeground(Color.red);
    newPortfolioPanel.add(clearButton, gbc);
  }

  public void showHome() {
    cardLayout.show(getContentPane(), "HomePanel");
  }

  public void showPortfolioPanel() {
    cardLayout.show(getContentPane(), "PortfolioPanel");
  }

  public void showOptionsPanel() {
    cardLayout.show(getContentPane(), "OptionsPanel");
  }

  @Override
  public void showSuccessfulRetrieve() {
    JOptionPane.showMessageDialog(newPortfolioPanel, "Portfolio retrieved successfully.",
            "Success", JOptionPane.INFORMATION_MESSAGE);
    showOptionsPanel();
  }


  private void setOptionsPanel() {
    optionsPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    homeButton = setHomeButton();
    optionsPanel.add(homeButton, gbc);

    // Create buttons
    addStockButton = new JButton("Add Stock");
    removeStockButton = new JButton("Remove Stock");
    getValueButton = new JButton("Get Value");
    getCompositionButton = new JButton("Get Composition");
    savePortfolioButton = new JButton("Save Portfolio");

    // Common settings for all buttons
    gbc.insets = new Insets(15, 0, 10, 0); // Add some space between buttons
    gbc.gridx = 1;

    // Add buttons to the panel with vertical centering
    gbc.gridy = 0;
    optionsPanel.add(addStockButton, gbc);

    gbc.gridy = 1;
    optionsPanel.add(removeStockButton, gbc);

    gbc.gridy = 2;
    optionsPanel.add(getValueButton, gbc);

    gbc.gridy = 3;
    optionsPanel.add(getCompositionButton, gbc);

    gbc.gridy = 4;
    optionsPanel.add(savePortfolioButton, gbc);

    // Add vertical glue to center buttons vertically
    gbc.weighty = 1.0; // Request extra vertical space
    gbc.gridy = 5;
    gbc.anchor = GridBagConstraints.PAGE_END;
    optionsPanel.add(Box.createVerticalGlue(), gbc);

    // Add the optionsPanel to the main panel or frame
    add(optionsPanel, BorderLayout.CENTER);

  }

  public void addFeatures(Features features) {
    button.addActionListener(evt -> features.createPortfolio());
    clearButton.addActionListener(evt -> features.clear());
    clearButton2.addActionListener(evt -> features.clearAdd());
    clearButton3.addActionListener(evt -> features.clearRemove());
    clearButton4.addActionListener(evt -> features.clearValue());
    clearButton5.addActionListener(evt -> features.clearComp());
    createButton.addActionListener(evt -> features.submitPortfolio(
            nameField.getText(),
            symbolField.getText(),
            sharesField.getText(),
            monthField.getText(),
            dayField.getText(),
            yearField.getText()));
    addStockButton.addActionListener(evt -> features.showAddStock());
    addButton.addActionListener(evt -> features.addStock(
            symbolFieldAdd.getText(),
            sharesFieldAdd.getText(),
            monthFieldAdd.getText(),
            dayFieldAdd.getText(),
            yearFieldAdd.getText()
    ));
    removeStockButton.addActionListener(evt -> features.showRemoveStock());
    removeButton.addActionListener(evt -> features.removeStock(
            symbolFieldRemove.getText(),
            sharesFieldRemove.getText(),
            monthFieldRemove.getText(),
            dayFieldRemove.getText(),
            yearFieldRemove.getText()
    ));
    getValueButton.addActionListener(evt -> features.showGetValue());
    valueButton.addActionListener(evt -> features.getValue(
            monthFieldValue.getText(),
            dayFieldValue.getText(),
            yearFieldValue.getText()
    ));
    getCompositionButton.addActionListener(evt -> features.showComposition());
    compButton.addActionListener(evt -> features.getComposition(
            monthFieldComp.getText(),
            dayFieldComp.getText(),
            yearFieldComp.getText()
    ));
    savePortfolioButton.addActionListener(evt -> features.savePortfolio());
    homeButton.addActionListener(evt -> features.goHome());
    homeButton2.addActionListener(evt -> features.goHome());
    homeButton3.addActionListener(evt -> features.goHome());
    homeButton4.addActionListener(evt -> features.goHome());
    homeButton5.addActionListener(evt -> features.goHome());
    homeButton6.addActionListener(evt -> features.goHome());
    homeButton7.addActionListener(evt -> features.goHome());
    retrieveButton.addActionListener(evt -> features.showPortfolio());
    retrieveEnter.addActionListener(evt -> features.retrievePortfolio(
            retrieveField.getText()
    ));
  }

  public void showSuccessfulPortfolio(String name, String symbol, String shares, String month,
                                      String day, String year) {
    JOptionPane.showMessageDialog(newPortfolioPanel, "Portfolio created successfully.\nName: "
            + name + "\nSymbol: " + symbol + "\nShares: " + shares + "\nDate: " + month + "/" + day
            + "/" + year, "Success", JOptionPane.INFORMATION_MESSAGE);
    showOptionsPanel();
  }

  @Override
  public void showSave() {
    JOptionPane.showMessageDialog(newPortfolioPanel, "Portfolio was saved successfully."
            , "Success", JOptionPane.INFORMATION_MESSAGE);
    showHome();
  }

  @Override
  public void showInvalidPortfolio() {
    JOptionPane.showMessageDialog(newPortfolioPanel, "Portfolio doesn't exist." +
                    " Please check spelling"
            , "Success", JOptionPane.INFORMATION_MESSAGE);
    showHome();
  }

  @Override
  public void showInvalidSymbol() {
    JOptionPane.showMessageDialog(newPortfolioPanel, "Stock symbol doesn't exist.",
            "Error", JOptionPane.ERROR_MESSAGE);
    showPortfolioPanel();
  }

  @Override
  public void showInvalidShares() {
    JOptionPane.showMessageDialog(newPortfolioPanel, "Invalid number of shares. " +
            "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
    showPortfolioPanel();
  }

  @Override
  public void showInvalidDate() {
    JOptionPane.showMessageDialog(newPortfolioPanel, "This date does not exist or is a " +
            "weekend/holiday. Please enter a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
    showPortfolioPanel();
  }

  public void showInvalidSymbolRemove() {
    JOptionPane.showMessageDialog(newPortfolioPanel, "Stock symbol doesn't exist " +
                    "in the portfolio.",
            "Error", JOptionPane.ERROR_MESSAGE);
    showRemoveStockPanel();
  }

  public void showInvalidDateRemove() {
    JOptionPane.showMessageDialog(newPortfolioPanel, "This date does not exist, is a " +
            "weekend/holiday, or you haven't added a stock on this date." +
            " Please enter a valid date.", "Error", JOptionPane.ERROR_MESSAGE);

    showRemoveStockPanel();
  }

  public void showInvalidSharesRemove() {
    JOptionPane.showMessageDialog(newPortfolioPanel, "Invalid number of shares. " +
            "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
    showRemoveStockPanel();
  }

  public void showInvalidDateValue() {
    JOptionPane.showMessageDialog(newPortfolioPanel, "This date does not exist or is in" +
            " the future. Please enter a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
    showGetValuePanel();
  }

  @Override
  public void showInvalidDateComp() {
    JOptionPane.showMessageDialog(newPortfolioPanel, "This date does not exist or is in" +
            " the future. Please enter a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
    showCompositionPanel();
  }


  public void clearFields() {
    nameField.setText("");
    symbolField.setText("");
    sharesField.setText("");
    monthField.setText("");
    dayField.setText("");
    yearField.setText("");
  }

  public void clearAddFields() {
    symbolFieldAdd.setText("");
    sharesFieldAdd.setText("");
    monthFieldAdd.setText("");
    dayFieldAdd.setText("");
    yearFieldAdd.setText("");
  }

  public void clearRemoveFields() {
    symbolFieldRemove.setText("");
    sharesFieldRemove.setText("");
    monthFieldRemove.setText("");
    dayFieldRemove.setText("");
    yearFieldRemove.setText("");
  }

  public void clearValueFields() {
    monthFieldValue.setText("");
    dayFieldValue.setText("");
    yearFieldValue.setText("");
  }

  public void clearCompFields() {
    monthFieldComp.setText("");
    dayFieldComp.setText("");
    yearFieldComp.setText("");
  }

  @Override
  public void showRetrieve() {
    cardLayout.show(getContentPane(), "RetrievePanel");
  }

  @Override
  public void showInvalidSymbolAdd() {
    JOptionPane.showMessageDialog(newPortfolioPanel, "Stock symbol doesn't exist.",
            "Error", JOptionPane.ERROR_MESSAGE);
    showAddStockPanel();
  }

  @Override
  public void showInvalidDateAdd() {
    JOptionPane.showMessageDialog(newPortfolioPanel, "This date does not exist or is a " +
            "weekend/holiday. Please enter a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
    showAddStockPanel();

  }

  @Override
  public void showInvalidSharesAdd() {
    JOptionPane.showMessageDialog(newPortfolioPanel, "Invalid number of shares. " +
            "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
    showAddStockPanel();
  }

  @Override
  public void showCompositionPanel() {
    cardLayout.show(getContentPane(), "CompPanel");
  }

  @Override
  public void showSuccessfulComp(Map<String, Double> composition) {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, Double> entry : composition.entrySet()) {
      sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
    }
    JOptionPane.showMessageDialog(newPortfolioPanel, sb.toString(), "Composition", JOptionPane.INFORMATION_MESSAGE);
    showOptionsPanel();
  }

  private void setAddStockPanel() {
    addStockPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    homeButton3 = setHomeButton();
    addStockPanel.add(homeButton3, gbc);

    gbc.insets = new Insets(6, 6, 6, 6);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel symbolLabel = new JLabel("Symbol:");
    symbolLabel.setToolTipText("Enter the ticker symbol of the stock.");
    gbc.gridx = 0;
    gbc.gridy = 2;
    addStockPanel.add(symbolLabel, gbc);

    symbolFieldAdd = new JTextField(10);
    symbolFieldAdd.setToolTipText("Enter the ticker symbol of the stock.");
    gbc.gridx = 1;
    gbc.gridy = 2;
    addStockPanel.add(symbolFieldAdd, gbc);

    JLabel sharesLabel = new JLabel("Shares to Add:");
    sharesLabel.setToolTipText("Enter the number of shares.");
    gbc.gridx = 0;
    gbc.gridy = 3;
    addStockPanel.add(sharesLabel, gbc);

    sharesFieldAdd = new JTextField(10);
    sharesFieldAdd.setToolTipText("Enter the number of shares.");
    gbc.gridx = 1;
    gbc.gridy = 3;
    addStockPanel.add(sharesFieldAdd, gbc);

    JLabel dateLabel = new JLabel("Date:");
    dateLabel.setToolTipText("Enter the date in MM/DD/YYYY format.");
    gbc.gridx = 0;
    gbc.gridy = 4;
    addStockPanel.add(dateLabel, gbc);

    JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
    monthFieldAdd = new JTextField(2);
    monthFieldAdd.setToolTipText("Month (MM)");
    datePanel.add(monthFieldAdd);
    datePanel.add(new JLabel("/"));
    dayFieldAdd = new JTextField(2);
    dayFieldAdd.setToolTipText("Day (DD)");
    datePanel.add(dayFieldAdd);
    datePanel.add(new JLabel("/"));
    yearFieldAdd = new JTextField(4);
    yearFieldAdd.setToolTipText("Year (YYYY)");
    datePanel.add(yearFieldAdd);
    gbc.gridx = 1;
    gbc.gridy = 4;
    addStockPanel.add(datePanel, gbc);

    addButton = new JButton("Add");
    gbc.gridx = 1;
    gbc.gridy = 5;
    gbc.anchor = GridBagConstraints.CENTER;
    addButton.setForeground(new Color(0, 128, 0));
    addStockPanel.add(addButton, gbc);

    clearButton2 = new JButton("Clear");
    gbc.gridx = 1;
    gbc.gridy = 6;
    gbc.anchor = GridBagConstraints.CENTER;
    clearButton2.setForeground(Color.red);
    addStockPanel.add(clearButton2, gbc);
  }

  public void showAddStockPanel() {
    cardLayout.show(getContentPane(), "AddStockPanel");
  }

  public void showSuccessfulAdd(String name, String symbol, String shares, String month,
                                String day, String year) {
    JOptionPane.showMessageDialog(newPortfolioPanel, "Stock added successfully.\nName: "
            + name + "\nSymbol: " + symbol.toUpperCase() + "\nShares Added: " + shares + "\nDate: " + month + "/" + day
            + "/" + year, "Success", JOptionPane.INFORMATION_MESSAGE);
    showOptionsPanel();
  }

  private void setRemoveStockPanel() {
    removeStockPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    homeButton4 = setHomeButton();
    removeStockPanel.add(homeButton4, gbc);

    gbc.insets = new Insets(6, 6, 6, 6);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel symbolLabel = new JLabel("Symbol:");
    symbolLabel.setToolTipText("Enter the ticker symbol of the stock.");
    gbc.gridx = 0;
    gbc.gridy = 2;
    removeStockPanel.add(symbolLabel, gbc);

    symbolFieldRemove = new JTextField(10);
    symbolFieldRemove.setToolTipText("Enter the ticker symbol of the stock.");
    gbc.gridx = 1;
    gbc.gridy = 2;
    removeStockPanel.add(symbolFieldRemove, gbc);

    JLabel sharesLabel = new JLabel("Shares to Remove:");
    sharesLabel.setToolTipText("Enter the number of shares.");
    gbc.gridx = 0;
    gbc.gridy = 3;
    removeStockPanel.add(sharesLabel, gbc);

    sharesFieldRemove = new JTextField(10);
    sharesFieldRemove.setToolTipText("Enter the number of shares.");
    gbc.gridx = 1;
    gbc.gridy = 3;
    removeStockPanel.add(sharesFieldRemove, gbc);

    JLabel dateLabel = new JLabel("Date:");
    dateLabel.setToolTipText("Enter the date in MM/DD/YYYY format.");
    gbc.gridx = 0;
    gbc.gridy = 4;
    removeStockPanel.add(dateLabel, gbc);

    JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
    monthFieldRemove = new JTextField(2);
    monthFieldRemove.setToolTipText("Month (MM)");
    datePanel.add(monthFieldRemove);
    datePanel.add(new JLabel("/"));
    dayFieldRemove = new JTextField(2);
    dayFieldRemove.setToolTipText("Day (DD)");
    datePanel.add(dayFieldRemove);
    datePanel.add(new JLabel("/"));
    yearFieldRemove = new JTextField(4);
    yearFieldRemove.setToolTipText("Year (YYYY)");
    datePanel.add(yearFieldRemove);
    gbc.gridx = 1;
    gbc.gridy = 4;
    removeStockPanel.add(datePanel, gbc);

    removeButton = new JButton("Remove");
    gbc.gridx = 1;
    gbc.gridy = 5;
    gbc.anchor = GridBagConstraints.CENTER;
    removeButton.setForeground(new Color(0, 128, 0));
    removeStockPanel.add(removeButton, gbc);

    clearButton3 = new JButton("Clear");
    gbc.gridx = 1;
    gbc.gridy = 6;
    gbc.anchor = GridBagConstraints.CENTER;
    clearButton3.setForeground(Color.red);
    removeStockPanel.add(clearButton3, gbc);
  }

  @Override
  public void showRemoveStockPanel() {
    cardLayout.show(getContentPane(), "RemoveStockPanel");
  }

  public void showSuccessfulRemove(String name, String symbol, String shares, String month,
                                   String day, String year) {
    JOptionPane.showMessageDialog(newPortfolioPanel, "Stock removed successfully.\nName: "
            + name + "\nSymbol: " + symbol.toUpperCase() + "\nShares Removed: " + shares + "\nDate: " + month + "/" + day
            + "/" + year, "Success", JOptionPane.INFORMATION_MESSAGE);
    showOptionsPanel();
  }

  private void setGetValuePanel() {
    getValuePanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    homeButton5 = setHomeButton();
    getValuePanel.add(homeButton5, gbc);

    gbc.insets = new Insets(6, 6, 6, 6);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel dateLabel = new JLabel("Date:");
    dateLabel.setToolTipText("Enter the date in MM/DD/YYYY format.");
    gbc.gridx = 0;
    gbc.gridy = 1;
    getValuePanel.add(dateLabel, gbc);

    JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
    monthFieldValue = new JTextField(2);
    monthFieldValue.setToolTipText("Month (MM)");
    datePanel.add(monthFieldValue);
    datePanel.add(new JLabel("/"));
    dayFieldValue = new JTextField(2);
    dayFieldValue.setToolTipText("Day (DD)");
    datePanel.add(dayFieldValue);
    datePanel.add(new JLabel("/"));
    yearFieldValue = new JTextField(4);
    yearFieldValue.setToolTipText("Year (YYYY)");
    datePanel.add(yearFieldValue);
    gbc.gridx = 1;
    gbc.gridy = 1;
    getValuePanel.add(datePanel, gbc);

    valueButton = new JButton("Get Value");
    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    valueButton.setForeground(new Color(0, 128, 0));
    getValuePanel.add(valueButton, gbc);

    clearButton4 = new JButton("Clear");
    gbc.gridx = 1;
    gbc.gridy = 3;
    gbc.anchor = GridBagConstraints.CENTER;
    clearButton4.setForeground(Color.red);
    getValuePanel.add(clearButton4, gbc);
  }

  public void showGetValuePanel() {
    cardLayout.show(getContentPane(), "GetValuePanel");
  }

  @Override
  public void showSuccessfulValue(String name, Double value, String month, String day, String year) {
    JOptionPane.showMessageDialog(newPortfolioPanel, "The value of the portfolio " + name + " on "
            + month + "/" + day + "/" + year + " is $" + String.format("%.2f", value), "Success", JOptionPane.INFORMATION_MESSAGE);
    showOptionsPanel();
  }

  private void setCompPanel() {
    compPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    homeButton6 = setHomeButton();
    compPanel.add(homeButton6, gbc);

    gbc.insets = new Insets(6, 6, 6, 6);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel dateLabel = new JLabel("Date:");
    dateLabel.setToolTipText("Enter the date in MM/DD/YYYY format.");
    gbc.gridx = 0;
    gbc.gridy = 1;
    compPanel.add(dateLabel, gbc);

    JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
    monthFieldComp = new JTextField(2);
    monthFieldComp.setToolTipText("Month (MM)");
    datePanel.add(monthFieldComp);
    datePanel.add(new JLabel("/"));
    dayFieldComp = new JTextField(2);
    dayFieldComp.setToolTipText("Day (DD)");
    datePanel.add(dayFieldComp);
    datePanel.add(new JLabel("/"));
    yearFieldComp = new JTextField(4);
    yearFieldComp.setToolTipText("Year (YYYY)");
    datePanel.add(yearFieldComp);
    gbc.gridx = 1;
    gbc.gridy = 1;
    compPanel.add(datePanel, gbc);

    compButton = new JButton("Get Composition");
    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    compButton.setForeground(new Color(0, 128, 0));
    compPanel.add(compButton, gbc);

    clearButton5 = new JButton("Clear");
    gbc.gridx = 1;
    gbc.gridy = 6;
    gbc.anchor = GridBagConstraints.CENTER;
    clearButton5.setForeground(Color.red);
    compPanel.add(clearButton5, gbc);
  }

  // label saying enter name of portfolio and text field to enter
  //button to enter
  private void setRetrievePanel() {
    retrievePanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    homeButton7 = setHomeButton();
    retrievePanel.add(homeButton7, gbc);

    gbc.insets = new Insets(6, 6, 6, 6);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel nameLabel = new JLabel("Name:");
    nameLabel.setToolTipText("Enter the name of the portfolio.");
    gbc.gridx = 0;
    gbc.gridy = 1;
    retrievePanel.add(nameLabel, gbc);

    retrieveField = new JTextField(10);
    retrieveField.setToolTipText("Enter the name of the portfolio.");
    gbc.gridx = 1;
    gbc.gridy = 1;
    retrievePanel.add(retrieveField, gbc);

    retrieveEnter = new JButton("Enter");
    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    retrieveEnter.setForeground(new Color(0, 128, 0));
    retrievePanel.add(retrieveEnter, gbc);
  }


}
