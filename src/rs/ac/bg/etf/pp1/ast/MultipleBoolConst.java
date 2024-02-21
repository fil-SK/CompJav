// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public class MultipleBoolConst extends MultipleBoolConstList {

    private MultipleBoolConstList MultipleBoolConstList;
    private String boolConstName;
    private Boolean B2;

    public MultipleBoolConst (MultipleBoolConstList MultipleBoolConstList, String boolConstName, Boolean B2) {
        this.MultipleBoolConstList=MultipleBoolConstList;
        if(MultipleBoolConstList!=null) MultipleBoolConstList.setParent(this);
        this.boolConstName=boolConstName;
        this.B2=B2;
    }

    public MultipleBoolConstList getMultipleBoolConstList() {
        return MultipleBoolConstList;
    }

    public void setMultipleBoolConstList(MultipleBoolConstList MultipleBoolConstList) {
        this.MultipleBoolConstList=MultipleBoolConstList;
    }

    public String getBoolConstName() {
        return boolConstName;
    }

    public void setBoolConstName(String boolConstName) {
        this.boolConstName=boolConstName;
    }

    public Boolean getB2() {
        return B2;
    }

    public void setB2(Boolean B2) {
        this.B2=B2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MultipleBoolConstList!=null) MultipleBoolConstList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MultipleBoolConstList!=null) MultipleBoolConstList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MultipleBoolConstList!=null) MultipleBoolConstList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleBoolConst(\n");

        if(MultipleBoolConstList!=null)
            buffer.append(MultipleBoolConstList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+boolConstName);
        buffer.append("\n");

        buffer.append(" "+tab+B2);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleBoolConst]");
        return buffer.toString();
    }
}
