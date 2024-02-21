// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class ConditionBitwiseOr extends Condition {

    private CondTerm CondTerm;
    private MultipleCondTerms MultipleCondTerms;

    public ConditionBitwiseOr (CondTerm CondTerm, MultipleCondTerms MultipleCondTerms) {
        this.CondTerm=CondTerm;
        if(CondTerm!=null) CondTerm.setParent(this);
        this.MultipleCondTerms=MultipleCondTerms;
        if(MultipleCondTerms!=null) MultipleCondTerms.setParent(this);
    }

    public CondTerm getCondTerm() {
        return CondTerm;
    }

    public void setCondTerm(CondTerm CondTerm) {
        this.CondTerm=CondTerm;
    }

    public MultipleCondTerms getMultipleCondTerms() {
        return MultipleCondTerms;
    }

    public void setMultipleCondTerms(MultipleCondTerms MultipleCondTerms) {
        this.MultipleCondTerms=MultipleCondTerms;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CondTerm!=null) CondTerm.accept(visitor);
        if(MultipleCondTerms!=null) MultipleCondTerms.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondTerm!=null) CondTerm.traverseTopDown(visitor);
        if(MultipleCondTerms!=null) MultipleCondTerms.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondTerm!=null) CondTerm.traverseBottomUp(visitor);
        if(MultipleCondTerms!=null) MultipleCondTerms.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConditionBitwiseOr(\n");

        if(CondTerm!=null)
            buffer.append(CondTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MultipleCondTerms!=null)
            buffer.append(MultipleCondTerms.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConditionBitwiseOr]");
        return buffer.toString();
    }
}
