package player;

import controller.Controller;
import map.populationHub.PopulationHub;

import javax.swing.*;

public class Player implements Comparable<Player>{

	protected Integer playerID = -1;
	protected String playerName = "";

	protected EnumEmpires allegiance = null;
	protected PopulationHub capital = null;

	public Player(String name) {
		this.playerID = Controller.addPlayer(name);
		this.playerName = name;
		this.allegiance = (EnumEmpires)(JOptionPane.showInputDialog(null, "Choose an Allegiance:", "Allegiance", JOptionPane.QUESTION_MESSAGE, null, EnumEmpires.values(), null));
		System.out.println(this.playerName + " is now a member of the " + this.allegiance);
	}

	public PopulationHub getCapital () { return this.capital; }
	public void setCapital(PopulationHub newCapital) { this.capital = newCapital; }
	@Override
	public String toString() {
		return ("[ Name: " + playerName + ", ID: " + playerID + " ]");
	}

	@Override
	public int compareTo(Player other) {
		int sortByName = this.playerName.compareTo(other.playerName);
		return (sortByName == 0) ? this.playerID.compareTo(other.playerID) : sortByName;
	}
}
