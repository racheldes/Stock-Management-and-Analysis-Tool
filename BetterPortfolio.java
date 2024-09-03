package stock;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static stock.Service.Purchase;
import static stock.Service.Sale;

/**
 * Represents a portfolio of stocks and the transactions of adding/removing stocks from the
 * portfolio. This class also handles saving and retrieving the portfolio from a xml file.
 */
public class BetterPortfolio {
  private List<Stock> stocks;
  private String name;
  private List<Transaction> transactions;
  private Document portfolios;

  /**
   * Constructs a portfolio given a list of stocks.
   */
  public BetterPortfolio(List<Stock> stocks, String name) {
    this.stocks = stocks;
    this.name = name;
    this.transactions = new ArrayList<>();
    this.portfolios = createDocument();
    // Register the portfolio in the StockModel
    StockModel.getInstance().addPortfolio(this);
  }

  /**
   * Buys stock on a specific date. Changed from previous implementation where the
   * date was not needed. Got rid of previous implementation of buying a stock
   * without specifying the date.
   *
   * @param ticker   ticker symbol
   * @param quantity number of shares user wants to purchase
   * @param date     the date at which the user wants to purchase at
   */
  protected void buyStock(String ticker, double quantity, String date) {
    boolean stockExists = false;

    // Use an iterator to avoid ConcurrentModificationException
    for (Stock stock : stocks) {
      // if the stock already exists in the portfolio
      if (stock.getTicker().equals(ticker) && validTransaction(ticker, date)) {
        addStockToExistingStock(ticker, quantity, date);
        transactions.add(new Transaction(ticker, quantity, date, Purchase));
        editExisting(ticker, quantity);
        stockExists = true;
        // break; // Exit the loop since the stock has been processed
      }
    }
    if (!stockExists) {
      Stock s = new Stock(ticker, quantity, date);
      this.stocks.add(s);
      transactions.add(new Transaction(ticker, quantity, date, Purchase));
      addNew(ticker, quantity, date);
    }
  }

  /**
   * Helper method to determine how stock should be added
   * and designates it.
   *
   * @param ticker   represents the ticker symbol
   * @param quantity represents the quantity
   * @param date     represents the date the stock is being added
   */
  private void addStockToExistingStock(String ticker, double quantity, String date) {
    boolean isNewStock = false;
    for (Stock stock : stocks) {
      if (stock.getDate().equals(date)) {   //if stock is in portfolio and already bought shares on that date
        isNewStock = true;
      }
      if (isNewStock) {
        // redundant and can be simplified
        stock.setShares(this.getValidShares(ticker, date) + quantity);
        transactions.add(new Transaction(ticker, quantity, date, Purchase));
        editExisting(ticker, quantity);
      } else {
        //buying shares of the same stock but on a new date
        //not adding to same stock but object but creating new one as each purchase is date specific
        Stock s = new Stock(ticker, quantity, date);
        stocks.add(s);
        addNew(ticker, quantity, date);
        transactions.add(new Transaction(ticker, quantity, date, Purchase));
        break;
      }
    }
  }


  /**
   * Helper method that determines whether a
   * transaction can take place.
   *
   * @param ticker represents the ticker symbol
   * @param date   represents the date the transaction is taking place
   * @return whether the transaction is in chronological order
   */
  private boolean validTransaction(String ticker, String date) {
    for (Transaction transaction : transactions) {
      // date is before
      if (date.compareTo(transaction.getServiceDate()) < 1 && transaction.getTicker().equals(ticker)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Gets the name of this portfolio.
   *
   * @return the name of this portfolio
   */
  protected String getName() {
    return name;
  }

  /**
   * Gets the list of stocks in this portfolio.
   *
   * @return the list of stocks in this portfolio.
   */
  protected List<Stock> getStocks() {
    return stocks;
  }

  /**
   * Helper method to return the composition of a portfolio.
   *
   * @param date represents the given date
   * @return the composition of a portfolio
   */
  protected Map<String, Double> getCompositionHelp(String date) {
    Map<String, Double> composition = new HashMap<>();
    // checks if the date is valid
    for (Stock stock : stocks) {
      // needs to also exist at the time
      if (stock.getDate().compareTo(date) < 1 || stock.getShares() > 0) {
        composition.put(stock.getTicker(), stock.getShares());
      }
    }
    return composition;
  }

  /**
   * Helper method to return the distribution of a portfolio.
   *
   * @param date         represents the given date
   * @param price        represents the price of the stock
   * @param distribution represents the
   *                     current distribution to be added to.
   * @return the distribution of stocks
   */
  protected Map<String, Double> getDistributionHelp(Stock stock, String date,
                                                    double price, Map<String, Double> distribution) {
    if (stock.getDate().compareTo(date) < 1) {
      // need to actually compute value
      distribution.put(stock.getTicker(), stock.getShares() * price);
    }
    return distribution;
  }

  /**
   * Helper method to create an xml document.
   */
  protected Document createDocument() {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.newDocument();
      Element root = document.createElement("profile");
      document.appendChild(root);
      return document;
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Helper method to remove a stock from a portfolio.
   *
   * @param ticker   represents the ticker symbol of the stock
   * @param quantity represents the number of stocks to remove
   * @param date     represents the date this transaction is taking place
   * @throws IllegalArgumentException if you attempt to remove more stocks than shares available
   */
  protected void removeFromPortfolio(String ticker, double quantity,
                                     String date) throws IllegalArgumentException {
    for (Stock stock : stocks) {
      if (stock.getDate().equals(date) &&
              validTransaction(ticker, date)) {
        stock.setShares(stock.getShares() - quantity);
        // remove completely and add to transaction
        transactions.add(new Transaction(ticker, quantity, date, Sale));
        removeFromDoc(ticker, quantity, date);
      }
      // valid and won't result in a negative number
      else if (validTransaction(ticker, date)
              && stock.getShares() - quantity > 0) {
        // edit the amount of shared
        stock.setShares(stock.getShares() - quantity);
        transactions.add(new Transaction(ticker, quantity, date, Sale));
        // edits existing stock
        editExisting(ticker, -quantity);
      } else {
        throw new IllegalArgumentException("You can't removed"
                + " more shares than are available.");
      }
    }
  }

  /**
   * @param date    the date at which the user wishes to rebalance the portfolio
   * @param weights list of new corresponding (indices align with indices of 'stocks')
   *                weight distribution
   * @return a list of rebalanced shares corresponding to each stock
   * represented as List<Double> because shares can be fractional after rebalancing
   */
  protected List<Double> rebalancePortfolio(String date, List<Integer> weights) {
    int totalWeight = 0;      //total weight of the inputted weights
    double totalPrice = 0;    //total price of the stocks at the given date
    List<Double> prices = new ArrayList<>();
    List<Double> weightedPrices = new ArrayList<>();
    List<Double> newShares = new ArrayList<>();

    StockModel stock = StockModel.getInstance();
    List<Double> closingPrices = stock.getClosingPrices(name, date);

    for (int weight : weights) {
      totalWeight += weight;
    }

    //ensuring that the total weight inputted by the user adds up to 100%
    if (totalWeight != 100) {
      throw new IllegalArgumentException("Total weight must equal 100%");
    }

    //for each stock
    //multiply shares by closing price on that date (shares on/previous of the date)
    //add all together
    for (int i = 0; i < stocks.size(); i++) {
      double price;

      price = this.getValidShares(stocks.get(i).getTicker(), date) * closingPrices.get(i);

      prices.add(price);
      totalPrice += price;
    }

    //multiply respective weights by total value and add to list
    for (int weight : weights) {
      double weightedPrice;
      weightedPrice = (double) weight / 100 * totalPrice;

      weightedPrices.add(weightedPrice);
    }

    for (int i = 0; i < stocks.size(); i++) {
      if (prices.get(i) > weightedPrices.get(i)) {
        double sharesSold;
        sharesSold = (prices.get(i) - weightedPrices.get(i))
                / closingPrices.get(i);
        stocks.get(i).setShares(this.getValidShares(stocks.get(i).getTicker(), date) - sharesSold);
      } else if (prices.get(i) < weightedPrices.get(i)) {
        double sharesSold;
        sharesSold = (weightedPrices.get(i) - prices.get(i))
                / closingPrices.get(i);
        stocks.get(i).setShares(this.getValidShares(stocks.get(i).getTicker(), date) + sharesSold);
      }

      newShares.add(this.getValidShares(stocks.get(i).getTicker(), date));
    }

    return newShares;
  }

  /**
   * Gets the number of shares purchased on and before a given date. Ensures that the correct
   * number of shares is being used in certain calculations.
   *
   * @param ticker ticker symbol of shares trying to get
   * @param date   the date at which the shares need to be on/before
   * @return the number of shares on/before the given date
   */
  protected double getValidShares(String ticker, String date) {
    double shares = 0;
    for (Stock stock : stocks) {
      if (stock.getTicker().equals(ticker)) {
        //looked up documentation for this. compares the date of purchase and the given date
        LocalDate datePurchase = LocalDate.parse(stock.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate dateValue = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);

        if (datePurchase.isBefore(dateValue) || datePurchase.isEqual(dateValue)) {
          //since each stock is its own object if bought on different date, keep counter to
          //calculate total number of shares
          shares += stock.getShares();
        }
      }

    }

    return shares;
  }

  /**
   * Helper method to add a new Stock to the xml document.
   *
   * @param ticker    represents ticker symbol
   * @param numStocks represents stock being added
   * @param date      represents the date the stocks were added.
   */
  protected void addNew(String ticker, double numStocks, String date) {
    String filePath = "src/res/profile.xml";
    try {
      Document doc = loadDocument(filePath);

      Element newStock = doc.createElement("stock");

      Element symbolElement = doc.createElement("ticker");
      symbolElement.appendChild(doc.createTextNode(ticker));
      newStock.appendChild(symbolElement);

      Element dateElement = doc.createElement("date");
      dateElement.appendChild(doc.createTextNode(date));
      newStock.appendChild(dateElement);

      Element quantityElement = doc.createElement("shares");
      quantityElement.appendChild(doc.createTextNode(String.valueOf(numStocks)));
      newStock.appendChild(quantityElement);

      doc.getDocumentElement().appendChild(newStock);

      saveDocument(doc, filePath);
      addToTransactionAndSave(doc.getDocumentElement(), doc, ticker, numStocks, date, Purchase);
    } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * Helper method to remove a stock from the document.
   *
   * @param ticker    represents ticker symbol
   * @param numStocks represents stock being added
   * @param date      represents the date the stocks were added.
   */
  private void removeFromDoc(String ticker, double numStocks, String date) {
    String filePath = "src/res/profile.xml";
    try {
      Document doc = loadDocument(filePath);
      doc.getDocumentElement().normalize();

      NodeList portfolios = doc.getElementsByTagName("portfolio");

      for (int i = 0; i < portfolios.getLength(); i++) {
        Element portfolio = (Element) portfolios.item(i);
        if (portfolio.getAttribute("name").equals(this.name)) {
          NodeList stocks = portfolio.getElementsByTagName("stock");

          // Remove the stock
          for (int j = 0; j < stocks.getLength(); j++) {
            Element stock = (Element) stocks.item(j);
            String stockTicker = stock.getElementsByTagName("ticker")
                    .item(0).getTextContent();
            String stockDate = stock.getElementsByTagName("date")
                    .item(0).getTextContent();

            if (stockTicker.equals(ticker) && stockDate.equals(date)) {
              portfolio.removeChild(stock);
              break;
            }
          }

          saveDocument(doc, filePath);
          addToTransactionAndSave(portfolio, doc, ticker,
                  numStocks, date, Sale);
        }
      }
    } catch (ParserConfigurationException | IOException
             | SAXException | TransformerException e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * Helper method to edit an existing stock's shares.
   *
   * @param ticker    represents the ticker symbol.
   * @param numStocks represents the number of stocks being changed
   */
  protected void editExisting(String ticker, double numStocks) {
    String filePath = "src/res/profile.xml";
    try {
      // Load and parse the XML document
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(new File(filePath));

      // Normalize the XML structure
      doc.getDocumentElement().normalize();

      // Get all stock elements
      NodeList stockList = doc.getElementsByTagName("stock");

      // Iterate through each stock
      for (int i = 0; i < stockList.getLength(); i++) {
        Node stockNode = stockList.item(i);

        if (stockNode.getNodeType() == Node.ELEMENT_NODE) {
          Element stockElement = (Element) stockNode;

          // Get the symbol element of the current stock
          String symbol = stockElement.getElementsByTagName("ticker")
                  .item(0).getTextContent();

          // Check if the symbol matches the target symbol
          if (symbol.equals(ticker)) {
            // Get the current quantity
            double currentQuantity = Double.parseDouble(stockElement
                    .getElementsByTagName("shares").item(0).getTextContent());

            // Calculate the new quantity
            double newQuantity = currentQuantity + numStocks;

            // Update the quantity in the XML
            stockElement.getElementsByTagName("shares").item(0)
                    .setTextContent(String.valueOf(newQuantity));

            break; // Assuming only one stock should match the symbol
          }
        }
      }
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File(filePath));
      transformer.transform(source, result);
    } catch (IOException | SAXException | ParserConfigurationException
             | TransformerException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Helper method to update the transaction list in the xml doc and save it.
   *
   * @param portfolio represents the element being appended to
   * @param doc       represents the document
   * @param ticker    represents the ticker symbol
   * @param numStocks represents the number of stocks
   * @param date      represents the date
   * @param s         represents the kind of service being performed
   */
  private void addToTransactionAndSave(Element portfolio, Document doc,
                                       String ticker, double numStocks, String date, Service s) {
    try {
      NodeList transactionsList = portfolio.getElementsByTagName("transactions");
      Element transactions;
      if (transactionsList.getLength() > 0) {
        transactions = (Element) transactionsList.item(0);
      } else {
        transactions = doc.createElement("transactions");
        portfolio.appendChild(transactions);
      }

      Element transaction = doc.createElement("trans");

      Element tickerElement = doc.createElement("ticker");
      tickerElement.appendChild(doc.createTextNode(ticker));
      transaction.appendChild(tickerElement);

      Element sharesElement = doc.createElement("share");
      sharesElement.appendChild(doc.createTextNode(String.valueOf(numStocks)));
      transaction.appendChild(sharesElement);

      Element dateElement = doc.createElement("date");
      dateElement.appendChild(doc.createTextNode(date));
      transaction.appendChild(dateElement);

      Element serviceElement = doc.createElement("service");
      serviceElement.appendChild(doc.createTextNode(String.valueOf(s)));
      transaction.appendChild(serviceElement);

      transactions.appendChild(transaction);

      saveDocument(doc, "src/res/profile.xml");
    } catch (TransformerException e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * Helper method to load the document.
   *
   * @param filePath represents the path to the xml doc
   * @return the xml document
   * @throws ParserConfigurationException if there's an error getting the document
   * @throws SAXException                 if there's an error with parsing
   * @throws IOException                  if the filePath doesn't exist
   */
  private Document loadDocument(String filePath)
          throws ParserConfigurationException, SAXException, IOException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    return builder.parse(new File(filePath));
  }

  /**
   * Helper method to save the document after editing.
   *
   * @param doc      represents the document
   * @param filePath represents the document's location
   * @throws TransformerException if it can't be converted back
   */
  private void saveDocument(Document doc, String filePath)
          throws TransformerException {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(new File(filePath));
    transformer.transform(source, result);
  }

}
