import java.util.*;

public class DT {
    // a desision tree using the hepatits data set

    List<String> Trainattributes;
    List<Instances> Traininstances;
    Set<Instances> TraininstanceSet;

    List<String> testAtts;
    List<Instances> testinst;
    Set<Instances> testinstSet;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java DT <train file> <test file>");
            System.exit(1);
        }
        DT dt = new DT();

        dt.run(args);
    }

    DTNode root;
    DTNode testRoot;

    private void run(String[] args) {
        helper dTrain = new helper();
        dTrain.readDataFile(args[0]);
        Trainattributes = dTrain.getALlAtributes();
        Traininstances = dTrain.getInstances();
        TraininstanceSet = new HashSet<>(Traininstances);

        DTBuilder dtb = new DTBuilder(TraininstanceSet, Trainattributes);
        root = dtb.buildTree(TraininstanceSet, new ArrayList<>(Trainattributes));
        root.report("");

        helper dTest = new helper();
        dTest.readDataFile(args[1]);
        testAtts = dTest.getALlAtributes();
        testinst = dTest.getInstances();

        // make predictions
        int correct = 0;
        int total = 0;
        for (Instances instance : testinst) {
            String prediction = getPrediction(instance, root);
            if (prediction.equals(instance.getCategory())) {
                correct++;
            }
            total++;
        }
        System.out.println("Correct: " + correct + " Total: " + total);
        System.out.println("Accuracy: " + (double) correct / total);
    }

    public String getPrediction(Instances testCase, DTNode root) {
        if (root instanceof DTLeaf) {
            return ((DTLeaf) root).getCategory();
        } else {
            DTNode internalNode = (DTNode) root;
            String attName = internalNode.getATT();
            int attIndex = testAtts.indexOf(attName);
            boolean attValue = testCase.getAtt(attIndex);
            if (attValue) {
                return getPrediction(testCase, internalNode.getYes());
            } else {
                return getPrediction(testCase, internalNode.getNo());
            }
        }
    }

}
