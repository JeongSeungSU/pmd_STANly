package net.sourceforge.pmd.lang.java.rule.stanly.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.ast.ASTEnumDeclaration;
import net.sourceforge.pmd.lang.java.ast.JavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNodeType;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageDomain;

public class NumberOfUnits extends AbstractCalculator {
	public NumberOfUnits(){}
	
	public void calc(Stack<ElementNode> entryStack,JavaNode node,Object data)
	{
		if(node.getNthParent(2) instanceof ASTCompilationUnit)
		{
			List<ElementNode> nodeList = new ArrayList<ElementNode>();
			while(entryStack.size() > 0 && entryStack.peek().getType() != ElementNodeType.PACKAGE)
			{
				nodeList.add(0,entryStack.pop());
			}
			if(entryStack.size() != 0)
			{
				((PackageDomain)entryStack.peek()).metric.addUnits(1);
				System.out.println(((PackageDomain)entryStack.peek()).getFullName() + " has " + ((PackageDomain)entryStack.peek()).metric.getUnits());
			}
			for(ElementNode n:nodeList)
				entryStack.push(n);
		}
	}
	
	
	public void calcMetric(Stack<ElementNode> entryStack, ASTClassOrInterfaceDeclaration node, Object data)
	{
		calc(entryStack,node,data);
	}
	public void calcMetric(Stack<ElementNode> entryStack, ASTEnumDeclaration node, Object data)
	{
		calc(entryStack,node,data);
	}
}
