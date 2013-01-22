package net.sourceforge.pmd.lang.java.rule.yhctest;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.ast.ASTConstructorDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTEnumDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;
import net.sourceforge.pmd.lang.java.rule.yhctest.tree.StanlyMethodNode;
import net.sourceforge.pmd.lang.java.rule.yhctest.tree.StanlyNodeType;
import net.sourceforge.pmd.lang.java.rule.yhctest.tree.StanlyAttributeNode;
import net.sourceforge.pmd.lang.java.rule.yhctest.tree.StanlyClassNode;
import net.sourceforge.pmd.lang.java.rule.yhctest.tree.StanlyConstructorNode;
import net.sourceforge.pmd.lang.java.rule.yhctest.tree.StanlyEnumNode;
import net.sourceforge.pmd.lang.java.rule.yhctest.tree.StanlyFolderNode;
import net.sourceforge.pmd.lang.java.rule.yhctest.tree.StanlyInterfaceNode;
import net.sourceforge.pmd.lang.java.rule.yhctest.tree.StanlyNode;
import net.sourceforge.pmd.lang.java.rule.yhctest.tree.StanlyPackageNode;

public class StanlyMap extends AbstractJavaRule {
	private List<StanlyFolderNode> folderNode;
	private Stack<StanlyNode> stack = new Stack<StanlyNode>();
	
	// jdbc, hibernate
	
	public StanlyMap()
	{
		folderNode = new ArrayList<StanlyFolderNode>();
	}
	public Object visit(ASTCompilationUnit node, Object data)
	{
		System.out.println("운영체제 종류: " + System.getProperty("os.name") );
		StanlyFolderNode currentFolderNode = null;
		StanlyPackageNode currentPackageNode = null;
		//Iterator it = topNode.iterator();
		String folderName = ((RuleContext)data).getSourceCodeFilename();		
		String packageName = node.getPackageDeclaration().getPackageNameImage();
		
		folderName = folderName.substring(0, folderName.indexOf(packageName.replace('.', '/')));
		
		for(StanlyNode a : folderNode)
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
			currentFolderNode = new StanlyFolderNode(StanlyNodeType.FOLDER,folderName);
			folderNode.add(currentFolderNode);
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
			
			currentFolderNode.addChildren(StanlyNodeType.PACKAGE,packageName);
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
			System.out.println("        new interface node : " + name);
		}
		else	
		{
			parent.addChildren(StanlyNodeType.CLASS, name);
			System.out.println("        new class node : " + name);
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

		System.out.println("        new enum node : " + name);
		
		
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
		System.out.println("            new attribute node : " + name);
		

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
		System.out.println("            new constructor node : " + name);
	
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
		
		parent.addChildren(StanlyNodeType.METHOD, name);
		System.out.println("            new method node : " + name);
		
		stack.push(parent.getLastChild());
		super.visit(node, data);
		stack.pop();
		
		return data;
	}
}
