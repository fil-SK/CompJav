// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class VoidMethodNoParam extends MethodDecl {

    private MethodVoidName MethodVoidName;
    private VarDeclList VarDeclList;
    private StatementList StatementList;

    public VoidMethodNoParam (MethodVoidName MethodVoidName, VarDeclList VarDeclList, StatementList StatementList) {
        this.MethodVoidName=MethodVoidName;
        if(MethodVoidName!=null) MethodVoidName.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public MethodVoidName getMethodVoidName() {
        return MethodVoidName;
    }

    public void setMethodVoidName(MethodVoidName MethodVoidName) {
        this.MethodVoidName=MethodVoidName;
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodVoidName!=null) MethodVoidName.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodVoidName!=null) MethodVoidName.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodVoidName!=null) MethodVoidName.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VoidMethodNoParam(\n");

        if(MethodVoidName!=null)
            buffer.append(MethodVoidName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VoidMethodNoParam]");
        return buffer.toString();
    }
}
