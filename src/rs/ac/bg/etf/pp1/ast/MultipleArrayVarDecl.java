// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class MultipleArrayVarDecl extends MultipleVarDeclList {

    private MultipleVarDeclList MultipleVarDeclList;
    private String additionalArrayVarName;

    public MultipleArrayVarDecl (MultipleVarDeclList MultipleVarDeclList, String additionalArrayVarName) {
        this.MultipleVarDeclList=MultipleVarDeclList;
        if(MultipleVarDeclList!=null) MultipleVarDeclList.setParent(this);
        this.additionalArrayVarName=additionalArrayVarName;
    }

    public MultipleVarDeclList getMultipleVarDeclList() {
        return MultipleVarDeclList;
    }

    public void setMultipleVarDeclList(MultipleVarDeclList MultipleVarDeclList) {
        this.MultipleVarDeclList=MultipleVarDeclList;
    }

    public String getAdditionalArrayVarName() {
        return additionalArrayVarName;
    }

    public void setAdditionalArrayVarName(String additionalArrayVarName) {
        this.additionalArrayVarName=additionalArrayVarName;
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
        buffer.append("MultipleArrayVarDecl(\n");

        if(MultipleVarDeclList!=null)
            buffer.append(MultipleVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+additionalArrayVarName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleArrayVarDecl]");
        return buffer.toString();
    }
}
