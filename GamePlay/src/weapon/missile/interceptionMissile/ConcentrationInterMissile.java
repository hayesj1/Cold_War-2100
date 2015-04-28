package weapon.missile.interceptionMissile;

import map.populationHub.PopulationHub;
import player.Player;
import weapon.missile.baseMissile.InterceptMissile;

/**
 * Created by hayesj3 on 4/25/2015.
 */
public class ConcentrationInterMissile extends InterceptMissile {

    public ConcentrationInterMissile(Player owner) { this(owner.getCapital()); }
    public ConcentrationInterMissile(PopulationHub homebase) { super(homebase, MissileTypes.ConcentrationInterMissile); }

    @Override
    public void applyEffect() {
        //TODO apply shockwave to the terrain under the sky centered on the blast, within the blast radius
    }
}
