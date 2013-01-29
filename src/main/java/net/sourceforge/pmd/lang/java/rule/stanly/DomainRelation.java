package net.sourceforge.pmd.lang.java.rule.stanly;

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
}
