package net.sourceforge.pmd.lang.java.rule.matrics;

import java.util.Stack;

import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

public class LinesOfCode extends AbstractJavaRule{
	private static class Entry {
		private Node node;
		public int toc;
		public int methodCount;
		
		private Entry(Node node) {
			this.node = node;
		}
	}

	private Stack<Entry> entryStack = new Stack<Entry>();
	
	public Object visit(ASTCompilationUnit node, Object data) {
		entryStack.push( new Entry( node ) );
		super.visit(node, data);
		return data;
	}
	
	public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
		entryStack.push( new Entry( node ) );
		super.visit(node, data);
		return data;
	}
	
	public Object visit(ASTMethodDeclaration node, Object data) {
		entryStack.push( new Entry( node ) );
		super.visit(node, data);
		return data;
	}
}
