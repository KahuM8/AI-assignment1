import java.util.*;

public class DTBuilder {
    Set<Instances> allInstances;
    List<String> allAtts;
    int correctPreds = 0;
    int totalPreds = 0;

    // constructor
    public DTBuilder(Set<Instances> allInstances, List<String> attriburtes) {
        this.allInstances = allInstances;
        this.allAtts = attriburtes;

    }

    public DTNode buildTree(Set<Instances> instances, List<String> attributes) {
        if (instances.isEmpty()) {
            return new DTLeaf(mostCat(allInstances), mostCatoagories(allInstances));
        } else if (isPure(instances)) {
            return new DTLeaf(instances.iterator().next().getCategory(), 1);
        } else if (attributes.isEmpty()) {
            return new DTLeaf(mostCat(instances), mostCatoagories(instances));
        } else {
            return chooseNode(instances, attributes);
        }
    }

    public DTNode chooseNode(Set<Instances> instances, List<String> attriburtes) {
        Set<Instances> bestTSet = new HashSet<>();
        Set<Instances> BestFSet = new HashSet<>();
        String bestATT = "";
        double bestImp = Double.POSITIVE_INFINITY;
        for (int i = 0; i < allAtts.size(); i++) {
            if (!attriburtes.contains(allAtts.get(i))) {
                continue;
            }
            Set<Instances> trueInstances = new HashSet<>();
            Set<Instances> falseInstances = new HashSet<>();
            for (Instances instance : instances) {

                if (instance.getAtt(i)) {
                    trueInstances.add(instance);
                    continue;
                }
                falseInstances.add(instance);
            }
            double impur = computeImpur(trueInstances, falseInstances);
            if (impur <= bestImp) {
                bestImp = impur;
                bestATT = allAtts.get(i);
                bestTSet = trueInstances;
                BestFSet = falseInstances;
            }
        }
        attriburtes.remove(bestATT);

        DTNode left = buildTree(bestTSet, new ArrayList<>(attriburtes));
        DTNode right = buildTree(BestFSet, new ArrayList<>(attriburtes));
        return new DTNode(bestATT, left, right);
    }

    public double mostCatoagories(Set<Instances> instances) {
        int liveC = 0;
        int dieC = 0;
        for (Instances instance : instances) {
            if (instance.getCategory().equals("live")) {
                liveC++;
            } else {
                dieC++;
            }
        }
        return liveC > dieC ? (double) liveC / instances.size() : (double) dieC / instances.size();
    }

    public String mostCat(Set<Instances> instances) {
        int liveC = 0;
        int dieC = 0;
        for (Instances instance : instances) {
            if (instance.getCategory().equals("live")) {
                liveC++;
            } else {
                dieC++;
            }
        }
        if (liveC > dieC) {
            return "live";
        }
        return "die";
    }

    public boolean isPure(Set<Instances> instances) {
        String category = instances.iterator().next().getCategory();
        for (Instances instance : instances) {
            if (!category.equals(instance.getCategory())) {
                return false;
            }
        }
        return true;
    }

    public double computeImpur(Set<Instances> trues, Set<Instances> falses) {
        double dt = ((double) trues.size()) / (trues.size() + falses.size());
        double df = 1.0 - dt;
        return dt * calcADratio(trues) + df * calcADratio(falses);

    }

    public double calcADratio(Set<Instances> instances) {
        if (instances.isEmpty()) {
            return 0;
        }
        int catTotals[] = new int[2];
        for (Instances instance : instances) {
            if (instance.getCategory().equals("live")) {
                catTotals[0]++;
                continue;
            }
            catTotals[1]++;

        }

        return ((double) catTotals[0] / instances.size())
                * ((double) catTotals[1] / instances.size());
    }

}
