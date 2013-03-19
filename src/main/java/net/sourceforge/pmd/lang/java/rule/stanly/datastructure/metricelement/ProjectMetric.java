package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.metricelement;

public class ProjectMetric {
	private int Libraries;
	private float Fat;
	private float Tangled;
	private float ACDLibrary;
	
	public int getLibraries() {
		return Libraries;
	}
	public void setLibraries(int libraries) {
		Libraries = libraries;
	}
	public float getFat() {
		return Fat;
	}
	public void setFat(float fat) {
		Fat = fat;
	}
	public float getTangled() {
		return Tangled;
	}
	public void setTangled(float tangled) {
		Tangled = tangled;
	}
	public float getACD() {
		return ACDLibrary;
	}
	public void setACD(float acd) {
		ACDLibrary = acd;
	}
	
}
