package weapon.missile.std_level_missile;

import map.populationHub.PopulationHub;
import player.Player;
import weapon.missile.IMissile;
import weapon.missile.base_missile.NuclearMissile;

/**
 * Created by hayesj3 on 4/15/2015.
 */
public class StdNMissile extends NuclearMissile {

    protected StdNMissile(Player owner, NuclearMissile missile) { this(owner.getCapital(), missile); }
    protected StdNMissile(PopulationHub homebase, NuclearMissile missile) {
        super(homebase, missile);
    }

    @Override
    public void strike () {
    }

    @Override
    public void intercepted (IMissile m) {
    }
}
