package net.sourceforge.pmd.lang.java.rule.stanly.relation;

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
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

public class ExpressionAnalysisNode extends AbstractASTAnalysisNode {

	public ExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException 
	{
		ASTExpression expression = (ASTExpression)analysisnode;
		if(expression.jjtGetNumChildren() > 1)
			return new MethodResult("",MethodAnlysistor.GetUnknownTypeName(),false);
		
		MethodResult result = ExceptionProcess((AbstractJavaNode) expression.jjtGetChild(0), sourcenode);
		
		
		return result;
	}
	
	private MethodResult ExceptionProcess(AbstractJavaNode exceptionnode,ElementNode sourcenode) throws MethodAnalysisException
	{
		MethodResult result = new MethodResult("",MethodAnlysistor.GetUnknownTypeName(),true);
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
