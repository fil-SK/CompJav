// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class NumConstDecl extends ConstDecl {

    private Type Type;
    private String numConstName;
    private Integer numValue;
    private MultipleNumConstList MultipleNumConstList;

    public NumConstDecl (Type Type, String numConstName, Integer numValue, MultipleNumConstList MultipleNumConstList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.numConstName=numConstName;
        this.numValue=numValue;
        this.MultipleNumConstList=MultipleNumConstList;
        if(MultipleNumConstList!=null) MultipleNumConstList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getNumConstName() {
        return numConstName;
    }

    public void setNumConstName(String numConstName) {
        this.numConstName=numConstName;
    }

    public Integer getNumValue() {
        return numValue;
    }

    public void setNumValue(Integer numValue) {
        this.numValue=numValue;
    }

    public MultipleNumConstList getMultipleNumConstList() {
        return MultipleNumConstList;
    }

    public void setMultipleNumConstList(MultipleNumConstList MultipleNumConstList) {
        this.MultipleNumConstList=MultipleNumConstList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(MultipleNumConstList!=null) MultipleNumConstList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(MultipleNumConstList!=null) MultipleNumConstList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(MultipleNumConstList!=null) MultipleNumConstList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NumConstDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+numConstName);
        buffer.append("\n");

        buffer.append(" "+tab+numValue);
        buffer.append("\n");

        if(MultipleNumConstList!=null)
            buffer.append(MultipleNumConstList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NumConstDecl]");
        return buffer.toString();
    }
}
