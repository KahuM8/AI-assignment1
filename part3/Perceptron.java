
import java.util.*;

public class Perceptron {
    private List<Instance> instances;

    // constructor
    public Perceptron(List<Instance> instances) {
        this.instances = instances;
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
        return (double) numCorrect / testInstances.size();

    }

    // checking something with git 2

    // git check 3
}
