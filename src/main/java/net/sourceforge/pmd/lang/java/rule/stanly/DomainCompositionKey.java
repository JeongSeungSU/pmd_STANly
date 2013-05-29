package net.sourceforge.pmd.lang.java.rule.stanly;

public class DomainCompositionKey {
	private int SourceID;
	private int TargetID;
	
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
	@Override
	public boolean equals(Object o)
	{
		if(o == this)
			return true;
		if(!(o instanceof DomainCompositionKey))
			return false;
		
		DomainCompositionKey dc = (DomainCompositionKey)o;
		//3개 비교 함 SourceID와 TargetID
		return dc.SourceID == SourceID &&
			   dc.TargetID == TargetID;
	}
	@Override 
	public int hashCode()
	{
		int Result = 17;
		Result = 31 * Result + SourceID;
		Result = 31 * Result + TargetID;
		
		return Result;
	}
	
}
