package net.sourceforge.pmd.lang.java.rule.stanly.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ClassDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNodeType;

public class NumberOfMemberClass extends AbstractCalculator {
	
	public void NumberOfMember(){}
	
	public void calc(Stack<ElementNode> entryStack)
	{
		List<ElementNode> nodeList = new ArrayList<ElementNode>();
		nodeList.add(0,entryStack.pop());//처음 ClassDomain은 무시
		while(entryStack.size() > 0 && entryStack.peek().getType() != ElementNodeType.CLASS)
		{
			nodeList.add(0,entryStack.pop());
		}
		if(entryStack.size() != 0)
		{
			((ClassDomain)entryStack.peek()).metric.addClasses(1);
			//System.out.println(((ClassDomain)entryStack.peek()).getFullName() + " has " + ((ClassDomain)entryStack.peek()).metric.getClasses());
		}
		for(ElementNode n:nodeList)
			entryStack.push(n);
	}
	
	public void calcMetric(Stack<ElementNode> entryStack, ASTClassOrInterfaceDeclaration node, Object data)
	{
		//if(node.isInterface())	return;
		
		calc(entryStack);
		
	}
	
	/*public void calcMetric(Stack<ElementNode> entryStack, ASTClassOrInterfaceBody node, Object data)
	{		
		if(node.getNthParent(1) instanceof ASTAllocationExpression)
		{
			calc(entryStack);
		}
	}*/
}
