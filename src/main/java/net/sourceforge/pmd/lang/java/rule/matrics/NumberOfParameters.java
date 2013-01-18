package net.sourceforge.pmd.lang.java.rule.matrics;

import java.util.Stack;

import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.ast.ASTConstructorDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTEnumDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTFormalParameter;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclarator;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

public class NumberOfParameters extends AbstractJavaRule {
	private static class Entry {
		private Node node;
		public int highestNumberOfParam;
		public int methodCount;
		public int parameterCount;

		private Entry(Node node) {
			this.node = node;
		}
		
		public double getNumberOfParameterAverage() {
			return (double) methodCount == 0 ? 1 : (double)( (double) parameterCount / (double) methodCount );
		}
	}
	private Stack<Entry> entryStack = new Stack<Entry>();

	@Override
	public Object visit(ASTCompilationUnit node, Object data) {	
		return super.visit( node, data );
	}
	
	@Override
	public Object visit(ASTFormalParameter node, Object data) {
		Entry methodEntry = entryStack.peek();
		methodEntry.parameterCount++;
		super.visit( node, data );
		return data;
	}
	
	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
		if ( node.isInterface() ) {
			return data;
		}

		entryStack.push( new Entry( node ) );
		super.visit( node, data );
		Entry classEntry = entryStack.pop();
		//if ( classEntry.getNumberOfParameterAverage() >= reportLevel
		//		|| classEntry.highestDecisionPoints >= reportLevel ) {
		addViolation( data, node, new String[] {
				"class",
				node.getImage(),
				classEntry.getNumberOfParameterAverage() + " (Highest = "
						+ classEntry.highestNumberOfParam + ')' } );
		//}
		return data;
	}
	
	@Override
	public Object visit(ASTEnumDeclaration node, Object data) {

		entryStack.push( new Entry( node ) );
		super.visit( node, data );
		Entry classEntry = entryStack.pop();
		//if ( classEntry.getNumberOfParameterAverage() >= reportLevel
		//		|| classEntry.highestDecisionPoints >= reportLevel ) {
		addViolation( data, node, new String[] {
				"enum",
				node.getImage(),
				classEntry.getNumberOfParameterAverage() + " (Highest = "
						+ classEntry.highestNumberOfParam + ')' } );
		//}
		return data;
	}

	@Override
	public Object visit(ASTMethodDeclaration node, Object data) {
		entryStack.push( new Entry( node ) );
		super.visit( node, data );
		
		Entry methodEntry = entryStack.pop();
		
		int parameterCount = methodEntry.parameterCount;
		Entry classEntry = entryStack.peek();
		classEntry.methodCount++;
		classEntry.parameterCount += parameterCount;

		if ( parameterCount > classEntry.highestNumberOfParam ) {
			classEntry.highestNumberOfParam = parameterCount;
		}
		ASTMethodDeclarator methodDeclarator = null;
		for ( int n = 0; n < node.jjtGetNumChildren(); n++ ) {
			Node childNode = node.jjtGetChild( n );
			if ( childNode instanceof ASTMethodDeclarator ) {
				methodDeclarator = (ASTMethodDeclarator) childNode;
				break;
			}
		}

		//if ( methodEntry.decisionPoints >= reportLevel ) {
		addViolation( data, node, new String[] { "method",
				methodDeclarator == null ? "" : methodDeclarator.getImage(),
						String.valueOf( methodEntry.parameterCount ) } );
		//}
		return data;
	}
	
	@Override
	public Object visit(ASTConstructorDeclaration node, Object data) {
		entryStack.push( new Entry( node ) );
		super.visit( node, data );
		Entry constructorEntry = entryStack.pop();
		int constructorParameterCount = constructorEntry.parameterCount;
		Entry classEntry = entryStack.peek();
		classEntry.methodCount++;
		classEntry.parameterCount += constructorParameterCount;
		if ( constructorParameterCount > classEntry.highestNumberOfParam ) {
			classEntry.highestNumberOfParam = constructorParameterCount;
		}
		//if ( constructorEntry.decisionPoints >= reportLevel ) {
		addViolation( data, node, new String[] { "constructor",
				classEntry.node.getImage(),
				String.valueOf( constructorParameterCount ) } );
		//}
		return data;
	}
}
