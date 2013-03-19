package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTConditionalAndExpression;
import net.sourceforge.pmd.lang.java.ast.ASTConditionalOrExpression;
import net.sourceforge.pmd.lang.java.ast.ASTEqualityExpression;
import net.sourceforge.pmd.lang.java.ast.ASTExpression;
import net.sourceforge.pmd.lang.java.ast.ASTInstanceOfExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTRelationalExpression;
import net.sourceforge.pmd.lang.java.ast.ASTUnaryExpressionNotPlusMinus;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationResult;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationAnalyst;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.exception.RelationAnalysisException;

/**
 * Expression표현식 AST중 상윗단 하나의 표현식을 나타낸다.
 * Expression 밑에 assign, equality등등 많은 표현식이 있다.
 * 그것들의 하위들은 primaryExpression의 자를수 있는 의미를 가진 최소단위로 나뉜다고 보면됨
 * 당연히 Expression의 자식을 보고 그 자식의 타입에 따라서 일단 비교문이면 boolean으로 타입 리턴 아니면
 * 이것을 처리 할 수 있는 다른 analysisnode에 위임
 * TargetResult =  모름
 * TypeName		=  때때로 boolean
 * IsProcess	=  true
 * @since 2013. 3. 19.오후 9:40:34
 * @author JeongSeungsu
 */
public class ExpressionAnalysisNode extends AbstractASTAnalysisNode {

	public ExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, RelationResult> PrimaryExpressionList,
			RelationAnalyst anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RelationResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws RelationAnalysisException 
	{
		ASTExpression expression = (ASTExpression)analysisnode;
		if(expression.jjtGetNumChildren() > 1)
			return new RelationResult("",MethodAnlysistor.GetUnknownTypeName(),false);
		
		RelationResult result = ExceptionProcess((AbstractJavaNode) expression.jjtGetChild(0), sourcenode);
		
		
		return result;
	}
	
	private RelationResult ExceptionProcess(AbstractJavaNode exceptionnode,ElementNode sourcenode) throws RelationAnalysisException
	{
		RelationResult result = new RelationResult("",MethodAnlysistor.GetUnknownTypeName(),true);
		if(exceptionnode.getClass() == ASTInstanceOfExpression.class)
			return result;
		else if(exceptionnode.getClass() == ASTConditionalOrExpression.class
				|| exceptionnode.getClass() == ASTRelationalExpression.class
				|| exceptionnode.getClass() == ASTUnaryExpressionNotPlusMinus.class
				|| exceptionnode.getClass() == ASTEqualityExpression.class
				|| exceptionnode.getClass() == ASTConditionalAndExpression.class)
		{
			result.TypeName = "boolean";
			return result;
		}
		else
			result = MethodAnlysistor.ProcessMethodCallAndAccess(exceptionnode, sourcenode);
		return result;
	}
	

}
