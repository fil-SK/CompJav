// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class MultipleConditionsOr extends MultipleCondTerms {

    private MultipleCondTerms MultipleCondTerms;
    private CondTerm CondTerm;

    public MultipleConditionsOr (MultipleCondTerms MultipleCondTerms, CondTerm CondTerm) {
        this.MultipleCondTerms=MultipleCondTerms;
        if(MultipleCondTerms!=null) MultipleCondTerms.setParent(this);
        this.CondTerm=CondTerm;
        if(CondTerm!=null) CondTerm.setParent(this);
    }

    public MultipleCondTerms getMultipleCondTerms() {
        return MultipleCondTerms;
    }

    public void setMultipleCondTerms(MultipleCondTerms MultipleCondTerms) {
        this.MultipleCondTerms=MultipleCondTerms;
    }

    public CondTerm getCondTerm() {
        return CondTerm;
    }

    public void setCondTerm(CondTerm CondTerm) {
        this.CondTerm=CondTerm;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MultipleCondTerms!=null) MultipleCondTerms.accept(visitor);
        if(CondTerm!=null) CondTerm.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MultipleCondTerms!=null) MultipleCondTerms.traverseTopDown(visitor);
        if(CondTerm!=null) CondTerm.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MultipleCondTerms!=null) MultipleCondTerms.traverseBottomUp(visitor);
        if(CondTerm!=null) CondTerm.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleConditionsOr(\n");

        if(MultipleCondTerms!=null)
            buffer.append(MultipleCondTerms.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondTerm!=null)
            buffer.append(CondTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleConditionsOr]");
        return buffer.toString();
    }
}
