// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class MultipleParams extends MultipleParamsList {

    private MultipleParamsList MultipleParamsList;
    private Expr Expr;

    public MultipleParams (MultipleParamsList MultipleParamsList, Expr Expr) {
        this.MultipleParamsList=MultipleParamsList;
        if(MultipleParamsList!=null) MultipleParamsList.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public MultipleParamsList getMultipleParamsList() {
        return MultipleParamsList;
    }

    public void setMultipleParamsList(MultipleParamsList MultipleParamsList) {
        this.MultipleParamsList=MultipleParamsList;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MultipleParamsList!=null) MultipleParamsList.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MultipleParamsList!=null) MultipleParamsList.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MultipleParamsList!=null) MultipleParamsList.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleParams(\n");

        if(MultipleParamsList!=null)
            buffer.append(MultipleParamsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleParams]");
        return buffer.toString();
    }
}
