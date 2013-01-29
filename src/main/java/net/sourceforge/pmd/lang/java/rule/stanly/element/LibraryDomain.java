package net.sourceforge.pmd.lang.java.rule.stanly.element;

import net.sourceforge.pmd.lang.java.rule.stanly.metrics.LibraryMetric;


public class LibraryDomain extends ElementNode {
	public LibraryMetric metric;
	
	public LibraryDomain(ElementNode parent, ElementNodeType type, String name) {
		super(parent,type, name);
		// TODO Auto-generated constructor stub
		if(metric == null)
			metric = new LibraryMetric();
	}
	
	public LibraryDomain(ElementNodeType type, String name) {
		super(type, name);
		// TODO Auto-generated constructor stub
		if(metric == null)
			metric = new LibraryMetric();
	}
}
