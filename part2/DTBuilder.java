import java.util.*;

public class DTBuilder {
    Set<Instance> allInstances;

    // constructor
    public DTBuilder(Set<Instance> allInstances, List<String> attriburtes) {
        this.allInstances = allInstances;
        
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
        double bestImp = Double.POSITIVE_INFINITY;
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

    public double mostCatoagories(Set<Instance> instances) {
        int liveC = 0;
        int dieC = 0;
        for (Instance instance : instances) {
            if (instance.getCategory().equals("live")) {
                liveC++;
            } else {
                dieC++;
            }
        }
        return liveC > dieC ? (double) liveC / instances.size() : (double) dieC / instances.size();
    }

    public boolean isPure(Set<Instance> instances) {
        String category = instances.iterator().next().getCategory();
        for (Instance instance : instances) {
            if (!category.equals(instance.getCategory())) {
                return false;
            }
        }
        return true;
    }

    public double computeImpur(Set<Instance> trues, Set<Instance> falses) {
        double dt = ((double) trues.size()) / (trues.size() + falses.size());
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

        return ((double) catTotals[0] / instances.size()) * ((double) catTotals[1] / instances.size());
    }

}