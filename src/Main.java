import java.util.Map;

public class Main {
    public static void main(String[] args) {
        PlateCalculator calc = new PlateCalculator();

        double[] testWeights = {45, 135, 185, 225, 250};
        double barWeight = 45.0;

        for (double target : testWeights) {
            System.out.println("=== Target: " + target + " lbs (Bar: " + barWeight + " lbs) ===");
            Map<Double, Integer> result = calc.calculatePlates(target, barWeight);
            System.out.println(calc.formatResult(result));
        }
    }
}