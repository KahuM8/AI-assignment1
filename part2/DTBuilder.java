import java.util.*;

public class DTBuilder {
    Set<Instance> allInstances;

    // constructor
    public DTBuilder(Set<Instance> allInstances, List<String> attriburtes) {
        this.allInstances = allInstances;
        buildTree(allInstances, attriburtes);
    }

    public DTNode buildTree(Set<Instance> instances, List<String> attributes) {
        if (instances.isEmpty()) {
            return new DTLeaf(null, mostCatoagories(allInstances));
        }
        if (attributes.isEmpty() || isPure(instances)) {
            return new DTLeaf(instances.iterator().next().getCategory(), 1);
        } else {
            return chooseNode(instances, attributes);
        }
    }

    public DTNode chooseNode(Set<Instance> instances, List<String> attriburtes) {
        Set<Instance> bestTSet = new HashSet<>();
        Set<Instance> BestFSet = new HashSet<>();
        String bestATT = "";
        double bestImp = Double.MAX_VALUE;
        for (int i = 0; i < attriburtes.size(); i++) {
            Set<Instance> trueInstances = new HashSet<>();
            Set<Instance> falseInstances = new HashSet<>();
            for (Instance instance : instances) {
                if (instance.getAtt(i)) {
                    trueInstances.add(instance);
                    continue;
                }
                falseInstances.add(instance);
            }
            double impur = computeImpur(trueInstances, falseInstances);
            if (impur < bestImp) {
                bestImp = impur;
                bestATT = attriburtes.get(i);
                bestTSet = trueInstances;
                BestFSet = falseInstances;
            }
        }
        attriburtes.remove(bestATT);
        DTNode left = buildTree(bestTSet, attriburtes);
        DTNode right = buildTree(BestFSet, attriburtes);
        return new DTNode(bestATT, left, right);
    }

    // overall most common node calc
    public double mostCatoagories(Set<Instance> instances) {
        int liveC = 0;
        int dieC = 0;
        for (Instance instance : instances) {
            if (instance.getCategory().equals("live")) {
                liveC++;
                break;
            }
            dieC++;
        }
        return liveC > dieC ? liveC / instances.size() : dieC / instances.size();

    }

    // is pure
    public boolean isPure(Set<Instance> instances) {
        String category = null;
        for (Instance instance : instances) {
            if (category == null) {
                category = instance.getCategory();
            } else if (!category.equals(instance.getCategory())) {
                return false;
            }
        }
        return true;

    }

    public double computeImpur(Set<Instance> trues, Set<Instance> falses) {
        double dt = (trues.size()) / (trues.size() + falses.size());
        double df = 1.0 - dt;
        return dt * computePurity(trues) + df * computePurity(falses);

    }

    public double computePurity(Set<Instance> instances) {
        if (instances.isEmpty()) {
            return 0;
        }
        int catTotals[] = new int[2];
        for (Instance instance : instances) {
            if (instance.getCategory().equals("live")) {
                catTotals[0]++;
                continue;
            }
            catTotals[1]++;
        }
        return catTotals[0] / instances.size() + catTotals[1] / instances.size();

    }

}