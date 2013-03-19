package net.sourceforge.pmd.lang.java.rule.stanly.relation.analysisnode;

import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.Relations;

/**
 * 각 도메인 사이의 arch
 * @since 2013. 1. 29.오후 11:16:50
 * @author JeongSeungsu
 */
public class DomainRelation {
	
	public DomainRelation() {
		super();
		Relation = Relations.UNKNOWN;
		Source = new String();
		Target = new String();
	}
	private Relations			Relation;
	private ElementNode			SourceNode;
	private ElementNode			TargetNode;
	private String 				Source;
	private String				Target;
	
	public Relations getRelation() {
		return Relation;
	}
	public void setRelation(Relations relation) {
		Relation = relation;
	}
	public String getSource() {
		return Source;
	}
	public void setSource(String source) {
		Source = source;
	}
	public String getTarget() {
		return Target;
	}
	public void setTarget(String target) {
		Target = target;
	}
	public ElementNode getSourceNode() {
		return SourceNode;
	}
	public void setSourceNode(ElementNode sourceNode) {
		SourceNode = sourceNode;
	}
	public ElementNode getTargetNode() {
		return TargetNode;
	}
	public void setTargetNode(ElementNode targetNode) {
		TargetNode = targetNode;
	}
}
