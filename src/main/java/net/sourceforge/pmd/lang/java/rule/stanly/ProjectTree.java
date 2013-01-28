package net.sourceforge.pmd.lang.java.rule.stanly;

import java.util.Stack;

import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.ast.ASTConstructorDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTEnumDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTPackageDeclaration;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNodeType;
import net.sourceforge.pmd.lang.java.rule.stanly.element.LibraryDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ProjectDomain;

public class ProjectTree extends AbstractJavaRule {
	private static ProjectDomain projectNode = null;
	private static Stack<ElementNode> entryStack = new Stack<ElementNode>();
	
	// jdbc, hibernate
	
	public ProjectTree()
	{
		if(projectNode == null)
			projectNode = new ProjectDomain(ElementNodeType.PROJECT,"Project");
	}
	
	public static ProjectDomain getProjectNode() {
		return projectNode;
	}
	
	public static Stack<ElementNode> getEntryStack() {
		return entryStack;
	}
	
	public Object visit(ASTCompilationUnit node, Object data)
	{
		LibraryDomain currentLibraryNode = null;
		PackageDomain currentPackageNode = null;
		
		String folderName = ((RuleContext)data).getSourceCodeFilename();
		ASTPackageDeclaration apd = node.getPackageDeclaration();
		
		if(apd == null)	//패키지가 정의되지 않은경우에는 return 시켜 탐색을하지 않도록함
			return data;
		String packageName = apd.getPackageNameImage();
		folderName = folderName.substring(0, folderName.indexOf(packageName.replace('.', '/')));
		
		for(ElementNode a : projectNode.getChildren())
		{
			if(a.getName().equals(folderName))
			{
				currentLibraryNode = (LibraryDomain)a;
				break;
			}
		}
		if(currentLibraryNode == null)
		{
			System.out.println("new folder node : " + folderName);
			currentLibraryNode = (LibraryDomain)projectNode.addChildren(ElementNodeType.FOLDER, folderName);
		}
		
		for(ElementNode a : currentLibraryNode.getChildren())
		{
			if(a.getName().equals(packageName))
			{
				currentPackageNode = (PackageDomain)a;
				break;
			}
		}
		if(currentPackageNode == null)
		{
			System.out.println("    new package node : " + packageName);
			
			currentPackageNode = (PackageDomain)currentLibraryNode.addChildren(ElementNodeType.PACKAGE,packageName);
		}
		
		entryStack.push(currentPackageNode);
		super.visit(node, data);
		entryStack.pop();
		
		//Gson gson = new Gson();
		//String temp = gson.toJson(topNode);
		//System.out.println(temp);
		return data;
	}
	
	public Object visit(ASTClassOrInterfaceDeclaration node, Object data)
	{

		ElementNode parent = entryStack.peek();
		String name = node.getImage();
		if(node.isInterface())
		{
			parent.addChildren(ElementNodeType.INTERFACE, name);
//			System.out.println("        new interface node : " + name);
		}
		else	
		{
			parent.addChildren(ElementNodeType.CLASS, name);
//			System.out.println("        new class node : " + name);
		}
		
	
		entryStack.push(parent.getLastChild());
		super.visit(node,data);
		entryStack.pop();
		return data;
	}
	
	public Object visit(ASTEnumDeclaration node, Object data)
	{
		//StanlyNode thisNode;
		ElementNode parent = entryStack.peek();
		String name = node.getImage();

//		System.out.println("        new enum node : " + name);
		
		
		parent.addChildren(ElementNodeType.ENUM, name);
		entryStack.push(parent.getLastChild());
		super.visit(node,data);
		entryStack.pop();
		
		return data;
	}
	
	public Object visit(ASTFieldDeclaration node, Object data)
	{
		//StanlyNode thisNode;
		ElementNode parent = entryStack.peek();
		String name = node.getVariableName();
		
		parent.addChildren(ElementNodeType.ATTRIBUTE, name);
//		System.out.println("            new attribute node : " + name);
		

		entryStack.push(parent.getLastChild());
		super.visit(node, data);
		entryStack.pop();
		
		return data;
	}
	
	public Object visit(ASTConstructorDeclaration node, Object data)
	{
		//StanlyNode thisNode;
		ElementNode parent = entryStack.peek();
		String name = entryStack.peek().getName();
		
		parent.addChildren(ElementNodeType.CONSTRUCTOR, name);
//		System.out.println("            new constructor node : " + name);
	
		entryStack.push(parent.getLastChild());
		super.visit(node, data);
		entryStack.pop();
		
		return data;
	}
	
	public Object visit(ASTMethodDeclaration node, Object data)
	{
		ElementNode thisNode;
		ElementNode parent = entryStack.peek();
		String name = node.getMethodName();
		
		thisNode = parent.addChildren(ElementNodeType.METHOD, name);
		System.out.println("            new method node : " + thisNode.getFullName());
		
		entryStack.push(parent.getLastChild());
		super.visit(node, data);
		entryStack.pop();
		
		return data;
	}
}
