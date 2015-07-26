package weapon.missile.strikeMissile;

import map.populationHub.PopulationHub;
import player.Player;
import weapon.missile.baseMissile.Missile;

/**
 * Created by hayesj3 on 4/13/2015.
 */
public class BioMissile extends Missile {

    public BioMissile(Player owner) { this(owner.getCapital()); }
    public BioMissile(PopulationHub homebase) {
        super(homebase, MissileTypes.BioMissile);
}

    @Override
    public void strike() {
        this.applyEffect();
        this.target.populationChange(this.tier.getPayload().getPopLoss());
        System.out.println(this.target + " struck by " + this);
        super.strike();
    }
    @Override
    public void applyEffect() {
        //TODO apply Bio taint to the terrain within the blast radius + tier.range
    }
}
