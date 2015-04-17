package weapon.missile.std_missile;

import map.populationHub.PopulationHub;
import player.Player;
import weapon.missile.IMissile;
import weapon.missile.base_missile.BioMissile;

/**
 * Created by hayesj3 on 4/13/2015.
 */
public class StdBMissile extends BioMissile {

	protected StdBMissile(Player owner, IMissile missile) { this(owner.getCapital(), missile); }
	protected StdBMissile(PopulationHub homebase, IMissile missile) {
		super(homebase, missile);
	}

	@Override
	public void strike () {
	}

	@Override
	public void intercepted (IMissile m) {
	}
}
