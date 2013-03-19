package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode;

import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTEnumDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTTypeArgument;
import net.sourceforge.pmd.lang.java.ast.ASTTypeArguments;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationResult;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationAnalyst;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.exception.RelationAnalysisException;

/**
 * enum 처리
 * TargetResult =  x
 * TypeName		=  enum class 이름
 * IsProcess	=  true
 * @since 2013. 3. 19.오후 9:39:04
 * @author JeongSeungsu
 */
public class EnumDeclarationAnalysisNode extends AbstractASTAnalysisNode {

	public EnumDeclarationAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, RelationResult> PrimaryExpressionList,
			RelationAnalyst anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RelationResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws RelationAnalysisException 
	{
		ASTEnumDeclaration enumdeclaration = (ASTEnumDeclaration)analysisnode;
		String ClassName = "";
		
		if(enumdeclaration.getType() != null) // Class 파일이 있을경우 바로 전체 클레스 경로를 입력함 YHC
			ClassName = enumdeclaration.getType().getName();
		else if(enumdeclaration.getQualifiedName() != null)
			ClassName = enumdeclaration.getQualifiedName();
		else
			ClassName = enumdeclaration.getImage();
		
		return new RelationResult("",ClassName,true);
	}
}
