package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTTypeArgument;
import net.sourceforge.pmd.lang.java.ast.ASTTypeArguments;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

/**
 * TargetResult = x
 * TypeName		= ClassName+ArgumentList ==> abc<a<b>> 이런식
 * IsProcess	= true
 * @since 2013. 2. 19.오전 4:01:59
 * @author JeongSeungsu
 */
public class ClassOrInterfaceTypeAnalysisNode extends AbstractASTAnalysisNode {

	public ClassOrInterfaceTypeAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException {
		String ClassName = "";
		String ArgumentList = "";
		ASTClassOrInterfaceType classorinterfacetype = (ASTClassOrInterfaceType)analysisnode;
		ASTTypeArguments typeargument = classorinterfacetype.getFirstDescendantOfType(ASTTypeArguments.class);
		if(typeargument != null)
		{
			ArgumentList += "<";
			List<ASTTypeArgument> argument = typeargument.findChildrenOfType(ASTTypeArgument.class);
			for(ASTTypeArgument ar : argument)
			{
				ArgumentList += MethodAnlysistor.ProcessMethodCallAndAccess(ar, sourcenode).TypeName;
				ArgumentList += ",";
			}
			ArgumentList = ArgumentList.substring(0,ArgumentList.length()-1);
			ArgumentList += ">";
		}

		if(classorinterfacetype.getType() != null) // Class 파일이 있을경우 바로 전체 클레스 경로를 입력함 YHC
			ClassName = classorinterfacetype.getType().getName();
		else if(classorinterfacetype.getQualifiedName() != null)
			ClassName = classorinterfacetype.getQualifiedName();
		else
			ClassName = classorinterfacetype.getImage() + " (Cannot Found!)";
		
		return new MethodResult("",ClassName+ArgumentList,true);
	}

}
