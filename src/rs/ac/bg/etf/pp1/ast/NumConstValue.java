// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class NumConstValue implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private Integer numConstValue;

    public NumConstValue (Integer numConstValue) {
        this.numConstValue=numConstValue;
    }

    public Integer getNumConstValue() {
        return numConstValue;
    }

    public void setNumConstValue(Integer numConstValue) {
        this.numConstValue=numConstValue;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NumConstValue(\n");

        buffer.append(" "+tab+numConstValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NumConstValue]");
        return buffer.toString();
    }
}
