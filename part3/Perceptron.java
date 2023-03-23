package part3;

import java.util.*;

public class Perceptron {
    private List<Instance> instances;
    private List<String> attributes;



    // constructor
    public Perceptron(List<Instance> instances, List<String> attributes) {
        this.instances = instances;
        this.attributes = attributes;
    }

    public void train() {
        // UNTIL THE PERCEPTRON IS ALWAYS RIGHT

        // pRESENT AN EXAMPLE (+VE OR - VE)

        // IF THE EXAMPLE IS CORRECTLY CLASSIFIED, DO NOTHING

        // elseif -ve example and wrong:
        // subtract feature vector from weight vector

        // elseif +ve example and wrong:
        // add feature vector to weight vector

        // END UNTIL

        // return weight vector


    }

    public void test() {
        // for each instance in the test set
        // if the instance is correctly classified
        // increment the number of correct predictions
        // increment the total number of predictions
        // print the accuracy
    }
}
