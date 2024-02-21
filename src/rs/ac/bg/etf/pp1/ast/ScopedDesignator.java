// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class ScopedDesignator extends Designator {

    private ScopedDesignatorName ScopedDesignatorName;
    private MultipleDesignatorExpansion MultipleDesignatorExpansion;

    public ScopedDesignator (ScopedDesignatorName ScopedDesignatorName, MultipleDesignatorExpansion MultipleDesignatorExpansion) {
        this.ScopedDesignatorName=ScopedDesignatorName;
        if(ScopedDesignatorName!=null) ScopedDesignatorName.setParent(this);
        this.MultipleDesignatorExpansion=MultipleDesignatorExpansion;
        if(MultipleDesignatorExpansion!=null) MultipleDesignatorExpansion.setParent(this);
    }

    public ScopedDesignatorName getScopedDesignatorName() {
        return ScopedDesignatorName;
    }

    public void setScopedDesignatorName(ScopedDesignatorName ScopedDesignatorName) {
        this.ScopedDesignatorName=ScopedDesignatorName;
    }

    public MultipleDesignatorExpansion getMultipleDesignatorExpansion() {
        return MultipleDesignatorExpansion;
    }

    public void setMultipleDesignatorExpansion(MultipleDesignatorExpansion MultipleDesignatorExpansion) {
        this.MultipleDesignatorExpansion=MultipleDesignatorExpansion;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ScopedDesignatorName!=null) ScopedDesignatorName.accept(visitor);
        if(MultipleDesignatorExpansion!=null) MultipleDesignatorExpansion.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ScopedDesignatorName!=null) ScopedDesignatorName.traverseTopDown(visitor);
        if(MultipleDesignatorExpansion!=null) MultipleDesignatorExpansion.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ScopedDesignatorName!=null) ScopedDesignatorName.traverseBottomUp(visitor);
        if(MultipleDesignatorExpansion!=null) MultipleDesignatorExpansion.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ScopedDesignator(\n");

        if(ScopedDesignatorName!=null)
            buffer.append(ScopedDesignatorName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MultipleDesignatorExpansion!=null)
            buffer.append(MultipleDesignatorExpansion.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ScopedDesignator]");
        return buffer.toString();
    }
}
