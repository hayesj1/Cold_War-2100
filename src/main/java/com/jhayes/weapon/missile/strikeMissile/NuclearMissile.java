package main.java.com.jhayes.weapon.missile.strikeMissile;

import main.java.com.jhayes.map.populationHub.PopulationHub;
import main.java.com.jhayes.player.Player;
import main.java.com.jhayes.weapon.missile.baseMissile.Missile;

/**
 * Created by hayesj3 on 4/13/2015.
 */
public class NuclearMissile extends Missile {

    public NuclearMissile(Player owner) { this(owner.getCapital()); }
    public NuclearMissile(PopulationHub homebase) {
        super(homebase, MissileTypes.NuclearMissile);
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
        //TODO apply fallout to the terrain within the blast radius + tier.range
    }
}
