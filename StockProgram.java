package stock;

import java.io.InputStreamReader;

/**
 * Starting point of our stock program.
 * This contains the main method that begins
 * our stock program.
 */
public class StockProgram {

  /**
   * The main method that runs the program using a mvc.
   *
   * @param args a default argument that is unused.
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      // Start the GUI interface
      startGui();
    } else if (args.length == 1 && args[0].equals("-text")) {
      // Start the text-based interface
      startText();
    } else {
      // Invalid arguments
      System.err.println("Invalid command-line arguments.");
      System.err.println("Usage:");
      System.err.println("  java -jar Program.jar           // for GUI interface");
      System.err.println("  java -jar Program.jar -text     // for text-based interface");
      System.exit(1);
    }
  }

  private static void startGui() {
    // Start the GUI interface
    IGuiView guiView = new GuiView();
    Model model = new StockModel(); // Initial empty model
    Controller guiController = new GuiController(model);
    guiController.setView(guiView);
  }

  private static void startText() {
    IStockView stockView = new StockView();
    Readable r = new InputStreamReader(System.in);
    Appendable a = System.out;
    Model model = new StockModel(); // Initial empty model
    Controller textController = new StockController(model, stockView, r, a);
    textController.start();
  }


}
