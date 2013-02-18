package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTTypeArgument;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

/**
 * TargetResult = X 
 * TypeName		= argument마다 타입 <T,AB<C>>이런것
 * IsProcess	= true
 * @since 2013. 2. 19.오전 4:09:03
 * @author JeongSeungsu
 */
public class TypeArgumentAnalysisNode extends AbstractASTParserNode{

	public TypeArgumentAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException 
	{
		ASTTypeArgument argument = (ASTTypeArgument)analysisnode; 
		ASTClassOrInterfaceType typename = argument.getFirstDescendantOfType(ASTClassOrInterfaceType.class);
		MethodResult result = new MethodResult("","",true);
		
		if(typename == null)
			result.TypeName = "?";
		else
			result = MethodAnlysistor.ProcessMethodCallAndAccess(typename, sourcenode);
		
		return result;
	}

}
