package weapon.missile.base_missile;

import map.populationHub.PopulationHub;
import weapon.missile.IMissile;
import weapon.missile.Missile;

/**
 * Created by hayesj3 on 4/13/2015.
 */
public abstract class NuclearMissile extends Missile {

    protected FalloutTiers falloutTier = null;
    protected NuclearMissile(PopulationHub homebase, IMissile missile) {
        super(homebase, missile);
    }
    public enum FalloutTiers{
        low(3.0, 0.25, FalloutType.mild),
        std(5.0, 1.0, FalloutType.medium),
        high(10.0, 1.25, FalloutType.deadly),
        elite(20.0, 1.5, FalloutType.deadly);

        private double falloutRange;
        private double falloutDensity;
        private FalloutType falloutType;
        private enum FalloutType { mild, medium, deadly; }

        private FalloutTiers(double range, double density, FalloutType type) {
            this.falloutRange = range;
            this.falloutDensity = density;
            this.falloutType = type;
        }
    }
}
