package net.sourceforge.pmd.lang.java.rule.stanly.relation;

public class MethodAnalysisException extends Exception {
	public String Error = ""; 
	
	public MethodAnalysisException(String error)
	{
		Error 		= error;
    }


	public void PrintCauseException()
	{
		//System.out.println(Error);
	}
}
