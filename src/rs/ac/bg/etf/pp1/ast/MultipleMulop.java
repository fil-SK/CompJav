// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class MultipleMulop extends MultipleMulopFactors {

    private MultipleMulopFactors MultipleMulopFactors;
    private Mulop Mulop;
    private Factor Factor;

    public MultipleMulop (MultipleMulopFactors MultipleMulopFactors, Mulop Mulop, Factor Factor) {
        this.MultipleMulopFactors=MultipleMulopFactors;
        if(MultipleMulopFactors!=null) MultipleMulopFactors.setParent(this);
        this.Mulop=Mulop;
        if(Mulop!=null) Mulop.setParent(this);
        this.Factor=Factor;
        if(Factor!=null) Factor.setParent(this);
    }

    public MultipleMulopFactors getMultipleMulopFactors() {
        return MultipleMulopFactors;
    }

    public void setMultipleMulopFactors(MultipleMulopFactors MultipleMulopFactors) {
        this.MultipleMulopFactors=MultipleMulopFactors;
    }

    public Mulop getMulop() {
        return Mulop;
    }

    public void setMulop(Mulop Mulop) {
        this.Mulop=Mulop;
    }

    public Factor getFactor() {
        return Factor;
    }

    public void setFactor(Factor Factor) {
        this.Factor=Factor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MultipleMulopFactors!=null) MultipleMulopFactors.accept(visitor);
        if(Mulop!=null) Mulop.accept(visitor);
        if(Factor!=null) Factor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MultipleMulopFactors!=null) MultipleMulopFactors.traverseTopDown(visitor);
        if(Mulop!=null) Mulop.traverseTopDown(visitor);
        if(Factor!=null) Factor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MultipleMulopFactors!=null) MultipleMulopFactors.traverseBottomUp(visitor);
        if(Mulop!=null) Mulop.traverseBottomUp(visitor);
        if(Factor!=null) Factor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleMulop(\n");

        if(MultipleMulopFactors!=null)
            buffer.append(MultipleMulopFactors.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Mulop!=null)
            buffer.append(Mulop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Factor!=null)
            buffer.append(Factor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleMulop]");
        return buffer.toString();
    }
}
