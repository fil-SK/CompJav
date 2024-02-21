// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class MultipleConditionsAnd extends MultipleCondFacts {

    private MultipleCondFacts MultipleCondFacts;
    private CondFact CondFact;

    public MultipleConditionsAnd (MultipleCondFacts MultipleCondFacts, CondFact CondFact) {
        this.MultipleCondFacts=MultipleCondFacts;
        if(MultipleCondFacts!=null) MultipleCondFacts.setParent(this);
        this.CondFact=CondFact;
        if(CondFact!=null) CondFact.setParent(this);
    }

    public MultipleCondFacts getMultipleCondFacts() {
        return MultipleCondFacts;
    }

    public void setMultipleCondFacts(MultipleCondFacts MultipleCondFacts) {
        this.MultipleCondFacts=MultipleCondFacts;
    }

    public CondFact getCondFact() {
        return CondFact;
    }

    public void setCondFact(CondFact CondFact) {
        this.CondFact=CondFact;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MultipleCondFacts!=null) MultipleCondFacts.accept(visitor);
        if(CondFact!=null) CondFact.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MultipleCondFacts!=null) MultipleCondFacts.traverseTopDown(visitor);
        if(CondFact!=null) CondFact.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MultipleCondFacts!=null) MultipleCondFacts.traverseBottomUp(visitor);
        if(CondFact!=null) CondFact.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleConditionsAnd(\n");

        if(MultipleCondFacts!=null)
            buffer.append(MultipleCondFacts.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondFact!=null)
            buffer.append(CondFact.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleConditionsAnd]");
        return buffer.toString();
    }
}
