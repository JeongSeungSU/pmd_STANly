package net.sourceforge.pmd.lang.java.rule.stanly.relation.analysisnode;

import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTAllocationExpression;
import net.sourceforge.pmd.lang.java.ast.ASTArguments;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTName;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryPrefix;
import net.sourceforge.pmd.lang.java.ast.ASTPrimarySuffix;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.Util.MacroFunctions;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodAnalysisException;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodAnlaysis;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodResult;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.Relations;

/**
 * TargetResult = +type+->abc.b(!!!)
 * TypeName		= Unknown아니면 return type
 * IsProcess	= true
 * @since 2013. 2. 19.오전 4:05:45
 * @author JeongSeungsu
 */
public class PrimaryExpressionAnalysisNode extends AbstractASTAnalysisNode{


	public PrimaryExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException {
		ASTPrimaryExpression primaryexpression = (ASTPrimaryExpression)analysisnode;
		
		//중복되면 이미 처리되있는 데이터를 리턴해줌...
		if(ProcessedPrimaryExpressionList.containsKey(primaryexpression))
			return ProcessedPrimaryExpressionList.get(primaryexpression);
		
		MethodResult Result = new MethodResult("", MethodAnlysistor.GetUnknownTypeName(), true);
		
		MethodResult PrefixResult = MethodAnlysistor.ProcessMethodCallAndAccess(
									(AbstractJavaNode)primaryexpression.jjtGetChild(0), sourcenode);
		Result.TargetResult = PrefixResult.TargetResult;
		Result.TypeName = PrefixResult.TypeName;
		
		//여기해야됨
		//Suffix처리...
		for(int i = 1; i < primaryexpression.jjtGetNumChildren(); i++)
		{
			Result.TypeName = MethodAnlysistor.GetUnknownTypeName();
			Node ChildrenNode = primaryexpression.jjtGetChild(i);
			
			MethodResult SuffixResult = MethodAnlysistor.ProcessMethodCallAndAccess((AbstractJavaNode)ChildrenNode, sourcenode);

			//1번재 서픽스가 Argument...
			if(i == 1 && PrefixResult.IsProcess && SuffixResult.IsProcess)
			{
				String DeleteType = MethodAnlysistor.TypeSperateApplyer(PrefixResult.TypeName);
				Result.TargetResult = MacroFunctions.NotRegularExpressionReplaceFirst(Result.TargetResult, DeleteType, "");
			}
			Result.TargetResult += SuffixResult.TargetResult;
		}
		
		if(!Result.TargetResult.equals(""))
			RelationList.AddRelation(Relations.UNKNOWN, sourcenode.getFullName(),Result.TargetResult,sourcenode,null);
		
		//이미 처리된것 중복을 막기 위해서 처리된 데이터를 입력...
		ProcessedPrimaryExpressionList.put(primaryexpression, Result);
		
		return Result;
	}
}
