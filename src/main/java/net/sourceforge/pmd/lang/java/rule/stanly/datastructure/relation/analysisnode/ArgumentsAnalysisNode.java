package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTArgumentList;
import net.sourceforge.pmd.lang.java.ast.ASTArguments;
import net.sourceforge.pmd.lang.java.ast.ASTExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.Util.MacroFunctions;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationResult;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationAnalyst;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.exception.RelationAnalysisException;

/**
 * Argument를 분석하여 String 형식으로 변환시켜 리턴
 * TargetResult =  (a(PrimaryExpression),b,c) --> (+type+a)이런식
 * TypeName		=  unknown
 * IsProcess	=  true
 * @since 2013. 2. 19.오전 3:59:01
 * @author JeongSeungsu
 */
public class ArgumentsAnalysisNode extends AbstractASTAnalysisNode {

	public ArgumentsAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, RelationResult> PrimaryExpressionList,
			RelationAnalyst anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RelationResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws RelationAnalysisException 
	{
		ASTArguments arguments = (ASTArguments)analysisnode;
		ASTArgumentList argumentlist = arguments.getFirstChildOfType(ASTArgumentList.class);
		if(MacroFunctions.NULLTrue(argumentlist))
			return new RelationResult("()",MethodAnlysistor.GetUnknownTypeName(),true);
		int argumentcount = argumentlist.jjtGetNumChildren();
		String ParameterText = "(";
		
		for(int i = 0; i < argumentcount; i++)
		{
			ASTExpression expression = (ASTExpression)argumentlist.jjtGetChild(i);
			//여기서 타입 나눠서 줘야함...ㅇㅋ?
			RelationResult argumentresult =  MethodAnlysistor.ProcessMethodCallAndAccess(expression, sourcenode);
			if(argumentresult.TypeName.equals(MethodAnlysistor.GetUnknownTypeName()))
				ParameterText += argumentresult.TargetResult + ",";
			else
				ParameterText += MethodAnlysistor.TypeSperateApplyer(argumentresult.TypeName) + ",";
		}
		if(argumentcount > 0)
			ParameterText = ParameterText.substring(0,ParameterText.length()-1);
		
		ParameterText += ")";
		
		return new RelationResult(ParameterText,MethodAnlysistor.GetUnknownTypeName(),true);
	}

}
