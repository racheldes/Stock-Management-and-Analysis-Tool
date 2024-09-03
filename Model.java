package stock;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * The model interface of the MVC model.
 * Holds the important fields, calculates stock
 * information, and creates/edits portfolios.
 */
public interface Model {

  /**
   * Method that calculates whether a stock has grown or
   * lost it's worth between two dates.
   *
   * @param recent  represents the recent date to compare stock price
   * @param initial represents the older date to compare stock price
   * @return the gain or loss over a specified period
   */
  double calculateGainLoss(String recent, String initial);

  /**
   * Method that calculates the moving average of a
   * stock given a date and number of days.
   *
   * @param date represents the date to calculate the closing price
   * @param x    represents the number of days used to calculate
   *             the moving average
   * @return the moving average of a stock
   */
  double calculateMovingAverage(String date, int x);

  /**
   * Method that finds the crossovers between two given dates.
   *
   * @param startDate represents the starting date used for calculation
   * @param endDate   represents the end date used for calculation
   * @param x         represents the value of x-day crossovers
   * @return a list of dates where the closing price is greater than the moving average
   */
  List<String> findCrossovers(String startDate, String endDate, int x);

  /**
   * Method that calculates the value of a portfolio
   * given a date and a name.
   *
   * @param name represents the name of the portfolio
   *             to get the value of
   * @param date represents the date used to calculate the value of the stock
   * @return the portfolios worth on a given day
   */
  double getValue(String name, String date);

  /**
   * Method that gets a csv from the internet and parses through it.
   *
   * @param symbol represents the ticker symbol passed through
   * @throws IllegalArgumentException if the symbol isn't a
   *                                  supported exception
   */
  void readURLFile(String symbol) throws IllegalArgumentException;


  /**
   * Method that removes a stock from a portfolio given the
   * necessary parameters.
   *
   * @param name   represents the name of the portfolio
   * @param ticker represents the ticker symbol
   * @param shares represents the number of shares to be removed
   * @param date   represents the date at which stock was removed
   * @throws IllegalArgumentException if an attempt to remove
   *                                  more stock than available is made
   */
  void removeStock(String name, String ticker, double shares, String date) throws IllegalArgumentException;

  /**
   * Method that adds a stock to a portfolio given the
   * necessary parameters.
   *
   * @param name   represents the name of the portfolio
   * @param ticker represents the ticker symbol of the stock
   * @param shares represents the number of shares to be removed
   * @param date   represents the date at which stock was removed
   */
  void addToStock(String name, String ticker, double shares, String date);


  /**
   * Method that gets the Portfolio with the given name and allows
   * to access its methods and functions.
   *
   * @param name represents the name of the portfolio
   * @return the portfolio with the given name
   */
  BetterPortfolio getBetterPortfolio(String name);

  /**
   * Gets how many stocks are in a given portfolio. Checks if the portfolio exists,
   * then calculates how many stocks are in that portfolio.
   *
   * @param name the name of the portfolio
   * @return number of stocks in the given portfolio
   */
  int getPortfolioSize(String name);

  /**
   * Rebalances portfolio to the given weights. Calls on method in BetterPortfolio to
   * handle actual calculations. Mandates the user only input the same amount of weights as there
   * are stocks. Returns the list of updated shares, corresponding to list
   * of stocks in a portfolio (indices align).
   *
   * @param name    name of portfolio
   * @param weights arbitrary number of weights for each stock
   * @return list of the updated shares for each stock in the portfolio
   */
  List<Double> rebalancePortfolio(String name, String date, List<Integer> weights);

  /**
   * Uses corresponding index of the list of stocks in a portfolio and list of updated shares to
   * build a string. (ex. IBM: 5.0)
   *
   * @param name          name of the portfolio
   * @param updatedShares list of updated shares after rebalancing
   * @return a StringBuilder that corresponds a stock to its new updated share
   */
  StringBuilder alignStocksAndShares(String name, List<Double> updatedShares);

  /**
   * Method to return the closing price given an index.
   *
   * @param index represents the index of a date to get date from stock's list of dates
   * @return the closing price at the given index
   */
  double getClosePrice(int index);

  /**
   * Method to return the index of the given date for the given stock.
   *
   * @param date represents the inputted date
   * @return the index of the list of dates from csv file where the date can be found
   * @throws IllegalArgumentException if the date isn't available
   */
  int getDate(String date) throws IllegalArgumentException;

  /**
   * Gets the closing price for each stock in a given portfolio on a specific date.
   * Adds each closing price to a list of closing prices.
   *
   * @param name name of portfolio
   * @param date date at which to find closing price
   * @return list of closing prices
   */
  List<Double> getClosingPrices(String name, String date);

  /**
   * Adds a given portfolio to the profile.
   *
   * @param portfolio the portfolio to add to the profile
   */
  void addPortfolio(BetterPortfolio portfolio);

  /**
   * Using the start date and end date, an appropriate scale of time stamps are created to
   * display the bar chart. Calculates the value of a portfolio at these time stamps.
   *
   * @param portfolio portfolio that user wishes to see bar chart of
   * @param startDate start date of time range
   * @param endDate   end date of time range
   * @return a map of the time stamps and the value of the portfolio at each time stamp
   */
  Map<LocalDate, Double> calculatePerformance(BetterPortfolio portfolio,
                                              String startDate, String endDate);

  /**
   * Generates the visual bar chart to be displayed to the user.
   *
   * @param performanceData the data of the time stamps and their respective values
   * @param scale           scale of the asterisks
   * @param portfolioName   name of the portfolio
   * @param startDate       start date of time range
   * @param endDate         end date of time range
   * @return the string to be outputted to the user
   */
  String generateBarChart(Map<LocalDate, Double> performanceData, double scale,
                          String portfolioName, String startDate, String endDate);

  /**
   * Determines the scale of the asterisks given the data of the portfolio. Finds the
   * largest value of the portfolio and divides by 50 to ensure that no more than
   * 50 asterisks are displayed.
   *
   * @param performanceData the data of the time stamps and their respective values
   * @return the scale of the asterisks
   */
  double determineScale(Map<LocalDate, Double> performanceData);

  /**
   * Method that calculates the composition of a portfolio on a given date.
   *
   * @param date the date computed on
   * @param name the name of the portfolio
   * @return the composition of the portfolio
   * @throws IllegalArgumentException if the portfolio doesn't exist
   */
  Map<String, Double> getComposition(String date, String name) throws IllegalArgumentException;

  /**
   * Helper method to return the distribution of a portfolio.
   *
   * @param date represents the given date
   * @return the distribution of stocks
   */
  Map<String, Double> getDistribution(String date, String name) throws IllegalArgumentException;


  /**
   * Method to check the validity of a ticker symbol.
   *
   * @param ticker represents the ticker symbol passed in
   * @return whether the ticker is a valid one
   */
  boolean checkValidTicker(String ticker);

  /**
   * Method to check whether the amount of shares is a valid amoount.
   *
   * @param shares represents the number of shares being passed in
   * @return whether the amount of shares is a valid number to remove
   */
  boolean checkValidShares(String shares);

  /**
   * Helper method to determine whether a date exists.
   *
   * @param date represents the inputted String
   * @return true if the date is in the list
   */
  boolean dateExists(String date);


  /**
   * Method to check whether the date is a valid.
   *
   * @param ticker represents the ticker symbol needed to check
   * @param date   represents the date being checked
   * @return whether the stock exists at the given date
   */
  boolean checkValidDate(String ticker, String date);

  /**
   * Method that converts a map to a string to be displayed.
   *
   * @param data represents the map of values
   * @param s    represents the kind of
   * @return the String value of the map
   */
  String mapToString(Map<String, Double> data, String s);

  /**
   * Helper that returns the index of a portfolio in a profile.
   *
   * @param name represents the name of the portfolio
   * @return the numerical index of the portfolio, if -1, it doesn't exist
   */
  public int betterPortfolioExists(String name);

  /**
   * Checks if the date the user is trying to remove is a valid date.
   * @param name name of the portfolio
   * @param ticker ticker symbol user is trying to remove
   * @param date date at which the user is trying to remove the stock
   * @return
   */
  boolean checkValidRemoveDate(String name, String ticker, String date);

  boolean checkValidRemoveTicker(String name, String symbol);

  boolean isDateBeforeToday(String date);
}


