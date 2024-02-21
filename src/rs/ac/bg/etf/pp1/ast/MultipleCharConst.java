// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class MultipleCharConst extends MultipleCharConstList {

    private MultipleCharConstList MultipleCharConstList;
    private String charConstName;
    private Character C2;

    public MultipleCharConst (MultipleCharConstList MultipleCharConstList, String charConstName, Character C2) {
        this.MultipleCharConstList=MultipleCharConstList;
        if(MultipleCharConstList!=null) MultipleCharConstList.setParent(this);
        this.charConstName=charConstName;
        this.C2=C2;
    }

    public MultipleCharConstList getMultipleCharConstList() {
        return MultipleCharConstList;
    }

    public void setMultipleCharConstList(MultipleCharConstList MultipleCharConstList) {
        this.MultipleCharConstList=MultipleCharConstList;
    }

    public String getCharConstName() {
        return charConstName;
    }

    public void setCharConstName(String charConstName) {
        this.charConstName=charConstName;
    }

    public Character getC2() {
        return C2;
    }

    public void setC2(Character C2) {
        this.C2=C2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MultipleCharConstList!=null) MultipleCharConstList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MultipleCharConstList!=null) MultipleCharConstList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MultipleCharConstList!=null) MultipleCharConstList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleCharConst(\n");

        if(MultipleCharConstList!=null)
            buffer.append(MultipleCharConstList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+charConstName);
        buffer.append("\n");

        buffer.append(" "+tab+C2);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleCharConst]");
        return buffer.toString();
    }
}
