package stock;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * A Class of StockModel that implements the main Model Class.
 * The class can support calculations of a stock, adding a stock,
 * removing a stock, and adding a portfolio.
 */
public class StockModel implements Model {
  private List<String> dates;
  private List<String> closePrice;
  private String oldestDate;
  private String mostRecentDate;
  private List<BetterPortfolio> betterProfile;
  private boolean isUrl;
  private Document doc;

  private static StockModel instance;

  /**
   * Constructs the stock model.
   * Doesn't take in any parameters and initializes fields
   */
  public StockModel() {
    dates = new ArrayList<>();
    closePrice = new ArrayList<>();
    oldestDate = "";
    mostRecentDate = "";
    isUrl = true;
    betterProfile = new ArrayList<>();
    this.doc = createDocument();
  }


  /**
   * Method to read the file of the given ticker symbol
   * and return a newStockModel with the statistics of
   * the corresponding ticket symbol.
   *
   * @param symbol represents the inputted ticker symbol
   */
  public void readFile(String symbol) {
    String file = getExistingStock(symbol);
    try (BufferedReader br = new BufferedReader
            (new FileReader(file))) {
      String line = "";
      line = br.readLine();
      dates = new ArrayList<>();
      closePrice = new ArrayList<>();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(",");
        dates.add(data[0]);
        closePrice.add(data[4]);
        oldestDate = dates.get(dates.size() - 1);
        mostRecentDate = dates.get(0);
      }
    } catch (FileNotFoundException ex) {
      throw new IllegalArgumentException("File not found." +
              " Check spacing or ticker symbol");
    } catch (IOException ex) {
      throw new IllegalArgumentException("Error reading file");
    }
  }

  /**
   * Helper method that retrieves an alpha vantage url
   * given a stock symbol.
   *
   * @param symbol represents a ticker symbol of a stock
   * @return a URL to download a csv
   */
  private URL getUrlStock(String symbol) {
    String apiKey = "ASDIVE6SCAN0YYZO";
    URL url = null;

    try {
      /*
      create the URL. This is the query to the web service.
       The query string includes the type of query
       (DAILY stock prices), stock symbol to be looked up,
        the API key and the format of the returned data
        (comma-separated values:csv). This service also
        supports JSON which you are welcome to use.
       */
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + symbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      System.out.println("Malformed URL but using downloaded version instead.");
      isUrl = false;
      //this.readFile(symbol);
    }
    return url;
  }

  @Override
  public void readURLFile(String symbol) throws IllegalArgumentException {
    StringBuilder output = new StringBuilder();
    URL url = this.getUrlStock(symbol);
    dates = new ArrayList<>();
    closePrice = new ArrayList<>();

    try (InputStream in = url.openStream();
         BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
      String line;
      boolean isFirstLine = true;
      while ((line = reader.readLine()) != null) {
        if (isFirstLine) {
          isFirstLine = false;
          continue; // Skip the header line
        }
        output.append(line).append("\n");
        String[] data = line.split(",");
        dates.add(data[0]);
        closePrice.add(data[4]);
        oldestDate = dates.get(dates.size() - 1);
        mostRecentDate = dates.get(0);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data " +
              "found for " + symbol);
    }
  }

  @Override
  public void removeStock(String name, String ticker, double shares, String date)
          throws IllegalArgumentException {
    boolean valid = false;
    if (betterProfile.isEmpty()) {
      throw new IllegalArgumentException("Cannot remove stock "
              + "from portfolio with no stocks");
    }

    for (BetterPortfolio bp : betterProfile) {    //for each portfolio in the profile
      if (bp.getName().equals(name)) {    //get to correct portfolio that user asks for
        bp.removeFromPortfolio(ticker, shares, date);
        valid = true;
      }
    }

    if (!valid) {
      // should throw an exception that the stock isn't in any portfolio
      throw new IllegalArgumentException("This stock isn't in any of the portfolios");
    }
  }

  /**
   * Helper method that returns the path to an existing
   * file for the given ticker symbol.
   *
   * @param symbol represents the given ticker symbol
   * @return a path to access the stock's information
   */
  private String getExistingStock(String symbol) {
    return "src/res/" + symbol + ".csv";
  }


  @Override
  public boolean dateExists(String date) {
    return dates.contains(date);
  }

  /**
   * Getter method to return the field of the oldestDate.
   *
   * @return the oldest date as a string value
   */
  private String getOldestDate() {
    return oldestDate;
  }

  /**
   * Getter method to return the field of the mostRecentDate.
   *
   * @return the most recent date as a string value
   */
  private String getMostRecentDate() {
    return mostRecentDate;
  }

  /**
   * Method to return the index of the given date for the given stock.
   *
   * @param date represents the inputted date
   * @return the index of the csv file where the date can be found
   * @throws IllegalArgumentException if the date isn't available
   */
  public int getDate(String date) throws IllegalArgumentException {
    if (dateExists(date)) {
      for (int i = 0; i < dates.size(); i++) {
        if (dates.get(i).equals(date)) {
          return i;
        }
      }
    }
    if (date.compareTo(getOldestDate()) < 0 || date.compareTo(getMostRecentDate()) > 0) {
      throw new IllegalArgumentException("Our stock does not support this date");
    }
    return -1;
  }

  @Override
  public double getClosePrice(int index) {
    return Double.parseDouble(closePrice.get(index));
  }

  @Override
  public double getValue(String name, String date) {
    double totalValue = 0.0;
    BetterPortfolio portfolio = getBetterPortfolio(name);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Calculate the value of the portfolio for all stocks on this date
    for (Stock stock : portfolio.getStocks()) {
      readURLFile(stock.getTicker());
      double sharesHeld = portfolio.getValidShares(stock.getTicker(), date);
      int index;

      //if the date is not available, get the value of the stock at the previous known date
      if (getDate(date) == -1) {
        index = getPreviousValidDate(LocalDate.parse(date, formatter));
      } else {
        index = getDate(date);
      }

      double stockPrice = getClosePrice(index);


      totalValue += sharesHeld * stockPrice;
    }
    return totalValue;
  }

  @Override
  public double calculateGainLoss(String recent, String initial) {
    return getClosePrice(getDate(recent)) - getClosePrice(getDate(initial));
  }

  @Override
  public double calculateMovingAverage(String date, int x) {
    double sum = 0;
    if (isUrl) {
      if (getDate(date) - x + 1 < 0) {
        throw new IllegalArgumentException("Not enough data to calculate the moving average.");
      }
      for (int i = getDate(date); i >= getDate(date) - x + 1; i--) {
        sum += getClosePrice(i);
      }
      return sum / x;
    } else {
      if (getDate(date) + x + 1 >= dates.size()) {
        throw new IllegalArgumentException("Not enough data to calculate the moving average.");
      }
      for (int i = getDate(date); i <= getDate(date) + x + 1; i++) {
        sum += getClosePrice(i);
      }
      return sum / x;
    }
  }

  @Override
  public List<String> findCrossovers(String startDate, String endDate, int x) {
    List<String> result = new ArrayList<>();
    int startIdx = getDate(startDate);
    int endIdx = getDate(endDate);

    if (endIdx < 0) {
      throw new IllegalArgumentException("Date range exceeds available data.");
    }

    for (int i = startIdx; i <= endIdx; i++) {
      if (i - x + 1 < 0) {
        throw new IllegalArgumentException("Not enough data to calculate the moving average.");
      }
      double sum = 0;
      for (int j = i; j >= i - x + 1; j--) {
        sum += getClosePrice(j);
      }
      double movingAverage = sum / x;

      if (getClosePrice(i) > movingAverage) {
        result.add(dates.get(i));
      }
    }
    return result;
  }

  @Override
  public void addToStock(String name, String ticker, double shares, String date) {
    // if there aren't any portfolios
    if (betterProfile.isEmpty()) {
      ArrayList<Stock> stocks = new ArrayList<>();
      stocks.add(new Stock(ticker, shares, date));
      betterProfile.add(new BetterPortfolio(stocks, name));

      // allows the document to be edited
      loadXmlDocument();

      // constructs a profile
      Element profile;
      if (doc.getDocumentElement() == null) {
        profile = doc.createElement("profile");
        doc.appendChild(profile);
      } else {
        profile = doc.getDocumentElement();
      }

      // construct a portfolio for the document and calls necessary fields
      createPortfolio(profile, name, ticker, date, String.valueOf(shares));
      saveDocumentToFile();

    } else {
      // Check if the portfolio exists
      int index = betterPortfolioExists(name);
      if (index >= 0) {
        BetterPortfolio p = betterProfile.get(index);
        p.buyStock(ticker, shares, date);
        // add to existing
        saveDocumentToFile();
      } else {
        // makes new stock list
        ArrayList<Stock> stocks = new ArrayList<>();
        // creates a new profile
        betterProfile.add(new BetterPortfolio(stocks, name));
        // gets the portfolio
        BetterPortfolio p = betterProfile.get(betterPortfolioExists(name));
        p.buyStock(ticker, shares, date);
      }
    }
  }

  @Override
  public int betterPortfolioExists(String name) {
    for (int i = 0; i < betterProfile.size(); i++) {
      if (betterProfile.get(i).getName().equals(name)) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public BetterPortfolio getBetterPortfolio(String name) {
    return betterProfile.get(betterPortfolioExists(name));
  }

  @Override
  public int getPortfolioSize(String name) {
    int i = this.betterPortfolioExists(name);
    return betterProfile.get(i).getStocks().size();
  }

  @Override
  public List<Double> rebalancePortfolio(String name, String date, List<Integer> weights) {
    return this.getBetterPortfolio(name).rebalancePortfolio(date, weights);
  }

  @Override
  public StringBuilder alignStocksAndShares(String name, List<Double> updatedShares) {
    BetterPortfolio bp = this.getBetterPortfolio(name);
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < bp.getStocks().size(); i++) {
      result.append(bp.getStocks().get(i).getTicker() + ": "
              + updatedShares.get(i)).append(" shares");
      result.append("\n");
    }

    return result;
  }

  @Override
  public List<Double> getClosingPrices(String name, String date) {
    List<Double> closingPrices = new ArrayList<>();

    for (Stock stock : this.getBetterPortfolio(name).getStocks()) {
      readURLFile(stock.getTicker());
      closingPrices.add(getClosePrice(getDate(date)));
    }

    return closingPrices;
  }

  /**
   * Helper method that returns an instance of a stock model.
   *
   * @return a new stock model
   */
  protected static StockModel getInstance() {
    if (instance == null) {
      instance = new StockModel();
    }
    return instance;
  }

  @Override
  public void addPortfolio(BetterPortfolio portfolio) {
    betterProfile.add(portfolio);
  }

  @Override
  public Map<LocalDate, Double> calculatePerformance(BetterPortfolio portfolio,
                                                     String startDate, String endDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    List<LocalDate> timePoints = generateTimePoints(startDate, endDate);
    Map<LocalDate, Double> performanceData = new TreeMap<>();

    for (LocalDate date : timePoints) {
      double totalValue = getValue(portfolio.getName(), date.format(formatter));

      performanceData.put(date, totalValue);
    }

    return performanceData;
  }

  /**
   * Helper method that checks whether the previous date is a validDate.
   *
   * @param date represents the date passed
   * @return the index of the date
   */
  private int getPreviousValidDate(LocalDate date) {
    while (getDate(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) == -1
            && !date.isAfter(LocalDate.parse(dates.get(0)))) {
      date = date.minusDays(1);
    }

    date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    return getDate(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
  }

  @Override
  public Map<String, Double> getComposition(String date, String name)
          throws IllegalArgumentException {
    if (betterPortfolioExists(name) > -1) {
      return betterProfile.get(betterPortfolioExists(name)).getCompositionHelp(date);
    } else {
      throw new IllegalArgumentException("This portfolio doesn't exist");
    }
  }

  @Override
  public Map<String, Double> getDistribution(String date, String name)
          throws IllegalArgumentException {
    Map<String, Double> distribution = new HashMap<>();
    if (betterPortfolioExists(name) > -1) {
      BetterPortfolio temp = betterProfile.get(betterPortfolioExists(name));
      for (Stock stock : temp.getStocks()) {
        // of what stock though??
        String s = stock.getTicker();
        readURLFile(s);
        double price = getClosePrice(getDate(date));
        distribution = temp.getDistributionHelp(stock, date, price, distribution);
      }
    } else {
      throw new IllegalArgumentException("This portfolio doesn't exist");
    }
    return distribution;
  }


  @Override
  public String mapToString(Map<String, Double> lst, String s) {
    StringBuilder sb = new StringBuilder();
    sb.append(s).append(":\n");
    for (Map.Entry<String, Double> entry : lst.entrySet()) {
      sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
    }
    return sb.toString();
  }

  /**
   * Helper method that returns the given dates as time to use.
   *
   * @param startDate represents the start date given
   * @param endDate   represents the end date given
   * @return a list of local dates to iterate over
   */
  private List<LocalDate> generateTimePoints(String startDate, String endDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate start;
    LocalDate end;

    try {
      start = LocalDate.parse(startDate, formatter);
      end = LocalDate.parse(endDate, formatter);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd.");
    }

    List<LocalDate> timePoints = new ArrayList<>();
    long daysBetween = ChronoUnit.DAYS.between(start, end);
    long monthsBetween = ChronoUnit.MONTHS.between(start, end);
    long yearsBetween = ChronoUnit.YEARS.between(start, end);

    String incrementBy;

    if (daysBetween < 5) {
      while (!start.isAfter(end)) {
        timePoints.add(start);
        start = start.plusDays(1);
      }
    } else if (daysBetween <= 30) {
      incrementBy = "days";
      timePoints = generateFixedIncrementTimePoints(start, end, 1, incrementBy);
    } else if (monthsBetween <= 5) {
      incrementBy = "days";
      timePoints = generateFixedIncrementTimePoints(start, end, 6, incrementBy);
    } else if (monthsBetween <= 30) {
      incrementBy = "months";
      timePoints = generateFixedIncrementTimePoints(start, end, 1, incrementBy);
    } else if (yearsBetween <= 5) {
      incrementBy = "months";
      timePoints = generateFixedIncrementTimePoints(start, end, 6, incrementBy);
    } else if (yearsBetween <= 30) {
      incrementBy = "years";
      timePoints = generateFixedIncrementTimePoints(start, end, 1, incrementBy);
    }

    return timePoints;
  }

  /**
   * Helper method to get the increments of time points.
   *
   * @param startDate   represents the start date
   * @param endDate     represents the end dat
   * @param increment   represents the amount to increment by
   * @param incrementBy represents the string value to increment by
   * @return a list of local dates
   */
  private List<LocalDate> generateFixedIncrementTimePoints(LocalDate startDate, LocalDate endDate,
                                                           long increment, String incrementBy) {
    List<LocalDate> adjustedTimePoints = new ArrayList<>();
    LocalDate currentDate = startDate;

    if (incrementBy == "days") {
      while (!currentDate.isAfter(endDate)) {
        adjustedTimePoints.add(currentDate);
        currentDate = currentDate.plusDays(increment);
      }
    } else if (incrementBy == "months") {
      while (!currentDate.isAfter(endDate)) {
        adjustedTimePoints.add(currentDate);
        currentDate = currentDate.plusMonths(increment);
        currentDate = currentDate.with(TemporalAdjusters.lastDayOfMonth());
      }
    } else if (incrementBy == "years") {
      while (!currentDate.isAfter(endDate)) {
        adjustedTimePoints.add(currentDate);
        currentDate = currentDate.plusYears(increment);
        currentDate = currentDate.with(TemporalAdjusters.lastDayOfYear());
      }
    }

    if (!adjustedTimePoints.contains(endDate)) {
      adjustedTimePoints.add(endDate);
    }

    return adjustedTimePoints;
  }

  @Override
  public String generateBarChart(Map<LocalDate, Double> performanceData, double scale,
                                 String portfolioName, String startDate, String endDate) {
    StringBuilder chart = new StringBuilder();
    chart.append("Performance of portfolio ").append(portfolioName)
            .append(" from ").append(startDate)
            .append(" to ").append(endDate).append("\n");

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    for (Map.Entry<LocalDate, Double> entry : performanceData.entrySet()) {
      String formattedDate = entry.getKey().format(formatter);
      chart.append(formattedDate).append(": ");
      int stars = (int) (entry.getValue() / scale);
      for (int i = 0; i < stars; i++) {
        chart.append("*");
      }
      chart.append("\n");
    }
    chart.append("\nScale: * = ").append(scale).append(" dollars\n");
    return chart.toString();
  }

  @Override
  public double determineScale(Map<LocalDate, Double> performanceData) {
    double maxValue = 0.0;

    for (Double value : performanceData.values()) {
      if (value > maxValue) {
        maxValue = value;
      }
    }

    return maxValue / 50.0;
  }

  /**
   * Helper method to create an xml file to write to.
   */
  private Document createDocument() {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.newDocument();

      // Check if root element "profile" exists
      Element root;
      if (document.getDocumentElement() == null) {
        root = document.createElement("profile");
        document.appendChild(root);
      } else {
        root = document.getDocumentElement();
      }

      return document;
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Helper method to get a previously saved xml document.
   */
  private void loadXmlDocument() {
    String filePath = "src/res/profile.xml";
    File xmlFile = new File(filePath);

    if (xmlFile.length() == 0) {
      // File is empty, initialize a new document with the root element
      doc = createDocument();
      saveDocumentToFile();
      return;
    }

    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      doc = dBuilder.parse(xmlFile);
      doc.getDocumentElement().normalize();
    } catch (ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
      // doc = createDocument(); // fallback to create a new document if parsing fails
    }
  }

  /**
   * Adds a portfolio to the XML document.
   */
  private void createPortfolio(Element profile, String name,
                               String ticker, String date, String shares) {
    // construct a portfolio
    Element portfolio = doc.createElement("portfolio");
    portfolio.setAttribute("name", name);
    profile.appendChild(portfolio);
    addStockToPortfolio(portfolio, ticker, date, shares);
  }

  /**
   * Adds a stock to the XML document.
   *
   * @param portfolio represents the portfolio element
   * @param ticker    represents the ticker symbol of the stock
   * @param date      represents the date the stock is being calculated
   * @param shares    represents the amount of shares attempting to add
   */
  private void addStockToPortfolio(Element portfolio,
                                   String ticker,
                                   String date, String shares) {
    // constructs a stock
    Element stock1 = doc.createElement("stock");
    portfolio.appendChild(stock1);

    // adds ticker symbol
    Element ticker1 = doc.createElement("ticker");
    ticker1.appendChild(doc.createTextNode(ticker));
    stock1.appendChild(ticker1);

    // adds date
    Element date1 = doc.createElement("date");
    date1.appendChild(doc.createTextNode(date));
    stock1.appendChild(date1);

    // adds shares
    Element shares1 = doc.createElement("shares");
    shares1.appendChild(doc.createTextNode(shares));
    stock1.appendChild(shares1);

    // adds this to the list of transactions
    addStockToTransactions(portfolio, stock1, ticker, shares, date);
  }

  /**
   * Helper method to add new transactions to the xml file.
   *
   * @param portfolio represents the element to append children to
   * @param stock1    represents the
   * @param ticker    represents the ticker symbol of the stock
   * @param shares    represents the number of shares
   * @param date      represents the date the transaction took place
   */
  private void addStockToTransactions(Element portfolio, Element stock1,
                                      String ticker,
                                      String shares, String date) {
    // Creates transactions
    Element t1 = doc.createElement("transaction");
    portfolio.appendChild(t1);

    // Adds a name for transaction
    Element service = doc.createElement("service");
    t1.appendChild(service);

    // Adds ticker of transaction
    Element serviceTicker = doc.createElement("ticker");
    serviceTicker.appendChild(doc.createTextNode(ticker));
    t1.appendChild(serviceTicker);

    // Adds share of transaction
    Element serviceShare = doc.createElement("shares");
    serviceShare.appendChild(doc.createTextNode(shares));
    t1.appendChild(serviceShare);

    // Adds date of transaction
    Element serviceDate = doc.createElement("date");
    serviceDate.appendChild(doc.createTextNode(date));
    t1.appendChild(serviceDate);

    // Adds type of transaction
    Element serviceKind = doc.createElement("service");
    serviceKind.appendChild(doc.createTextNode("Purchase"));
    t1.appendChild(serviceKind);
  }

  /**
   * Helper method to close file and update the xml file.
   */
  private void saveDocumentToFile() {
    String filePath = "src/res/profile.xml";
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    try {
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File(filePath));
      transformer.transform(source, result);
    } catch (TransformerException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean checkValidTicker(String ticker) {
    ticker = ticker.toUpperCase();
    String apiKey = "ASDIVE6SCAN0YYZO";
    URL url = null;
    isUrl = true;

    try {
      // Create the URL
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol=" + ticker
              + "&apikey=" + apiKey
              + "&datatype=csv");

      // Check if the URL is valid by making a request
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");

      int responseCode = conn.getResponseCode();
      if (responseCode != HttpURLConnection.HTTP_OK) {
        System.out.println("Invalid stock symbol or error in fetching data. Response code: " + responseCode);
        isUrl = false;
      }

      // Check the content of the response
      BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String inputLine;
      StringBuffer content = new StringBuffer();
      while ((inputLine = in.readLine()) != null) {
        content.append(inputLine);
      }
      in.close();

      // If the content contains "Invalid API call", the symbol is invalid
      if (content.toString().contains("Invalid API call")) {
        System.out.println("Invalid stock symbol.");
        isUrl = false;
      }
    } catch (MalformedURLException e) {
      System.out.println("Malformed URL but using downloaded version instead.");
      isUrl = false;
    } catch (IOException e) {
      System.out.println("Error in connecting to the URL or reading data: " + e.getMessage());
      isUrl = false;
    }


    return ticker == null || ticker.equals("") || !isUrl;
  }

  @Override
  public boolean checkValidShares(String shares) {
    try {
      double doubleShares = Double.parseDouble(shares);
      return doubleShares <= 0;
    } catch (NumberFormatException e) {
      return true;
    }
  }

  @Override
  public boolean checkValidDate(String ticker, String date) {
    readURLFile(ticker);

    return dateExists(date);

  }

  public boolean checkValidRemoveDate(String name, String ticker, String date) {
    for (Stock stock : getBetterPortfolio(name).getStocks()) {
      if (stock.getTicker().equals(ticker)) {
        readURLFile(ticker);
        if (!dateExists(date)) {
          return false;
        }
      }
    }

    return true;
  }

  @Override
  public boolean checkValidRemoveTicker(String name, String ticker) {
    ticker = ticker.toUpperCase();
    for (Stock stock : getBetterPortfolio(name).getStocks()) {
      if (!stock.getTicker().equals(ticker)) {
        return false;
      }
    }

    return true;
  }

  public boolean isDateBeforeToday(String date) {
    try {
      LocalDate inputDate = LocalDate.parse(date);

      // Get today's date
      LocalDate today = LocalDate.now();

      // Compare dates
      return inputDate.isBefore(today);
    } catch (DateTimeParseException e) {
      // Handle invalid date format
      return false;
    }
  }

}
