package weapon.missile.strikeMissile;

import map.populationHub.PopulationHub;
import player.Player;
import weapon.missile.baseMissile.Missile;

/**
 * Created by hayesj3 on 4/13/2015.
 */
public class NuclearMissile extends Missile {

    protected FalloutTiers tier = null;
    public NuclearMissile(Player owner) {
        this(owner.getCapital());
    }
    public NuclearMissile(PopulationHub homebase) { super(homebase, MissileTypes.NuclearMissile); }

    @Override
    public void applyEffect() {
        //TODO apply fallout to the terrain within the blast radius + tier.range
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
