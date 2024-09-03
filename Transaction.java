package stock;

/**
 * Class Transaction representing the
 * transactions that can be supported. Each transaction has
 * a ticker symbol, number of shares, date, and specifies
 * the service (purchase or sale) that took place.
 */
public class Transaction {
  private String ticker;
  private double shares;
  private String date;
  private Service buyOrSell;

  /** Constructs a transaction given the following parameters.
   * @param ticker represents the ticker symbol
   * @param shares represents the number os shares
   * @param date represents the date the transaction took place
   * @param buyOrSell represents whether the transaction was purchase or sale
   */
  public Transaction(String ticker, double shares, String date, Service buyOrSell) {
    this.ticker = ticker;
    this.shares = shares;
    this.date = date;
    this.buyOrSell = buyOrSell;
  }

  /**
   * Getter method that returns the transaction's ticker.
   * @return the ticker symbol
   */
  public String getTicker() {
    return ticker;
  }

  /**
   * Getter method that returns the transaction's shares.
   * @return the number of shares.
   */
  public double getShares() {
    return shares;
  }

  /**
   * Getter method that returns the transaction's date.
   * @return the service date
   */
  public String getServiceDate() {
    return date;
  }

}
