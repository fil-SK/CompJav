// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class MultipleNumConst extends MultipleNumConstList {

    private MultipleNumConstList MultipleNumConstList;
    private String numConstName;
    private NumConstValue NumConstValue;

    public MultipleNumConst (MultipleNumConstList MultipleNumConstList, String numConstName, NumConstValue NumConstValue) {
        this.MultipleNumConstList=MultipleNumConstList;
        if(MultipleNumConstList!=null) MultipleNumConstList.setParent(this);
        this.numConstName=numConstName;
        this.NumConstValue=NumConstValue;
        if(NumConstValue!=null) NumConstValue.setParent(this);
    }

    public MultipleNumConstList getMultipleNumConstList() {
        return MultipleNumConstList;
    }

    public void setMultipleNumConstList(MultipleNumConstList MultipleNumConstList) {
        this.MultipleNumConstList=MultipleNumConstList;
    }

    public String getNumConstName() {
        return numConstName;
    }

    public void setNumConstName(String numConstName) {
        this.numConstName=numConstName;
    }

    public NumConstValue getNumConstValue() {
        return NumConstValue;
    }

    public void setNumConstValue(NumConstValue NumConstValue) {
        this.NumConstValue=NumConstValue;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MultipleNumConstList!=null) MultipleNumConstList.accept(visitor);
        if(NumConstValue!=null) NumConstValue.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MultipleNumConstList!=null) MultipleNumConstList.traverseTopDown(visitor);
        if(NumConstValue!=null) NumConstValue.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MultipleNumConstList!=null) MultipleNumConstList.traverseBottomUp(visitor);
        if(NumConstValue!=null) NumConstValue.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleNumConst(\n");

        if(MultipleNumConstList!=null)
            buffer.append(MultipleNumConstList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+numConstName);
        buffer.append("\n");

        if(NumConstValue!=null)
            buffer.append(NumConstValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleNumConst]");
        return buffer.toString();
    }
}
