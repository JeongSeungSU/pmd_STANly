package net.sourceforge.pmd.lang.java.rule;

public class Violation {
	public final int BASIC = 0;
	public final int NAMING = 1;
	private Integer violationType;//0: Basic, 1:Naming
	private String sourcePath;
	private String sourceName;
	private String sourceLine;
	private String message;
	
	Violation()
	{
		
	}
	Violation(int type,String path,String name,String line,String msg)
	{
		violationType = type;
		sourcePath = path;
		sourceName = name;
		sourceLine = line;
		message = msg;
	}
}
