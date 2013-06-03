package net.sourceforge.pmd.lang.java.rule;

public class Violation {
	public final static int BASIC = 0;
	public final static int NAMING = 1;
	private Integer violationType;//0: Basic, 1:Naming
	
	public Integer getViolationType() {
		return violationType;
	}
	public void setViolationType(Integer violationType) {
		this.violationType = violationType;
	}
	public String getSourcePath() {
		return sourcePath;
	}
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}
	public int getSourceLine() {
		return sourceLine;
	}
	public void setSourceLine(int sourceLine) {
		this.sourceLine = sourceLine;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getDomainLeftValue() {
		return domainLeftValue;
	}
	public void setDomainLeftValue(int domainLeftValue) {
		this.domainLeftValue = domainLeftValue;
	}
	private String sourcePath;
	private int sourceLine;
	private String message;
	private int domainLeftValue;
	
	
	Violation()
	{
		
	}
	Violation(int type,String path,int line,String msg,int domainleftvalue)
	{
		violationType = type;
		sourcePath = path;
		sourceLine = line;
		message = msg;
		domainLeftValue = domainleftvalue;
	}
}
