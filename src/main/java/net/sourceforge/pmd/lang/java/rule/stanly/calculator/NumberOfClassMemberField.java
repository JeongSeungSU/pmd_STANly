package net.sourceforge.pmd.lang.java.rule.stanly.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ClassDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNodeType;

public class NumberOfClassMemberField extends AbstractCalculator {
	
	public void NumberOfMember(){}	
	
	public void calcMetric(Stack<ElementNode> entryStack, ASTClassOrInterfaceDeclaration node, Object data)
	{
		List<ElementNode> nodeList = new ArrayList<ElementNode>();
		nodeList.add(0,entryStack.pop());//처음 ClassDomain은 자기 자신을 가르키므로 무시
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
	
	/*public void calcMetric(Stack<ElementNode> entryStack, ASTClassOrInterfaceBody node, Object data)
	{	// Instance Class는 메트릭에 추가하지 않음
		if(node.getNthParent(1) instanceof ASTAllocationExpression)
		{
			calc(entryStack);
		}
	}*/
	
	public void calcMetric(Stack<ElementNode> entryStack, ASTMethodDeclaration node, Object data)
	{
		List<ElementNode> nodeList = new ArrayList<ElementNode>();
		//nodeList.add(0,entryStack.pop());//처음 MethodDomain은 자기 자신을 가르키므로 무시
		while(entryStack.size() > 0 && entryStack.peek().getType() != ElementNodeType.CLASS)
		{
			nodeList.add(0,entryStack.pop());
		}
		if(entryStack.size() != 0)
		{
			((ClassDomain)entryStack.peek()).metric.addMethods(1);
			//System.out.println(((ClassDomain)entryStack.peek()).getFullName() + " has " + ((ClassDomain)entryStack.peek()).metric.getMethods());
		}
		for(ElementNode n:nodeList)
			entryStack.push(n);
	}
	
	public void calcMetric(Stack<ElementNode> entryStack, ASTFieldDeclaration node, Object data)
	{
		List<ElementNode> nodeList = new ArrayList<ElementNode>();
		//nodeList.add(0,entryStack.pop());//처음 ClassDomain은 자기 자신을 가르키므로 무시
		while(entryStack.size() > 0 && entryStack.peek().getType() != ElementNodeType.CLASS)
		{
			nodeList.add(0,entryStack.pop());
		}
		if(entryStack.size() != 0)
		{
			((ClassDomain)entryStack.peek()).metric.addFields(1);
			//System.out.println(((ClassDomain)entryStack.peek()).getFullName() + " has " + ((ClassDomain)entryStack.peek()).metric.getFields());
		}
		for(ElementNode n:nodeList)
			entryStack.push(n);
	}
}
