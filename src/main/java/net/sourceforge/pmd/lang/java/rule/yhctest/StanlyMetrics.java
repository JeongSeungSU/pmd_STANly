package net.sourceforge.pmd.lang.java.rule.yhctest;

import java.util.HashMap;

public class StanlyMetrics {
	
	private HashMap<String,StanlyMetricValue> metric;

	public StanlyMetrics(){
		metric = new HashMap<String,StanlyMetricValue>();
		metric.put("CyclomaticComplexity", new StanlyMetricValue());
		metric.put("NumberOfParameters", new StanlyMetricValue());
		metric.put("NestedBlockDepth", new StanlyMetricValue());
		metric.put("WeightedMethodsPerClass", new StanlyMetricValue());
		metric.put("NumberOfAttributes", new StanlyMetricValue());
		metric.put("NumberOfStaticAttributes", new StanlyMetricValue());
		metric.put("NumberOfMethods", new StanlyMetricValue());
		metric.put("NumberOfStaticMethods", new StanlyMetricValue());
		metric.put("NumberOfClasses", new StanlyMetricValue());
		metric.put("NumberOfInterfaces", new StanlyMetricValue());
		metric.put("NumberOfPackages", new StanlyMetricValue());
		metric.put("MethodLinesOfCode", new StanlyMetricValue());
		metric.put("TotalLinesOfCode", new StanlyMetricValue());
		metric.put("AfferentCoupling", new StanlyMetricValue());
		metric.put("EfferentCoupling", new StanlyMetricValue());
		metric.put("Instability", new StanlyMetricValue());
		metric.put("NormalizedDistance", new StanlyMetricValue());
		metric.put("Abstractness", new StanlyMetricValue());
		metric.put("DepthOfInheritanceTree", new StanlyMetricValue());
		metric.put("NumberOfChildren", new StanlyMetricValue());
		metric.put("NumberOfOverriddenMethods", new StanlyMetricValue());
		metric.put("SpecializationIndex", new StanlyMetricValue());
		metric.put("LackOfCohesionOfMethods", new StanlyMetricValue());
	}
	
	public StanlyMetricValue getMetric(String key)
	{
		return metric.get(key);
	}
	
	
}
