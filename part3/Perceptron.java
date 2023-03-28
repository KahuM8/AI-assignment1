
import java.util.*;

public class Perceptron {
    private List<Instance> instances;

    // split data
    private List<Instance> sInstances;
    private List<Instance> tInstances;

    // constructor
    public Perceptron(List<Instance> instances, List<Instance> sInstances, List<Instance> tInstances) {
        this.instances = instances;

        // split data
        this.sInstances = sInstances;
        this.tInstances = tInstances;

    }

    public void train() {
        double[] weights = new double[35];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Math.random();
        }
        double learningRate = 0.1;
        int count = 0;
        while (test(instances, weights) < 0.95 && count < 10000) {
            for (Instance instance : instances) {
                double output = weights[0]; // Bias term
                for (int i = 0; i < 34; i++) {
                    output += weights[i + 1] * instance.getFeature(i);
                }
                double y = output > 0 ? 1 : 0;
                double d = instance.getCategory().equals("g") ? 1 : 0;

                if (y == 0 && d == 1) {
                    weights[0] += learningRate;
                    for (int i = 0; i < 34; i++) {
                        weights[i + 1] += instance.getFeature(i) * learningRate;
                    }
                }
                if (y == 1 && d == 0) {
                    weights[0] -= learningRate;
                    for (int i = 0; i < 34; i++) {
                        weights[i + 1] -= instance.getFeature(i) * learningRate;
                    }
                }

                if (test(instances, weights) > 0.95) {
                    break;
                }

            }
            if (count % 20 == 0) {
                System.out.println("Epoch " + count + ": bias = " + weights[0] + " Accuracy: "
                        + test(instances, weights));
            }
            count++;
        }
        System.out.println("Epoch " + count + ": bias = " + weights[0]);

        System.out.println("Accuracy: " + test(instances, weights));
        System.out.println("Final Weights: ");
        for (int i = 0; i < weights.length; i++) {
            System.out.print(weights[i] + " ");
        }
    }

    public double test(List<Instance> testInstances, double[] weights) {
        int numCorrect = 0;
        for (Instance instance : testInstances) {
            double output = weights[0]; // Bias term
            for (int i = 0; i < 34; i++) {
                output += weights[i + 1] * instance.getFeature(i);
            }
            String predictedCategory = output > 0 ? "g" : "b";
            if (predictedCategory.equals(instance.getCategory())) {
                numCorrect++;
            }
        }
        // print final weights

        return (double) numCorrect / testInstances.size();

    }

    public void trainSplit() {
        double[] weights = new double[35];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Math.random();
        }
        double learningRate = 0.1;
        int count = 0;
        while (count < 200) {
            for (Instance instance : sInstances) {
                double output = weights[0]; // Bias term
                for (int i = 0; i < 34; i++) {
                    output += weights[i + 1] * instance.getFeature(i);
                }
                double y = output > 0 ? 1 : 0;
                double d = instance.getCategory().equals("g") ? 1 : 0;

                if (y == 0 && d == 1) {
                    weights[0] += learningRate;
                    for (int i = 0; i < 34; i++) {
                        weights[i + 1] += instance.getFeature(i) * learningRate;
                    }
                }
                if (y == 1 && d == 0) {
                    weights[0] -= learningRate;
                    for (int i = 0; i < 34; i++) {
                        weights[i + 1] -= instance.getFeature(i) * learningRate;
                    }
                }

            }
            if (count % 20 == 0) {
                System.out.println("Epoch " + count + ": bias = " + weights[0] + " Accuracy: "
                        + testSplit(weights));
            }
            count++;
        }
        System.out.println("Epoch " + count + ": bias = " + weights[0]);

        System.out.println("Accuracy: " + testSplit(weights));
        System.out.println("Final Weights: ");
        for (int i = 0; i < weights.length; i++) {
            System.out.print(weights[i] + " ");
        }
    }

    public double testSplit(double[] weights) {
        int numCorrect = 0;
        for (Instance instance : tInstances) {
            double output = weights[0]; // Bias term
            for (int i = 0; i < 34; i++) {
                output += weights[i + 1] * instance.getFeature(i);
            }
            String predictedCategory = output > 0 ? "g" : "b";
            if (predictedCategory.equals(instance.getCategory())) {
                numCorrect++;
            }
        }
        // print final weights

        return (double) numCorrect / tInstances.size();

    }

    // checking something with git 2

    // git check 3
}
