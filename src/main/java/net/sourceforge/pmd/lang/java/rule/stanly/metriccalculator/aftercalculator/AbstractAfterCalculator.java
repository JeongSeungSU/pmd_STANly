package net.sourceforge.pmd.lang.java.rule.stanly.metriccalculator.aftercalculator;

import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ProjectDomain;

public interface AbstractAfterCalculator {
	public void calcMetric(ProjectDomain node);
}
