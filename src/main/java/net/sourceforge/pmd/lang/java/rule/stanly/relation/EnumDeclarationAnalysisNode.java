package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTEnumDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTTypeArgument;
import net.sourceforge.pmd.lang.java.ast.ASTTypeArguments;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

public class EnumDeclarationAnalysisNode extends AbstractASTAnalysisNode {

	public EnumDeclarationAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException 
	{
		ASTEnumDeclaration enumdeclaration = (ASTEnumDeclaration)analysisnode;
		String ClassName = "";
		
		if(enumdeclaration.getType() != null) // Class 파일이 있을경우 바로 전체 클레스 경로를 입력함 YHC
			ClassName = enumdeclaration.getType().getName();
		else if(enumdeclaration.getQualifiedName() != null)
			ClassName = enumdeclaration.getQualifiedName();
		else
			ClassName = enumdeclaration.getImage();
		
		return new MethodResult("",ClassName,true);
	}
}
