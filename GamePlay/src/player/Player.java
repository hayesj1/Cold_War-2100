package player;

import controller.Controller;
import map.populationHub.PopulationHub;
import resource.Resources;

import javax.swing.*;
import java.util.ArrayList;

public class Player implements Comparable<Player>{

	private ArrayList<PopulationHub> ownedCities;

	protected Integer playerID = -1;
	protected String playerName = "";

	protected EnumEmpires allegiance = null;
	protected PopulationHub capital = null;

	public Player() {
		this.playerID = Controller.getNextPlayerID();
		this.playerName =  (String) JOptionPane.showInputDialog(null, "Enter a name for player #" + (Controller.getPlayers().size()+1), "Player name",
				JOptionPane.QUESTION_MESSAGE, Resources.iconII, null,("Player" + (Controller.getPlayers().size()+1)));
		this.allegiance = (EnumEmpires)(JOptionPane.showInputDialog(null, "Choose an Allegiance:", "Allegiance",
				JOptionPane.QUESTION_MESSAGE, Resources.iconII, EnumEmpires.values(), null));
		System.out.println(this.playerName + " is now a member of the " + this.allegiance);
	}

	public String getName() { return this.playerName; }
	public void setName(String newName) { this.playerName = newName; }
	public PopulationHub getCapital () { return this.capital; }
	public void setCapital(PopulationHub newCapital) { this.capital = newCapital; }

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
