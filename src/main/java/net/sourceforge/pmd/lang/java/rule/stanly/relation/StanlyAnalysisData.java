package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.List;

import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.analysisnode.DomainRelation;

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
