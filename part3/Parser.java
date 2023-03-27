
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Parser {

    public static void main(String[] args) {

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
        Perceptron p = new Perceptron(instances);
        p.train();
    }

}
