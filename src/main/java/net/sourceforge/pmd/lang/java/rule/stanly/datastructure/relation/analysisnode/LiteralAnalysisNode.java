package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTBooleanLiteral;
import net.sourceforge.pmd.lang.java.ast.ASTLiteral;
import net.sourceforge.pmd.lang.java.ast.ASTNullLiteral;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationResult;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationAnalyst;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.exception.RelationAnalysisException;

/**
 * 이건 자바에서 쓰이는 기본적인 타입들을 나타냄
 * TargetResult = ""빈칸
 * TypeName		= float,int,string,null,boolean
 * IsProcess	= true
 * @since 2013. 2. 21.오후 4:52:27
 * @author JeongSeungsu
 */
public class LiteralAnalysisNode extends AbstractASTAnalysisNode {

	public LiteralAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, RelationResult> PrimaryExpressionList,
			RelationAnalyst anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RelationResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws RelationAnalysisException 
	{
		ASTLiteral literal = (ASTLiteral)analysisnode;
		RelationResult result = new RelationResult("","",true);
		if(literal.isCharLiteral())
			result.TypeName = "char";
		else if(literal.isFloatLiteral())
			result.TypeName = "float";
		else if(literal.isStringLiteral())
			result.TypeName = "string";
		else if(literal.isIntLiteral())
			result.TypeName = "int";
		else
		{
			AbstractJavaNode childenode= (AbstractJavaNode)literal.jjtGetChild(0);
			
			if(childenode.getClass() == ASTNullLiteral.class)
				result.TypeName = "null";
			else if(childenode.getClass() == ASTBooleanLiteral.class)
				result.TypeName = "boolean";
			else
				throw new RelationAnalysisException("Literal 에서 " + childenode.getClass().toString()+
													"을 찾을 수 없습니다.");
		}
		return result;
	}

}
