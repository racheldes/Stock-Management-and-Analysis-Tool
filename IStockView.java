package stock;

/**
 * Represents the view of the stock program with the text-based interface.
 */
public interface IStockView extends View {
  /**
   * Method that displays the given string to the console.
   *
   * @param message    the given message to be displayed
   * @param appendable represents the characters added on
   */
  void displayMessage(String message, Appendable appendable);

  /**
   * Method that displays to the user whether
   * to access a portfolio or stock information.
   *
   * @param a represents the characters added on
   */
  void choosePortfolioOrStat(Appendable a);

  /**
   * Method that displays to the user whether
   * an error and the unique message.
   *
   * @param a represents the characters added on
   */
  void displayError(String s, Appendable a);

  /**
   * Method that displays to the user whether
   * to create a portfolio or edit one.
   *
   * @param a represents the characters added on
   */
  void createEditRebalanceChartPortfolio(Appendable a);

  /**
   * Method that displays to the user whether
   * with options to add stock or remove stock.
   *
   * @param a represents the characters added on
   */
  void editExistingPortfolio(Appendable a);

  /**
   * Method that displays to the user to input
   * a ticker symbol.
   *
   * @param a represents the characters added on
   */
  void getTickerSymbol(Appendable a);

  /**
   * Method that displays to the user to prompt
   * the number of shares.
   *
   * @param a represents the characters added on
   */
  void getShares(Appendable a);

  /**
   * Method that displays to the user to prompt
   * getting the first date.
   *
   * @param a represents the characters added on
   */
  void getInitialDate(Appendable a);

  /**
   * Method that displays to the user to prompt
   * getting the last date.
   *
   * @param a represents the characters added on
   */
  void getEndDate(Appendable a);

  /**
   * Method that displays to the user the
   * result of a method call.
   *
   * @param a represents the characters added on
   */
  void displayResult(String s, Appendable a);

  /**
   * Method that displays to the user to
   * prompt getting a date.
   *
   * @param a represents the characters added on
   */
  void getDate(Appendable a);

  /**
   * Method that displays to the user to prompt
   * getting an x-value to compute with.
   *
   * @param a represents the characters added on
   */
  void getXValue(Appendable a);

  /**
   * Method that displays to the user to determine
   * what statistic they want to calculate.
   *
   * @param a represents the characters added on
   */
  void getStatisticChoice(Appendable a);

  /**
   * Method that displays to the user with the
   * amount of shares they've removed.
   *
   * @param a represents the characters added on
   */
  void removeShares(String name, String ticker, double shares, String date, Appendable a);

  /**
   * Method that displays to the user to prompt
   * retrieving the name of a portfolio.
   *
   * @param a represents the characters added on
   */
  void getName(Appendable a);

  /**
   * Method that displays to the user to prompt
   * adding another portfolio.
   *
   * @param appendable represents the characters added on
   */
  void portfolioMenu(Appendable appendable);

  /**
   * Method that displays the appropriate message
   * to the user whether there has been a
   * gain or loss in value of stock.
   *
   * @param a represents the characters added on
   */
  void changeShares(Appendable a);

  /**
   * Method that displays to the user the name of the portfolio they added a stock's shares to a
   * given date.
   *
   * @param name       name of portfolio
   * @param ticker     ticker symbol
   * @param shares     number of shares user added
   * @param date       the date at which user added shares
   * @param appendable represents the characters added on
   */
  void addBetterShares(String name, String ticker, double shares, String date,
                       Appendable appendable);

  /**
   * Prompts the user to input which date they want to remove stocks on.
   *
   * @param a represents the characters added on
   */
  void getDateToRemove(Appendable a);

  /**
   * Displays the number of stocks in a portfolio, which is also the number of weights the
   * user must input. Then informs the user to input weights as a whole number.
   *
   * @param numberStocks number of stocks in a portfolio
   * @param a            represents the characters added on
   */
  void displayNumberStocks(int numberStocks, Appendable a);

  /**
   * Prompts user to input a weight for a given stock.
   *
   * @param i the index of the stock in a portfolio
   * @param a represents the characters added on
   */
  void enterWeights(int i, Appendable a);

  /**
   * Displays to the user what the updated portfolio will look like.
   *
   * @param a represents the characters added on
   */
  void displayUpdatedPortfolioIntro(Appendable a);

  /**
   * Displays to the user each stock in the portfolio and its new updated amount of shares
   * after the user rebalances a portfolio.
   *
   * @param s organized message of each stock and its new shares
   * @param a represents the characters added on
   */
  void displayUpdatedPortfolio(StringBuilder s, Appendable a);

  /**
   * Prompts the user to input what date they want to rebalance the portfolio.
   *
   * @param a represents the characters added on
   */
  void getDateRebalance(Appendable a);

}
