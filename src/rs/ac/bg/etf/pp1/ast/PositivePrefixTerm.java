// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class PositivePrefixTerm extends Expr {

    private Term Term;
    private MultipleAddopTerms MultipleAddopTerms;

    public PositivePrefixTerm (Term Term, MultipleAddopTerms MultipleAddopTerms) {
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
        this.MultipleAddopTerms=MultipleAddopTerms;
        if(MultipleAddopTerms!=null) MultipleAddopTerms.setParent(this);
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public MultipleAddopTerms getMultipleAddopTerms() {
        return MultipleAddopTerms;
    }

    public void setMultipleAddopTerms(MultipleAddopTerms MultipleAddopTerms) {
        this.MultipleAddopTerms=MultipleAddopTerms;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Term!=null) Term.accept(visitor);
        if(MultipleAddopTerms!=null) MultipleAddopTerms.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
        if(MultipleAddopTerms!=null) MultipleAddopTerms.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Term!=null) Term.traverseBottomUp(visitor);
        if(MultipleAddopTerms!=null) MultipleAddopTerms.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PositivePrefixTerm(\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MultipleAddopTerms!=null)
            buffer.append(MultipleAddopTerms.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PositivePrefixTerm]");
        return buffer.toString();
    }
}
