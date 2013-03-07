package net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator;

import net.sourceforge.pmd.lang.java.rule.stanly.element.ProjectDomain;

public interface AbstractAfterCalculator {
	public void calcMetric(ProjectDomain node);
}
