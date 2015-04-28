package weapon.missile.interceptionMissile;

import map.populationHub.PopulationHub;
import player.Player;
import weapon.missile.baseMissile.InterceptMissile;

/**
 * Created by hayesj3 on 4/25/2015.
 */
public class NuclearInterMissile extends InterceptMissile {

    public NuclearInterMissile(Player owner) {
        this(owner.getCapital());
    }
    public NuclearInterMissile(PopulationHub homebase) {
        super(homebase, MissileTypes.NuclearInterMissile);
    }

    @Override
    public void applyEffect() {
        //TODO apply fallout to the terrain under the sky centered on the blast, within the blast radius
    }
}
