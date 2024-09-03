package stock;

import java.util.Map;

/**
 * Represents all the operations for displaying the GUI.
 */
public interface IGuiView extends View {

  /**
   * Displays the panel to create a portfolio.
   */
  void showPortfolioPanel();

  /**
   * Adds all the features involving user interaction required for the GUI.
   * @param features the features to be added
   */
  void addFeatures(Features features);

  /**
   * Displays a pop-up that tells the user the portfolio was successfully created.
   * @param name name of the portfolio
   * @param symbol the stock symbol the user added
   * @param shares the number of shares the user added
   * @param month the month the user added the stock
   * @param day the day the user added the stock
   * @param year the year the user added the stock
   */
  void showSuccessfulPortfolio(String name, String symbol, String shares, String month, String day, String year);

  /**
   * Displays a pop-up that tells the user the symbol was invalid when creating a portfolio.
   */
  void showInvalidSymbol();

  /**
   * Displays a pop-up that tells the user the shares were invalid when creating a portfolio.
   */
  void showInvalidShares();

  /**
   * Displays a pop-up that tells the user the date was invalid when creating a portfolio.
   */
  void showInvalidDate();

  /**
   * Displays the home panel.
   */
  void showHome();

  /**
   * Displays the panel to add a stock to a portfolio.
   */
  void showAddStockPanel();

  /**
   * Displays a pop-up that the portfolio was successfully saved and directs user back to the
   * home panel.
   */
  void showSave();

  /**
   * Displays the panel to remove a stock from a portfolio.
   */
  void showRemoveStockPanel();

  /**
   * Displays a pop-up that tells the user the stock was successfully added.
   * @param name name of the portfolio
   * @param symbol the stock symbol the user added
   * @param shares the number of shares the user added
   * @param month the month the user added the stock
   * @param day the day the user added the stock
   * @param year the year the user added the stock
   */
  void showSuccessfulAdd(String name, String symbol, String shares, String month,
                    String day, String year);

  /**
   * Displays a pop-up that tells the user the stock was successfully removed.
   * @param name name of the portfolio
   * @param symbol the stock symbol the user removed
   * @param shares the number of shares the user removed
   * @param month the month the user removed the stock
   * @param day the day the user removed the stock
   * @param year the year the user removed the stock
   */
  void showSuccessfulRemove(String name, String symbol, String shares, String month,
                       String day, String year);

  /**
   * Displays a pop-up that tells the user the symbol was invalid when removing a stock .
   */
  void showInvalidSymbolRemove();

  /**
   * Displays a pop-up that tells the user the date was invalid when removing a stock.
   */
  void showInvalidDateRemove();

  /**
   * Displays the panel to get the value of a portfolio.
   */
  void showGetValuePanel();

  /**
   * Displays a pop-up that tells the user the value of the portfolio.
   * @param name name of the portfolio
   * @param value value of the portfolio on the given date
   * @param month month of the date the user got the value of
   * @param day day of the date the user got the value of
   * @param year year of the date the user got the value of
   */
  void showSuccessfulValue(String name, Double value, String month,
                            String day, String year);

  /**
   * Displays a pop-up that tells the user the shares were invalid when getting
   * the removing a stock.
   */
  void showInvalidSharesRemove();

  /**
   * Displays a pop-up that tells the user the date was invalid when getting the
   * value of a portfolio.
   */
  void showInvalidDateValue();

  /**
   * Clears the text fields of the panel to create a portfolio.
   */
  void clearFields();

  /**
   * Displays a pop-up that tells the user the symbol was invalid when adding a stock.
   */
  void showInvalidSymbolAdd();

  /**
   * Displays a pop-up that tells the user the date was invalid when adding a stock.
   */
  void showInvalidDateAdd();

  /**
   * Displays a pop-up that tells the user the number of shares was invalid when adding a stock.
   */
  void showInvalidSharesAdd();

  /**
   * Displays the panel to get the composition of a portfolio.
   */
  void showCompositionPanel();

  /**
   * Displays a pop-up that tells the user the composition of the portfolio.
   * @param composition the composition of the portfolio
   */
  void showSuccessfulComp(Map<String, Double> composition);

  /**
   * Displays a pop-up that tells the user the date was invalid when getting the composition.
   */
  void showInvalidDateComp();

  /**
   * Clears the text fields of the panel to add a stock.
   */
  void clearAddFields();

  /**
   * Clears the text fields of the panel to remove a stock.
   */
  void clearRemoveFields();

  /**
   * Clears the text fields of the panel to get the value of a portfolio.
   */
  void clearValueFields();

  /**
   * Clears the text fields of the panel to get the composition of a portfolio.
   */
  void clearCompFields();

  /**
   * Displays the panel to retrieve a portfolio.
   */
  void showRetrieve();

  /**
   * Displays the panel to either add a stock, remove a stock, get the value, get the composition,
   * or save the portfolio.
   */
  void showOptionsPanel();

  /**
   * Displays a pop-up that tells the user the portfolio was successfully retrieved.
   */
  void showSuccessfulRetrieve();

  /**
   * Displays a pop-up that tells the user the portfolio was invalid when retrieving it.
   */
  void showInvalidPortfolio();
}
