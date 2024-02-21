// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class MultipleRegularFormPars extends MultipleFormParsList {

    private MultipleFormParsList MultipleFormParsList;
    private Type Type;
    private String I3;

    public MultipleRegularFormPars (MultipleFormParsList MultipleFormParsList, Type Type, String I3) {
        this.MultipleFormParsList=MultipleFormParsList;
        if(MultipleFormParsList!=null) MultipleFormParsList.setParent(this);
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.I3=I3;
    }

    public MultipleFormParsList getMultipleFormParsList() {
        return MultipleFormParsList;
    }

    public void setMultipleFormParsList(MultipleFormParsList MultipleFormParsList) {
        this.MultipleFormParsList=MultipleFormParsList;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getI3() {
        return I3;
    }

    public void setI3(String I3) {
        this.I3=I3;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MultipleFormParsList!=null) MultipleFormParsList.accept(visitor);
        if(Type!=null) Type.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MultipleFormParsList!=null) MultipleFormParsList.traverseTopDown(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MultipleFormParsList!=null) MultipleFormParsList.traverseBottomUp(visitor);
        if(Type!=null) Type.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleRegularFormPars(\n");

        if(MultipleFormParsList!=null)
            buffer.append(MultipleFormParsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I3);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleRegularFormPars]");
        return buffer.toString();
    }
}
