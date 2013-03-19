package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTUnaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationResult;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationAnalyst;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.exception.RelationAnalysisException;

public class UnaryExpressionAnalysisNode extends AbstractASTAnalysisNode {

	public UnaryExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, RelationResult> PrimaryExpressionList,
			RelationAnalyst anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RelationResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws RelationAnalysisException 
	{
		ASTUnaryExpression unaryexpression = (ASTUnaryExpression)analysisnode;
		if(unaryexpression.jjtGetNumChildren() > 1)
			throw new RelationAnalysisException("UnaryExpression자식이 1개가 넘어 이거 구현해줘야됨");

		AbstractJavaNode childnode = (AbstractJavaNode)unaryexpression.jjtGetChild(0);
		RelationResult result = MethodAnlysistor.ProcessMethodCallAndAccess(childnode, sourcenode);
		
		return result;
	}

}
