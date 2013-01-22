package net.sourceforge.pmd.lang.java.rule.yhctest;

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
import net.sourceforge.pmd.lang.java.rule.yhctest.tree.StanlyFolderNode;
import net.sourceforge.pmd.lang.java.rule.yhctest.tree.StanlyNode;
import net.sourceforge.pmd.lang.java.rule.yhctest.tree.StanlyNodeType;
import net.sourceforge.pmd.lang.java.rule.yhctest.tree.StanlyPackageNode;
import net.sourceforge.pmd.lang.java.rule.yhctest.tree.StanlyProjectNode;

public class StanlyMap extends AbstractJavaRule {
	private static StanlyProjectNode projectNode;
	private Stack<StanlyNode> stack = new Stack<StanlyNode>();
	
	// jdbc, hibernate
	
	public StanlyMap()
	{
		projectNode = new StanlyProjectNode(StanlyNodeType.PROJECT,"Project");
	}
	public Object visit(ASTCompilationUnit node, Object data)
	{
		StanlyFolderNode currentFolderNode = null;
		StanlyPackageNode currentPackageNode = null;
		//Iterator it = topNode.iterator();
		String folderName = ((RuleContext)data).getSourceCodeFilename();
		ASTPackageDeclaration apd = node.getPackageDeclaration();
		if(apd == null)	//패키지가 정의되지 않은경우에는 return 시켜 탐색을하지 않도록함
			return data;
		String packageName = apd.getPackageNameImage();
		folderName = folderName.substring(0, folderName.indexOf(packageName.replace('.', '/')));
		
		for(StanlyNode a : projectNode.getChildren())
		{
			if(a.getName().equals(folderName))
			{
				currentFolderNode = (StanlyFolderNode)a;
				break;
			}
		}
		if(currentFolderNode == null)
		{
			System.out.println("new folder node : " + folderName);
			currentFolderNode = (StanlyFolderNode)projectNode.addChildren(StanlyNodeType.FOLDER, folderName);
		}
		
		for(StanlyNode a : currentFolderNode.getChildren())
		{
			if(a.getName().equals(packageName))
			{
				currentPackageNode = (StanlyPackageNode)a;
				break;
			}
		}
		if(currentPackageNode == null)
		{
			System.out.println("    new package node : " + packageName);
			
			currentPackageNode = (StanlyPackageNode)currentFolderNode.addChildren(StanlyNodeType.PACKAGE,packageName);
		}
		
		stack.push(currentPackageNode);
		super.visit(node, data);
		stack.pop();
		
		//Gson gson = new Gson();
		//String temp = gson.toJson(topNode);
		//System.out.println(temp);
		return data;
	}
	
	public Object visit(ASTClassOrInterfaceDeclaration node, Object data)
	{

		StanlyNode parent = stack.peek();
		String name = node.getImage();
		if(node.isInterface())
		{
			parent.addChildren(StanlyNodeType.INTERFACE, name);
//			System.out.println("        new interface node : " + name);
		}
		else	
		{
			parent.addChildren(StanlyNodeType.CLASS, name);
//			System.out.println("        new class node : " + name);
		}
		
	
		stack.push(parent.getLastChild());
		super.visit(node,data);
		stack.pop();
		return data;
	}
	
	public Object visit(ASTEnumDeclaration node, Object data)
	{
		StanlyNode thisNode;
		StanlyNode parent = stack.peek();
		String name = node.getImage();

//		System.out.println("        new enum node : " + name);
		
		
		parent.addChildren(StanlyNodeType.ENUM, name);
		stack.push(parent.getLastChild());
		super.visit(node,data);
		stack.pop();
		
		return data;
	}
	
	public Object visit(ASTFieldDeclaration node, Object data)
	{
		StanlyNode thisNode;
		StanlyNode parent = stack.peek();
		String name = node.getVariableName();
		
		parent.addChildren(StanlyNodeType.ATTRIBUTE, name);
//		System.out.println("            new attribute node : " + name);
		

		stack.push(parent.getLastChild());
		super.visit(node, data);
		stack.pop();
		
		return data;
	}
	
	public Object visit(ASTConstructorDeclaration node, Object data)
	{
		StanlyNode thisNode;
		StanlyNode parent = stack.peek();
		String name = stack.peek().getName();
		
		parent.addChildren(StanlyNodeType.CONSTRUCTOR, name);
//		System.out.println("            new constructor node : " + name);
	
		stack.push(parent.getLastChild());
		super.visit(node, data);
		stack.pop();
		
		return data;
	}
	
	public Object visit(ASTMethodDeclaration node, Object data)
	{
		StanlyNode thisNode;
		StanlyNode parent = stack.peek();
		String name = node.getMethodName();
		
		thisNode = parent.addChildren(StanlyNodeType.METHOD, name);
		System.out.println("            new method node : " + thisNode.getFullName());
		
		stack.push(parent.getLastChild());
		super.visit(node, data);
		stack.pop();
		
		return data;
	}
}
