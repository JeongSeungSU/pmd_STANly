package net.sourceforge.pmd.lang.java.rule.stanly.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTArgumentList;
import net.sourceforge.pmd.lang.java.ast.ASTArguments;
import net.sourceforge.pmd.lang.java.ast.ASTExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.Util.MacroFunctions;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodAnalysisException;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodAnlaysis;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodResult;

/**
 * TargetResult =  (a(PrimaryExpression),b,c) --> (+type+a)이런식
 * TypeName		=  unknown
 * IsProcess	=  true
 * @since 2013. 2. 19.오전 3:59:01
 * @author JeongSeungsu
 */
public class ArgumentsAnalysisNode extends AbstractASTAnalysisNode {

	public ArgumentsAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException 
	{
		ASTArguments arguments = (ASTArguments)analysisnode;
		ASTArgumentList argumentlist = arguments.getFirstChildOfType(ASTArgumentList.class);
		if(MacroFunctions.NULLTrue(argumentlist))
			return new MethodResult("()",MethodAnlysistor.GetUnknownTypeName(),true);
		int argumentcount = argumentlist.jjtGetNumChildren();
		String ParameterText = "(";
		
		for(int i = 0; i < argumentcount; i++)
		{
			ASTExpression expression = (ASTExpression)argumentlist.jjtGetChild(i);
			//여기서 타입 나눠서 줘야함...ㅇㅋ?
			MethodResult argumentresult =  MethodAnlysistor.ProcessMethodCallAndAccess(expression, sourcenode);
			if(argumentresult.TypeName.equals(MethodAnlysistor.GetUnknownTypeName()))
				ParameterText += argumentresult.TargetResult + ",";
			else
				ParameterText += MethodAnlysistor.TypeSperateApplyer(argumentresult.TypeName) + ",";
		}
		if(argumentcount > 0)
			ParameterText = ParameterText.substring(0,ParameterText.length()-1);
		
		ParameterText += ")";
		
		return new MethodResult(ParameterText,MethodAnlysistor.GetUnknownTypeName(),true);
	}

}
