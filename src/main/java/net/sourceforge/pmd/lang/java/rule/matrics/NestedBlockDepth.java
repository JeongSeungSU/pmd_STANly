package net.sourceforge.pmd.lang.java.rule.matrics;

import java.util.Stack;

import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

public class NestedBlockDepth extends AbstractJavaRule {
	private static class Entry {
		private Node node;
		private int decisionPoints = 1;
		public int highestNestedBlockDepth;
		public int methodCount;

		private Entry(Node node) {
			this.node = node;
		}

		public void bumpDecisionPoints() {
			decisionPoints++;
		}

		public void bumpDecisionPoints(int size) {
			decisionPoints += size;
		}

		//public int getComplexityAverage() {
			//return (double) methodCount == 0 ? 1 : (int) Math.rint( (double) decisionPoints / (double) methodCount );
		//}
		
		public double getComplexityAverage() {
			return (double) methodCount == 0 ? 1 : (double)( (double) decisionPoints / (double) methodCount );
		}
	}

	private Stack<Entry> entryStack = new Stack<Entry>();
}
