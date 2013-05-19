package net.sourceforge.pmd.lang.java.rule.stanly;

public class DomainComposition {
	
	private int SourceID;
	private int TargetID;
	private int RelationCount;
	
	private Relations DelegateType;

	public DomainComposition()
	{
		SourceID = -1;
		TargetID = -1;
		RelationCount = 0;
		DelegateType = Relations.REFERENCES;
	}
	public void AddtionRelationCount()
	{
		++RelationCount;
	}
	
	public int getSourceID() {
		return SourceID;
	}
	public void setSourceID(int sourceID) {
		SourceID = sourceID;
	}
	public int getTargetID() {
		return TargetID;
	}
	public void setTargetID(int targetID) {
		TargetID = targetID;
	}
	public int getRelationCount() {
		return RelationCount;
	}
	public void setRelationCount(int relationCount) {
		RelationCount = relationCount;
	}
	public Relations getDelegateType() {
		return DelegateType;
	}
	public void setDelegateType(Relations delegateType) {
		DelegateType = delegateType;
	}

}
