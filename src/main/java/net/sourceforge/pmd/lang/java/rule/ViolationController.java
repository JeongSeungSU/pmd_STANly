package net.sourceforge.pmd.lang.java.rule;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.rule.stanly.StanlyControler;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNodeType;

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
    	//node.getParentsOfType(ASTCompilationUnit.class)
		Violation v = new Violation(type,path,line,message,-1);
		//System.out.println(path + ":" + line + "\t\t" + message);
		addViolation(v);
	}
	private static ElementNode TopLevelClassDomain(ElementNode elementnode)
	{
		ElementNode node = elementnode;
		ElementNode TopLevelNode = elementnode; 
		while(node != null)
		{
			ElementNodeType type = node.getType();
			if(type  == ElementNodeType.CLASS 		||
			   type  == ElementNodeType.INTERFACE 	||
			   type  == ElementNodeType.ENUM)
			{
				TopLevelNode = node;
			}
			node = node.getParent();
		}	
		return TopLevelNode;
	}
	
	static public void AfterCalculate()
	{
		for(Violation violation: violationList)
		{
			ElementNode elementnode = StanlyControler.SearchMatchingClassPath(violation.getSourcePath(),StanlyControler.getRootNode());
			elementnode = TopLevelClassDomain(elementnode);
			
			violation.setDomainLeftValue(elementnode.getLeftSideValue());
			if( violation.getDomainLeftValue() == -1)
				System.out.println("왓더퍽");
		}
	}
}
