package net.sourceforge.pmd.lang.java.rule.matrics;

import java.util.Stack;

import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.ast.ASTExtendsList;
import net.sourceforge.pmd.lang.java.ast.ASTImplementsList;
import net.sourceforge.pmd.lang.java.ast.ASTName;
import net.sourceforge.pmd.lang.java.ast.ASTPackageDeclaration;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

public class EfferentCoupling extends AbstractJavaRule {
	
	/*
	private static class Entry {
		private Node node;
		private int efferent;
		public String packageName;
		
		private Entry(Node node) {
			this.node = node;
		}
	}

	private Stack<Entry> entryStack = new Stack<Entry>();
	
	public Object visit(ASTCompilationUnit node, Object data) {
		entryStack.push( new Entry( node ) );
		super.visit(node, data);
		
		Entry packageEntry = entryStack.pop();
		addViolation( data, node, new String[] {
				"class",
				packageEntry.packageName,
				packageEntry.efferent + " "} );
		return data;
	}
	
	public Object visit(ASTPackageDeclaration node, Object data) {
		Entry packageEntry = entryStack.peek();
		
		ASTName packageDeclarator = null;
		for ( int n = 0; n < node.jjtGetNumChildren(); n++ ) {
			Node childNode = node.jjtGetChild( n );
			if ( childNode instanceof ASTName ) {
				packageDeclarator = (ASTName) childNode;
				break;
			}
		}
		if(packageDeclarator != null)
			packageEntry.packageName = packageDeclarator.getImage();
		
		return super.visit(node, data);
    }
	
	public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
		if(node.getNthParent(2) instanceof ASTCompilationUnit)
		{		
			Entry packageEntry = entryStack.peek();
			if(packageEntry.packageName.length() > 0)
				packageEntry.packageName += ".";
			packageEntry.packageName += node.getImage();
		}
		return super.visit(node, data);
	}
	
	public Object visit(ASTExtendsList node, Object data) {
		entryStack.peek().efferent += node.jjtGetNumChildren();
		return super.visit(node, data);
    }

    public Object visit(ASTImplementsList node, Object data) {
    	entryStack.peek().efferent += node.jjtGetNumChildren();
    	return super.visit(node, data);
	}
	*/
}
