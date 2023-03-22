import java.util.*;

public class DT {
    // a desision tree using the hepatits data set
    public static void main(String[] args) {
        DT dt = new DT();
        dt.run();
    }

    private void run() {
        DataReader dr = new DataReader();
        dr.readDataFile("part2/hepatitis-training");
        List<String> attributes = dr.getALlAtributes();
        List<Instance> instances = dr.getInstances();
        Set<Instance> instanceSet = new HashSet<>(instances);

        DTBuilder dtb = new DTBuilder(instanceSet, attributes);

        // print the tree

    }
}
