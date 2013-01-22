package net.sourceforge.pmd.lang.java.rule.matrics;

import java.util.Stack;

import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTEnumDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

public class NumberOfAttributes extends AbstractJavaRule {

	public static final double a = 10; 
	
	private static class Entry {
		private Node node;
		public int total;
		public int meanofclass;
		public int meanofmethod;
		public int stddev;
		public int maximum;
		public int noc; //Number of Class
		public int nom; //Number of Method
		
		private Entry(Node node) {
			this.node = node;
		}
		
		public double getMeanOfClass() {
			return noc == 0 ? 1 : (double)( (double) total / (double) noc );
		}
		public double getMeanOfMethod() {
			return nom == 0 ? 1 : (double)( (double) total / (double) nom );
		}
	}
	
	private Stack<Entry> entryStack = new Stack<Entry>();
	
		
	@Override
	public Object visit(ASTFieldDeclaration node, Object data) {
		if(node.isStatic() == false)	// Static은 다른 룰셋에서 구
			entryStack.peek().total++;

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
				classEntry.total + " (Highest = "
						+ classEntry.maximum + ')' } );
		//}
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
				classEntry.total + " (Highest = "
						+ classEntry.maximum + ')' } );
		//}
		return data;
	}
}
