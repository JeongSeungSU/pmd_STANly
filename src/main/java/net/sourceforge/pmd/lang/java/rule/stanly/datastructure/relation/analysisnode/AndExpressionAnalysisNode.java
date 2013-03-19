package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTAndExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationResult;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationAnalyst;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.exception.RelationAnalysisException;

/**
 * 각각의 &&식의 리턴값에 따른다.
  * TargetResult =  &&로 표현된 것들중 하나의 PrimaryExpression의 타입을 리턴
 * TypeName		=  unknown
 * IsProcess	=  true
 * @since 2013. 2. 21.오후 6:49:29
 * @author JeongSeungsu
 */
public class AndExpressionAnalysisNode extends AbstractASTAnalysisNode {

	public AndExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, RelationResult> PrimaryExpressionList,
			RelationAnalyst anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RelationResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws RelationAnalysisException 
	{
		ASTAndExpression andexpression = (ASTAndExpression)analysisnode;
		RelationResult childresult = new RelationResult("",MethodAnlysistor.GetUnknownTypeName(),true);
		
		for(int i = 0 ; i < andexpression.jjtGetNumChildren(); i++)
		{
			AbstractJavaNode childnode = (AbstractJavaNode)andexpression.jjtGetChild(i);
			childresult = MethodAnlysistor.ProcessMethodCallAndAccess(childnode, sourcenode);
			
			if(!childresult.TypeName.equals(MethodAnlysistor.GetUnknownTypeName()))
				return childresult;
		}
		return childresult;
	}

}
