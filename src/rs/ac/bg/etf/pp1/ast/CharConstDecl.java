// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class CharConstDecl extends ConstDecl {

    private Type Type;
    private String charConstName;
    private Character charValue;
    private MultipleCharConstList MultipleCharConstList;

    public CharConstDecl (Type Type, String charConstName, Character charValue, MultipleCharConstList MultipleCharConstList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.charConstName=charConstName;
        this.charValue=charValue;
        this.MultipleCharConstList=MultipleCharConstList;
        if(MultipleCharConstList!=null) MultipleCharConstList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getCharConstName() {
        return charConstName;
    }

    public void setCharConstName(String charConstName) {
        this.charConstName=charConstName;
    }

    public Character getCharValue() {
        return charValue;
    }

    public void setCharValue(Character charValue) {
        this.charValue=charValue;
    }

    public MultipleCharConstList getMultipleCharConstList() {
        return MultipleCharConstList;
    }

    public void setMultipleCharConstList(MultipleCharConstList MultipleCharConstList) {
        this.MultipleCharConstList=MultipleCharConstList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(MultipleCharConstList!=null) MultipleCharConstList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(MultipleCharConstList!=null) MultipleCharConstList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(MultipleCharConstList!=null) MultipleCharConstList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CharConstDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+charConstName);
        buffer.append("\n");

        buffer.append(" "+tab+charValue);
        buffer.append("\n");

        if(MultipleCharConstList!=null)
            buffer.append(MultipleCharConstList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CharConstDecl]");
        return buffer.toString();
    }
}
