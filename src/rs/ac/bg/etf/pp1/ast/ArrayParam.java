// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class ArrayParam extends FormPars {

    private Type Type;
    private String I2;
    private MultipleFormParsList MultipleFormParsList;

    public ArrayParam (Type Type, String I2, MultipleFormParsList MultipleFormParsList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.I2=I2;
        this.MultipleFormParsList=MultipleFormParsList;
        if(MultipleFormParsList!=null) MultipleFormParsList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getI2() {
        return I2;
    }

    public void setI2(String I2) {
        this.I2=I2;
    }

    public MultipleFormParsList getMultipleFormParsList() {
        return MultipleFormParsList;
    }

    public void setMultipleFormParsList(MultipleFormParsList MultipleFormParsList) {
        this.MultipleFormParsList=MultipleFormParsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(MultipleFormParsList!=null) MultipleFormParsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(MultipleFormParsList!=null) MultipleFormParsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(MultipleFormParsList!=null) MultipleFormParsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ArrayParam(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        if(MultipleFormParsList!=null)
            buffer.append(MultipleFormParsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayParam]");
        return buffer.toString();
    }
}
