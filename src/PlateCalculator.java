import java.util.LinkedHashMap;
import java.util.Map;

public class PlateCalculator {
    // Standard gym plates in lbs, ordered heaviest to lightest
    private static final double[] STANDARD_PLATES_LBS = {45.0, 35.0, 25.0, 10.0, 5.0, 2.5};

    /**
     * Calculates the plates needed for ONE side of the barbell using a greedy algorithm.
     * Returns a map of Plate Weight -> Count per side.
     */
    public Map<Double, Integer> calculatePlates(double targetWeight, double barWeight) {
        if (targetWeight < barWeight) {
            throw new IllegalArgumentException("Target weight cannot be less than the bar weight.");
        }

        double weightPerSide = (targetWeight - barWeight) / 2.0;
        Map<Double, Integer> platesPerSide = new LinkedHashMap<>();

        for (double plate : STANDARD_PLATES_LBS) {
            int count = (int) (weightPerSide / plate);
            if (count > 0) {
                platesPerSide.put(plate, count);
                weightPerSide -= count * plate;
            }
        }

        return platesPerSide;
    }

    /**
     * Formats the plate map into a readable string for display.
     */
    public String formatResult(Map<Double, Integer> plates) {
        if (plates.isEmpty()) {
            return "No plates needed (just the bar).";
        }

        StringBuilder sb = new StringBuilder("Plates per side:\n");
        for (Map.Entry<Double, Integer> entry : plates.entrySet()) {
            double weight = entry.getKey();
            int count = entry.getValue();
            // Print whole numbers without .0 (e.g., 45 instead of 45.0), keep 2.5 as 2.5
            String weightStr = (weight % 1 == 0) ? String.valueOf((int) weight) : String.valueOf(weight);
            sb.append("- ").append(count).append(" x ").append(weightStr).append(" lb\n");
        }
        return sb.toString();
    }
}