public class DTLeaf extends DTNode {
    // desision tree leaf
    private String category;
    private double prob;

    public DTLeaf(String category, double probibility) {
        super(category, null, null);
        this.category = category;
        this.prob = probibility;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void report(String indent){
        if (prob==0){ //Error-checking
        System.out.printf("%sUnknown%n", indent);
        }else{
        System.out.printf("%sClass %s, prob=%.2f%n", indent, category, prob);
        }}
}
