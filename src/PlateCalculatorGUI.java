import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class PlateCalculatorGUI extends JFrame {
    private static final String CONFIG_FILE = "config.txt";

    private JTextField targetWeightField;
    private JTextField barWeightField;
    private JButton calculateButton;
    private JTextArea resultArea;
    private PlateCalculator calculator;

    public PlateCalculatorGUI() {
        calculator = new PlateCalculator();

        setTitle("Barbell Plate Calculator");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main container with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        inputPanel.add(new JLabel("Target Weight (lbs):"));
        targetWeightField = new JTextField();
        inputPanel.add(targetWeightField);

        inputPanel.add(new JLabel("Bar Weight (lbs):"));
        barWeightField = new JTextField(loadDefaultBarWeight());
        inputPanel.add(barWeightField);

        inputPanel.add(new JLabel(""));
        calculateButton = new JButton("Calculate");
        inputPanel.add(calculateButton);

        // Output area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setText("Enter your weights and click Calculate.");
        JScrollPane scrollPane = new JScrollPane(resultArea);

        calculateButton.addActionListener(e -> handleCalculate());

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void handleCalculate() {
        String targetText = targetWeightField.getText().trim();
        String barText = barWeightField.getText().trim();

        if (targetText.isEmpty() || barText.isEmpty()) {
            showError("Please enter both a target weight and a bar weight.");
            return;
        }

        double targetWeight;
        double barWeight;
        try {
            targetWeight = Double.parseDouble(targetText);
            barWeight = Double.parseDouble(barText);
        } catch (NumberFormatException ex) {
            showError("Invalid input. Please enter valid numbers.");
            return;
        }

        if (targetWeight <= 0 || barWeight <= 0) {
            showError("Weights must be greater than zero.");
            return;
        }

        if (targetWeight < barWeight) {
            showError("Target weight cannot be less than the bar weight.");
            return;
        }

        saveDefaultBarWeight(barWeight);
        Map<Double, Integer> plates = calculator.calculatePlates(targetWeight, barWeight);
        resultArea.setText(calculator.formatResult(plates));
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    private String loadDefaultBarWeight() {
        File file = new File(CONFIG_FILE);
        if (!file.exists()) {
            return "45";
        }
        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextLine()) {
                String saved = scanner.nextLine().trim();
                Double.parseDouble(saved); // Verify it's a valid number
                return saved;
            }
        } catch (Exception e) {
            // Fall back to default if file is unreadable or corrupted
        }
        return "45";
    }

    private void saveDefaultBarWeight(double barWeight) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CONFIG_FILE))) {
            if (barWeight % 1 == 0) {
                writer.println((int) barWeight);
            } else {
                writer.println(barWeight);
            }
        } catch (IOException e) {
            // Non-fatal: app still works even if config can't be saved
        }
    }
}