// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class NegativePrefixTerm extends Expr {

    private NegatedTerm NegatedTerm;
    private MultipleAddopTerms MultipleAddopTerms;

    public NegativePrefixTerm (NegatedTerm NegatedTerm, MultipleAddopTerms MultipleAddopTerms) {
        this.NegatedTerm=NegatedTerm;
        if(NegatedTerm!=null) NegatedTerm.setParent(this);
        this.MultipleAddopTerms=MultipleAddopTerms;
        if(MultipleAddopTerms!=null) MultipleAddopTerms.setParent(this);
    }

    public NegatedTerm getNegatedTerm() {
        return NegatedTerm;
    }

    public void setNegatedTerm(NegatedTerm NegatedTerm) {
        this.NegatedTerm=NegatedTerm;
    }

    public MultipleAddopTerms getMultipleAddopTerms() {
        return MultipleAddopTerms;
    }

    public void setMultipleAddopTerms(MultipleAddopTerms MultipleAddopTerms) {
        this.MultipleAddopTerms=MultipleAddopTerms;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NegatedTerm!=null) NegatedTerm.accept(visitor);
        if(MultipleAddopTerms!=null) MultipleAddopTerms.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NegatedTerm!=null) NegatedTerm.traverseTopDown(visitor);
        if(MultipleAddopTerms!=null) MultipleAddopTerms.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NegatedTerm!=null) NegatedTerm.traverseBottomUp(visitor);
        if(MultipleAddopTerms!=null) MultipleAddopTerms.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NegativePrefixTerm(\n");

        if(NegatedTerm!=null)
            buffer.append(NegatedTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MultipleAddopTerms!=null)
            buffer.append(MultipleAddopTerms.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NegativePrefixTerm]");
        return buffer.toString();
    }
}
