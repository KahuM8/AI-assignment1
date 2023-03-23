public class DTNode implements DTTree {
    // desision tree node
    private String attribute;
    private DTNode yes;

    private DTNode no;

    public DTNode(String attribute, DTNode yes, DTNode no) {
        this.attribute = attribute;
        this.yes = yes;
        this.no = no;
    }



    public void report(String indent) {
        System.out.printf("%s%s = True:%n", indent, attribute);
        yes.report(indent + "|\t");
        System.out.printf("%s%s = False:%n", indent, attribute);
        no.report(indent + "|\t");
    }


    public String getATT() {
        return this.attribute;
    }

    public DTNode getYes() {
        return this.yes;
    }

    public DTNode getNo() {
        return this.no;
    }


}
