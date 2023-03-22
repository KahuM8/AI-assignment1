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

    public String getAttribute() {
        return attribute;
    }

    public DTNode getYes() {
        return yes;
    }

    public DTNode getNo() {
        return no;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setYes(DTNode yes) {
        this.yes = yes;
    }

    public void setNo(DTNode no) {
        this.no = no;
    }

    public String toString(String prefix) {
        return prefix + " " + attribute + " = yes: " + yes.toString(prefix + " ") + " ";
    }

    public void addChild(String value, DTNode child) {
        if (value.equals("yes")) {
            yes = child;
        } else {
            no = child;
        }
    }

    public DTNode getChild(String value) {
        if (value.equals("yes")) {
            return yes;
        } else {
            return no;
        }
    }

}
