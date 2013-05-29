package net.sourceforge.pmd.lang.java.rule;

public class Violation {
	public final static int BASIC = 0;
	public final static int NAMING = 1;
	private Integer violationType;//0: Basic, 1:Naming
	private String sourcePath;
	private int sourceLine;
	private String message;
	
	Violation()
	{
		
	}
	Violation(int type,String path,int line,String msg)
	{
		violationType = type;
		sourcePath = path;
		sourceLine = line;
		message = msg;
	}
}
