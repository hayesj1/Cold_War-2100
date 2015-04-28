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

	public Player(String name) {
		this.playerID = Controller.addPlayer();
		this.playerName = name;
		this.allegiance = (EnumEmpires)(JOptionPane.showInputDialog(null, "Choose an Allegiance:", "Allegiance", JOptionPane.QUESTION_MESSAGE, Resources.iconII, EnumEmpires.values(), null));
		System.out.println(this.playerName + " is now a member of the " + this.allegiance);
	}

	public PopulationHub getCapital () { return this.capital; }
	public void setCapital(PopulationHub newCapital) { this.capital = newCapital; }

	public ArrayList<PopulationHub> getOwnedCities() {
		if (this.ownedCities == null) {
			this.ownedCities = new ArrayList<>();
			for (PopulationHub ph : PopulationHub.getPlayersPopHubs(this)) {
				this.ownedCities.add(ph);
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
