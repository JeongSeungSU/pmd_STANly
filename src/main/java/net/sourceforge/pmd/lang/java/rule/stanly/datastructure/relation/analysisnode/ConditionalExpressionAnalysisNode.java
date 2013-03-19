package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTConditionalExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationResult;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationAnalyst;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.exception.RelationAnalysisException;

/**
 * 분기문 
 * TargetResult =  분기문각각의 존재하는 primaryExpression값에 따라서 리턴값 결정
 * TypeName		=  unknown
 * IsProcess	=  true
 * @since 2013. 3. 19.오후 9:38:08
 * @author JeongSeungsu
 */
public class ConditionalExpressionAnalysisNode extends AbstractASTAnalysisNode {

	public ConditionalExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, RelationResult> PrimaryExpressionList,
			RelationAnalyst anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RelationResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws RelationAnalysisException 
	{
		ASTConditionalExpression conditionalexpression = (ASTConditionalExpression)analysisnode;
		if(conditionalexpression.jjtGetNumChildren() != 3)
			throw new RelationAnalysisException("ConditionalExpression자식이 3개 분기문인데...");
		
		AbstractJavaNode childnode1 = (AbstractJavaNode) conditionalexpression.jjtGetChild(1);
		AbstractJavaNode childnode2 = (AbstractJavaNode) conditionalexpression.jjtGetChild(2);
		
		RelationResult childresult1 = MethodAnlysistor.ProcessMethodCallAndAccess(childnode1, sourcenode);
		RelationResult childresult2 = MethodAnlysistor.ProcessMethodCallAndAccess(childnode2, sourcenode);
		
		if(!childresult1.TypeName.equals(MethodAnlysistor.GetUnknownTypeName()))
			return childresult1;
		else if(!childresult2.TypeName.equals(MethodAnlysistor.GetUnknownTypeName()))
			return childresult2;
		else if(!childresult1.TypeName.equals(""))
			return childresult1;
		else
			return childresult2;
	}

}
