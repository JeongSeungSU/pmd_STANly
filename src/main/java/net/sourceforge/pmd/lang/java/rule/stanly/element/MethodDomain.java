package net.sourceforge.pmd.lang.java.rule.stanly.element;

import net.sourceforge.pmd.lang.java.rule.stanly.metrics.MethodMetric;


public class MethodDomain extends ElementNode {
	public MethodMetric metric;
	
	public MethodDomain(ElementNode parent, ElementNodeType type, String name) {
		super(parent,type, name);
		// TODO Auto-generated constructor stub
	}
	public MethodDomain(ElementNodeType type, String name) {
		super(type, name);
		// TODO Auto-generated constructor stub
	}

}
