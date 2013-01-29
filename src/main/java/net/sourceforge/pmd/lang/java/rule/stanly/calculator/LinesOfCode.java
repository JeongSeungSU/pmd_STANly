package net.sourceforge.pmd.lang.java.rule.stanly.calculator;

import java.util.Stack;

import net.sourceforge.pmd.lang.java.ast.ASTAssertStatement;
import net.sourceforge.pmd.lang.java.ast.ASTBreakStatement;
import net.sourceforge.pmd.lang.java.ast.ASTCatchStatement;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceBodyDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTConstructorDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTContinueStatement;
import net.sourceforge.pmd.lang.java.ast.ASTDoStatement;
import net.sourceforge.pmd.lang.java.ast.ASTEnumDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTExplicitConstructorInvocation;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTFinallyStatement;
import net.sourceforge.pmd.lang.java.ast.ASTForInit;
import net.sourceforge.pmd.lang.java.ast.ASTForStatement;
import net.sourceforge.pmd.lang.java.ast.ASTIfStatement;
import net.sourceforge.pmd.lang.java.ast.ASTImportDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTLabeledStatement;
import net.sourceforge.pmd.lang.java.ast.ASTLocalVariableDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMemberValue;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTPackageDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTReturnStatement;
import net.sourceforge.pmd.lang.java.ast.ASTStatementExpression;
import net.sourceforge.pmd.lang.java.ast.ASTStatementExpressionList;
import net.sourceforge.pmd.lang.java.ast.ASTSwitchLabel;
import net.sourceforge.pmd.lang.java.ast.ASTSwitchStatement;
import net.sourceforge.pmd.lang.java.ast.ASTSynchronizedStatement;
import net.sourceforge.pmd.lang.java.ast.ASTThrowStatement;
import net.sourceforge.pmd.lang.java.ast.ASTWhileStatement;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ClassDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.MethodDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageDomain;

public class LinesOfCode{
	
/*	protected Integer countNodeChildren(Node node, Object data) {
		Integer nodeCount = null;
		int lineCount = 0;
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			//nodeCount = (Integer) ((JavaNode) node.jjtGetChild(i)).jjtAccept(this, data);
			lineCount += nodeCount.intValue();
		}
		return ++lineCount;
	}*/


	public static void calc(Stack<ElementNode> entryStack,ASTForStatement node, Object data) {
		//return countNodeChildren(node, data);
		((MethodDomain)(entryStack.peek())).metric.addLOC(1);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTDoStatement node, Object data) {
		//return countNodeChildren(node, data);
		((MethodDomain)(entryStack.peek())).metric.addLOC(1);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTIfStatement node, Object data) {

		//Integer lineCount = countNodeChildren(node, data);
		((MethodDomain)(entryStack.peek())).metric.addLOC(1);
		if (node.hasElse()) {
			((MethodDomain)(entryStack.peek())).metric.addLOC(1);
		}
	}


	public static void calc(Stack<ElementNode> entryStack,ASTWhileStatement node, Object data) {
		//return countNodeChildren(node, data);
		((MethodDomain)(entryStack.peek())).metric.addLOC(1);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTBreakStatement node, Object data) {
		//return NumericConstants.ONE;
		((MethodDomain)(entryStack.peek())).metric.addLOC(1);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTCatchStatement node, Object data) {
		//return countNodeChildren(node, data);
		((MethodDomain)(entryStack.peek())).metric.addLOC(1);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTContinueStatement node, Object data) {
		//return NumericConstants.ONE;
		((MethodDomain)(entryStack.peek())).metric.addLOC(1);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTFinallyStatement node, Object data) {
		//return countNodeChildren(node, data);
		((MethodDomain)(entryStack.peek())).metric.addLOC(1);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTReturnStatement node, Object data) {
		((MethodDomain)(entryStack.peek())).metric.addLOC(1);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTSwitchStatement node, Object data) {
		//return countNodeChildren(node, data);
		((MethodDomain)(entryStack.peek())).metric.addLOC(1);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTSynchronizedStatement node, Object data) {
		//return countNodeChildren(node, data);
		((MethodDomain)(entryStack.peek())).metric.addLOC(1);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTThrowStatement node, Object data) {
		//return NumericConstants.ONE;
		((MethodDomain)(entryStack.peek())).metric.addLOC(1);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTStatementExpression node, Object data) {

		// "For" update expressions do not count as separate lines of code
		if (node.jjtGetParent() instanceof ASTStatementExpressionList) {
			//return NumericConstants.ZERO;
			return;
		}

		//return NumericConstants.ONE;
		((MethodDomain)(entryStack.peek())).metric.addLOC(1);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTLabeledStatement node, Object data) {
		//return countNodeChildren(node, data);
		((MethodDomain)(entryStack.peek())).metric.addLOC(1);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTLocalVariableDeclaration node, Object data) {

		// "For" init declarations do not count as separate lines of code
		if (node.jjtGetParent() instanceof ASTForInit) {
			return;
			//return NumericConstants.ZERO;
		}

		/*
		 * This will count variables declared on the same line as separate NCSS
		 * counts. This violates JavaNCSS standards, but I'm not convinced that's a
		 * bad thing here.
		 */

		//return countNodeChildren(node, data);
		((MethodDomain)(entryStack.peek())).metric.addLOC(1);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTSwitchLabel node, Object data) {
		//return countNodeChildren(node, data);
		((MethodDomain)(entryStack.peek())).metric.addLOC(1);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTImportDeclaration node, Object data) {
		//((MethodDomain)(entryStack.peek())).metric.addLOC(1);
		((PackageDomain)(entryStack.peek())).metric.addLOC(1);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTPackageDeclaration node, Object data) {
		//((MethodDomain)(entryStack.peek())).metric.addLOC(1);
		((PackageDomain)(entryStack.peek())).metric.addLOC(1);
	}

	public static void calc(Stack<ElementNode> entryStack,ASTClassOrInterfaceDeclaration node, Object data) {
		ClassDomain tmpNode = (ClassDomain)entryStack.pop();
		tmpNode.metric.addLOC(1);
		((PackageDomain)(entryStack.peek())).metric.addLOC(1);
		entryStack.push(tmpNode);		
	}


	public static void calc(Stack<ElementNode> entryStack,ASTClassOrInterfaceBodyDeclaration node, Object data) {
		//return (Integer)visit((JavaNode) node, data);
		//return null;
	}


	public static void calc(Stack<ElementNode> entryStack,ASTEnumDeclaration node, Object data) {
		ClassDomain tmpNode = (ClassDomain)entryStack.pop();
		tmpNode.metric.addLOC(1);
		((PackageDomain)(entryStack.peek())).metric.addLOC(1);
		entryStack.push(tmpNode);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTMemberValue node, Object data) {
		((MethodDomain)(entryStack.peek())).metric.addLOC(1);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTFieldDeclaration node, Object data) {
		//((MethodDomain)(entryStack.peek())).metric.addLOC(1);
		ElementNode tmpNode = entryStack.pop();
		((ClassDomain)(entryStack.peek())).metric.addLOC(1);
		entryStack.push(tmpNode);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTAssertStatement node, Object data) {
		((MethodDomain)(entryStack.peek())).metric.addLOC(1);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTMethodDeclaration node, Object data) {
		MethodDomain tmpNode = (MethodDomain)entryStack.pop();
		if(node.isSingleLine())
			tmpNode.metric.setLOC(1);
		else
			tmpNode.metric.addLOC(1);
		((ClassDomain)(entryStack.peek())).metric.addLOC(tmpNode.metric.getLOC());	//Method의 LOC를 Class에 더함
		entryStack.push(tmpNode);
	}



	public static void calc(Stack<ElementNode> entryStack,ASTConstructorDeclaration node, Object data) {
		MethodDomain tmpNode = (MethodDomain)entryStack.pop();
		if(node.isSingleLine())
			tmpNode.metric.setLOC(1);
		else
			tmpNode.metric.addLOC(1);
		((ClassDomain)(entryStack.peek())).metric.addLOC(tmpNode.metric.getLOC());	//Constructor의 LOC를 Class에 더함
		entryStack.push(tmpNode);
	}


	public static void calc(Stack<ElementNode> entryStack,ASTExplicitConstructorInvocation node, Object data) {
		((MethodDomain)(entryStack.peek())).metric.addLOC(1);
		//return countNodeChildren(node, data);
	}

}
