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
	public int gettotalNOC() {
		return totalNOC;
	}
	public void addLCOM(int lCOM) {
		totalLCOM += lCOM;
	}
	public int getUnit() {
		return Unit;
	}
	public void setUnit(int unit) {
		Unit = unit;
	}
	public float getTotalDistance() {
		return totalDistance;
	}
	public void setTotalDistance(float totalDistance) {
		this.totalDistance = totalDistance;
	}
	public float getTotalDistanceAbsolute() {
		return totalDistanceAbsolute;
	}
	public void setTotalDistanceAbsolute(float totalDistanceAbsolute) {
		this.totalDistanceAbsolute = totalDistanceAbsolute;
	}
	public int getTotalWMC() {
		return totalWMC;
	}
	public void setTotalWMC(int totalWMC) {
		this.totalWMC = totalWMC;
	}
	public int getTotalDIT() {
		return totalDIT;
	}
	public void setTotalDIT(int totalDIT) {
		this.totalDIT = totalDIT;
	}
	public int getTotalNOC() {
		return totalNOC;
	}
	public void setTotalNOC(int totalNOC) {
		this.totalNOC = totalNOC;
	}
	public int getTotalLCOM() {
		return totalLCOM;
	}
	public void setTotalLCOM(int totalLCOM) {
		this.totalLCOM = totalLCOM;
	}
	public void setPackages(int packages) {
		Packages = packages;
	}
	public void setNumberOfClass(int numberOfClass) {
		NumberOfClass = numberOfClass;
	}
	public void setFatPackages(int fatPackages) {
		FatPackages = fatPackages;
	}
	public void setFatUnits(int fatUnits) {
		FatUnits = fatUnits;
	}
	public void setTotalCBO(int totalCBO) {
		this.totalCBO = totalCBO;
	}
	public void setTotalRFC(int totalRFC) {
		this.totalRFC = totalRFC;
	}
	public int getNumberOfClass() {
		return NumberOfClass;
	}
	public void addNumberOfClass(int numberOfClass) {
		NumberOfClass += numberOfClass;
	}
}
