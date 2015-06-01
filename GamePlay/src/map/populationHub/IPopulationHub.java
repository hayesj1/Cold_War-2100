package map.populationHub;

import weapon.missile.baseMissile.Missile;

interface IPopulationHub {
	double hamletMissileProdPerTurn = 0.05;
	double villageMissileProdPerTurn = 0.1;
	double townMissileProdPerTurn = 0.25;
	double cityMissileProdPerTurn = 0.5;
	double metropolisMissileProdPerTurn = 1.0;
	
	int hamletMinPop = 1;
	int villageMinPop = 500;
	int townMinPop = 1500;
	int cityMinPop = 25000;
	int metropolisMinPop = 75000;

	double nationalismBonus = 0.25;
	double fearConstant = 1.125;
	double popGrowthRate = 0.05;
	double popGrowthConstant = Math.exp(popGrowthRate);

	void targetedByMissile(Missile m);

	void nationalisticChange(double change);
	void populationChange(int change);
	void fearChange(double change);
}
