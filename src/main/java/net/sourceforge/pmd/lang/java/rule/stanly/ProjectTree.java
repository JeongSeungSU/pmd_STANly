package net.sourceforge.pmd.lang.java.rule.stanly;

import java.util.Stack;

import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.lang.java.ast.*;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;
import net.sourceforge.pmd.lang.java.rule.stanly.calculator.MetricCalculator;
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
		
		for(ElementNode pn : projectNode.getChildren())
		{
			if(pn.getName().equals(folderName))
			{
				currentLibraryNode = (LibraryDomain)pn;
				break;
			}
		}
		if(currentLibraryNode == null)
		{
			System.out.println("new folder node : " + folderName);
			currentLibraryNode = (LibraryDomain)projectNode.addChildren(ElementNodeType.LIBRARY, folderName);
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
		ElementNode thisNode;
		ElementNode parent = entryStack.peek();
		String name = node.getImage();
		if(node.isInterface())
		{
			thisNode = parent.addChildren(ElementNodeType.INTERFACE, name);
			System.out.println("        new interface node : " + name);
		}
		else	
		{
			thisNode = parent.addChildren(ElementNodeType.CLASS, name);
			System.out.println("        new class node : " + name);
		}
		
	
		entryStack.push(thisNode);		
		super.visit(node,data);
		MetricCalculator.calcMetric(entryStack,node,data);
		entryStack.pop();
		return data;
	}
	
	public Object visit(ASTEnumDeclaration node, Object data)
	{
		ElementNode thisNode;
		ElementNode parent = entryStack.peek();
		String name = node.getImage();

		System.out.println("        new enum node : " + name);
		
		
		thisNode = parent.addChildren(ElementNodeType.ENUM, name);
		entryStack.push(thisNode);
		super.visit(node,data);
		MetricCalculator.calcMetric(entryStack,node,data);
		entryStack.pop();
		
		return data;
	}
	
	public Object visit(ASTFieldDeclaration node, Object data)
	{
		ElementNode thisNode;
		ElementNode parent = entryStack.peek();
		String name = node.getVariableName();
		
		thisNode = parent.addChildren(ElementNodeType.FIELD, name);
		//System.out.println("            new attribute node : " + name);
		

		entryStack.push(thisNode);
		super.visit(node, data);
		MetricCalculator.calcMetric(entryStack,node,data);
		entryStack.pop();
		
		return data;
	}
	
	public Object visit(ASTConstructorDeclaration node, Object data)
	{
		ElementNode thisNode;
		ElementNode parent = entryStack.peek();
		String name = entryStack.peek().getName();
		
		thisNode = parent.addChildren(ElementNodeType.CONSTRUCTOR, name);
		//System.out.println("            new constructor node : " + name);
	
		entryStack.push(thisNode);
		super.visit(node, data);
		MetricCalculator.calcMetric(entryStack,node,data);
		entryStack.pop();
		
		return data;
	}
	
	public Object visit(ASTMethodDeclaration node, Object data)
	{
		ElementNode thisNode;
		ElementNode parent = entryStack.peek();
		String name = node.getMethodName();
		
		thisNode = parent.addChildren(ElementNodeType.METHOD, name);
		//System.out.println("            new method node : " + thisNode.getFullName());
		
		entryStack.push(thisNode);
		super.visit(node, data);
		MetricCalculator.calcMetric(entryStack,node,data);
		entryStack.pop();
		
		return data;
	}
	
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////

	public Object visit(ASTExtendsList node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	//public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
	//	return super.visit(node, data);
	//}

	public Object visit(ASTImplementsList node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTTypeParameters node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTMemberSelector node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTTypeParameter node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTTypeBound node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTClassOrInterfaceBody node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTClassOrInterfaceBodyDeclaration node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTEnumBody node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTEnumConstant node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTReferenceType node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTClassOrInterfaceType node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTTypeArguments node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTTypeArgument node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTWildcardBounds node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTAnnotation node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTNormalAnnotation node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTMarkerAnnotation node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTSingleMemberAnnotation node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTMemberValuePairs node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTMemberValuePair node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTMemberValue node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTMemberValueArrayInitializer node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTAnnotationTypeDeclaration node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTAnnotationTypeBody node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTAnnotationTypeMemberDeclaration node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTAnnotationMethodDeclaration node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTDefaultValue node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTRUNSIGNEDSHIFT node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTRSIGNEDSHIFT node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	//public Object visit(ASTCompilationUnit node, Object data) {
		//return super.visit(node, data);
		//}

	//public Object visit(ASTEnumDeclaration node, Object data) {
		//return super.visit(node, data);
		//}
	
	public Object visit(ASTAssertStatement node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTPackageDeclaration node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTImportDeclaration node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTTypeDeclaration node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	//public Object visit(ASTFieldDeclaration node, Object data) {
	//return super.visit(node, data);
	//}

	public Object visit(ASTVariableDeclarator node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTVariableDeclaratorId node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTVariableInitializer node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTArrayInitializer node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	//public Object visit(ASTMethodDeclaration node, Object data) {
	//return super.visit(node, data);
	//}

	public Object visit(ASTMethodDeclarator node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTFormalParameters node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTFormalParameter node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	//public Object visit(ASTConstructorDeclaration node, Object data) {
	//return super.visit(node, data);
	//}

	public Object visit(ASTExplicitConstructorInvocation node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTInitializer node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTType node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTPrimitiveType node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTResultType node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTName node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTNameList node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTAssignmentOperator node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTConditionalExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTConditionalOrExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTConditionalAndExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTInclusiveOrExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTExclusiveOrExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTAndExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTEqualityExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTInstanceOfExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTRelationalExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTShiftExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTAdditiveExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTMultiplicativeExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTUnaryExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTPreIncrementExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTPreDecrementExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTUnaryExpressionNotPlusMinus node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTPostfixExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTCastExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTPrimaryExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTPrimaryPrefix node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTPrimarySuffix node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTLiteral node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTBooleanLiteral node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTNullLiteral node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTArguments node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTArgumentList node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTAllocationExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTArrayDimsAndInits node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTStatement node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTLabeledStatement node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTBlock node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTBlockStatement node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTLocalVariableDeclaration node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTEmptyStatement node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTStatementExpression node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTSwitchStatement node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTSwitchLabel node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTIfStatement node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTWhileStatement node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTDoStatement node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTForStatement node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTForInit node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTStatementExpressionList node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTForUpdate node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTBreakStatement node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTContinueStatement node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTReturnStatement node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTThrowStatement node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTSynchronizedStatement node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTTryStatement node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTFinallyStatement node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTCatchStatement node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTResourceSpecification node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTResources node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

	public Object visit(ASTResource node, Object data) {
		MetricCalculator.calcMetric(entryStack,node,data);
		return super.visit(node, data);
	}

}
