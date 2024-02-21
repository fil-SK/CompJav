// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class ActualParameters extends ActPars {

    private Expr Expr;
    private MultipleParamsList MultipleParamsList;

    public ActualParameters (Expr Expr, MultipleParamsList MultipleParamsList) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.MultipleParamsList=MultipleParamsList;
        if(MultipleParamsList!=null) MultipleParamsList.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public MultipleParamsList getMultipleParamsList() {
        return MultipleParamsList;
    }

    public void setMultipleParamsList(MultipleParamsList MultipleParamsList) {
        this.MultipleParamsList=MultipleParamsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(MultipleParamsList!=null) MultipleParamsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(MultipleParamsList!=null) MultipleParamsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(MultipleParamsList!=null) MultipleParamsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActualParameters(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MultipleParamsList!=null)
            buffer.append(MultipleParamsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActualParameters]");
        return buffer.toString();
    }
}
