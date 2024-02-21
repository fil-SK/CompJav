// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class RegularDesignator extends Designator {

    private RegularDesignatorName RegularDesignatorName;
    private MultipleDesignatorExpansion MultipleDesignatorExpansion;

    public RegularDesignator (RegularDesignatorName RegularDesignatorName, MultipleDesignatorExpansion MultipleDesignatorExpansion) {
        this.RegularDesignatorName=RegularDesignatorName;
        if(RegularDesignatorName!=null) RegularDesignatorName.setParent(this);
        this.MultipleDesignatorExpansion=MultipleDesignatorExpansion;
        if(MultipleDesignatorExpansion!=null) MultipleDesignatorExpansion.setParent(this);
    }

    public RegularDesignatorName getRegularDesignatorName() {
        return RegularDesignatorName;
    }

    public void setRegularDesignatorName(RegularDesignatorName RegularDesignatorName) {
        this.RegularDesignatorName=RegularDesignatorName;
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
        if(RegularDesignatorName!=null) RegularDesignatorName.accept(visitor);
        if(MultipleDesignatorExpansion!=null) MultipleDesignatorExpansion.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(RegularDesignatorName!=null) RegularDesignatorName.traverseTopDown(visitor);
        if(MultipleDesignatorExpansion!=null) MultipleDesignatorExpansion.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(RegularDesignatorName!=null) RegularDesignatorName.traverseBottomUp(visitor);
        if(MultipleDesignatorExpansion!=null) MultipleDesignatorExpansion.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RegularDesignator(\n");

        if(RegularDesignatorName!=null)
            buffer.append(RegularDesignatorName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MultipleDesignatorExpansion!=null)
            buffer.append(MultipleDesignatorExpansion.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RegularDesignator]");
        return buffer.toString();
    }
}
