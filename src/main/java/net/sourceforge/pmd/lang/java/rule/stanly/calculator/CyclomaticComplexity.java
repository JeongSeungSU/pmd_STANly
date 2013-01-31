package net.sourceforge.pmd.lang.java.rule.stanly.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTBlockStatement;
import net.sourceforge.pmd.lang.java.ast.ASTCatchStatement;
import net.sourceforge.pmd.lang.java.ast.ASTConditionalAndExpression;
import net.sourceforge.pmd.lang.java.ast.ASTConditionalExpression;
import net.sourceforge.pmd.lang.java.ast.ASTConditionalOrExpression;
import net.sourceforge.pmd.lang.java.ast.ASTConstructorDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTDoStatement;
import net.sourceforge.pmd.lang.java.ast.ASTExpression;
import net.sourceforge.pmd.lang.java.ast.ASTForStatement;
import net.sourceforge.pmd.lang.java.ast.ASTIfStatement;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTSwitchLabel;
import net.sourceforge.pmd.lang.java.ast.ASTSwitchStatement;
import net.sourceforge.pmd.lang.java.ast.ASTWhileStatement;
import net.sourceforge.pmd.lang.java.ast.JavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ClassDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNodeType;
import net.sourceforge.pmd.lang.java.rule.stanly.element.MethodDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageDomain;




public class CyclomaticComplexity extends AbstractCalculator {
	
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
	public void calcMetric(Stack<ElementNode> entryStack,ASTIfStatement node, Object data) {
		int boolCompIf = sumExpressionComplexity( node.getFirstChildOfType( ASTExpression.class ) );
		// If statement always has a complexity of at least 1
		boolCompIf++;
		
		((MethodDomain)(entryStack.peek())).metric.addCC(boolCompIf);
	}

	@Override
	public void calcMetric(Stack<ElementNode> entryStack,ASTCatchStatement node, Object data) {
		((MethodDomain)(entryStack.peek())).metric.addCC(1);
	}

	@Override
	public void calcMetric(Stack<ElementNode> entryStack,ASTForStatement node, Object data) {
		int boolCompFor = sumExpressionComplexity( node.getFirstDescendantOfType( ASTExpression.class ) );
		// For statement always has a complexity of at least 1
		boolCompFor++;
		
		((MethodDomain)(entryStack.peek())).metric.addCC(boolCompFor);
	}

	@Override
	public void calcMetric(Stack<ElementNode> entryStack,ASTDoStatement node, Object data) {
		int boolCompDo = sumExpressionComplexity( node.getFirstChildOfType( ASTExpression.class ) );
		// Do statement always has a complexity of at least 1
		boolCompDo++;

		((MethodDomain)(entryStack.peek())).metric.addCC(boolCompDo);
	}

	@Override
	public void calcMetric(Stack<ElementNode> entryStack,ASTSwitchStatement node, Object data) {

		int boolCompSwitch = sumExpressionComplexity( node.getFirstChildOfType( ASTExpression.class ) );
		((MethodDomain)(entryStack.peek())).metric.addCC(boolCompSwitch);

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
						((MethodDomain)(entryStack.peek())).metric.addCC(1);
					}
				}
			}
		}
	}

	@Override
	public void calcMetric(Stack<ElementNode> entryStack,ASTWhileStatement node, Object data) {
		int boolCompWhile = sumExpressionComplexity( node.getFirstChildOfType( ASTExpression.class ) );
		// While statement always has a complexity of at least 1
		boolCompWhile++;

		((MethodDomain)(entryStack.peek())).metric.addCC(boolCompWhile);
	}

	@Override
	public void calcMetric(Stack<ElementNode> entryStack,ASTConditionalExpression node, Object data) {
		if ( node.isTernary() ) {
			int boolCompTern = sumExpressionComplexity( node.getFirstChildOfType( ASTExpression.class ) );
			// Ternary statement always has a complexity of at least 1
			boolCompTern++;

			((MethodDomain)(entryStack.peek())).metric.addCC(boolCompTern);
		}
	}

	public void addCC2Package(Stack<ElementNode> entryStack,JavaNode node, Object data)
	{
		MethodDomain methodEntry = (MethodDomain)entryStack.peek();
		List<ElementNode> nodeList = new ArrayList<ElementNode>();
		nodeList.add(0,entryStack.pop());//처음 ClassDomain은 자기 자신을 가르키므로 무시
		while(entryStack.size() > 0 && entryStack.peek().getType() != ElementNodeType.CLASS)
			nodeList.add(0,entryStack.pop());
		if(entryStack.size() != 0)
		{
			ClassDomain classEntry = (ClassDomain)entryStack.pop();
			classEntry.metric.addWMC(methodEntry.metric.getCC());
			//System.out.println(((ClassDomain)entryStack.peek()).getFullName() + " has " + ((ClassDomain)entryStack.peek()).metric.getMethods());
			
			if(entryStack.peek() instanceof PackageDomain)
				((PackageDomain)entryStack.peek()).metric.addCC(methodEntry.metric.getCC());
			entryStack.push(classEntry);			
		}
		for(ElementNode n:nodeList)
			entryStack.push(n);
	}
	
	public void calcMetric(Stack<ElementNode> entryStack,ASTMethodDeclaration node, Object data) {
		addCC2Package(entryStack,node,data);
	}
	
	public void calcMetric(Stack<ElementNode> entryStack,ASTConstructorDeclaration node, Object data) {
		addCC2Package(entryStack,node,data);		
	}
	/*@Override
	public void calcMetric(Stack<ElementNode> entryStack,ASTClassOrInterfaceDeclaration node, Object data) {
		if ( node.isInterface() ) {
			return;
		}

		entryStack.push( new ElementNode( node ) );
		super.visit( node, data );
		if ( showClassesComplexity ) {
			ElementNode classEntry = entryStack.pop();
			if ( classEntry.getComplexityAverage() >= reportLevel
					|| classEntry.highestDecisionPoints >= reportLevel ) {
				addViolation( data, node, new String[] {
						"class",
						node.getImage(),
						classEntry.getComplexityAverage() + " (Highest = "
						+ classEntry.highestDecisionPoints + ')' } );
			}
		}
	}*/

	/*
	@Override
	public void calcMetric(Stack<ElementNode> entryStack,ASTMethodDeclaration node, Object data) {
		entryStack.push( new ElementNode( node ) );
		super.visit( node, data );
		if ( showMethodsComplexity ) {
			ElementNode methodEntry = entryStack.pop();
			int methodDecisionPoints = methodEntry.decisionPoints;
			ElementNode classEntry = entryStack.peek();
			classEntry.methodCount++;
			classEntry.bumpDecisionPoints( methodDecisionPoints );

			if ( methodDecisionPoints > classEntry.highestDecisionPoints ) {
				classEntry.highestDecisionPoints = methodDecisionPoints;
			}
			ASTMethodDeclarator methodDeclarator = null;
			for ( int n = 0; n < node.jjtGetNumChildren(); n++ ) {
				Node childNode = node.jjtGetChild( n );
				if ( childNode instanceof ASTMethodDeclarator ) {
					methodDeclarator = (ASTMethodDeclarator) childNode;
					break;
				}
			}

			if ( methodEntry.decisionPoints >= reportLevel ) {
				addViolation( data, node, new String[] { "method",
						methodDeclarator == null ? "" : methodDeclarator.getImage(),
								String.valueOf( methodEntry.decisionPoints ) } );
			}
		}
		return data;
	}
	
	@Override
	public void calcMetric(Stack<ElementNode> entryStack,ASTEnumDeclaration node, Object data) {
		entryStack.push( new ElementNode( node ) );
		super.visit( node, data );
		ElementNode classEntry = entryStack.pop();
		if ( classEntry.getComplexityAverage() >= reportLevel
				|| classEntry.highestDecisionPoints >= reportLevel ) {
			addViolation( data, node, new String[] {
					"class",
					node.getImage(),
					classEntry.getComplexityAverage() + "(Highest = "
							+ classEntry.highestDecisionPoints + ')' } );
		}
		return data;
	}

	@Override
	public void calcMetric(Stack<ElementNode> entryStack,ASTConstructorDeclaration node, Object data) {
		entryStack.push( new ElementNode( node ) );
		super.visit( node, data );
		ElementNode constructorEntry = entryStack.pop();
		int constructorDecisionPointCount = constructorEntry.decisionPoints;
		ElementNode classEntry = entryStack.peek();
		classEntry.methodCount++;
		classEntry.decisionPoints += constructorDecisionPointCount;
		if ( constructorDecisionPointCount > classEntry.highestDecisionPoints ) {
			classEntry.highestDecisionPoints = constructorDecisionPointCount;
		}
		if ( constructorEntry.decisionPoints >= reportLevel ) {
			addViolation( data, node, new String[] { "constructor",
					classEntry.node.getImage(),
					String.valueOf( constructorDecisionPointCount ) } );
		}
		return data;
	}*/
}
