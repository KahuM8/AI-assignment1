package part3;

import java.util.*;

public class Perceptron {
    private List<Instance> instances;



    // constructor
    public Perceptron(List<Instance> instances) {
        this.instances = instances;
    }

    public double[] train() {
        double[] weights = new double[35];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Math.random();
        }
        double learningRate = 0.1;
        int numEpochs = 200;
        for (int epoch = 1; epoch <= numEpochs; epoch++) {
            double totalError = 0.0;
            for (Instance instance : instances) {
                double output = weights[0]; // Bias term
                for (int i = 0; i < 34; i++) {
                    output += weights[i + 1] * instance.getFeature(i);
                }
                double target = instance.getCategory().equals("g") ? 1.0 : -1.0;
                double error = target - output;
                totalError += Math.abs(error);
                weights[0] += learningRate * error; // Update the bias
                for (int i = 0; i < 34; i++) {
                    weights[i + 1] += learningRate * error * instance.getFeature(i); // Update the
                                                                                     // weights
                }
            }
            System.out.println("Epoch " + epoch + ": total error = " + totalError);
        }
        return weights;
    }

    public void test(List<Instance> testInstances, double[] weights) {
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
        double accuracy = (double) numCorrect / testInstances.size();
        System.out.println("Accuracy on test set: " + accuracy);
    }
}
