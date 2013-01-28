package net.sourceforge.pmd.lang.java.rule.stanly.calculator;

import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;



public class CyclomaticComplexity extends AbstractJavaRule {
	/*
	Stack<StanlyNode> entryStack = StanlyMap.getEntryStack();
	
	public static int sumExpressionComplexity(ASTExpression expr){
		if (expr == null) {
			return 0;
		}

		List<ASTConditionalAndExpression> andNodes = expr.findDescendantsOfType(ASTConditionalAndExpression.class);
		List<ASTConditionalOrExpression> orNodes = expr.findDescendantsOfType(ASTConditionalOrExpression.class);

		int children = 0;

		for (ASTConditionalOrExpression element : orNodes) {
			children += element.jjtGetNumChildren();
			children--;
		}

		for (ASTConditionalAndExpression element : andNodes) {
			children += element.jjtGetNumChildren();
			children--;
		}

		return children;
	}

	
	@Override
	public Object visit(ASTIfStatement node, Object data) {
		int boolCompIf = sumExpressionComplexity( node.getFirstChildOfType( ASTExpression.class ) );
		// If statement always has a complexity of at least 1
		boolCompIf++;

		entryStack.peek().getMetrics().getMetric("CyclomaticComplexity").total += boolCompIf;
		super.visit( node, data );
		return data;
	}

	@Override
	public Object visit(ASTCatchStatement node, Object data) {
		entryStack.peek().getMetrics().getMetric("CyclomaticComplexity").total += 1;
		super.visit( node, data );
		return data;
	}

	@Override
	public Object visit(ASTForStatement node, Object data) {
		int boolCompFor = sumExpressionComplexity( node.getFirstDescendantOfType( ASTExpression.class ) );
		// For statement always has a complexity of at least 1
		boolCompFor++;

		entryStack.peek().getMetrics().getMetric("CyclomaticComplexity").total += boolCompFor;
		super.visit( node, data );
		return data;
	}

	@Override
	public Object visit(ASTDoStatement node, Object data) {
		int boolCompDo = sumExpressionComplexity( node.getFirstChildOfType( ASTExpression.class ) );
		// Do statement always has a complexity of at least 1
		boolCompDo++;

		entryStack.peek().getMetrics().getMetric("CyclomaticComplexity").total += boolCompDo;
		super.visit( node, data );
		return data;
	}

	@Override
	public Object visit(ASTSwitchStatement node, Object data) {

		int boolCompSwitch = sumExpressionComplexity( node.getFirstChildOfType( ASTExpression.class ) );
		entryStack.peek().getMetrics().getMetric("CyclomaticComplexity").total += boolCompSwitch;

		int childCount = node.jjtGetNumChildren();
		int lastIndex = childCount - 1;
		for ( int n = 0; n < lastIndex; n++ ) {
			Node childNode = node.jjtGetChild( n );
			if ( childNode instanceof ASTSwitchLabel ) {
				// default is generally not considered a decision (same as "else")
				ASTSwitchLabel sl = (ASTSwitchLabel) childNode;
				if ( !sl.isDefault() ) {
					childNode = node.jjtGetChild( n + 1 );
					if ( childNode instanceof ASTBlockStatement ) {
						entryStack.peek().getMetrics().getMetric("CyclomaticComplexity").total += 1;
					}
				}
			}
		}
		super.visit( node, data );
		return data;
	}

	@Override
	public Object visit(ASTWhileStatement node, Object data) {
		int boolCompWhile = sumExpressionComplexity( node.getFirstChildOfType( ASTExpression.class ) );
		// While statement always has a complexity of at least 1
		boolCompWhile++;

		entryStack.peek().getMetrics().getMetric("CyclomaticComplexity").total += boolCompWhile;
		super.visit( node, data );
		return data;
	}

	@Override
	public Object visit(ASTConditionalExpression node, Object data) {
		if ( node.isTernary() ) {
			int boolCompTern = sumExpressionComplexity( node.getFirstChildOfType( ASTExpression.class ) );
			// Ternary statement always has a complexity of at least 1
			boolCompTern++;

			entryStack.peek().getMetrics().getMetric("CyclomaticComplexity").total += boolCompTern;
			super.visit( node, data );
		}
		return data;
	}

	@Override
	public Object visit(ASTCompilationUnit node, Object data) {
		super.visit( node, data );
		return data;
	}
	
	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
		super.visit( node, data );
		return data;
	}
	
	@Override
	public Object visit(ASTFieldDeclaration node,Object data) {
		return data;
	}
	
	@Override
	public Object visit(ASTMethodDeclaration node, Object data) {
		super.visit( node, data );
		return data;
	}

	@Override
	public Object visit(ASTEnumDeclaration node, Object data) {
		super.visit( node, data );
		return data;
	}

	@Override
	public Object visit(ASTConstructorDeclaration node, Object data) {
		super.visit( node, data );
		return data;
	}*/
}
