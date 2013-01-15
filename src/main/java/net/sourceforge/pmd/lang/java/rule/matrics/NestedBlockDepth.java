package net.sourceforge.pmd.lang.java.rule.matrics;

import java.util.Stack;
import org.apache.log4j.Logger;

import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTCatchStatement;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTConstructorDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTDoStatement;
import net.sourceforge.pmd.lang.java.ast.ASTFinallyStatement;
import net.sourceforge.pmd.lang.java.ast.ASTForStatement;
import net.sourceforge.pmd.lang.java.ast.ASTIfStatement;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclarator;
import net.sourceforge.pmd.lang.java.ast.ASTSwitchStatement;
import net.sourceforge.pmd.lang.java.ast.ASTTryStatement;
import net.sourceforge.pmd.lang.java.ast.ASTWhileStatement;
import net.sourceforge.pmd.lang.java.ast.ASTBlock;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

public class NestedBlockDepth extends AbstractJavaRule {
	private static class Entry {
		private Node node;
		private int NestedBlockDepth;
		public int highestNestedBlockDepth;
		public int methodCount;

		private Entry(Node node) {
			this.node = node;
		}

		public void pushNestedBlockDepth()
		{
			NestedBlockDepth++;
			if(NestedBlockDepth > highestNestedBlockDepth)
				highestNestedBlockDepth = NestedBlockDepth;
		}
		
		public void popNestedBlockDepth()
		{
			NestedBlockDepth--;
		}
		//public int getComplexityAverage() {
			//return (double) methodCount == 0 ? 1 : (int) Math.rint( (double) decisionPoints / (double) methodCount );
		//}
		
		public double getNBDAverage() {
			return (double) methodCount == 0 ? 1 : (double)( (double) (NestedBlockDepth) / (double) methodCount );
		}
	}

	private Stack<Entry> entryStack = new Stack<Entry>();
	private Logger logger = Logger.getLogger("AppLogging1");
	
	@Override
	public Object visit(ASTIfStatement node, Object data) {
		entryStack.peek().pushNestedBlockDepth();		
		super.visit( node, data );
		entryStack.peek().popNestedBlockDepth();
		return data;
	}

	@Override
	public Object visit(ASTCatchStatement node, Object data) {
		entryStack.peek().pushNestedBlockDepth();		
		super.visit( node, data );
		entryStack.peek().popNestedBlockDepth();
		return data;
	}
	
	@Override
	public Object visit(ASTTryStatement node, Object data) {
		entryStack.peek().pushNestedBlockDepth();		
		super.visit( node, data );
		entryStack.peek().popNestedBlockDepth();
		return data;
	}

	@Override
	public Object visit(ASTFinallyStatement node, Object data) {
		entryStack.peek().pushNestedBlockDepth();		
		super.visit( node, data );
		entryStack.peek().popNestedBlockDepth();
		return data;
	}
	
	@Override
	public Object visit(ASTForStatement node, Object data) {
		entryStack.peek().pushNestedBlockDepth();		
		super.visit( node, data );
		entryStack.peek().popNestedBlockDepth();
		return data;
	}

	@Override
	public Object visit(ASTDoStatement node, Object data) {
		entryStack.peek().pushNestedBlockDepth();		
		super.visit( node, data );
		entryStack.peek().popNestedBlockDepth();
		return data;
	}

	@Override
	public Object visit(ASTSwitchStatement node, Object data) {
		entryStack.peek().pushNestedBlockDepth();		
		super.visit( node, data );
		entryStack.peek().popNestedBlockDepth();
		return data;
	}

	@Override
	public Object visit(ASTWhileStatement node, Object data) {
		entryStack.peek().pushNestedBlockDepth();		
		super.visit( node, data );
		entryStack.peek().popNestedBlockDepth();
		return data;
	}
/*
	@Override
	public Object visit(ASTConditionalExpression node, Object data) {
		if ( node.isTernary() ) {
			entryStack.peek().pushNestedBlockDepth();		
			super.visit( node, data );
			entryStack.peek().popNestedBlockDepth();
			return data;
		}
		return data;
	}
*/
	
	
	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
		if ( node.isInterface() ) {
			return data;
		}

		logger.info("안녕하세요! Test입니다");
		entryStack.push( new Entry( node ) );
		super.visit( node, data );
		Entry classEntry = entryStack.pop();
		//if ( classEntry.getNumberOfParameterAverage() >= reportLevel
		//		|| classEntry.highestDecisionPoints >= reportLevel ) {
		addViolation( data, node, new String[] {
				"class",
				node.getImage(),
				classEntry.getNBDAverage() + " (Highest = "
						+ classEntry.highestNestedBlockDepth + ')' } );
		//}
		return data;
	}

	@Override
	public Object visit(ASTMethodDeclaration node, Object data) {
		entryStack.push( new Entry( node ) );
		if(node.getFirstChildOfType(ASTBlock.class) != null)
			entryStack.peek().pushNestedBlockDepth();
		super.visit( node, data );
		
		Entry methodEntry = entryStack.pop();
		
		int NestedBlockDepth = methodEntry.highestNestedBlockDepth;
		Entry classEntry = entryStack.peek();
		classEntry.methodCount++;
		classEntry.NestedBlockDepth += NestedBlockDepth;

		if ( NestedBlockDepth > classEntry.highestNestedBlockDepth ) {
			classEntry.highestNestedBlockDepth = NestedBlockDepth;
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
						String.valueOf( methodEntry.highestNestedBlockDepth ) } );
		//}
		return data;
	}
	
	@Override
	public Object visit(ASTConstructorDeclaration node, Object data) {
		entryStack.push( new Entry( node ) );
		entryStack.peek().pushNestedBlockDepth();
		super.visit( node, data );
		Entry constructorEntry = entryStack.pop();
		int NestedBlockDepth = constructorEntry.highestNestedBlockDepth;
		Entry classEntry = entryStack.peek();
		classEntry.methodCount++;
		classEntry.NestedBlockDepth += NestedBlockDepth;
		if ( NestedBlockDepth > classEntry.highestNestedBlockDepth ) {
			classEntry.highestNestedBlockDepth = NestedBlockDepth;
		}
		//if ( constructorEntry.decisionPoints >= reportLevel ) {
		addViolation( data, node, new String[] { "constructor",
				classEntry.node.getImage(),
				String.valueOf( NestedBlockDepth ) } );
		//}
		return data;
	}
}
