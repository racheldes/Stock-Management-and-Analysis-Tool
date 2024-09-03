package stock;

import java.io.IOException;


/**
 * Class Stock view that displays the correct message
 * to the console depending on what is passed to it.
 */
public class StockView implements IStockView {

  @Override
  public void displayMessage(String message, Appendable appendable) {
    try {
      appendable.append(message).append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @Override
  public void choosePortfolioOrStat(Appendable appendable) {
    displayMessage("Welcome to our Stock Program! You may create a portfolio of stocks or "
            + "look at some statistics about a specific stock.", appendable);
    displayMessage("Select an option by entering the corresponding number:", appendable);
    displayMessage("1. Access a portfolio.", appendable);
    displayMessage("2. Look at some statistics about a specific stock.", appendable);
    displayMessage("9. Return to original menu.", appendable);
    displayMessage("0. Quit program", appendable);
  }

  @Override
  public void createEditRebalanceChartPortfolio(Appendable appendable) {
    displayMessage("Select an option by entering the corresponding number:", appendable);
    displayMessage("1. Create a new portfolio.", appendable);
    displayMessage("2. Edit an existing portfolio", appendable);
    displayMessage("3. Rebalance an existing portfolio", appendable);
    displayMessage("4. View bar chart of an existing portfolio", appendable);
    displayMessage("9. Return to original menu.", appendable);
    displayMessage("0. Quit program", appendable);
  }

  @Override
  public void editExistingPortfolio(Appendable appendable) {
    displayMessage("When editing an existing portfolio, select one of the options below:"
            , appendable);
    displayMessage("1. Add stock.", appendable);
    displayMessage("2. Remove stock.", appendable);
    displayMessage("9. Return to original menu.", appendable);
    displayMessage("0. Quit program", appendable);
  }

  @Override
  public void addBetterShares(String name, String ticker, double shares, String date,
                              Appendable appendable) {
    displayMessage("You just added " + shares + " shares of "
            + ticker + " stock on " + date + " to the " + name + " portfolio.", appendable);
    displayMessage("Select an option:", appendable);
    displayMessage("1. Add stock.", appendable);
    displayMessage("2. Find the value of a portfolio.", appendable);
    displayMessage("3. Create, edit, or rebalance a portfolio.", appendable);
    displayMessage("4. View bar chart of an existing portfolio", appendable);
    displayMessage("9. Return to original menu.", appendable);
    displayMessage("0. Quit program", appendable);
  }

  @Override
  public void getShares(Appendable appendable) {
    displayMessage("Enter the number of shares you would like to add:", appendable);

  }

  @Override
  public void getTickerSymbol(Appendable appendable) {
    displayMessage("Enter the ticker symbol: ", appendable);
  }

  @Override
  public void portfolioMenu(Appendable appendable) {
    displayMessage("Select an option:", appendable);
    displayMessage("1. Add stock.", appendable);
    displayMessage("2. Find the value of a portfolio.", appendable);
    displayMessage("3. Create a new portfolio.", appendable);
    displayMessage("9. Return to original menu.", appendable);
    displayMessage("0. Quit program", appendable);
  }

  @Override
  public void getStatisticChoice(Appendable appendable) {
    displayMessage("Select the statistic you want to see:", appendable);
    displayMessage("1. Gain/Loss over a specified period", appendable);
    displayMessage("2. X-day moving average for a specified date", appendable);
    displayMessage("3. X-day crossovers over a specified date range", appendable);
    displayMessage("9. Return to original menu.", appendable);
    displayMessage("0. Quit program", appendable);
  }

  @Override
  public void removeShares(String name, String ticker, double shares, String date,
                           Appendable appendable) {
    displayMessage("You just removed " + shares + " shares of "
            + ticker + " stock on " + date + " from the " + name + " portfolio.", appendable);
    displayMessage("Select an option:", appendable);
    displayMessage("1. Remove stock.", appendable);
    displayMessage("2. Find the value of a portfolio.", appendable);
    displayMessage("3. X-day crossovers over a specified date range", appendable);
    displayMessage("9. Return to original menu.", appendable);
    displayMessage("0. Quit program", appendable);
  }

  // left comment here to see if git works correctly
  @Override
  public void changeShares(Appendable appendable) {
    displayMessage("Enter the number of shares you would like to remove", appendable);
  }

  @Override
  public void getName(Appendable appendable) {
    displayMessage("Enter the name of the portfolio: ", appendable);
  }

  @Override
  public void getInitialDate(Appendable appendable) {
    displayMessage("Enter the initial date (yyyy-mm-dd): ", appendable);
  }

  @Override
  public void getEndDate(Appendable appendable) {
    displayMessage("Enter the end date (yyyy-mm-dd): ", appendable);
  }

  @Override
  public void getDate(Appendable appendable) {
    displayMessage("Enter the date (yyyy-mm-dd): ", appendable);
  }

  @Override
  public void getXValue(Appendable appendable) {
    displayMessage("Enter the value of x: ", appendable);
  }

  @Override
  public void displayResult(String result, Appendable appendable) {
    displayMessage(result, appendable);
  }

  @Override
  public void displayError(String error, Appendable appendable) {
    displayMessage("Error: " + error, appendable);
  }

  @Override
  public void getDateToRemove(Appendable appendable) {
    displayMessage("Enter the date at which you want to remove the stock (yyyy-mm-dd): "
            , appendable);
  }

  @Override
  public void enterWeights(int i, Appendable a) {
    displayMessage("", a);
    displayMessage("Enter the weight for stock " + (i + 1), a);
  }

  @Override
  public void displayNumberStocks(int numberStocks, Appendable a) {
    displayMessage("There are " + numberStocks + " amount of stock(s) in this portfolio."
            , a);
    displayMessage("You will enter " + numberStocks + " weight(s), one for each stock.", a);
    displayMessage("Enter the weights as a whole number (ex. inputting '30' means 30%)", a);
  }

  @Override
  public void displayUpdatedPortfolioIntro(Appendable a) {
    displayMessage("Here is what your updated portfolio looks like:", a);
  }

  @Override
  public void displayUpdatedPortfolio(StringBuilder updatedPortfolio, Appendable a) {
    displayMessage(updatedPortfolio.toString(), a);
  }

  @Override
  public void getDateRebalance(Appendable a) {
    displayMessage("Enter the date that you want to rebalance at (yyyy-mm-dd): ", a);
  }

}

