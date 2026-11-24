import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PlateCalculatorGUI gui = new PlateCalculatorGUI();
            gui.setVisible(true);
        });
    }
}