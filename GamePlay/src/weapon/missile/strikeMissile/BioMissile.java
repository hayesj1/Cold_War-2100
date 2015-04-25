package weapon.missile.strikeMissile;

import map.populationHub.PopulationHub;
import player.Player;
import weapon.missile.baseMissile.Missile;

/**
 * Created by hayesj3 on 4/13/2015.
 */
public class BioMissile extends Missile {

    protected BioTiers tier = null;
    public BioMissile(Player owner, BioMissile missile) { this(owner.getCapital(), missile); }
    public BioMissile(PopulationHub homebase, BioMissile missile) {
        super(homebase, missile);
    }

    @Override
    public void applyEffect() {
        //TODO apply Bio taint to the terrain within the blast radius + tier.range
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
