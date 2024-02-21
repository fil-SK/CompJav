// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class BoolConstDecl extends ConstDecl {

    private Type Type;
    private String boolConstName;
    private Boolean boolValue;
    private MultipleBoolConstList MultipleBoolConstList;

    public BoolConstDecl (Type Type, String boolConstName, Boolean boolValue, MultipleBoolConstList MultipleBoolConstList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.boolConstName=boolConstName;
        this.boolValue=boolValue;
        this.MultipleBoolConstList=MultipleBoolConstList;
        if(MultipleBoolConstList!=null) MultipleBoolConstList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getBoolConstName() {
        return boolConstName;
    }

    public void setBoolConstName(String boolConstName) {
        this.boolConstName=boolConstName;
    }

    public Boolean getBoolValue() {
        return boolValue;
    }

    public void setBoolValue(Boolean boolValue) {
        this.boolValue=boolValue;
    }

    public MultipleBoolConstList getMultipleBoolConstList() {
        return MultipleBoolConstList;
    }

    public void setMultipleBoolConstList(MultipleBoolConstList MultipleBoolConstList) {
        this.MultipleBoolConstList=MultipleBoolConstList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(MultipleBoolConstList!=null) MultipleBoolConstList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(MultipleBoolConstList!=null) MultipleBoolConstList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(MultipleBoolConstList!=null) MultipleBoolConstList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("BoolConstDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+boolConstName);
        buffer.append("\n");

        buffer.append(" "+tab+boolValue);
        buffer.append("\n");

        if(MultipleBoolConstList!=null)
            buffer.append(MultipleBoolConstList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [BoolConstDecl]");
        return buffer.toString();
    }
}
