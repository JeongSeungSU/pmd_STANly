package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode;

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
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationResult;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationAnalyst;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.Relations;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.exception.RelationAnalysisException;

/**
 * 이것은 더이상 쪼갤 수 없는 의미 있는 기본 Expression으로 보면됨
 * TargetResult = +type+->abc.b(!!!)
 * TypeName		= Unknown아니면 return type
 * IsProcess	= true
 * @since 2013. 2. 19.오전 4:05:45
 * @author JeongSeungsu
 */
public class PrimaryExpressionAnalysisNode extends AbstractASTAnalysisNode{


	public PrimaryExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, RelationResult> PrimaryExpressionList,
			RelationAnalyst anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RelationResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws RelationAnalysisException {
		ASTPrimaryExpression primaryexpression = (ASTPrimaryExpression)analysisnode;
		
		//중복되면 이미 처리되있는 데이터를 리턴해줌...
		if(ProcessedPrimaryExpressionList.containsKey(primaryexpression))
			return ProcessedPrimaryExpressionList.get(primaryexpression);
		
		RelationResult Result = new RelationResult("", MethodAnlysistor.GetUnknownTypeName(), true);
		
		RelationResult PrefixResult = MethodAnlysistor.ProcessMethodCallAndAccess(
									(AbstractJavaNode)primaryexpression.jjtGetChild(0), sourcenode);
		Result.TargetResult = PrefixResult.TargetResult;
		Result.TypeName = PrefixResult.TypeName;
		
		//여기해야됨
		//Suffix처리...
		for(int i = 1; i < primaryexpression.jjtGetNumChildren(); i++)
		{
			Result.TypeName = MethodAnlysistor.GetUnknownTypeName();
			Node ChildrenNode = primaryexpression.jjtGetChild(i);
			
			RelationResult SuffixResult = MethodAnlysistor.ProcessMethodCallAndAccess((AbstractJavaNode)ChildrenNode, sourcenode);

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
