package stock;

/**
 * The controller to handle the GUI view.
 */
public class GuiController implements Controller, Features {
  private Model model;
  private IGuiView guiView;
  private String portfolioName;

  /**
   * Constructor for the GuiController.
   * @param model the model to be used
   */
  public GuiController(Model model) {
    this.model = model;
  }

  public void setView(IGuiView v) {
    guiView = v;

    guiView.addFeatures(this);
  }

  @Override
  public void start() {

  }

  @Override
  public void createPortfolio() {
    guiView.clearFields();
    guiView.showPortfolioPanel();
  }

  @Override
  public void submitPortfolio(String name, String symbol,
                              String shares, String month, String day, String year) {
    String date = year + "-" + month + "-" + day;
    portfolioName = name;

    boolean valid = checkValid(symbol, shares, date, true);

    double sharesDouble = Double.parseDouble(shares);

    if (valid) {
      model.addToStock(name, symbol, sharesDouble, date);
      guiView.showSuccessfulPortfolio(name, symbol,
              shares, month, day, year);
    }

  }

  @Override
  public void addStock(String symbol, String shares,
                       String month, String day, String year) {
    String date = year + "-" + month + "-" + day;
    boolean valid = checkValid(symbol, shares, date, false);

    double sharesDouble = Double.parseDouble(shares);

    if (valid) {
      model.addToStock(portfolioName, symbol, sharesDouble, date);
      guiView.showSuccessfulAdd(portfolioName, symbol, shares, month, day, year);
    }
  }

  private boolean checkValid(String symbol, String shares,
                             String date, boolean portfolio) {
    boolean isValid = true;
    if (model.checkValidTicker(symbol)) {
      if (portfolio) {
        guiView.showInvalidSymbol();
        isValid = false;
      } else {
        guiView.showInvalidSymbolAdd();
        isValid = false;
      }
    } else if (!model.checkValidDate(symbol, date)) {
      if (portfolio) {
        guiView.showInvalidDate();
        isValid = false;
      } else {
        guiView.showInvalidDateAdd();
        isValid = false;
      }
    }

    if (model.checkValidShares(shares)) {
      if (portfolio) {
        guiView.showInvalidShares();
        isValid = false;
      } else {
        guiView.showInvalidSharesAdd();
        isValid = false;
      }
    }

    return isValid;
  }

  private boolean checkValidRemove(String symbol,
                                   String shares, String date) {
    boolean isValid = true;
    if (!model.checkValidRemoveTicker(portfolioName, symbol)) {
      guiView.showInvalidSymbolRemove();
      isValid = false;
    } else if (!model.checkValidRemoveDate(portfolioName, symbol, date)) {
      guiView.showInvalidDateRemove();
      isValid = false;
    }

    if (model.checkValidShares(shares)) {
      guiView.showInvalidSharesRemove();
      isValid = false;
    }

    return isValid;
  }

  @Override
  public void removeStock(String symbol, String shares,
                          String month, String day, String year) {
    String date = year + "-" + month + "-" + day;
    boolean valid = checkValidRemove(symbol, shares, date);

    double sharesDouble = Double.parseDouble(shares);

    if (valid) {
      model.removeStock(portfolioName, symbol, sharesDouble, date);
      guiView.showSuccessfulRemove(portfolioName, symbol,
              shares, month, day, year);
    }
  }

  @Override
  public void getComposition(String month, String day, String year) {
    String date = year + "-" + month + "-" + day;

    if (model.isDateBeforeToday(date)) {
      guiView.showCompositionPanel();
      guiView.showSuccessfulComp(model.getComposition(date, portfolioName));
    } else {
      guiView.showInvalidDateComp();
    }

  }

  @Override
  // saving and retrieving portfolio
  public void savePortfolio() {
    guiView.showSave();
    // create pop up
  }

  @Override
  public void retrievePortfolio(String name) {
    portfolioName = name;
    // checks if portfolio exists
    if (model.betterPortfolioExists(name) > -1) {
      guiView.showSuccessfulRetrieve();
      // show button options
      // based on the one that was clicked, call the controller method for it
    }
    else {
      guiView.showInvalidPortof
      // need a invalid popup
    }
    //check is portfolio exists
    //need a popup if it doesn't/invalid
    //retrieve portfolio
    //show options menu



  }

  @Override
  public void showPortfolio() {
    guiView.showRetrieve();
  }

  @Override
  public void goHome() {
    guiView.showHome();
  }

  @Override
  public void showAddStock() {
    clearAdd();
    guiView.showAddStockPanel();
  }

  @Override
  public void showRemoveStock() {
    clearRemove();
    guiView.showRemoveStockPanel();
  }

  @Override
  public void showGetValue() {
    clearValue();
    guiView.showGetValuePanel();
  }

  @Override
  public void showComposition() {
    clearComp();
    guiView.showCompositionPanel();

  }

  @Override
  public void clear() {
    guiView.clearFields();
  }

  @Override
  public void clearAdd() {
    guiView.clearAddFields();
  }

  @Override
  public void clearRemove() {
    guiView.clearRemoveFields();

  }

  @Override
  public void clearValue() {
    guiView.clearValueFields();

  }

  @Override
  public void clearComp() {
    guiView.clearCompFields();
  }

  @Override
  public void getValue(String month, String day, String year) {
    String date = year + "-" + month + "-" + day;

    if (model.isDateBeforeToday(date)) {
      double value = model.getValue(portfolioName, date);
      guiView.showSuccessfulValue(portfolioName, value, month, day, year);
    } else {
      guiView.showInvalidDateValue();
    }

  }

}
