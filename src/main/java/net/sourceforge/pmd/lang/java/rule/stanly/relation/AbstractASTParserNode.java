package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

public abstract class AbstractASTParserNode {
	protected DomainRelationList RelationList;
	protected Map<ASTPrimaryExpression, MethodResult> ProcessedPrimaryExpressionList;
	protected MethodAnlaysis MethodAnlysistor;
	
	public AbstractASTParserNode(DomainRelationList relationlist , Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList , 
					MethodAnlaysis anlysis)
	{
		RelationList = relationlist;
		ProcessedPrimaryExpressionList = PrimaryExpressionList;
		MethodAnlysistor = anlysis;
	}
	
	abstract public MethodResult AnalysisAST(AbstractJavaNode analysisnode, ElementNode sourcenode) throws MethodAnalysisException;
}
