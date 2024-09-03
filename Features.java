package stock;

/**
 * Represents the features of the GUI of the stock program.
 */
public interface Features {
  /**
   * Displays the panel to add a stock to create a portfolio.
   */
  void createPortfolio();

  /**
   * Checks if the inputs are valid to create a portfolio and creates it. If the inputs are invalid,
   * a pop-up appears telling the user which input is invalid. If the inputs are valid, the
   * portfolio is created and a pop-up appears telling the user that the portfolio was created.
   * @param name name of the portfolio
   * @param symbol ticker symbol of the stock
   * @param shares number of shares the user wants to add
   * @param month month of the date the user wants to add the stock
   * @param day day of the date the user wants to add the stock
   * @param year year of the date the user wants to add the stock
   */
  void submitPortfolio(String name, String symbol, String shares, String month, String day, String year);

  /**
   * Adds a stock to the same portfolio that was created. If the inputs are invalid, a pop-up
   * appears telling the user which input is invalid. If the inputs are valid, the stock is added
   * and a pop-up appears telling the user that the stock was added successfully.
   * @param symbol ticker symbol of the stock
   * @param shares number of shares the user wants to add
   * @param month month of the date the user wants to add the stock
   * @param day day of the date the user wants to add the stock
   * @param year year of the date the user wants to add the stock
   */
  void addStock(String symbol, String shares, String month, String day, String year);

  /**
   * Removes a stock from the same portfolio that was created. It can only remove a stock if a stock
   * that the user wants to remove was added on the same date. If the inputs are invalid, a pop-up
   * appears telling the user which input is invalid. If the inputs are valid, the stock is removed
   * and a pop-up appears telling the user that the stock was removed successfully.
   * @param symbol ticker symbol of the stock
   * @param shares number of shares the user wants to remove
   * @param month month of the date the user wants to remove the stock
   * @param day day of the date the user wants to remove the stock
   * @param year year of the date the user wants to remove the stock
   */
  void removeStock(String symbol, String shares, String month, String day, String year);

  /**
   * Gets the value of the portfolio on a given date. If the date is invalid, a pop-up appears
   * telling the user that the date is invalid. If the date is valid, the value of the portfolio is
   * calculated and a pop-up appears telling the user the value of the portfolio.
   * @param month month of the date the user wants to get value of the portfolio
   * @param day day of the date the user wants to get value of the portfolio
   * @param year year of the date the user wants to get value of the portfolio
   */
  void getValue(String month, String day, String year);

  /**
   * Gets the composition of the portfolio on a given date. If the date is invalid, a pop-up
   * appears telling the user that the date is invalid. If the date is valid, the composition of the
   * portfolio is calculated and a pop-up appears telling the user the composition of the portfolio.
   * @param month month of the date the user wants to get composition of the portfolio
   * @param day day of the date the user wants to get composition of the portfolio
   * @param year year of the date the user wants to get composition of the portfolio
   */
  void getComposition(String month, String day, String year);

  /**
   * Displays the panel to save the portfolio.
   */
  void savePortfolio();

  /**
   * Displays the panel of the home panel (first screen).
   */
  void goHome();

  /**
   * Clears the text field on the add stock panel and displays that panel.
   */
  void showAddStock();

  /**
   * Clears the text field on the remove stock panel and displays that panel.
   */
  void showRemoveStock();

  /**
   * Displays the panel to get the value of the portfolio.
   */
  void showGetValue();

  /**
   * Displays the panel to get the composition of the portfolio.
   */
  void showComposition();

  /**
   * Clears the text fields of the panel to create a portfolio.
   */
  void clear();

  /**
   * Clears the text fields of the panel to add a stock.
   */
  void clearAdd();

  /**
   * Clears the text fields of the panel to remove a stock.
   */
  void clearRemove();

  /**
   * Clears the text fields of the panel to get the value of the portfolio.
   */
  void clearValue();

  /**
   * Clears the text fields of the panel to get the composition of the portfolio.
   */
  void clearComp();

  /**
   * Checks if the given name of the portfolio exists. If the name exists, the portfolio is
   * retrieved and a pop-up appears telling the user that the portfolio was retrieved. If the name
   * does not exist, a pop-up appears telling the user that the name is invalid.
   * @param name name of the portfolio
   */
  void retrievePortfolio(String name);

  /**
   * Displays the panel to retrieve a portfolio.
   */
  void showPortfolio();
}
