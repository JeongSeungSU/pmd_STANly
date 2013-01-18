package net.sourceforge.pmd.lang.java.rule.matrics.HyeSeong;

import net.sourceforge.pmd.lang.ast.Node;

public class StackEntry {
	private Node node;
	private int count;
	private String ClassName;
	public StackEntry(Node node, String name)
	{
		count=0;
		ClassName = name;
	}
	public String getClassName() {
		return ClassName;
	}
	public void setClassName(String className) {
		ClassName = className;
	}
	public Node getNode() {
		return node;
	}
	public int getCount() {
		return count;
	}
	public void addCount(){
		count++;
	}
}
