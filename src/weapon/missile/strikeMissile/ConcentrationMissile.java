package weapon.missile.strikeMissile;

import map.populationHub.PopulationHub;
import player.Player;
import weapon.missile.baseMissile.Missile;

import java.io.Serializable;

/**
 * Created by hayesj3 on 4/13/2015.
 */
public class ConcentrationMissile extends Missile implements Serializable {

    private String name;
    public ConcentrationMissile(Player owner) { this(owner.getCapital()); }
    public ConcentrationMissile(PopulationHub homebase) {
        super(homebase, MissileTypes.ConcentrationMissile);
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
        //TODO apply shockwave to the terrain within the blast radius + tier.effectRange
    }
}
