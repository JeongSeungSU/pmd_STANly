package net.sourceforge.pmd.lang.java.rule.stanly.metrics;

public class LibraryMetric {
	private int Unit;
	private int Packages;
	private int NumberOfClass;	// unit class + inner class
	private int FatPackages;
	private int FatUnits;
	private float Tangled;
	private float ACDPackage;
	private float ACDUnit;
	private float totalDistance;
	private float totalDistanceAbsolute;
	private int totalWMC;
	private int totalDIT;
	private int totalNOC;
	private int totalCBO;
	private int totalRFC;
	private int totalLCOM;
	
	public int getPackages() {
		return Packages;
	}
	public void addPackages(int packages) {
		Packages += packages;
	}
	public float getUnitPerPackage() {
		return Packages == 0 ? 0 : (float)Unit / (float)Packages;
	}
	public int getUnits()
	{
		return Unit;
	}
	public void addUnits(int unit) {
		Unit += unit;
	}	
	public int getFatPackages() {
		return FatPackages;
	}
	public void addFatPackages(int fatPackages) {
		FatPackages += fatPackages;
	}
	public int getFatUnits() {
		return FatUnits;
	}
	public void addFatUnits(int fatUnits) {
		FatUnits += fatUnits;
	}
	public float getTangled() {
		return Tangled;
	}
	public void setTangled(float tangled) {
		Tangled = tangled;
	}
	public float getACDPackage() {
		return ACDPackage;
	}
	public void setACDPackage(float aCDPackage) {
		ACDPackage = aCDPackage;
	}
	public float getACDUnit() {
		return ACDUnit;
	}
	public void setACDUnit(float aCDUnit) {
		ACDUnit = aCDUnit;
	}
	public float getDistance() {
		return Packages == 0 ? 0 : totalDistance / (float)Packages;
	}
	public void addDistance(float distance) {
		totalDistance += distance;
	}
	public float getDistanceAbsolute() {
		return Packages == 0 ? 0 : totalDistanceAbsolute / (float)Packages;
	}
	public void addDistanceAbsolute(float distanceAbsolute) {
		totalDistanceAbsolute += distanceAbsolute;
	}
	public float getWMC() {
		return (float)totalWMC / (float)NumberOfClass;
	}
	public void addWMC(int wMC) {
		totalWMC += wMC;
	}
	public float getDIT() {
		return (float)totalDIT / (float)NumberOfClass;
	}
	public void addDIT(int dIT) {
		totalDIT += dIT;
	}
	public float getNOC() {
		return (float)totalNOC / (float)NumberOfClass;
	}
	public void addNOC(float nOC) {
		totalNOC += nOC;
	}
	public float getAverageCBO() {
		return NumberOfClass == 0 ? 0 : (float)totalCBO / (float)NumberOfClass;
	}
	public int getTotalCBO() {
		return totalCBO;
	}
	public void addCBO(float cBO) {
		totalCBO += cBO;
	}
	public float getAverageRFC() {
		return NumberOfClass == 0 ? 0 : (float)totalRFC / (float)NumberOfClass;
	}
	public int getTotalRFC() {
		return totalRFC;
	}
	public void addRFC(int rFC) {
		totalRFC += rFC;
	}
	public float getAverageLCOM() {
		return NumberOfClass == 0 ? 0 : (float)totalLCOM / (float)NumberOfClass;
	}
	public int getLCOM() {
		return totalLCOM;
	}
	public void addLCOM(int lCOM) {
		totalLCOM += lCOM;
	}
	public int getNumberOfClass() {
		return NumberOfClass;
	}
	public void addNumberOfClass(int numberOfClass) {
		NumberOfClass += numberOfClass;
	}
}
