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

	private int maxTurns = -1;
	private int currTurn = -1;
	private int numActivePlayers;

	private static Turn instance = null;
	private static Object[] maxTurnChoices = {"25", "50", "75", "\u221E"};

	private Turn() {
		this.numActivePlayers = Controller.getInstance().getNumPlayers();
		this.currTurn = 0;
		String temp = (String) JOptionPane.showInputDialog(null, "Choose Maximum number of turns:", "Max turns",
				JOptionPane.QUESTION_MESSAGE, Resources.iconII, maxTurnChoices, maxTurnChoices[3]);
		if (temp.equals("\u221E")) {
			temp = "0";
		}
		this.maxTurns = Integer.valueOf(temp);
		if (this.maxTurns == 0) {
			// player chose infinite turns; this creates issues because of the recursive nature of doTurn(). Thus there upper limit is 1000 turns.
			this.maxTurns = 1000;
		}
	}

	/**
	 * starts, does, and completes a single turn
	 *
	 * @return true when the game is over, either by victory or maximum number of turns, otherwise
	 */
	public boolean doTurn() {
	// pre-turn stuff
		// check for a tie from the previous turn
		if (currTurn > 0 && currTurn == maxTurns)
			return true;
	// begin the turn
		currTurn++;
		System.out.println("Turn #" + currTurn);
		// start the turn
		for (Player p : Controller.getPlayers()) {
			// missile production
			for (PopulationHub ph : PopulationHub.getAllPopHubsByPlayer(p)) {
				ph.produce();
				System.out.println(ph + " is owned by " + ph.getOwner());
			}
			// launch attack(s)
			int choice = -1;
			//int temp = p.getOwnedCities().forEach(ph -> {ph.getMissilesBasedHere().isEmpty(); } );
			while (true) {
				choice = JOptionPane.showConfirmDialog(null, "Would you like to attack?", "Attack Phase",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, Resources.iconII);
				if (choice == JOptionPane.NO_OPTION) {
					break;
				} else {
					new AttackPopHubScreen(p);
				}
			}
		}
	// post-turn stuff
		// missile movement
		for (IMissile m : Missile.getAllMissilesByID().values()) {
			if (m == null || !m.isLaunched()) { continue; }
			int temp = m.travel();
			if (temp == -1) { m.strike(); }
		}
		return false;
	}

	/**
	 * get the number of active players
	 *
	 * @return the number of players still in the game
	 */
	public int getNumPlayers() {
		return numActivePlayers;
	}

	public static Turn getInstance() {
		if (instance == null) {
			instance = new Turn();
		}
		return instance;
	}
}
