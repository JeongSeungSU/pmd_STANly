package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTAllocationExpression;
import net.sourceforge.pmd.lang.java.ast.ASTArguments;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTExpression;
import net.sourceforge.pmd.lang.java.ast.ASTInstanceOfExpression;
import net.sourceforge.pmd.lang.java.ast.ASTLiteral;
import net.sourceforge.pmd.lang.java.ast.ASTName;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryPrefix;
import net.sourceforge.pmd.lang.java.ast.ASTPrimitiveType;
import net.sourceforge.pmd.lang.java.ast.ASTResultType;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.Util.MacroFunctions;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationResult;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationAnalyst;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.exception.RelationAnalysisException;

/**
 * TargetResult = +type+a.b이런식 
 * TypeName		= type이 있으면 그 타입 리턴 없으면 걍 unknown
 * IsProcess	= true면 자기 자신의 오브젝트 내에서 콜되거나 액세스된것이라 볼수 있음
 * @since 2013. 2. 19.오전 4:06:11
 * @author JeongSeungsu
 */
public class PrimaryPrefixAnalysisNode extends AbstractASTAnalysisNode {

	public PrimaryPrefixAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, RelationResult> PrimaryExpressionList,
			RelationAnalyst anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RelationResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws RelationAnalysisException 
	{

		ASTPrimaryPrefix Prefixnode = (ASTPrimaryPrefix)analysisnode;
		String NowString = "";
		String ResultTypeName = MethodAnlysistor.GetUnknownTypeName();
		//자기자신안에 들어가 있는 오브젝트 예
		//a.b()가 아니라 b()이렇게 되어있는놈 자기 자신밖에 호출 못하는 놈인가 아닌가
		boolean IsThisObject = false;		
		
		if(Prefixnode.usesSuperModifier())
			NowString = "Super";
		else if(Prefixnode.usesThisModifier())
		{
			NowString = sourcenode.getParent().getFullName();
			ResultTypeName = NowString; 
		}
		else
		{
			if(Prefixnode.jjtGetNumChildren() > 1)
				throw new RelationAnalysisException("PrimaryPrefix의 자식이 두개이상이네요 처리해줘야 되요...");
			
			AbstractJavaNode ChildAnalysisNode = (AbstractJavaNode)Prefixnode.jjtGetChild(0);
			if(MacroFunctions.NULLTrue(ChildAnalysisNode))
				throw new RelationAnalysisException("PrimaryPrefix의 자식이 한개도 없네요...");
			
			if(ChildAnalysisNode.getClass() == ASTName.class)
			{
				ASTName name = (ASTName)ChildAnalysisNode;
				String[] TypeName = name.getImage().split("\\.");
				RelationResult Type = MethodAnlysistor.ProcessMethodCallAndAccess(name,sourcenode);
				String Isdot = "";
				if (TypeName.length == 1) 
				{
					NowString = sourcenode.getParent().getFullName();
					Isdot = ".";
					IsThisObject=  true;
				}
				
				if(Type.IsProcess == true)
				{
					NowString += Isdot + MethodAnlysistor.TypeSperateApplyer(Type.TypeName) +Type.TargetResult;
					ResultTypeName = Type.TypeName;
				}
				else
				{
					NowString += Isdot + MethodAnlysistor.TypeSperateApplyer(MethodAnlysistor.GetUnknownTypeName()) +TypeName[0];
					if(TypeName.length > 1)
						NowString += "." + TypeName[1];
				}
			}
			else if(ChildAnalysisNode.getClass() == ASTAllocationExpression.class)
			{
				ASTAllocationExpression allocation = (ASTAllocationExpression)ChildAnalysisNode;
				ASTClassOrInterfaceType allocationtype = allocation.getFirstDescendantOfType(ASTClassOrInterfaceType.class);
				if(MacroFunctions.NULLTrue(allocationtype))
					throw new RelationAnalysisException("처리할 필요 없음");
				ResultTypeName = MethodAnlysistor.ProcessMethodCallAndAccess(allocationtype, sourcenode).TypeName;
				NowString = ResultTypeName;
				
				ASTArguments arguments = allocation.getFirstDescendantOfType(ASTArguments.class);
				if(!MacroFunctions.NULLTrue(arguments))
					NowString += MethodAnlysistor.ProcessMethodCallAndAccess(arguments, sourcenode).TargetResult;
			}
			else if(ChildAnalysisNode.getClass() == ASTLiteral.class)
			{
				ASTLiteral	literal	= (ASTLiteral)ChildAnalysisNode;
				RelationResult literalresult = MethodAnlysistor.ProcessMethodCallAndAccess(literal, sourcenode);
				NowString = literalresult.TargetResult;
				ResultTypeName = literalresult.TypeName;
			}
			else if(ChildAnalysisNode.getClass() == ASTResultType.class)
			{
				ASTResultType resulttype = (ASTResultType)ChildAnalysisNode;
				RelationResult result = MethodAnlysistor.ProcessMethodCallAndAccess(resulttype, sourcenode);
				
				NowString = result.TargetResult;
				ResultTypeName = result.TypeName;
			}
			else if(ChildAnalysisNode.getClass() == ASTExpression.class)
			{
				ASTExpression expression = (ASTExpression)ChildAnalysisNode;
				
				RelationResult expressionresult = MethodAnlysistor.ProcessMethodCallAndAccess(expression, sourcenode);
				
				NowString = expressionresult.TargetResult;
				ResultTypeName = expressionresult.TypeName;
			}
			else
				throw new RelationAnalysisException("PrimaryPrefix의 childNode에서 처리되지 않는 타입인"
												  +ChildAnalysisNode.getClass().toString() + " 이 발견되었어요" );
		}
		return new RelationResult(NowString, ResultTypeName , IsThisObject);
	}
}
