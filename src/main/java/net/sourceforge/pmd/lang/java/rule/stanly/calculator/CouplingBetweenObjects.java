package net.sourceforge.pmd.lang.java.rule.stanly.calculator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTFormalParameter;
import net.sourceforge.pmd.lang.java.ast.ASTLocalVariableDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTReferenceType;
import net.sourceforge.pmd.lang.java.ast.ASTResultType;
import net.sourceforge.pmd.lang.java.ast.ASTType;
import net.sourceforge.pmd.lang.java.ast.JavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ClassDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNodeType;
import net.sourceforge.pmd.lang.java.rule.stanly.element.LibraryDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageDomain;
import net.sourceforge.pmd.lang.java.symboltable.ClassScope;

public class CouplingBetweenObjects extends AbstractCalculator {
	//private int couplingCount;
	private Set<String> typesFoundSoFar;

	@Override
	public void calcMetric(Stack<ElementNode> entryStack,ASTCompilationUnit cu, Object data) {
		typesFoundSoFar = new HashSet<String>();
		//couplingCount = 0;

		/*Object returnObj = cu.childrenAccept(this, data);

		if (couplingCount > getProperty(THRESHOLD_DESCRIPTOR)) {
			addViolation(data, cu, "A value of " + couplingCount + " may denote a high amount of coupling within the class");
		}*/

		return;
	}

	/*@Override
	public void calcMetric(Stack<ElementNode> entryStack,ASTClassOrInterfaceDeclaration node, Object data) {
		if (node.isInterface()) {
			return;
		}
		return super.visit(node, data);
	}*/

	/*public boolean isNotClass(Stack<ElementNode> entryStack)
	{
		for(int i=entryStack.size() - 1;i>=0;i--)
		{
			if(entryStack.get(i) instanceof ClassDomain && entryStack.get(i).getType() == ElementNodeType.CLASS)
				return false;
		}
		return true;
	}*/

	@Override
	public void calcMetric(Stack<ElementNode> entryStack,ASTResultType node, Object data) {
		//if(isNotClass(entryStack))	return;

		for (int x = 0; x < node.jjtGetNumChildren(); x++) {
			Node tNode = node.jjtGetChild(x);
			if (tNode instanceof ASTType) {
				Node reftypeNode = tNode.jjtGetChild(0);
				if (reftypeNode instanceof ASTReferenceType) {
					Node classOrIntType = reftypeNode.jjtGetChild(0);
					if (classOrIntType instanceof ASTClassOrInterfaceType) {
						Node nameNode = classOrIntType;
						this.checkVariableType(entryStack,nameNode, nameNode.getImage());
					}
				}
			}
		}
		//return super.visit(node, data);
	}

	@Override
	public void calcMetric(Stack<ElementNode> entryStack,ASTLocalVariableDeclaration node, Object data) {
		//if(isNotClass(entryStack))	return;
		handleASTTypeChildren(entryStack,node);
		//return super.visit(node, data);
	}

	@Override
	public void calcMetric(Stack<ElementNode> entryStack,ASTFormalParameter node, Object data) {
		//if(isNotClass(entryStack))	return;
		handleASTTypeChildren(entryStack,node);
		//return super.visit(node, data);
	}

	@Override
	public void calcMetric(Stack<ElementNode> entryStack,ASTFieldDeclaration node, Object data) {
		//if(isNotClass(entryStack))	return;
		for (int x = 0; x < node.jjtGetNumChildren(); ++x) {
			Node firstStmt = node.jjtGetChild(x);
			if (firstStmt instanceof ASTType) {
				ASTType tp = (ASTType) firstStmt;
				Node nd = tp.jjtGetChild(0);
				checkVariableType(entryStack, nd, nd.getImage());
			}
		}

		//return super.visit(node, data);
	}

	/**
	 * convience method to handle hierarchy. This is probably too much
	 * work and will go away once I figure out the framework
	 */
	private void handleASTTypeChildren(Stack<ElementNode> entryStack, Node node) {
		for (int x = 0; x < node.jjtGetNumChildren(); x++) {
			Node sNode = node.jjtGetChild(x);
			if (sNode instanceof ASTType) {
				Node nameNode = sNode.jjtGetChild(0);
				checkVariableType(entryStack, nameNode, nameNode.getImage());
			}
		}
	}

	/**
	 * performs a check on the variable and updates the counter. Counter is
	 * instance for a class and is reset upon new class scan.
	 *
	 * @param variableType The variable type.
	 */
	private void checkVariableType(Stack<ElementNode> entryStack,Node nameNode, String variableType) {
		// TODO - move this into the symbol table somehow?
		if (nameNode.getParentsOfType(ASTClassOrInterfaceDeclaration.class).isEmpty()) {
			return;
		}
		//if the field is of any type other than the class type
		//increment the count
		ClassScope clzScope = ((JavaNode)nameNode).getScope().getEnclosingClassScope();
		if (!clzScope.getClassName().equals(variableType) && !this.filterTypes(variableType) && !this.typesFoundSoFar.contains(variableType)) {
			addCouplingCount(entryStack);
			typesFoundSoFar.add(variableType);
		}
	}

	public void addCouplingCount(Stack<ElementNode> entryStack)
	{
		List<ElementNode> nodeList = new ArrayList<ElementNode>();
		while(entryStack.size() > 0)
		{
			if(entryStack.peek().getType() == ElementNodeType.CLASS)
				((ClassDomain)entryStack.peek()).metric.addCBO(1);
			else if(entryStack.peek().getType() == ElementNodeType.PACKAGE)
				((PackageDomain)entryStack.peek()).metric.addCBO(1);
			else if(entryStack.peek().getType() == ElementNodeType.LIBRARY)
				((LibraryDomain)entryStack.peek()).metric.addCBO(1);
			nodeList.add(0,entryStack.pop());
		}

		for(ElementNode n:nodeList)
			entryStack.push(n);
	}

	/**
	 * Filters variable type - we don't want primatives, wrappers, strings, etc.
	 * This needs more work. I'd like to filter out super types and perhaps interfaces
	 *
	 * @param variableType The variable type.
	 * @return boolean true if variableType is not what we care about
	 */
	private boolean filterTypes(String variableType) {
		return variableType != null && (variableType.startsWith("java.lang.") || variableType.equals("String") || filterPrimitivesAndWrappers(variableType));
	}

	/**
	 * @param variableType The variable type.
	 * @return boolean true if variableType is a primitive or wrapper
	 */
	private boolean filterPrimitivesAndWrappers(String variableType) {
		return variableType.equals("int") || variableType.equals("Integer") || variableType.equals("char") || variableType.equals("Character") || variableType.equalsIgnoreCase("double") || variableType.equalsIgnoreCase("long") || variableType.equalsIgnoreCase("short") || variableType.equalsIgnoreCase("float") || variableType.equalsIgnoreCase("byte") || variableType.equalsIgnoreCase("boolean");
	}
}
