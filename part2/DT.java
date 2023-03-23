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
        DataReader dTest = new DataReader();
        Trainattributes = dTrain.getALlAtributes();
        Traininstances = dTrain.getInstances();
        TraininstanceSet = new HashSet<>(Traininstances);

        dTest.readDataFile("part2/hepatitis-test");
        testAtts = dTrain.getALlAtributes();
        testinst = dTrain.getInstances();
        testinstSet = new HashSet<>(testinst);

        DTBuilder dtb = new DTBuilder(TraininstanceSet, Trainattributes);
        root = dtb.buildTree(TraininstanceSet, Trainattributes);
        root.report("");

        DTBuilder dtbTest = new DTBuilder(testinstSet, testAtts);
        testRoot = dtbTest.buildTree(testinstSet, testAtts);
        testRoot.report("");

        int count = 0;
        for (Instance instance : dTest.allInstances) {
            String makePred = getPrediction(instance, testRoot);
            if (makePred.equals(instance.getCategory())) {
                count++;
            }
        }
        System.out.println("accuracy = " + count / dTest.allInstances.size());

        // print the tree

    }

    public String getPrediction(Instance testCase, DTNode testRoot) {
        DTNode current = testRoot;
        while (!(current instanceof DTLeaf)) {
            String attribute = current.getATT();
            int index = Trainattributes.indexOf(attribute);
            if (index >= 0) {
                boolean val = testCase.getAtt(index);
                if (val) {
                    current = current.getYes();
                } else {
                    current = current.getNo();
                }
            }
        }
        return current.getATT();
    }
}
