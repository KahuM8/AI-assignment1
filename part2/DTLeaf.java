public class DTLeaf extends DTNode {
    // desision tree leaf
    private String category;
    private double prob;

    public DTLeaf(String category, double probibility) {
        super(null, null, null);
        this.category = category;
        this.prob = probibility;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String toString(String prefix) {
        return prefix + "Class " + category + ", prob =  " + prob;
    }
}
