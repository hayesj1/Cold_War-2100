package game;

import gui.AttackPopHubScreen;
import gui.EnterAString;
import gui.EnterAStringTypes;
import map.populationHub.PopulationHub;
import net.GEP;
import net.server.ServerThread;
import player.Player;
import resource.Resources;
import weapon.missile.baseMissile.IMissile;
import weapon.missile.baseMissile.Missile;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by hayesj3 on 4/13/2015.
 */
public final class Turn {

	private int maxTurns = -1;
	private int currTurn = -1;
	private int numActivePlayers;
	/** the id of the current player; corresponds to the index of the player in {@link ColdWar2100#players} */
	private int currentPlayer;

	private static Turn instance = null;
	private static Object[] maxTurnChoices = {"25", "50", "75", "\u221E"};

	private Turn() {
		this.numActivePlayers = ColdWar2100.getNumPlayers();
		this.currTurn = 0;
		this.currentPlayer = 0;
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
	 * @return true when the game is over, either by victory or maximum number of turns, otherwise false
	 */
	public boolean doTurn() {
	// pre-turn stuff
		// check for a tie from the previous turn
		if (currTurn > 0 && currTurn == maxTurns)
			return true;
	//turn stuff
		currTurn++;
		System.out.println("Turn #" + currTurn);
		// start the turn
		for (Player p : ColdWar2100.getPlayers()) {
			currentPlayer = p.getID();
			System.out.println(PopulationHub.getAllPopHubsByPlayer(p));

			// missile production
			System.out.println(p + "\'s turn");
			for (PopulationHub ph : PopulationHub.getAllPopHubsByPlayer(p)) {
				if (ph.getRuined()) { continue; }
				ph.processTurn(p);
				System.out.println(ph + " is owned by " + ph.getOwner());
			}
			// launch attack(s)
			int choice = -1;
			while (p.getOwnedCities().stream().anyMatch(ph -> ph.getMissilesBasedHere().size() != 0)) {
				choice = JOptionPane.showConfirmDialog(null, "Would you like to attack?", "Attack Phase",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, Resources.iconII);
				if (choice == JOptionPane.NO_OPTION) {
					break;
				} else {
					new AttackPopHubScreen(p);
				}
			}
			// found new PopHubs
			choice = -1;
			do {
				choice = JOptionPane.showConfirmDialog(null, "Would you like to found a new PopHub?", "Founding Phase",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, Resources.iconII);
				if (choice == JOptionPane.NO_OPTION) {
					break;
				} else {
					String coords = new EnterAString(EnterAStringTypes.ENTER_COORDS, null).getText();
					while(!coords.matches("\\d+,\\d+")) {
						coords = new EnterAString(EnterAStringTypes.ENTER_COORDS, null).getText();
					}
					String[] temp = coords.split(",");
					new PopulationHub(p, Integer.valueOf(temp[0]), Integer.valueOf(temp[1])).addToDS();
				}
			} while (true);
		// end the turn
		}
	// post-turn stuff
		// missile movement
		ArrayList<IMissile> toStrike = new ArrayList<>();
		for (IMissile m : Missile.getAllMissilesByID().values()) {
			if (m == null || !m.isLaunched()) { continue; }
			int temp = m.travel();
			if (temp == -1) { toStrike.add(m); }
		}
		toStrike.forEach( m -> m.strike() );
		toStrike.clear();

		return false;
	}
	public boolean doTurn(ServerThread server) {
	// pre-turn stuff
		// check for a tie from the previous turn
		if (currTurn > 0 && currTurn == maxTurns)
			return true;
	//turn stuff
		currTurn++;
		System.out.println("Turn #" + currTurn);
		// start the turn
		for (Player p : ColdWar2100.getPlayers()) {
			currentPlayer = p.getID();
			System.out.println(PopulationHub.getAllPopHubsByPlayer(p));

			// missile production
			System.out.println(p + "\'s turn");
			for (PopulationHub ph : PopulationHub.getAllPopHubsByPlayer(p)) {
				if (ph.getRuined()) {
					continue;
				}
				ph.processTurn(p);
				System.out.println(ph + " is owned by " + ph.getOwner());
			}
			// launch attack(s)
			try {
				GEP.sendEvent(server.getOut(), GEP.EnumEvents.ATTACKED);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// found new PopHubs
			try {
				GEP.sendEvent(server.getOut(), GEP.EnumEvents.FOUNDED);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
