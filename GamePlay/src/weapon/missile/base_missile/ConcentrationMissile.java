package weapon.missile.base_missile;

import map.populationHub.PopulationHub;
import weapon.missile.Missile;

/**
 * Created by hayesj3 on 4/13/2015.
 */
public abstract class ConcentrationMissile extends Missile {

    protected ConcentrationTiers tier = null;
    protected ConcentrationMissile(PopulationHub homebase, ConcentrationMissile missile) {
        super(homebase, missile);
    }


    @Override
    public void applyEffect() {
        //TODO apply shockwave to the terrain within the blast radius + tier.range
    }

    public enum ConcentrationTiers{
        low(3.0, 0.25, ConcentrationType.mild),
        std(4.0, 1.0, ConcentrationType.medium),
        high(7.0, 1.25, ConcentrationType.deadly),
        elite(10.0, 1.5, ConcentrationType.deadly);

        private double concenRange;
        private double concenDensity;
        private ConcentrationType concenType;
        private enum ConcentrationType { mild, medium, deadly; }

        private ConcentrationTiers(double range, double density, ConcentrationType type) {
            this.concenRange = range;
            this.concenDensity = density;
            this.concenType = type;
        }
    }
}
