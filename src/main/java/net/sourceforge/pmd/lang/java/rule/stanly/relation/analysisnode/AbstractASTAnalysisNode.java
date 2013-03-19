package net.sourceforge.pmd.lang.java.rule.stanly.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodAnalysisException;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodAnlaysis;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodResult;

public abstract class AbstractASTAnalysisNode {
	protected DomainRelationList RelationList;
	protected Map<ASTPrimaryExpression, MethodResult> ProcessedPrimaryExpressionList;
	protected MethodAnlaysis MethodAnlysistor;
	
	public AbstractASTAnalysisNode(DomainRelationList relationlist , Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList , 
					MethodAnlaysis anlysis)
	{
		RelationList = relationlist;
		ProcessedPrimaryExpressionList = PrimaryExpressionList;
		MethodAnlysistor = anlysis;
	}
	
	abstract public MethodResult AnalysisAST(AbstractJavaNode analysisnode, ElementNode sourcenode) throws MethodAnalysisException;
}
