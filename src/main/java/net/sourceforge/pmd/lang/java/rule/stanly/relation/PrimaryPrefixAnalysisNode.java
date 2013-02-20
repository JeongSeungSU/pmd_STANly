package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTAllocationExpression;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTName;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryPrefix;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.Util.MacroFunctions;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

/**
 * TargetResult = +type+a.b이런식 
 * TypeName		= type이 있으면 그 타입 리턴 없으면 걍 unknown
 * IsProcess	= true면 자기 자신의 오브젝트 내에서 콜되거나 액세스된것이라 볼수 있음
 * @since 2013. 2. 19.오전 4:06:11
 * @author JeongSeungsu
 */
public class PrimaryPrefixAnalysisNode extends AbstractASTAnalysisNode {

	public PrimaryPrefixAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException 
	{
		ASTPrimaryPrefix Prefixnode = (ASTPrimaryPrefix)analysisnode;
		String NowString = "";
		String ResultTypeName = "unknown";
		//자기자신안에 들어가 있는 오브젝트 예
		//a.b()가 아니라 b()이렇게 되어있는놈 자기 자신밖에 호출 못하는 놈인가 아닌가
		boolean IsThisObject = false;		
		
		if(Prefixnode.usesSuperModifier())
			NowString = "Super";
		else if(Prefixnode.usesThisModifier())
			NowString = sourcenode.getParent().getFullName();
		else
		{
			ASTName name = Prefixnode.getFirstChildOfType(ASTName.class);
			if(!MacroFunctions.NULLTrue(name))
			{
				String[] TypeName = name.getImage().split("\\.");
				MethodResult Type = MethodAnlysistor.ProcessMethodCallAndAccess(name,sourcenode);
				String Isdot = "";
				if (TypeName.length == 1) 
				{
					NowString = sourcenode.getParent().getFullName();
					Isdot = ".";
					IsThisObject=  true;
				}
				
				if(Type.IsProcess == true)
				{
					NowString += Isdot + Type.TargetResult;
					NowString = MethodAnlysistor.TypeSperateApplyer(Type.TypeName) + NowString;
					ResultTypeName = Type.TypeName;
				}
				else
				{
					NowString += Isdot + TypeName[0];
					NowString = MethodAnlysistor.TypeSperateApplyer("Unknown") + NowString;
				}

				if(TypeName.length > 1)
					NowString += "." + TypeName[1];
			}
			
			ASTAllocationExpression allocation = Prefixnode.getFirstChildOfType(ASTAllocationExpression.class);
			if(!MacroFunctions.NULLTrue(allocation))
			{
				ASTClassOrInterfaceType allocationtype = allocation.getFirstDescendantOfType(ASTClassOrInterfaceType.class);
				//여기에 추가할 필요 있음...생성자다 알려줄 필요가 있음...
				NowString = MethodAnlysistor.ProcessMethodCallAndAccess(allocationtype, sourcenode).TypeName;
			}
		}
		return new MethodResult(NowString, ResultTypeName , IsThisObject);
	}
	
}
