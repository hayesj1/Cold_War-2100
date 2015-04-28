package controller;

import gui.AttackPopHubScreen;
import map.populationHub.PopulationHub;
import player.Player;
import resource.Resources;
import weapon.missile.baseMissile.IMissile;
import weapon.missile.baseMissile.Missile;

import javax.swing.*;

/**
 * Created by hayesj3 on 4/13/2015.
 */
public final class Turn {

	private int maxTurn = 5;
	private int currTurn = 0;
	private int numActivePlayers = Controller.getPlayers().size();

	private static Turn instance = null;

	private Turn () {
		this.doTurn();
	}

	/**
	 * starts a turn, and at the end calls itself; this way the game is in one recursive method
	 * In other words this method encapsulate  single turn, and starts the next turn in its return statement
	 * @return true when a player wins, false if there is a tie and the max turns is reached
	 */
	private boolean doTurn () {
		if(currTurn == maxTurn)
			return false;

		currTurn++;
	// start the turn
		for (Player p : Controller.getPlayers()) {
			// missile production
			for (PopulationHub ph : PopulationHub.getPlayersPopHubs(p)) {
				ph.produce();
				System.out.println(ph + " is owned by " + ph.getOwner());
			}
			// launch attack(s)
			int choice = -1;
			do {
				choice = JOptionPane.showConfirmDialog(null, "Would you like to attack?", "Attack Phase",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, Resources.iconII);
				if (choice == JOptionPane.NO_OPTION) { break; }
				else {
					new AttackPopHubScreen(p);
				}
			} while (true);
		}
	// end the turn
		// missile movement
		for (IMissile m : Missile.getAllMissilesByID().values()) {
			if (m == null) {
				break;
			}
			m.travel();
		}
		return this.doTurn();
	}
	/**
	 * get the number of active players
	 * @return the number of players still in the game
	 */
	public int getNumPlayers () {
		return numActivePlayers;
	}

	public static Turn getInstance () {
		if (instance == null) {
			instance = new Turn();
		}
		return instance;
	}
}
