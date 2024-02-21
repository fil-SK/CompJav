// generated with ast extension for cup
// version 0.8
// 15/0/2024 23:31:38


package rs.ac.bg.etf.pp1.ast;

public abstract class VisitorAdaptor implements Visitor { 

    public void visit(MultipleCharConstList MultipleCharConstList) { }
    public void visit(Mulop Mulop) { }
    public void visit(MethodDecl MethodDecl) { }
    public void visit(Relop Relop) { }
    public void visit(MultipleFormParsList MultipleFormParsList) { }
    public void visit(MultipleCondFacts MultipleCondFacts) { }
    public void visit(MultipleMulopFactors MultipleMulopFactors) { }
    public void visit(MultipleParamsList MultipleParamsList) { }
    public void visit(StatementList StatementList) { }
    public void visit(NamespaceList NamespaceList) { }
    public void visit(ConstVarDeclList ConstVarDeclList) { }
    public void visit(Addop Addop) { }
    public void visit(Factor Factor) { }
    public void visit(CondTerm CondTerm) { }
    public void visit(Designator Designator) { }
    public void visit(Condition Condition) { }
    public void visit(MultipleCondTerms MultipleCondTerms) { }
    public void visit(MultipleAddopTerms MultipleAddopTerms) { }
    public void visit(MultipleDesignatorExpansion MultipleDesignatorExpansion) { }
    public void visit(VarDeclList VarDeclList) { }
    public void visit(Expr Expr) { }
    public void visit(ActPars ActPars) { }
    public void visit(DesignatorStatement DesignatorStatement) { }
    public void visit(Statement Statement) { }
    public void visit(MultipleNumConstList MultipleNumConstList) { }
    public void visit(VarDecl VarDecl) { }
    public void visit(Type Type) { }
    public void visit(ConstDecl ConstDecl) { }
    public void visit(CondFact CondFact) { }
    public void visit(MethodDeclList MethodDeclList) { }
    public void visit(FormPars FormPars) { }
    public void visit(MultipleVarDeclList MultipleVarDeclList) { }
    public void visit(MultipleBoolConstList MultipleBoolConstList) { }
    public void visit(MulopModulus MulopModulus) { visit(); }
    public void visit(MulopDivision MulopDivision) { visit(); }
    public void visit(MulopMultiplication MulopMultiplication) { visit(); }
    public void visit(AddopSubstraction AddopSubstraction) { visit(); }
    public void visit(AddopAddition AddopAddition) { visit(); }
    public void visit(RelopLowerOrEqual RelopLowerOrEqual) { visit(); }
    public void visit(RelopLowerThan RelopLowerThan) { visit(); }
    public void visit(RelopGreaterOrEqual RelopGreaterOrEqual) { visit(); }
    public void visit(RelopGreaterThan RelopGreaterThan) { visit(); }
    public void visit(RelopNotEqual RelopNotEqual) { visit(); }
    public void visit(RelopIsEqual RelopIsEqual) { visit(); }
    public void visit(Assignop Assignop) { visit(); }
    public void visit(Label Label) { visit(); }
    public void visit(MultipleDesignatorExpansionArray MultipleDesignatorExpansionArray) { visit(); }
    public void visit(MultipleDesignatorExpansionClassField MultipleDesignatorExpansionClassField) { visit(); }
    public void visit(NoMultipleDesignatorExpansion NoMultipleDesignatorExpansion) { visit(); }
    public void visit(ScopedDesignatorName ScopedDesignatorName) { visit(); }
    public void visit(RegularDesignatorName RegularDesignatorName) { visit(); }
    public void visit(ScopedDesignator ScopedDesignator) { visit(); }
    public void visit(RegularDesignator RegularDesignator) { visit(); }
    public void visit(FactorParenthesisGrouping FactorParenthesisGrouping) { visit(); }
    public void visit(FactorObjectWithParam FactorObjectWithParam) { visit(); }
    public void visit(FactorObjectNoParam FactorObjectNoParam) { visit(); }
    public void visit(FactorObjectArray FactorObjectArray) { visit(); }
    public void visit(FactorBoolConst FactorBoolConst) { visit(); }
    public void visit(FactorCharConst FactorCharConst) { visit(); }
    public void visit(FactorNumConst FactorNumConst) { visit(); }
    public void visit(FactorFunctionWithParam FactorFunctionWithParam) { visit(); }
    public void visit(FactorFunctionNoParam FactorFunctionNoParam) { visit(); }
    public void visit(FactorVariable FactorVariable) { visit(); }
    public void visit(MultipleMulop MultipleMulop) { visit(); }
    public void visit(NoMultipleMulopFactors NoMultipleMulopFactors) { visit(); }
    public void visit(Term Term) { visit(); }
    public void visit(MultipleAddop MultipleAddop) { visit(); }
    public void visit(NoMultipleAddopTerms NoMultipleAddopTerms) { visit(); }
    public void visit(NegatedTerm NegatedTerm) { visit(); }
    public void visit(NegativePrefixTerm NegativePrefixTerm) { visit(); }
    public void visit(PositivePrefixTerm PositivePrefixTerm) { visit(); }
    public void visit(ExpressionWithRelationalOperator ExpressionWithRelationalOperator) { visit(); }
    public void visit(SingleExpression SingleExpression) { visit(); }
    public void visit(MultipleConditionsAnd MultipleConditionsAnd) { visit(); }
    public void visit(NoMultipleCondFacts NoMultipleCondFacts) { visit(); }
    public void visit(ConditionBitwiseAnd ConditionBitwiseAnd) { visit(); }
    public void visit(MultipleConditionsOr MultipleConditionsOr) { visit(); }
    public void visit(NoMultipleCondTerms NoMultipleCondTerms) { visit(); }
    public void visit(ConditionBitwiseOr ConditionBitwiseOr) { visit(); }
    public void visit(MultipleParams MultipleParams) { visit(); }
    public void visit(NoMultipleParams NoMultipleParams) { visit(); }
    public void visit(ActualParameters ActualParameters) { visit(); }
    public void visit(DecrementStatement DecrementStatement) { visit(); }
    public void visit(IncrementStatement IncrementStatement) { visit(); }
    public void visit(FunctionCallWithParam FunctionCallWithParam) { visit(); }
    public void visit(FunctionCallNoParam FunctionCallNoParam) { visit(); }
    public void visit(AssignmentStatement AssignmentStatement) { visit(); }
    public void visit(SyntaxError SyntaxError) { visit(); }
    public void visit(StatementBlock StatementBlock) { visit(); }
    public void visit(PrintWithArgStatement PrintWithArgStatement) { visit(); }
    public void visit(PrintNoArgStatement PrintNoArgStatement) { visit(); }
    public void visit(ReadStatement ReadStatement) { visit(); }
    public void visit(ReturnStatement ReturnStatement) { visit(); }
    public void visit(NoReturnStatement NoReturnStatement) { visit(); }
    public void visit(ContinueStatement ContinueStatement) { visit(); }
    public void visit(BreakStatement BreakStatement) { visit(); }
    public void visit(IfElseStatement IfElseStatement) { visit(); }
    public void visit(IfStatement IfStatement) { visit(); }
    public void visit(DeclarationStatement DeclarationStatement) { visit(); }
    public void visit(ScopedType ScopedType) { visit(); }
    public void visit(RegularType RegularType) { visit(); }
    public void visit(MultipleArrayFormPars MultipleArrayFormPars) { visit(); }
    public void visit(MultipleRegularFormPars MultipleRegularFormPars) { visit(); }
    public void visit(NoMultipleFormPars NoMultipleFormPars) { visit(); }
    public void visit(ArrayParam ArrayParam) { visit(); }
    public void visit(RegularParam RegularParam) { visit(); }
    public void visit(VarDeclarations VarDeclarations) { visit(); }
    public void visit(NoVarDecl NoVarDecl) { visit(); }
    public void visit(MultipleMethodDecl MultipleMethodDecl) { visit(); }
    public void visit(NoMethodDecl NoMethodDecl) { visit(); }
    public void visit(MethodVoidName MethodVoidName) { visit(); }
    public void visit(MethodTypeName MethodTypeName) { visit(); }
    public void visit(VoidMethodWithParam VoidMethodWithParam) { visit(); }
    public void visit(VoidMethodNoParam VoidMethodNoParam) { visit(); }
    public void visit(RetTypeMethodWithParam RetTypeMethodWithParam) { visit(); }
    public void visit(RetTypeMethodNoParam RetTypeMethodNoParam) { visit(); }
    public void visit(MultipleStatementList MultipleStatementList) { visit(); }
    public void visit(NoStatementList NoStatementList) { visit(); }
    public void visit(StaticInitializer StaticInitializer) { visit(); }
    public void visit(MultipleArrayVarDecl MultipleArrayVarDecl) { visit(); }
    public void visit(MultipleRegularVarDecl MultipleRegularVarDecl) { visit(); }
    public void visit(NoMultipleVarDeclList NoMultipleVarDeclList) { visit(); }
    public void visit(ArrayVariable ArrayVariable) { visit(); }
    public void visit(RegularVariable RegularVariable) { visit(); }
    public void visit(MultipleBoolConst MultipleBoolConst) { visit(); }
    public void visit(NoMultipleBoolConstList NoMultipleBoolConstList) { visit(); }
    public void visit(MultipleCharConst MultipleCharConst) { visit(); }
    public void visit(NoMultipleCharConstList NoMultipleCharConstList) { visit(); }
    public void visit(NumConstValue NumConstValue) { visit(); }
    public void visit(MultipleNumConst MultipleNumConst) { visit(); }
    public void visit(NoMultipleNumConstList NoMultipleNumConstList) { visit(); }
    public void visit(MultipleVarDecl MultipleVarDecl) { visit(); }
    public void visit(MultipleConstDecl MultipleConstDecl) { visit(); }
    public void visit(NoConstVarDecl NoConstVarDecl) { visit(); }
    public void visit(BoolConstDecl BoolConstDecl) { visit(); }
    public void visit(CharConstDecl CharConstDecl) { visit(); }
    public void visit(NumConstDecl NumConstDecl) { visit(); }
    public void visit(MultipleNamespace MultipleNamespace) { visit(); }
    public void visit(NoNamespace NoNamespace) { visit(); }
    public void visit(NamespaceName NamespaceName) { visit(); }
    public void visit(Namespace Namespace) { visit(); }
    public void visit(ProgramName ProgramName) { visit(); }
    public void visit(Program Program) { visit(); }


    public void visit() { }
}
