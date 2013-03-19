package net.sourceforge.pmd.lang.java.rule.stanly.datastructure;

import java.util.List;

import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelation;

/**
 * 최종적으로 분석된 결과값
 * ElementNode의 RootNode => ProjectNode 와
 * RelationList를 가지고 있는 class
 * @since 2013. 3. 20.오전 1:06:37
 * @author JeongSeungsu
 */
public class StanlyAnalysisData {
	private List<DomainRelation> RelationList;
	private ElementNode	RootNode;
	
	
	public StanlyAnalysisData()
	{
		RelationList = null;
		RootNode = null;
	}
	
	public List<DomainRelation> getRelationList() {
		return RelationList;
	}
	public void setRelationList(List<DomainRelation> relationList) {
		RelationList = relationList;
	}
	public ElementNode getRootNode() {
		return RootNode;
	}
	public void setRootNode(ElementNode rootNode) {
		RootNode = rootNode;
	}
	
	
}
