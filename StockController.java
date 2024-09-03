package stock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Stock Controller Class. Controls the entire
 * program and connects the view to the model.
 * Passes information in and out using readable
 * and appendable.
 */
public class StockController implements Controller {
  private Model model;
  private IStockView view;
  private Appendable a;
  private Scanner in;

  /**
   * Constructs a controller with a model, view,
   * readable, and appendable.
   */
  public StockController(Model model, IStockView view,
                         Readable r, Appendable a) {
    this.model = model;
    this.view = view;
    this.a = a;
    this.in = new Scanner(r);

  }


  @Override
  public void setView(IGuiView view) {
    // to prevent an empty method body
  }

  @Override
  public void start() {
    view.choosePortfolioOrStat(a);
    int path = in.nextInt();
    in.nextLine();
    switch (path) {
      case 1:
        handlePortfolio();
        break;
      case 2:
        handleStock();
        break;
      case 9:
        start();
        break;
      case 0:
        System.exit(0);
        break;
      default:
        view.displayError("Invalid choice.", a);
    }
  }


  /**
   * Helper method that prompts the user whether
   * to access a portfolio or stock information.
   */
  private void handlePortfolio() {
    view.createEditRebalanceChartPortfolio(a);
    int choice = in.nextInt();
    switch (choice) {
      case 1:
        addToStock();   //creates new portfolio by adding stock
        break;
      case 2:
        editExistingPortfolio();
        break;
      case 3:
        rebalancePortfolio();
        break;
      case 4:
        portfolioBarChart();
        break;
      case 9:
        start();
        break;
      case 0:
        System.exit(0);
        break;
      default:
        view.displayError("Invalid choice.", a);
    }
  }

  /**
   * Helper method that prompts the user whether
   * to add a stock or remove a stock.
   */
  private void editExistingPortfolio() {
    view.editExistingPortfolio(a);
    int choice = in.nextInt();
    switch (choice) {
      case 1:
        addToStock();
        break;
      case 2:
        removeFromStock();
        break;
      case 3:
        getComposition();
        break;
      case 4:
        getValue();
        break;
      case 5:
        getDistribution();
        break;
      case 9:
        start();
        break;
      case 0:
        System.exit(0);
        break;
      default:
        view.displayError("Invalid choice.", a);
    }
  }

  /**
   * Helper method to handle the cases in which
   * one can balance a portfolio.
   */
  private void rebalancePortfolio() {
    view.getName(a);
    String name = in.next();
    int numberStocks = model.getPortfolioSize(name);
    view.getDateRebalance(a);
    String date = in.next();
    view.displayNumberStocks(numberStocks, a);
    List<Integer> weights = new ArrayList<>();
    for (int i = 0; i < numberStocks; i++) {
      view.enterWeights(i, a);
      int weight = in.nextInt();
      weights.add(weight);
    }

    List<Double> updatedShares = model.rebalancePortfolio(name, date, weights);
    StringBuilder updatedPortfolio = model.alignStocksAndShares(name, updatedShares);
    view.displayUpdatedPortfolioIntro(a);
    view.displayUpdatedPortfolio(updatedPortfolio, a);

    this.start();

  }

  /**
   * Helper method that prompts the user to give
   * information to calculate the value of a portfolio.
   */
  private void getValue() {
    view.getName(a);
    String name = in.next();
    view.getDate(a);
    String date = in.next();
    double value = model.getValue(name, date);
    view.displayResult("The value of the " + name + " portfolio is " + value + ".", a);

    view.portfolioMenu(a);
    int choice = in.nextInt();
    switch (choice) {
      case 1:
        addToStock();
        break;
      case 2:
        getValue();
        break;
      case 3:
        handlePortfolio();
        break;
      case 9:
        start();
        break;
      case 0:
        System.exit(0);
        break;
      default:
        view.displayError("Invalid choice.", a);
        break;
    }
  }

  /**
   * Helper method to conduct options
   * to add to a stock.
   */
  private void addToStock() {
    view.getName(a);
    String name = in.next();
    view.getTickerSymbol(a);
    String ticker = in.next();
    view.getShares(a);
    double shares = in.nextInt();
    view.getDate(a);
    String date = in.next();

    model.addToStock(name, ticker, shares, date);
    view.addBetterShares(name, ticker, shares, date, a);
    int choice = in.nextInt();
    switch (choice) {
      case 1:
        addToStock();
        break;
      case 2:
        getValue();
        break;
      case 3:
        handlePortfolio();    //gives option to create/edit portfolio
        break;
      case 4:
        portfolioBarChart();
        break;
      case 9:
        start();
        break;
      case 0:
        System.exit(0);
        break;
      default:
        view.displayError("Invalid choice.", a);
        break;
    }
  }

  /**
   * Helper method to get information from
   * the user to remove a stock.
   */
  private void removeFromStock() {
    view.getName(a);
    String name = in.next();
    view.getTickerSymbol(a);
    String ticker = in.next();
    view.changeShares(a);
    double shares = in.nextInt();
    view.getDateToRemove(a);
    String date = in.next();

    model.removeStock(name, ticker, shares, date);
    view.removeShares(name, ticker, shares, date, a);

    int choice = in.nextInt();
    switch (choice) {
      case 1:
        removeFromStock();
        break;
      case 2:
        getValue();
        break;
      case 3:
        handlePortfolio();    //gives option to create/edit portfolio
        break;
      case 9:
        start();
        break;
      case 0:
        System.exit(0);
        break;
      default:
        view.displayError("Invalid choice.", a);
        break;
    }
  }

  /**
   * Helper method to prompt the user to calculate a
   * gain or loss statistic of a stock.
   */
  private void handleGainLoss() {
    try {
      view.getInitialDate(a);
      String initialDate = in.next();
      view.getEndDate(a);
      String endDate = in.next();
      double gainLoss = model.calculateGainLoss(endDate, initialDate);
      if (gainLoss > 0) {
        view.displayResult("Gain from " + initialDate + " to " + endDate + ": " + gainLoss, a);
      } else {
        view.displayResult("Loss from " + initialDate + " to " + endDate + ": " + gainLoss, a);
      }
    } catch (Exception e) {
      view.displayError(e.getMessage(), a);
    }

    this.start();
  }

  /**
   * Helper method to prompt the user to calculate a
   * moving average statistic of a stock.
   */
  private void handleMovingAverage() {
    try {
      view.getDate(a);
      String date = in.next();
      view.getXValue(a);
      int x = in.nextInt();
      double movingAverage = model.calculateMovingAverage(date, x);
      view.displayResult("The " + x + "-day moving average for "
              + date + " is " + movingAverage, a);
    } catch (Exception e) {
      view.displayError(e.getMessage(), a);
    }

    this.start();
  }

  /**
   * Helper method to prompt the user to calculate a
   * x-day crossover statistic of a stock.
   */
  private void handleCrossovers() {
    try {
      view.getInitialDate(a);
      String startDate = in.next();
      view.getEndDate(a);
      String endDate = in.next();
      view.getXValue(a);
      int x = in.nextInt();
      StringBuilder result = new StringBuilder();
      result.append("X-day crossovers from ").append(startDate).append(" to ")
              .append(endDate).append(": ");
      for (String date : model.findCrossovers(startDate, endDate, x)) {
        result.append("\n").append(date);
      }
      view.displayResult(result.toString(), a);
    } catch (Exception e) {
      view.displayError(e.getMessage(), a);
    }

    this.start();
  }

  /**
   * Helper method to prompt the user to
   * get the correct csv file.
   */
  private void handleStock() {
    view.getTickerSymbol(a);
    String symbol = in.nextLine().toUpperCase();
    model.readURLFile(symbol);
    handleStatistic();
  }

  /**
   * Helper method to prompt the user which
   * statistic they would like to calculate.
   */
  private void handleStatistic() {
    view.getStatisticChoice(a);
    int choice = in.nextInt();
    switch (choice) {
      case 1:
        handleGainLoss();
        break;
      case 2:
        handleMovingAverage();
        break;
      case 3:
        handleCrossovers();
        break;
      case 9:
        start();
        break;
      case 0:
        System.exit(0);
        break;
      default:
        view.displayError("Invalid choice.", a);
        break;
    }
  }

  /**
   * Helper method that produces the bar chart.
   */
  private void portfolioBarChart() {
    view.getName(a);
    String name = in.next();
    BetterPortfolio portfolio = model.getBetterPortfolio(name);
    view.getInitialDate(a);
    String startDate = in.next();
    view.getEndDate(a);
    String endDate = in.next();


    Map<LocalDate, Double> data = model.calculatePerformance(portfolio, startDate, endDate);
    double scale = model.determineScale(data);
    String barChart = model.generateBarChart(data, scale, name, startDate, endDate);
    view.displayResult(barChart, a);

    this.start();

  }

  /**
   * Helper method that gets the composition
   * of a portfolio.
   */
  private void getComposition() {
    view.getName(a);
    String name = in.next();
    BetterPortfolio p = model.getBetterPortfolio(name);
    view.getDate(a);
    String date = in.next();

    Map<String, Double> data = model.getComposition(date, name);
    String result = model.mapToString(data, "Composition");
    view.displayResult(result, a);
    this.start();
  }

  /**
   * Helper method that gets the distribution
   * of a portfolio.
   */
  private void getDistribution() {
    view.getDate(a);
    String date = in.next();
    view.getName(a);
    String name = in.next();
    BetterPortfolio p = model.getBetterPortfolio(name);

    Map<String, Double> data = model.getComposition(date, name);
    String result = model.mapToString(data, "Distribution");
    view.displayResult(result, a);
    this.start();
  }

}