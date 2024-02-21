// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class ConditionBitwiseAnd extends CondTerm {

    private CondFact CondFact;
    private MultipleCondFacts MultipleCondFacts;

    public ConditionBitwiseAnd (CondFact CondFact, MultipleCondFacts MultipleCondFacts) {
        this.CondFact=CondFact;
        if(CondFact!=null) CondFact.setParent(this);
        this.MultipleCondFacts=MultipleCondFacts;
        if(MultipleCondFacts!=null) MultipleCondFacts.setParent(this);
    }

    public CondFact getCondFact() {
        return CondFact;
    }

    public void setCondFact(CondFact CondFact) {
        this.CondFact=CondFact;
    }

    public MultipleCondFacts getMultipleCondFacts() {
        return MultipleCondFacts;
    }

    public void setMultipleCondFacts(MultipleCondFacts MultipleCondFacts) {
        this.MultipleCondFacts=MultipleCondFacts;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CondFact!=null) CondFact.accept(visitor);
        if(MultipleCondFacts!=null) MultipleCondFacts.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondFact!=null) CondFact.traverseTopDown(visitor);
        if(MultipleCondFacts!=null) MultipleCondFacts.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondFact!=null) CondFact.traverseBottomUp(visitor);
        if(MultipleCondFacts!=null) MultipleCondFacts.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConditionBitwiseAnd(\n");

        if(CondFact!=null)
            buffer.append(CondFact.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MultipleCondFacts!=null)
            buffer.append(MultipleCondFacts.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConditionBitwiseAnd]");
        return buffer.toString();
    }
}
