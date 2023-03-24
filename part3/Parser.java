package part3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Parser {

    public static void main(String[] args) {
        // read in the file ionosphere.data
        Scanner sc;
        List<Instance> instances = new ArrayList<>();
        try {
            sc = new Scanner(new File("part3/ionosphere.data"));
            sc.nextLine();
            while (sc.hasNextLine()) {
                Scanner line = new Scanner(sc.nextLine());
                List<Double> features = new ArrayList<>();
                while (line.hasNextDouble()) {
                    features.add(line.nextDouble());
                }
                String category = line.next();
                instances.add(new Instance(category, features));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // train the perceptron
        Perceptron p = new Perceptron(instances);
        p.train();
    }

}
