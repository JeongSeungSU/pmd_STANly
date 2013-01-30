package net.sourceforge.pmd.lang.java.rule.stanly.element;

import net.sourceforge.pmd.lang.java.rule.stanly.metrics.AttributeMetric;


public class FieldDomain extends ElementNode {
	public AttributeMetric metric;
	
	public FieldDomain(ElementNode parent, ElementNodeType type, String name) {
		super(parent,type, name);
		// TODO Auto-generated constructor stub
		if(metric == null)
			metric = new AttributeMetric();
	}
	public FieldDomain(ElementNodeType type, String name) {
		super(type, name);
		// TODO Auto-generated constructor stub
		if(metric == null)
			metric = new AttributeMetric();
	}

	
}
