package net.sourceforge.pmd.lang.java.rule;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.lang.ast.Node;

public class ViolationController {
	static private List<Violation> violationList;
	
	ViolationController()
	{
		violationList = new ArrayList<Violation>();
	}
	
	static public List<Violation> getViolationList()
	{
		return violationList;
	}
	
	static public void addViolation(Violation e)
	{
		if(violationList == null)
			violationList = new ArrayList<Violation>();
		violationList.add(e);
	}
	static public void AddViolation(int type, Object data, Node node, String message)
	{
		String path = ((RuleContext)data).getSourceCodeFilename();
    	int line = node.getBeginLine();
		Violation v = new Violation(type,path,line,message);
		//System.out.println(path + ":" + line + "\t\t" + message);
		addViolation(v);
	}
}
