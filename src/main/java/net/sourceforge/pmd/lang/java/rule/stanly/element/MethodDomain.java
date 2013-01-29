package net.sourceforge.pmd.lang.java.rule.stanly.element;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pmd.lang.java.rule.stanly.metrics.MethodMetric;		


public class MethodDomain extends ElementNode {
	public MethodMetric metric;
	public List<String> parameters;
	public MethodDomain(ElementNode parent, ElementNodeType type, String name) {
		super(parent,type, name);
		// TODO Auto-generated constructor stub
		metric = new MethodMetric();
		parameters = new ArrayList<String>();
	}
	public MethodDomain(ElementNodeType type, String name) {
		super(type, name);
		// TODO Auto-generated constructor stub
		metric = new MethodMetric();
		parameters = new ArrayList<String>();
	}

}
