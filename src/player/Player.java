package player;

import game.ColdWar2100;
import gui.EnterAString;
import gui.EnterAStringTypes;
import gui.HUD;
import map.populationHub.PopulationHub;
import net.client.Client;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public final class Player implements Comparable<Player>, Serializable {
	/** @serial The popHubs owned by the Player */
	private ArrayList<PopulationHub> ownedCities;

	/** @serial The Player's id number; all players have different id numbers so they can use the same name */
	private Integer playerID = -1;
	/** @serial The Player's display name */
	private String playerName = "";
	/** @serial True if the player has a capital; false otherwsie */
	private boolean hasCapital = false;

	/** @serial The empire the Player is allied to */
	private EnumEmpires allegiance = null;
	/** #serial The name of this player's capital; allows for finding and identifying a player's capital in a data structure of Pophubs */
	private String capitalName = null;

	/**  The capital popHub of a Player */
	private transient PopulationHub capital = null;
	/** The main game HUD */
	private transient HUD mainGUI = null;
	/** The client associated this player */
	private transient Client client = null;

	public Player() {
		this.playerID = ColdWar2100.getNextPlayerID();
		this.allegiance = (EnumEmpires)(JOptionPane.showInputDialog(null, "Choose an Allegiance:", "Allegiance",
				JOptionPane.QUESTION_MESSAGE, null, EnumEmpires.values(), null));
		System.out.println(this.playerName + " is now a member of the " + this.allegiance);
		this.playerName = (new EnterAString(EnterAStringTypes.PLAYER, this)).getText();
	}

	public boolean hasCapital() { return this.hasCapital; }

	public int getID() { return this.playerID; }
	public String getName() { return this.playerName; }
	public EnumEmpires getAllegiance() { return this.allegiance; }
	public PopulationHub getCapital () { return this.capital; }
	public HUD getMainGUI() { return this.mainGUI; }
	public Client getClient() { return this.client; }

	public void setName(String newName) { this.playerName = newName; }
	public void setCapital(PopulationHub newCapital) {
		this.capital = newCapital;
		this.capitalName = newCapital.getName();
		this.hasCapital = true;
	}
	public void setMainGUI(HUD mainGUI) { this.mainGUI = mainGUI; }
	public void setClient(Client client) { this.client = client; }

	public ArrayList<PopulationHub> getOwnedCities() {
		if (this.ownedCities == null) {
			this.ownedCities = new ArrayList<>(1);
			for (PopulationHub ph : PopulationHub.getAllPopHubs()) {
				if (ph == null) { break; }
				else if (ph.getOwner().equals(this)) { this.ownedCities.add(ph); }
				else { continue; }
			}
		}
		return this.ownedCities;
	}
	@Override
	public String toString() {
		return playerName;
	}
	@Override
	public int compareTo(Player other) {
		int sortByName = this.playerName.compareTo(other.playerName);
		return (sortByName == 0) ? this.playerID.compareTo(other.playerID) : sortByName;
	}
}
