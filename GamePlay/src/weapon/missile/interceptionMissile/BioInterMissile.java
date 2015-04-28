package weapon.missile.interceptionMissile;

import map.populationHub.PopulationHub;
import player.Player;
import weapon.missile.baseMissile.InterceptMissile;

/**
 * Created by hayesj3 on 4/25/2015.
 */
public class BioInterMissile extends InterceptMissile {

    public BioInterMissile(Player owner) {
        this(owner.getCapital());
    }
    public BioInterMissile(PopulationHub homebase) {
        super(homebase, MissileTypes.BioInterMissile);
    }
    @Override
    public void applyEffect() {
        //TODO apply Bio taint to the terrain under the sky centered on the blast, within the blast radius
    }
}
