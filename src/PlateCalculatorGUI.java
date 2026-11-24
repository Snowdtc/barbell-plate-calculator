import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PlateCalculatorGUI extends JFrame {
    private JTextField targetWeightField;
    private JTextField barWeightField;
    private JButton calculateButton;
    private JTextArea resultArea;

    public PlateCalculatorGUI() {
        setTitle("Barbell Plate Calculator");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window on screen

        // Main container with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Input panel (Target Weight, Bar Weight, Calculate Button)
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        inputPanel.add(new JLabel("Target Weight (lbs):"));
        targetWeightField = new JTextField();
        inputPanel.add(targetWeightField);

        inputPanel.add(new JLabel("Bar Weight (lbs):"));
        barWeightField = new JTextField("45");
        inputPanel.add(barWeightField);

        inputPanel.add(new JLabel("")); // Empty spacer
        calculateButton = new JButton("Calculate");
        inputPanel.add(calculateButton);

        // Output area for displaying plates
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setText("Enter your weights and click Calculate.");
        JScrollPane scrollPane = new JScrollPane(resultArea);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }
}