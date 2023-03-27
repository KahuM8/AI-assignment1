package part1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Parser {
    public static void main(String[] args) throws IOException {
        int k = 3;
        List<Wine> testWine = parse(new File(args[0]));
        List<Wine> trainWine = parse(new File(args[1]));
        normilize(testWine, trainWine);
        int correctPredictions = 0;
        int totalPredictions = 0;

        for (Wine test : testWine) {
            float[] minDistances = new float[k];
            Wine[] closestWines = new Wine[k];
            Arrays.fill(minDistances, Float.MAX_VALUE);
            for (Wine train : trainWine) {
                float distance = calcDistance(test, train);
                for (int i = 0; i < minDistances.length; i++) {
                    if (distance < minDistances[i]) {
                        for (int j = minDistances.length - 1; j > i; j--) {
                            minDistances[j] = minDistances[j - 1];
                            closestWines[j] = closestWines[j - 1];
                        }
                        minDistances[i] = distance;
                        closestWines[i] = train;
                        break;
                    }
                }
            }
            if (predict(closestWines) + 1 == test.type()) {
                correctPredictions++;
            }
            totalPredictions++;
        }
        System.out.println("Accuracy: " + calcAccuracy(correctPredictions, totalPredictions));
    }

    /**
     * Parses the wine data from the file
     * 
     * @param file
     * @return returns a list of wines
     */
    public static List<Wine> parse(File file) {
        List<Wine> wineList = new ArrayList<>();
        try {
            Scanner sc = new Scanner(file);
            sc.nextLine();
            while (sc.hasNextLine()) {

                float[] attributes = new float[13];
                for (int i = 0; i < attributes.length; i++) {
                    attributes[i] = sc.nextFloat();
                }
                Wine wine = new Wine(attributes, sc.nextInt());
                wineList.add(wine);

            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return wineList;
    }

    /**
     * Calculates the distance between two wines using the Euclidean distance
     * 
     * @param wine1
     * @param wine2
     * @return returns the distance between the two wines
     */
    public static float calcDistance(Wine wine1, Wine wine2) {
        float distance = 0;
        for (int i = 0; i < wine1.attributes().length; i++) {
            distance += Math.pow(wine1.attributes()[i] - wine2.attributes()[i], 2);
        }
        return distance;
    }

    /**
     * Predicts the type of wine based on the closest wines
     * 
     * @param closestWines
     * @return returns the predicted type of wine
     */
    public static int predict(Wine[] closestWines) {
        int[] counts = new int[3];
        for (Wine wine : closestWines) {
            counts[wine.type() - 1]++;
        }
        int maxIndex = 0;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > counts[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    /**
     * Calculates the accuracy of the predictions
     * 
     * @param correctPredictions
     * @param totalPredictions
     * @return returns the accuracy of the predictions
     */
    public static float calcAccuracy(int correctPredictions, int totalPredictions) {
        System.out.println("Correct Predictions: " + correctPredictions);
        System.out.println("Total Predictions: " + totalPredictions);
        return (float) correctPredictions / totalPredictions * 100;
    }

    /**
     * Normalizes the wines data to the training set
     * 
     * @param testWine
     * @param trainWine
     */
    public static void normilize(List<Wine> testWine, List<Wine> trainWine) {
        float[] mins = new float[trainWine.get(0).attributes().length];
        float[] maxes = new float[trainWine.get(0).attributes().length];
        Arrays.fill(maxes, Float.MIN_VALUE);
        Arrays.fill(mins, Float.MAX_VALUE);
        for (Wine wine : trainWine) {
            for (int i = 0; i < wine.attributes().length; i++) {
                if (wine.attributes()[i] < mins[i]) {
                    mins[i] = wine.attributes()[i];
                }
                if (wine.attributes()[i] > maxes[i]) {
                    maxes[i] = wine.attributes()[i];
                }
            }
        }
        for (int i = 0; i < testWine.size(); i++) {
            for (int j = 0; j < testWine.get(i).attributes().length; j++) {
                trainWine.get(i).attributes()[j] = (trainWine.get(i).attributes()[j] / (maxes[j] - mins[j]));
                testWine.get(i).attributes()[j] = (testWine.get(i).attributes()[j] / (maxes[j] - mins[j]));
            }
        }
    }

    public record Wine(
            float[] attributes,
            int type) {
    }

}
