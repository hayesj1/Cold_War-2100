package weapon.missile.interceptionMissile;

import map.populationHub.PopulationHub;
import player.Player;
import weapon.missile.baseMissile.InterceptMissile;

/**
 * Created by hayesj3 on 4/25/2015.
 */
public class BioInterMissile extends InterceptMissile {

    public BioInterMissile(Player owner, BioInterMissile missile) {
        this(owner.getCapital(), missile);
    }
    public BioInterMissile(PopulationHub homebase, BioInterMissile missile) {
        super(homebase, missile);
    }
    @Override
    public void applyEffect() {
        //TODO apply Bio taint to the terrain under the sky centered on the blast, within the blast radius
    }
}
