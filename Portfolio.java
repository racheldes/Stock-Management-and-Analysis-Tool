//package stock;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
///**
// * Object of a Portfolio type.
// * Represents a portfolio of one or more stocks.
// */
//public class Portfolio {
//  private String name;
//  private Map<String, Integer> stocks;
//
//  /**
//   * Constructs a portfolio given a name,
//   * ticker symbol, and shares.
//   */
//  public Portfolio(String name, String ticker, int shares) {
//    this.name = name;
//    stocks = new HashMap<String, Integer>();
//    stocks.put(ticker, shares);
//  }
//
//  /**
//   * Constructs a portfolio given just a name.
//   */
//  public Portfolio(String name) {
//    this.name = name;
//    stocks = new HashMap<String, Integer>();
//  }
//
//  /**
//   * Getter method that returns the Portfolio's name.
//   *
//   * @return the field of name
//   */
//  protected String getName() {
//    return name;
//  }
//
//  /**
//   * Getter method that returns tha Portfolio's stocks.
//   *
//   * @return the field of stocks
//   */
//  protected Map<String, Integer> getStocks() {
//    return stocks;
//  }
//
//  /**
//   * Method that adds a new Stock to a portfolio based
//   * on whether the stock already exists.
//   *
//   * @param ticker   represents the ticker symbol of the stock
//   * @param quantity represents the amount of shares being added
//   */
//  public void addNewStock(String ticker, int quantity) {
//    // doesn't contain key
//    if (stocks.containsKey(ticker)) {
//      for (Map.Entry<String, Integer> entry : stocks.entrySet()) {
//        if (entry.getKey().equals(ticker)) {
//          stocks.put(entry.getKey(), quantity + entry.getValue());
//        }
//      }
//
//    } else {
//      stocks.put(ticker, quantity);
//      //stocks.put(ticker, stocks.get(ticker) + quantity);
//    }
//    // check if the company already exists
//    // if it does add the quantity to the existing stock
//    // else add the stock to the map of stocks
//  }
//
//  @Override
//  public boolean equals(Object o) {
//    if (this == o) return true;
//    if (o == null || getClass() != o.getClass()) return false;
//    Portfolio portfolio = (Portfolio) o;
//    return Objects.equals(name, portfolio.name) &&
//            Objects.equals(stocks, portfolio.stocks);
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(name, stocks);
//  }
//}
