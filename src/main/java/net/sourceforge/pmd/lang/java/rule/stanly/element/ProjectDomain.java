package net.sourceforge.pmd.lang.java.rule.stanly.element;

import net.sourceforge.pmd.lang.java.rule.stanly.metrics.ProjectMetric;


public class ProjectDomain extends ElementNode {
	public ProjectMetric metric;
	
	public ProjectDomain(ElementNode parent, ElementNodeType type, String name) {
		super(parent,type, name);
		// TODO Auto-generated constructor stub
	}	
	public ProjectDomain(ElementNodeType type, String name) {
		super(type,name);
		// TODO Auto-generated constructor stub
	}
}
