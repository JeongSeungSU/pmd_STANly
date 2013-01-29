package net.sourceforge.pmd.lang.java.rule.stanly.calculator;

import java.util.Stack;

import net.sourceforge.pmd.lang.java.ast.*;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

public abstract class AbstractCalculator {
		
	public void calcMetric(Stack<ElementNode> entryStack,ASTExtendsList node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTClassOrInterfaceDeclaration node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTImplementsList node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTTypeParameters node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTMemberSelector node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTTypeParameter node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTTypeBound node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTClassOrInterfaceBody node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTClassOrInterfaceBodyDeclaration node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTEnumBody node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTEnumConstant node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTReferenceType node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTClassOrInterfaceType node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTTypeArguments node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTTypeArgument node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTWildcardBounds node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTAnnotation node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTNormalAnnotation node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTMarkerAnnotation node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTSingleMemberAnnotation node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTMemberValuePairs node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTMemberValuePair node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTMemberValue node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTMemberValueArrayInitializer node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTAnnotationTypeDeclaration node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTAnnotationTypeBody node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTAnnotationTypeMemberDeclaration node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTAnnotationMethodDeclaration node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTDefaultValue node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTRUNSIGNEDSHIFT node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTRSIGNEDSHIFT node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTCompilationUnit node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTEnumDeclaration node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTAssertStatement node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTPackageDeclaration node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTImportDeclaration node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTTypeDeclaration node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTFieldDeclaration node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTVariableDeclarator node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTVariableDeclaratorId node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTVariableInitializer node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTArrayInitializer node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTMethodDeclaration node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTMethodDeclarator node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTFormalParameters node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTFormalParameter node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTConstructorDeclaration node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTExplicitConstructorInvocation node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTInitializer node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTType node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTPrimitiveType node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTResultType node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTName node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTNameList node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTAssignmentOperator node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTConditionalExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTConditionalOrExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTConditionalAndExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTInclusiveOrExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTExclusiveOrExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTAndExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTEqualityExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTInstanceOfExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTRelationalExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTShiftExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTAdditiveExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTMultiplicativeExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTUnaryExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTPreIncrementExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTPreDecrementExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTUnaryExpressionNotPlusMinus node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTPostfixExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTCastExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTPrimaryExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTPrimaryPrefix node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTPrimarySuffix node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTLiteral node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTBooleanLiteral node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTNullLiteral node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTArguments node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTArgumentList node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTAllocationExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTArrayDimsAndInits node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTStatement node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTLabeledStatement node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTBlock node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTBlockStatement node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTLocalVariableDeclaration node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTEmptyStatement node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTStatementExpression node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTSwitchStatement node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTSwitchLabel node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTIfStatement node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTWhileStatement node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTDoStatement node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTForStatement node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTForInit node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTStatementExpressionList node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTForUpdate node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTBreakStatement node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTContinueStatement node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTReturnStatement node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTThrowStatement node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTSynchronizedStatement node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTTryStatement node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTFinallyStatement node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTCatchStatement node, Object data) {}
	
	public void calcMetric(Stack<ElementNode> entryStack,ASTResourceSpecification node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTResources node, Object data) {}

	public void calcMetric(Stack<ElementNode> entryStack,ASTResource node, Object data) {}
}
