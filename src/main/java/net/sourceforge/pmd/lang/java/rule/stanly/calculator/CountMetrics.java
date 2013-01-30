package net.sourceforge.pmd.lang.java.rule.stanly.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.ast.ASTConstructorDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTEnumDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.JavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ClassDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNodeType;

public class CountMetrics extends AbstractCalculator {
	
	public void NumberOfMember(){}	
	
	public void calcMetric(Stack<ElementNode> entryStack, ASTCompilationUnit node, Object data)
	{
		if(entryStack.peek().getFullName().equals("net.sourceforge.pmd.lang.java.rule.matrics"))
			System.out.println("");
		ElementNode packageEntry = entryStack.peek();
		/*for(ElementNode classEntry:packageEntry.getChildren())
		{
			if(classEntry instanceof ClassDomain)
			{
				int numberOfClasses = ((ClassDomain) classEntry).metric.getClasses(); 
				int numberOfMethods = ((ClassDomain) classEntry).metric.getMethods();
				int numberOfFields =  ((ClassDomain) classEntry).metric.getFields();
				((PackageDomain) packageEntry).metric.addNumberOfClasses(numberOfClasses);
				((PackageDomain) packageEntry).metric.addNumberOfClass(numberOfClasses + 1);
				((PackageDomain) packageEntry).metric.addNumberOfMethods(numberOfMethods);
				((PackageDomain) packageEntry).metric.addNumberOfMethods(numberOfFields);
			}
		}*/
		//System.out.println(((PackageDomain) packageEntry).metric.getClassesPerClass() + " " +
		//					((PackageDomain) packageEntry).metric.getMethodsPerClass() + " " +
		//					((PackageDomain) packageEntry).metric.getFieldsPerClass());
	}
	
	
	public void calcUnit(Stack<ElementNode> entryStack,JavaNode node,Object data)
	{
		if(node.getNthParent(2) instanceof ASTCompilationUnit)
		{
			List<ElementNode> nodeList = new ArrayList<ElementNode>();
			while(entryStack.size() > 0 && entryStack.peek().getType() != ElementNodeType.PACKAGE)
				nodeList.add(0,entryStack.pop());
			if(entryStack.size() != 0)
				((PackageDomain)entryStack.peek()).metric.addUnits(1);
				//System.out.println(((PackageDomain)entryStack.peek()).getFullName() + " has " + ((PackageDomain)entryStack.peek()).metric.getUnits());
			for(ElementNode n:nodeList)
				entryStack.push(n);
		}
	}
	
	public void calcMetric(Stack<ElementNode> entryStack, ASTClassOrInterfaceDeclaration node, Object data)
	{
		List<ElementNode> nodeList = new ArrayList<ElementNode>();
		nodeList.add(0,entryStack.pop());//처음 ClassDomain은 자기 자신을 가르키므로 무시
		while(entryStack.size() > 0 && entryStack.peek().getType() != ElementNodeType.CLASS)
			nodeList.add(0,entryStack.pop());
		if(entryStack.size() != 0)
		{
			((ClassDomain)entryStack.peek()).metric.addClasses(1);
			//System.out.println(((ClassDomain)entryStack.peek()).getFullName() + " has " + ((ClassDomain)entryStack.peek()).metric.getClasses());
			while(entryStack.size() > 0 && entryStack.peek().getType() != ElementNodeType.PACKAGE)
				nodeList.add(0,entryStack.pop());
			((PackageDomain)entryStack.peek()).metric.addNumberOfClasses(1);
			((PackageDomain)entryStack.peek()).metric.addNumberOfClass(1);
		}
		else 
			((PackageDomain)nodeList.get(0)).metric.addNumberOfClass(1);
		for(ElementNode n:nodeList)
			entryStack.push(n);
		
		calcUnit(entryStack,node,data);
	}
	
	public void calcMetric(Stack<ElementNode> entryStack, ASTEnumDeclaration node, Object data)
	{
		calcUnit(entryStack,node,data);
	}
	
	/*public void calcMetric(Stack<ElementNode> entryStack, ASTClassOrInterfaceBody node, Object data)
	{	// Instance Class는 메트릭에 추가하지 않음
		if(node.getNthParent(1) instanceof ASTAllocationExpression)
		{
			calc(entryStack);
		}
	}*/
	
	public void calcMethod(Stack<ElementNode> entryStack)
	{
		List<ElementNode> nodeList = new ArrayList<ElementNode>();
		//nodeList.add(0,entryStack.pop());//처음 MethodDomain은 자기 자신을 가르키므로 무시
		while(entryStack.size() > 0 && entryStack.peek().getType() != ElementNodeType.CLASS)
			nodeList.add(0,entryStack.pop());
		if(entryStack.size() != 0)
		{
			ClassDomain classEntry = (ClassDomain)entryStack.pop();
			classEntry.metric.addMethods(1);
			//System.out.println(((ClassDomain)entryStack.peek()).getFullName() + " has " + ((ClassDomain)entryStack.peek()).metric.getMethods());
			
			if(entryStack.peek() instanceof PackageDomain)
				((PackageDomain)entryStack.peek()).metric.addNumberOfMethods(1);
			entryStack.push(classEntry);
		}
		for(ElementNode n:nodeList)
			entryStack.push(n);
	}
	public void calcMetric(Stack<ElementNode> entryStack, ASTConstructorDeclaration node, Object data)
	{
		calcMethod(entryStack);
	}
	public void calcMetric(Stack<ElementNode> entryStack, ASTMethodDeclaration node, Object data)
	{
		calcMethod(entryStack);		
	}
	
	public void calcMetric(Stack<ElementNode> entryStack, ASTFieldDeclaration node, Object data)
	{
		List<ElementNode> nodeList = new ArrayList<ElementNode>();
		//nodeList.add(0,entryStack.pop());//처음 ClassDomain은 자기 자신을 가르키므로 무시
		((PackageDomain)entryStack.get(0)).metric.addNumberOfFields(1);
		while(entryStack.size() > 0 && entryStack.peek().getType() != ElementNodeType.CLASS)
			nodeList.add(0,entryStack.pop());
		if(entryStack.size() != 0)
		{
			ClassDomain classEntry = (ClassDomain)entryStack.pop();
			classEntry.metric.addFields(1);
			//System.out.println(((ClassDomain)entryStack.peek()).getFullName() + " has " + ((ClassDomain)entryStack.peek()).metric.getFields());
			
			//if(entryStack.peek() instanceof PackageDomain)
				//((PackageDomain)entryStack.peek()).metric.addNumberOfFields(1);
			entryStack.push(classEntry);			
		}
		
		for(ElementNode n:nodeList)
			entryStack.push(n);
	}
}
