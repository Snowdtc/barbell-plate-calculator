import java.util.LinkedHashMap;
import java.util.Map;

public class PlateCalculator {
    // Standard gym plates, ordered heaviest to lightest
    private static final double[] STANDARD_PLATES_LBS = {45.0, 35.0, 25.0, 10.0, 5.0, 2.5};
    private static final double[] STANDARD_PLATES_KG = {25.0, 20.0, 15.0, 10.0, 5.0, 2.5, 1.25};

    public Map<Double, Integer> calculatePlates(double targetWeight, double barWeight) {
        return calculatePlates(targetWeight, barWeight, false);
    }

    /**
     * Calculates the plates needed for ONE side of the barbell using a greedy algorithm.
     */
    public Map<Double, Integer> calculatePlates(double targetWeight, double barWeight, boolean isMetric) {
        if (targetWeight < barWeight) {
            throw new IllegalArgumentException("Target weight cannot be less than the bar weight.");
        }

        double[] availablePlates = isMetric ? STANDARD_PLATES_KG : STANDARD_PLATES_LBS;
        double weightPerSide = (targetWeight - barWeight) / 2.0;
        Map<Double, Integer> platesPerSide = new LinkedHashMap<>();

        for (double plate : availablePlates) {
            int count = (int) (weightPerSide / plate);
            if (count > 0) {
                platesPerSide.put(plate, count);
                weightPerSide -= count * plate;
            }
        }

        return platesPerSide;
    }

    public String formatResult(Map<Double, Integer> plates, boolean isMetric) {
        if (plates.isEmpty()) {
            return "No plates needed (just the bar).";
        }

        String unit = isMetric ? "kg" : "lb";
        StringBuilder sb = new StringBuilder("Plates per side:\n");
        for (Map.Entry<Double, Integer> entry : plates.entrySet()) {
            double weight = entry.getKey();
            int count = entry.getValue();
            String weightStr = (weight % 1 == 0) ? String.valueOf((int) weight) : String.valueOf(weight);
            sb.append("- ").append(count).append(" x ").append(weightStr).append(" ").append(unit).append("\n");
        }
        return sb.toString();
    }
}