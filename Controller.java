package stock;

/**
 * The Controller interface for the MVC model.
 * Contains the start method that runs the program.
 */
public interface Controller {

  /**
   * Executes the program and calls on other methods to
   * pass information between the model and view
   */
  void start();

  /**
   * Method that changes the view.
   *
   * @param view represents the view being taken in
   */
  void setView(IGuiView view);


}
