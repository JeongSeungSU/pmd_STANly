package net.sourceforge.pmd.lang.java.rule;

import java.util.ArrayList;
import java.util.List;

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
		violationList.add(e);
	}
	static public void AddViolation(int type, String path,String name,String line, String msg)
	{
		Violation v = new Violation(type,path,name,line,msg);
	}
}
