package player;

import controller.Controller;
import map.populationHub.PopulationHub;

import java.util.ArrayList;
import java.util.TreeMap;

public class Player {

	private static TreeMap<Player, ArrayList<PopulationHub>> cities;
	protected int playerID = -1;
	protected PopulationHub capital = null;

	public Player() {
		this.playerID = Controller.getInstance().addPlayer();
	}

	public static ArrayList<PopulationHub> getPlayersPopHubs (Player p) {
		if (cities == null) {
			cities = new TreeMap<Player, ArrayList<PopulationHub>>();
		}
		return cities.get(p);
	}

	public PopulationHub getCapital () { return this.capital; }
}
