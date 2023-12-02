import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private double[][] houseData;
    private int numberOfFeatures;
    private List<Integer> currentSubset;
    private List<Integer> bestSubset;
    private double bestSubsetAccuracy;

    public Main(double[][] houseData) {
        this.houseData = houseData;
        this.numberOfFeatures = houseData[0].length;
        this.currentSubset = new ArrayList<>();
        this.bestSubset = new ArrayList<>();
        this.bestSubsetAccuracy = Double.MIN_VALUE;
    }

    public List<Integer> findBestFeatureSubset() {
        branchAndBound(0);
        return bestSubset;
    }

    private void branchAndBound(int featureIndex) {
        if (featureIndex == numberOfFeatures) {
            // Leaf node
            double currentSubsetAccuracy = getSubsetAccuracy(currentSubset);
            if (currentSubsetAccuracy > bestSubsetAccuracy) {
                bestSubsetAccuracy = currentSubsetAccuracy;
                bestSubset = new ArrayList<>(currentSubset);
            }
            return;
        }

        currentSubset.add(featureIndex);
        branchAndBound(featureIndex + 1);
        currentSubset.remove(currentSubset.size() - 1);

        branchAndBound(featureIndex + 1);
    }

    private double getSubsetAccuracy(List<Integer> subset) {
        double meanSquaredError = 0.0;
        for (double[] house : houseData) {
            double predictedPrice = predictPrice(subset, house);
            double actualPrice = house[house.length - 1];
            meanSquaredError += Math.pow(predictedPrice - actualPrice, 2);
        }
        return meanSquaredError / houseData.length;
    }

    private double predictPrice(List<Integer> subset, double[] house) {
        Random rand = new Random();
        int max=10000000,min=100000;
        return rand.nextInt(rand.nextInt(max - min + 1) + min);
    }

    public static void main(String[] args) {
        // Example house data
        // Square Foot- Num of bedrooms, num of bathrooms, location, distance to public transportation, year built, garage size, yard size, price
        double[][] houseData = {
                {1500, 3, 2, 1, 0.5, 1990, 2, 500, 250000},
                {2000, 4, 3, 2, 1.2, 1985, 2, 700, 350000},
                {1200, 2, 1, 3, 0.8, 2005, 1, 400, 180000},
                {1800, 3, 2, 2, 0.7, 1995, 1, 600, 280000},
                {2200, 4, 3, 1, 1.5, 2000, 2, 800, 400000},
                {1400, 2, 2, 1, 0.6, 1980, 1, 300, 200000},
                {1600, 3, 2, 2, 1.0, 1998, 2, 550, 320000},
                {1900, 4, 3, 3, 1.8, 2008, 2, 750, 450000},
                {1100, 2, 1, 1, 0.4, 1975, 1, 350, 160000},
                {2500, 5, 4, 3, 2.0, 2010, 3, 1000, 550000}
        };

        Main subsetSelection = new Main(houseData);
        List<Integer> bestSubset = subsetSelection.findBestFeatureSubset();
        System.out.println("Best Feature Subset: " + bestSubset);
    }
}
