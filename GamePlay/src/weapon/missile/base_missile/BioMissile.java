package weapon.missile.base_missile;

import map.populationHub.PopulationHub;
import weapon.missile.Missile;

/**
 * Created by hayesj3 on 4/13/2015.
 */
public abstract class BioMissile extends Missile {

    protected BioTiers tier = null;
    protected BioMissile(PopulationHub homebase, BioMissile missile) {
        super(homebase, missile);
    }

    public enum BioTiers{
        low(3.0, 0.25, BioType.mild),
        std(5.0, 1.0, BioType.medium),
        high(10.0, 1.25, BioType.deadly),
        elite(20.0, 1.5, BioType.deadly);

        private double bioRange;
        private double bioDensity;
        private BioType bioType;
        private enum BioType { mild, medium, deadly; }

        private BioTiers(double range, double density, BioType type) {
            this.bioRange = range;
            this.bioDensity = density;
            this.bioType = type;
        }
    }
}
