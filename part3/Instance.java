package part3;


import java.util.List;


public class Instance {
    private String category;
    private List<Double> features;

    public Instance(String category, List<Double> features) {
        this.category = category;
        this.features = features;
    }

    public String getCategory() {
        return category;
    }

    public List<Double> getFeatures() {
        return features;
    }

    public double getFeature(int i) {
        return features.get(i);
    }

    public boolean getAtt(int i) {
        return features.get(i) == 1;
    }

    public String toString() {
        return category + " " + features;
    }

}
