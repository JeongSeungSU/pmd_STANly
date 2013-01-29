package net.sourceforge.pmd.lang.java.rule.stanly.metrics;

public class PackageSetMetric{
	private int	Units;
	private int LOC;
	private float ClassesPerClass;
	private float MethodsPerClass;
	private float FieldsPerClass;
	private float ELOCPerUnit;
	private float CC;
	private float FAT;
	private float Tangled;
	public int getUnits() {
		return Units;
	}
	public void setUnits(int units) {
		Units = units;
	}
	public float getClassesPerClass() {
		return ClassesPerClass;
	}
	public void setClassesPerClass(float classesPerClass) {
		ClassesPerClass = classesPerClass;
	}
	public float getMethodsPerClass() {
		return MethodsPerClass;
	}
	public void setMethodsPerClass(float methodsPerClass) {
		MethodsPerClass = methodsPerClass;
	}
	public float getFieldsPerClass() {
		return FieldsPerClass;
	}
	public void setFieldsPerClass(float fieldsPerClass) {
		FieldsPerClass = fieldsPerClass;
	}
	public int getLOC() {
		return LOC;
	}
	public void setLOC(int loc) {
		LOC = loc;
	}
	public void addLOC(int loc) {
		// TODO Auto-generated method stub
		LOC += loc;
	}
	public float getELOCPerUnit() {
		return ELOCPerUnit;
	}
	public void setELOCPerUnit(float eLOCPerUnit) {
		ELOCPerUnit = eLOCPerUnit;
	}
	public float getCC() {
		return CC;
	}
	public void setCC(float cC) {
		CC = cC;
	}
	public float getFAT() {
		return FAT;
	}
	public void setFAT(float fAT) {
		FAT = fAT;
	}
	public float getTangled() {
		return Tangled;
	}
	public void setTangled(float tangled) {
		Tangled = tangled;
	}
	
}
