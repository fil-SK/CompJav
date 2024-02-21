// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class ArrayVariable extends VarDecl {

    private Type Type;
    private String arrayName;
    private MultipleVarDeclList MultipleVarDeclList;

    public ArrayVariable (Type Type, String arrayName, MultipleVarDeclList MultipleVarDeclList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.arrayName=arrayName;
        this.MultipleVarDeclList=MultipleVarDeclList;
        if(MultipleVarDeclList!=null) MultipleVarDeclList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getArrayName() {
        return arrayName;
    }

    public void setArrayName(String arrayName) {
        this.arrayName=arrayName;
    }

    public MultipleVarDeclList getMultipleVarDeclList() {
        return MultipleVarDeclList;
    }

    public void setMultipleVarDeclList(MultipleVarDeclList MultipleVarDeclList) {
        this.MultipleVarDeclList=MultipleVarDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(MultipleVarDeclList!=null) MultipleVarDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(MultipleVarDeclList!=null) MultipleVarDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(MultipleVarDeclList!=null) MultipleVarDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ArrayVariable(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+arrayName);
        buffer.append("\n");

        if(MultipleVarDeclList!=null)
            buffer.append(MultipleVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayVariable]");
        return buffer.toString();
    }
}
