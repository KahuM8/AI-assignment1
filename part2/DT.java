import java.util.*;

public class DT {
    // a desision tree using the hepatits data set

    List<String> Trainattributes;
    List<Instance> Traininstances;
    Set<Instance> TraininstanceSet;

    List<String> testAtts;
    List<Instance> testinst;
    Set<Instance> testinstSet;

    public static void main(String[] args) {
        DT dt = new DT();
        dt.run();
    }

    DTNode root;
    DTNode testRoot;

    private void run() {
        DataReader dTrain = new DataReader();
        dTrain.readDataFile("part2/hepatitis-training");
        Trainattributes = dTrain.getALlAtributes();
        Traininstances = dTrain.getInstances();
        TraininstanceSet = new HashSet<>(Traininstances);


        DTBuilder dtb = new DTBuilder(TraininstanceSet, Trainattributes);
        root = dtb.buildTree(TraininstanceSet, Trainattributes);
        root.report("");

        DataReader dTest = new DataReader();
        dTest.readDataFile("part2/hepatitis-test");
        testAtts = dTest.getALlAtributes();
        testinst = dTest.getInstances();


        DTBuilder dtb2 = new DTBuilder(testinstSet, testAtts);
        testRoot = dtb2.buildTree(new HashSet<>(testinst), testAtts);

        // print all test attributes
        for (String s : testAtts) {
            System.out.println(s);
        }



        // make predictions
        int correct = 0;
        int total = 0;
        for (Instance instance : testinst) {
            String prediction = getPrediction(instance, testRoot);
            if (prediction.equals(instance.getCategory())) {
                correct++;
            }
            total++;
        }
        System.out.println("Correct: " + correct + " Total: " + total);
        System.out.println("Accuracy: " + (double) correct / total);
    }

    public String getPrediction(Instance testCase, DTNode root) {
        if (testRoot instanceof DTLeaf) {
            return ((DTLeaf) root).getCategory();
        } else {
            DTNode internalNode = (DTNode) root;
            String attName = internalNode.getATT();
            int attIndex = -1;
            for (int i = 0; i < testAtts.size(); i++) {
                if (testAtts.get(i).equals(attName)) {
                    attIndex = i;
                    break;
                }
            }
            System.out.println("attIndex: " + attIndex);
            boolean attValue = testCase.getAtt(attIndex);
            if (attValue) {
                return getPrediction(testCase, internalNode.getYes());
            } else {
                return getPrediction(testCase, internalNode.getNo());
            }
        }
    }

}
