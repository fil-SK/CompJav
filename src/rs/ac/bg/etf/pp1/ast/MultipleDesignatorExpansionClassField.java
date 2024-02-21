// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class MultipleDesignatorExpansionClassField extends MultipleDesignatorExpansion {

    private MultipleDesignatorExpansion MultipleDesignatorExpansion;
    private String I2;

    public MultipleDesignatorExpansionClassField (MultipleDesignatorExpansion MultipleDesignatorExpansion, String I2) {
        this.MultipleDesignatorExpansion=MultipleDesignatorExpansion;
        if(MultipleDesignatorExpansion!=null) MultipleDesignatorExpansion.setParent(this);
        this.I2=I2;
    }

    public MultipleDesignatorExpansion getMultipleDesignatorExpansion() {
        return MultipleDesignatorExpansion;
    }

    public void setMultipleDesignatorExpansion(MultipleDesignatorExpansion MultipleDesignatorExpansion) {
        this.MultipleDesignatorExpansion=MultipleDesignatorExpansion;
    }

    public String getI2() {
        return I2;
    }

    public void setI2(String I2) {
        this.I2=I2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MultipleDesignatorExpansion!=null) MultipleDesignatorExpansion.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MultipleDesignatorExpansion!=null) MultipleDesignatorExpansion.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MultipleDesignatorExpansion!=null) MultipleDesignatorExpansion.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleDesignatorExpansionClassField(\n");

        if(MultipleDesignatorExpansion!=null)
            buffer.append(MultipleDesignatorExpansion.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleDesignatorExpansionClassField]");
        return buffer.toString();
    }
}
