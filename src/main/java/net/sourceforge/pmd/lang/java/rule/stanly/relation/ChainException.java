package net.sourceforge.pmd.lang.java.rule.stanly.relation;

public class ChainException extends Exception {
	public String ErrorPath = ""; 
    public ChainNodeResult ExceptionResult;
	
	public ChainException(String errorpath,ChainNodeResult result)
	{
		ErrorPath 		= errorpath;
		ExceptionResult = result;
    }


	public void PrintCauseException()
	{
		System.out.println(ErrorPath);
		System.out.println("ExceptionResult");
		System.out.println("처리할 노드 : "+ ExceptionResult.NextProecessNode);
	}
}
