package stock;

/**
 * Represents a single stock that is bought or sold. We changed the design of how to store
 * stock data from Assignment 4 because we needed to include a third field, the date of
 * purchase/removal. We felt the easiest way to represent this would be for the stock to
 * be its own class, and have fields like the name of the ticker of the stock, the number
 * of shares the user wishes to purchase/remove, and the date of purchase/removal.
 */
public class Stock {
  private String ticker;
  private double shares;
  private String date;

  /**
   * Constructs a portfolio given a name,
   * ticker symbol, and shares.
   */
  public Stock(String ticker, double shares, String date) {
    this.ticker = ticker;
    this.shares = shares;
    this.date = date;
  }

  /**
   * Getter method that returns the stock's ticker symbol.
   *
   * @return the field of ticker
   */
  protected String getTicker() {
    return ticker;
  }

  /**
   * Getter method that returns the stock's shares.
   *
   * @return the field of shares
   */
  protected double getShares() {
    return shares;
  }

  /**
   * Getter method that returns the stock's purchase date.
   *
   * @return the field of date
   */
  protected String getDate() {
    return date;
  }

  /**
   * Setter method that changes the stock's number of shares.
   * @param shares represents what to set the shares equal to
   */
  protected void setShares(double shares) {
    this.shares = shares;
  }

}
