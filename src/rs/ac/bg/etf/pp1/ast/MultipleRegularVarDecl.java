// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class MultipleRegularVarDecl extends MultipleVarDeclList {

    private MultipleVarDeclList MultipleVarDeclList;
    private String additionalVariableName;

    public MultipleRegularVarDecl (MultipleVarDeclList MultipleVarDeclList, String additionalVariableName) {
        this.MultipleVarDeclList=MultipleVarDeclList;
        if(MultipleVarDeclList!=null) MultipleVarDeclList.setParent(this);
        this.additionalVariableName=additionalVariableName;
    }

    public MultipleVarDeclList getMultipleVarDeclList() {
        return MultipleVarDeclList;
    }

    public void setMultipleVarDeclList(MultipleVarDeclList MultipleVarDeclList) {
        this.MultipleVarDeclList=MultipleVarDeclList;
    }

    public String getAdditionalVariableName() {
        return additionalVariableName;
    }

    public void setAdditionalVariableName(String additionalVariableName) {
        this.additionalVariableName=additionalVariableName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MultipleVarDeclList!=null) MultipleVarDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MultipleVarDeclList!=null) MultipleVarDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MultipleVarDeclList!=null) MultipleVarDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleRegularVarDecl(\n");

        if(MultipleVarDeclList!=null)
            buffer.append(MultipleVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+additionalVariableName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleRegularVarDecl]");
        return buffer.toString();
    }
}
