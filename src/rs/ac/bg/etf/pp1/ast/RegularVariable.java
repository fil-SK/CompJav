// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class RegularVariable extends VarDecl {

    private Type Type;
    private String variableName;
    private MultipleVarDeclList MultipleVarDeclList;

    public RegularVariable (Type Type, String variableName, MultipleVarDeclList MultipleVarDeclList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.variableName=variableName;
        this.MultipleVarDeclList=MultipleVarDeclList;
        if(MultipleVarDeclList!=null) MultipleVarDeclList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName=variableName;
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
        buffer.append("RegularVariable(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+variableName);
        buffer.append("\n");

        if(MultipleVarDeclList!=null)
            buffer.append(MultipleVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RegularVariable]");
        return buffer.toString();
    }
}
