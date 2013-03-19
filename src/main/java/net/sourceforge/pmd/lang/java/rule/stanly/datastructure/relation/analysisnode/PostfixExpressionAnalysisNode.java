package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTPostfixExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationResult;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationAnalyst;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.exception.RelationAnalysisException;

/**
 * ++나 -- 뒤에 붙는것이지
 * TargetResult =  x
 * TypeName		=  primaryExpression의 리턴값
 * IsProcess	=  true
 * @since 2013. 3. 19.오후 9:47:10
 * @author JeongSeungsu
 */
public class PostfixExpressionAnalysisNode extends AbstractASTAnalysisNode{

	public PostfixExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, RelationResult> PrimaryExpressionList,
			RelationAnalyst anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RelationResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws RelationAnalysisException 
	{
		ASTPostfixExpression postfixexpression = (ASTPostfixExpression)analysisnode;
		if(postfixexpression.jjtGetNumChildren() > 1)
			throw new RelationAnalysisException("PostfixExpression자식이 2개네? 말도안되..");
		
		AbstractJavaNode childnode = (AbstractJavaNode) postfixexpression.jjtGetChild(0);
		RelationResult result = MethodAnlysistor.ProcessMethodCallAndAccess((AbstractJavaNode)childnode , sourcenode) ;
		
		return result;
	}

}
