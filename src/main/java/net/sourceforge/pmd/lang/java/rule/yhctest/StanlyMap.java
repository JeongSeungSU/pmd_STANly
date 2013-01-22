package net.sourceforge.pmd.lang.java.rule.yhctest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

//import com.google.gson.Gson;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.ast.ASTConstructorDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTEnumDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;
import net.sourceforge.pmd.RuleContext;

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
			if(a.name.equals(folderName))
			{
				currentFolderNode = (StanlyFolderNode)a;
				break;
			}
		}
		if(currentFolderNode == null)
		{
			System.out.println("new folder node : " + folderName);
			currentFolderNode = new StanlyFolderNode(StanlyNode.FOLDER,folderName);
			folderNode.add(currentFolderNode);
		}
		
		for(StanlyNode a : currentFolderNode.children)
		{
			if(a.name.equals(packageName))
			{
				currentPackageNode = (StanlyPackageNode)a;
				break;
			}
		}
		if(currentPackageNode == null)
		{
			System.out.println("    new package node : " + packageName);
			currentPackageNode = new StanlyPackageNode(StanlyNode.FOLDER,packageName);
			currentFolderNode.children.add(currentPackageNode);
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
		StanlyNode thisNode;
		StanlyNode parent = stack.peek();
		String name = node.getImage();
		if(node.isInterface())
		{
			thisNode = new StanlyInterfaceNode(StanlyNode.INTERFACE, name);
			System.out.println("        new interface node : " + name);
		}
		else	
		{
			thisNode = new StanlyClassNode(StanlyNode.CLASS, name);
			System.out.println("        new class node : " + name);
		}
		
		parent.children.add(thisNode);
		stack.push(thisNode);
		super.visit(node,data);
		stack.pop();
		return data;
	}
	
	public Object visit(ASTEnumDeclaration node, Object data)
	{
		StanlyNode thisNode;
		StanlyNode parent = stack.peek();
		String name = node.getImage();
		thisNode = new StanlyEnumNode(StanlyNode.ENUM, name);
		System.out.println("        new enum node : " + name);
		
		parent.children.add(thisNode);
		stack.push(thisNode);
		super.visit(node,data);
		stack.pop();
		
		return data;
	}
	
	public Object visit(ASTFieldDeclaration node, Object data)
	{
		StanlyNode thisNode;
		StanlyNode parent = stack.peek();
		String name = node.getVariableName();
		
		thisNode = new StanlyAttributeNode(StanlyNode.ATTRIBUTE, name);
		System.out.println("            new attribute node : " + name);
		
		parent.children.add(thisNode);
		stack.push(thisNode);
		super.visit(node, data);
		stack.pop();
		
		return data;
	}
	
	public Object visit(ASTConstructorDeclaration node, Object data)
	{
		StanlyNode thisNode;
		StanlyNode parent = stack.peek();
		String name = stack.peek().name;
		
		thisNode = new StanlyConstructorNode(StanlyNode.CONSTRUCTOR, name);
		System.out.println("            new constructor node : " + name);
		
		parent.children.add(thisNode);
		stack.push(thisNode);
		super.visit(node, data);
		stack.pop();
		
		return data;
	}
	
	public Object visit(ASTMethodDeclaration node, Object data)
	{
		StanlyNode thisNode;
		StanlyNode parent = stack.peek();
		String name = node.getMethodName();
		
		thisNode = new StanlyMethodNode(StanlyNode.METHOD, name);
		System.out.println("            new method node : " + name);
		
		parent.children.add(thisNode);
		stack.push(thisNode);
		super.visit(node, data);
		stack.pop();
		
		return data;
	}
}
