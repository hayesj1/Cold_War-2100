package player;

import controller.Controller;
import gui.EnterAString;
import gui.EnterAStringTypes;
import map.populationHub.PopulationHub;
import resource.Resources;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public final class Player implements Comparable<Player>, Serializable {
	/** @serial The popHubs owned by the Player */
	private ArrayList<PopulationHub> ownedCities;

	/** @serial The Player's id number; all players have different id numbers so they can use the same name */
	protected Integer playerID = -1;
	/** @serial The Player's display name */
	protected String playerName = "";

	/** @serial The empire the Player is allied to */
	protected EnumEmpires allegiance = null;
	/** @serial The capital popHub of a Player */
	protected PopulationHub capital = null;

	public Player() {
		this.playerID = Controller.getNextPlayerID();
		this.allegiance = (EnumEmpires)(JOptionPane.showInputDialog(null, "Choose an Allegiance:", "Allegiance",
				JOptionPane.QUESTION_MESSAGE, Resources.iconII, EnumEmpires.values(), null));
		System.out.println(this.playerName + " is now a member of the " + this.allegiance);
		this.playerName = (new EnterAString(EnterAStringTypes.PLAYER, this)).getText();
	}

	public int getID() { return this.playerID; }
	public String getName() { return this.playerName; }
	public void setName(String newName) { this.playerName = newName; }
	public PopulationHub getCapital () { return this.capital; }
	public void setCapital(PopulationHub newCapital) { this.capital = newCapital; }
	public EnumEmpires getAllegiance() { return this.allegiance; }

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
