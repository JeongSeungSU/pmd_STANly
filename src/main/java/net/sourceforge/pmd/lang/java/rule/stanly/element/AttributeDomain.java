package net.sourceforge.pmd.lang.java.rule.stanly.element;

import net.sourceforge.pmd.lang.java.rule.stanly.metrics.AttributeMetric;


public class AttributeDomain extends ElementNode {
	public AttributeMetric metric;
	
	public AttributeDomain(ElementNode parent, ElementNodeType type, String name) {
		super(parent,type, name);
		// TODO Auto-generated constructor stub
	}
	public AttributeDomain(ElementNodeType type, String name) {
		super(type, name);
		// TODO Auto-generated constructor stub
	}

}
