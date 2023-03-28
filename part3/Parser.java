
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Parser {

    public static void main(String[] args) {

        if (args.length == 2) {
            split(args);
            return;
        }
        if (args.length != 1) {
            System.out.println("Usage: java Parser.java <filename>");
            System.exit(1);
        }
        // read in the file ionosphere.data
        Scanner sc;
        List<Instance> instances = new ArrayList<>();
        try {
            sc = new Scanner(new File(args[0]));
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
        Perceptron p = new Perceptron(instances, null, null);
        p.train();
    }

    // split data run
    public static void split(String[] args) {
        // read from train and test and splittrain and test
        Scanner sc;
        Scanner sc2;
        List<Instance> instances = new ArrayList<>();
        List<Instance> instances2 = new ArrayList<>();

        try {
            sc = new Scanner(new File(args[0]));
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

        try {
            sc2 = new Scanner(new File(args[1]));
            sc2.nextLine();
            while (sc2.hasNextLine()) {
                Scanner line = new Scanner(sc2.nextLine());
                List<Double> features = new ArrayList<>();
                while (line.hasNextDouble()) {
                    features.add(line.nextDouble());
                }
                String category = line.next();
                instances2.add(new Instance(category, features));
            }
            sc2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // train the perceptron
        Perceptron p = new Perceptron(null, instances, instances2);
        p.trainSplit();
    }

}
