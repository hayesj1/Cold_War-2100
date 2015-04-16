package map.populationHub;

enum EnumPopulationHub {

	hamlet(IPopulationHub.hamletMinPop, IPopulationHub.hamletMissileProdPerTurn),
	village(IPopulationHub.villageMinPop, IPopulationHub.villageMissileProdPerTurn),
	town(IPopulationHub.townMinPop, IPopulationHub.townMissileProdPerTurn),
	city(IPopulationHub.cityMinPop, IPopulationHub.cityMissileProdPerTurn),
	metropolis(IPopulationHub.metropolisMinPop, IPopulationHub.metropolisMissileProdPerTurn);
	
	private int minPop;
	private double missileProdPerTurn;
	
	EnumPopulationHub(int minPop, double missilesPerTurn) {
		this.minPop = minPop;
		this.missileProdPerTurn = missilesPerTurn;
	}

	public int getMinPop() { return minPop; }

	public double getMissileProdPerTurn() { return missileProdPerTurn; }
}
