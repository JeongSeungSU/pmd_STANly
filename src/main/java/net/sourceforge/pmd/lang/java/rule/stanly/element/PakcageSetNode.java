package net.sourceforge.pmd.lang.java.rule.stanly.element;

import net.sourceforge.pmd.lang.java.rule.stanly.metrics.PackageMetric;

public class PakcageSetNode extends ElementNode {
	public PackageMetric metric;
	

	public PakcageSetNode(ElementNode parent, ElementNodeType type, String name) {
		super(parent,type, name);
		// TODO Auto-generated constructor stub
	}
	public PakcageSetNode(ElementNodeType type, String name) {
		super(type, name);
		// TODO Auto-generated constructor stub
	}
}
