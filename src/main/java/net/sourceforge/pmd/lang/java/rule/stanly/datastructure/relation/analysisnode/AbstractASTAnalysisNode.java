package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationResult;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationAnalyst;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.exception.RelationAnalysisException;

/**
 * 기본 AST 분석을 위한 자료구조
 * 상속 받아서 오버라이딩 해서 처리
 * @since 2013. 3. 19.오후 9:32:46
 * @author JeongSeungsu
 */
public abstract class AbstractASTAnalysisNode {
	protected DomainRelationList RelationList;
	protected Map<ASTPrimaryExpression, RelationResult> ProcessedPrimaryExpressionList;
	protected RelationAnalyst MethodAnlysistor;
	
	public AbstractASTAnalysisNode(DomainRelationList relationlist , Map<ASTPrimaryExpression, RelationResult> PrimaryExpressionList , 
					RelationAnalyst anlysis)
	{
		RelationList = relationlist;
		ProcessedPrimaryExpressionList = PrimaryExpressionList;
		MethodAnlysistor = anlysis;
	}
	
	/**
	 * 여기서 각자의 AST타입에 맞게 알아서 처리함
	 * @since 2013. 3. 19.오후 9:33:21
	 * @author JeongSeungsu
	 * @param analysisnode
	 * @param sourcenode
	 * @return 처리된 결과를 리턴
	 * @throws RelationAnalysisException
	 */
	abstract public RelationResult AnalysisAST(AbstractJavaNode analysisnode, ElementNode sourcenode) throws RelationAnalysisException;
}
