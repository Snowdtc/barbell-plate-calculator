import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class PlateCalculatorGUI extends JFrame {
    private static final String CONFIG_FILE = "config.txt";

    private JComboBox<String> unitDropdown;
    private JLabel targetWeightLabel;
    private JLabel barWeightLabel;
    private JTextField targetWeightField;
    private JTextField barWeightField;
    private JButton calculateButton;
    private JTextArea resultArea;
    private PlateCalculator calculator;

    public PlateCalculatorGUI() {
        calculator = new PlateCalculator();

        setTitle("Barbell Plate Calculator");
        setSize(420, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main container with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Input panel (4 rows now to fit Unit selector)
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        inputPanel.add(new JLabel("Unit:"));
        unitDropdown = new JComboBox<>(new String[]{"lbs", "kg"});
        inputPanel.add(unitDropdown);

        targetWeightLabel = new JLabel("Target Weight (lbs):");
        inputPanel.add(targetWeightLabel);
        targetWeightField = new JTextField();
        inputPanel.add(targetWeightField);

        barWeightLabel = new JLabel("Bar Weight (lbs):");
        inputPanel.add(barWeightLabel);
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

        // Listeners
        unitDropdown.addActionListener(e -> handleUnitChange());
        calculateButton.addActionListener(e -> handleCalculate());

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void handleUnitChange() {
        boolean isMetric = isMetricSelected();
        String unit = isMetric ? "kg" : "lbs";

        targetWeightLabel.setText("Target Weight (" + unit + "):");
        barWeightLabel.setText("Bar Weight (" + unit + "):");

        // Automatically swap standard bar weight if currently at the other unit's default
        String currentBar = barWeightField.getText().trim();
        if (isMetric && currentBar.equals("45")) {
            barWeightField.setText("20");
        } else if (!isMetric && currentBar.equals("20")) {
            barWeightField.setText("45");
        }
    }

    private boolean isMetricSelected() {
        return "kg".equals(unitDropdown.getSelectedItem());
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

        boolean isMetric = isMetricSelected();
        if (!isMetric) {
            saveDefaultBarWeight(barWeight);
        }

        Map<Double, Integer> plates = calculator.calculatePlates(targetWeight, barWeight, isMetric);
        resultArea.setText(calculator.formatResult(plates, isMetric));
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
                Double.parseDouble(saved);
                return saved;
            }
        } catch (Exception e) {
            // Fall back to default
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
            // Non-fatal
        }
    }
}