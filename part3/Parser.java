package part3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Parser {



    public static void main(String[] args) {
        // read in the file ionosphere.data
        Scanner sc;
        List<Instance> instances = new ArrayList<>();
        List<String> attributes = new ArrayList<>();
        try {
            sc = new Scanner(new File("part3/ionosphere.data"));
            Scanner l1 = new Scanner(sc.nextLine());
            while (l1.hasNext()) {
                attributes.add(l1.next());
            }
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
        // print each of the instances in the list and say im here
        for (Instance i : instances) {
            System.out.println(i);
        }

        // train the perceptron
        Perceptron p = new Perceptron(instances, attributes);
        p.train();
        // test the perceptron
        p.test();
    }


}
