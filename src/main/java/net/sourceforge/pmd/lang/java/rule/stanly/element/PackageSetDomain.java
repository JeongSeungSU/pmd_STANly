package net.sourceforge.pmd.lang.java.rule.stanly.element;

import net.sourceforge.pmd.lang.java.rule.stanly.metrics.PackageSetMetric;

public class PackageSetDomain extends ElementNode {
	public PackageSetMetric metric;
	

	public PackageSetDomain(ElementNode parent, ElementNodeType type, String name) {
		super(parent,type, name);
		// TODO Auto-generated constructor stub
		if(metric == null)
			metric = new PackageSetMetric();
	}
	public PackageSetDomain(ElementNodeType type, String name) {
		super(type, name);
		// TODO Auto-generated constructor stub
		if(metric == null)
			metric = new PackageSetMetric();
	}
}
