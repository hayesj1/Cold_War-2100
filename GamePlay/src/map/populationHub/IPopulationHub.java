package map.populationHub;

import weapon.missile.baseMissile.Missile;

interface IPopulationHub {
	double hamletMissileProdPerTurn = 0.05;
	double villageMissileProdPerTurn = 0.25;
	double townMissileProdPerTurn = 0.5;
	double cityMissileProdPerTurn = 1.0;
	double metropolisMissileProdPerTurn = 2.5;
	
	int hamletMinPop = 1;
	int villageMinPop = 500;
	int townMinPop = 1500;
	int cityMinPop = 25000;
	int metropolisMinPop = 75000;

	double nationalismBonus = 1.25;
	double fearConstant = 1.125;

	void produce ();
	double targettedByMissle(Missile m);
	double nationalisticShift(double shift);
	
	int populationChange(int change);
	double fearChange(double change);
}
