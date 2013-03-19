package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement;

import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.metricelement.PackageMetric;


public class PackageDomain extends ElementNode {
	public PackageMetric metric;
	
	public PackageDomain(ElementNode parent, ElementNodeType type, String name) {
		super(parent,type, name);
		// TODO Auto-generated constructor stub
		if(metric == null)
			metric = new PackageMetric();
	}
	public PackageDomain(ElementNodeType type, String name) {
		super(type, name);
		// TODO Auto-generated constructor stub
		if(metric == null)
			metric = new PackageMetric();
	}

}
