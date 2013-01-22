package net.sourceforge.pmd.lang.java.rule.yhctest;

public class StanlyMetricValue {
	public float total;
	public float mean;
	public float stddev;
	public float maximum;
	public String cause;
	
	public StanlyMetricValue()
	{
		maximum = stddev = mean = total = 0.0f;
		cause = null;
	}
}
